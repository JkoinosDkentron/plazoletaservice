package com.juanda.powerup.plazoletaservice.infrastructure;

import com.juanda.powerup.plazoletaservice.infrastructure.out.jpa.repository.RestaurantJpaRepository;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:hu2;MODE=MySQL;DB_CLOSE_DELAY=-1",
                "spring.datasource.username=sa",
                "spring.datasource.password=",
                "spring.jpa.hibernate.ddl-auto=create-drop",
                "spring.cloud.openfeign.client.config.users-service.connectTimeout=1000",
                "spring.cloud.openfeign.client.config.users-service.readTimeout=1000"
        })
class RestaurantHttpIntegrationTest {
    private static final AtomicReference<String> OWNER_BODY =
            new AtomicReference<>("{\"exists\":true,\"owner\":true}");
    private static final AtomicInteger OWNER_STATUS = new AtomicInteger(200);
    private static final AtomicInteger OWNER_CALLS = new AtomicInteger();
    private static final AtomicReference<String> OWNER_PATH = new AtomicReference<>();
    private static final HttpServer OWNER_SERVER = startOwnerServer();

    @LocalServerPort private int port;
    @Autowired private RestaurantJpaRepository repository;
    private final HttpClient http = HttpClient.newHttpClient();
    private final UUID ownerId = UUID.randomUUID();

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("users-service.url",
                () -> "http://localhost:" + OWNER_SERVER.getAddress().getPort());
    }

    private static HttpServer startOwnerServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
            server.createContext("/api/v1/users/", exchange -> {
                OWNER_CALLS.incrementAndGet();
                OWNER_PATH.set(exchange.getRequestURI().getPath());
                byte[] body = OWNER_BODY.get().getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(OWNER_STATUS.get(), body.length);
                try (var output = exchange.getResponseBody()) {
                    output.write(body);
                }
            });
            server.start();
            return server;
        } catch (IOException exception) {
            throw new IllegalStateException(exception);
        }
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        OWNER_BODY.set("{\"exists\":true,\"owner\":true}");
        OWNER_STATUS.set(200);
        OWNER_CALLS.set(0);
    }

    @AfterAll
    static void stopServer() {
        OWNER_SERVER.stop(0);
    }

    @Test
    void shouldCreateAndPersistRestaurantThroughHttpAndFeign() throws Exception {
        HttpResponse<String> response = post(body());
        assertEquals(201, response.statusCode(), response.body());
        assertEquals(1, repository.count());
        var saved = repository.findAll().getFirst();
        assertEquals(ownerId, saved.getOwnerId());
        assertEquals("Pizza 123", saved.getName());
        assertEquals("900123456", saved.getNit());
        assertEquals("Calle 10", saved.getAddress());
        assertEquals("+573001234567", saved.getPhone());
        assertEquals("https://example.com/logo.png", saved.getUrlLogo());
        assertTrue(response.body().contains(saved.getId().toString()));
        assertEquals("/api/v1/users/" + ownerId + "/owner-validation", OWNER_PATH.get());
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"exists\":true,\"owner\":false}", "{\"exists\":false,\"owner\":false}"})
    void shouldRejectInvalidOwnerWithoutPersistence(String validation) throws Exception {
        OWNER_BODY.set(validation);
        var response = post(body());
        assertEquals(400, response.statusCode(), response.body());
        assertTrue(response.body().contains("The user is not a valid owner"));
        assertEquals(0, repository.count());
    }

    @ParameterizedTest
    @ValueSource(strings = {"{}", "{\"exists\":true}", "{\"exists\":false,\"owner\":true}", "not-json"})
    void shouldReturnUnavailableForInvalidUpstreamPayload(String payload) throws Exception {
        OWNER_BODY.set(payload);
        var response = post(body());
        assertEquals(503, response.statusCode(), response.body());
        assertEquals(0, repository.count());
    }

    @Test
    void shouldReturnUnavailableForUpstreamServerError() throws Exception {
        OWNER_STATUS.set(500);
        var response = post(body());
        assertEquals(503, response.statusCode(), response.body());
        assertEquals(0, repository.count());
    }

    @ParameterizedTest
    @ValueSource(strings = {"name", "nit", "address", "phone", "urlLogo", "ownerId"})
    void shouldRejectMissingFieldsBeforeCallingUsers(String field) throws Exception {
        String payload = body().replaceAll("\"" + field + "\"\\s*:\\s*\"[^\"]*\"", "\"" + field + "\":null");
        var response = post(payload);
        assertEquals(400, response.statusCode(), response.body());
        assertEquals(0, OWNER_CALLS.get());
        assertEquals(0, repository.count());
    }

    @Test
    void shouldRejectNumericNameWithSpaces() throws Exception {
        var response = post(body().replace("Pizza 123", " 123 "));
        assertEquals(400, response.statusCode(), response.body());
        assertEquals(0, OWNER_CALLS.get());
        assertEquals(0, repository.count());
    }

    @ParameterizedTest
    @CsvSource({"900123456,900ABC", "+573001234567,+57300123456789", "+573001234567,300ABC"})
    void shouldRejectInvalidNitOrPhoneWithoutCallingUsers(String original, String invalid) throws Exception {
        var response = post(body().replace(original, invalid));
        assertEquals(400, response.statusCode(), response.body());
        assertEquals(0, OWNER_CALLS.get());
        assertEquals(0, repository.count());
    }

    @Test
    void shouldAcceptThirteenDigitPhoneWithoutPlus() throws Exception {
        var response = post(body().replace("+573001234567", "1234567890123"));
        assertEquals(201, response.statusCode(), response.body());
        assertEquals("1234567890123", repository.findAll().getFirst().getPhone());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "{", "null", "{\"ownerId\":\"not-a-uuid\"}"})
    void shouldRejectMalformedRequests(String payload) throws Exception {
        var response = post(payload);
        assertEquals(400, response.statusCode(), response.body());
        assertEquals(0, OWNER_CALLS.get());
        assertEquals(0, repository.count());
    }

    @Test
    void shouldPublishRestaurantContractInOpenApi() throws Exception {
        var response = http.send(HttpRequest.newBuilder(
                URI.create("http://localhost:" + port + "/v3/api-docs")).GET().build(),
                HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("/api/v1/restaurants"));
        assertTrue(response.body().contains("\"503\""));
        assertTrue(response.body().contains("CreateRestaurantRequest"));
    }

    private HttpResponse<String> post(String body) throws Exception {
        return http.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/v1/restaurants"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body)).build(),
                HttpResponse.BodyHandlers.ofString());
    }

    private String body() {
        return """
                {"name":"Pizza 123","nit":"900123456","address":"Calle 10",
                 "phone":"+573001234567","urlLogo":"https://example.com/logo.png","ownerId":"%s"}
                """.formatted(ownerId);
    }
}
