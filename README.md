# Plazoleta Service

Backend del reto PowerUp. HU2 permite crear un restaurante asociado a un usuario existente con rol OWNER.

## Requisitos y ejecución local

Java 25, wrapper Gradle incluido y MySQL 8.4 (Docker Compose opcional). Users Service debe estar disponible para crear restaurantes.

1. Copia `.env.example` a `.env` y configura las credenciales de tu instancia.
2. Si utilizas Docker, ejecuta `docker compose up -d` dentro de cada servicio.
3. Desde `usersservice`, ejecuta `.\gradlew.bat bootRun --args='--spring.profiles.active=local'`.
4. Desde `plazoletaservice`, ejecuta el mismo comando.

El perfil local carga el `.env` del directorio de trabajo. MySQL usa normalmente el puerto 3306 para usuarios y 3307 para plazoleta. HTTP usa 8081 y 8082, respectivamente. Hibernate usa `update` solo para desarrollo local; los tests usan una base H2 temporal. En Unix, usa `./gradlew`.

## Contrato HU2

`POST /api/v1/restaurants`

Todos los campos son obligatorios:

```json
{
  "name": "Pizza 123",
  "nit": "900123456",
  "address": "Calle 10 #20-30",
  "phone": "+573001234567",
  "urlLogo": "https://example.com/logo.png",
  "ownerId": "550e8400-e29b-41d4-a716-446655440000"
}
```

El nombre se recorta y no puede ser exclusivamente numérico. NIT admite solo dígitos. Teléfono admite dígitos y un `+` inicial, con máximo 13 caracteres en total. Dirección y logo no pueden estar vacíos. La HU exige obligatoriedad del logo, sin agregar una regla de formato URL no especificada.

- `201`: devuelve los datos del restaurante y su UUID.
- `400`: datos inválidos, UUID mal formado, propietario inexistente o rol distinto de OWNER.
- `503`: no se pudo validar al propietario por error, timeout o respuesta inválida del servicio de usuarios.
- `500`: error inesperado; no se devuelve el detalle interno.

Un rechazo de validación no guarda el restaurante. No se agregan restricciones de NIT único ni de un solo restaurante por propietario porque HU2 no las especifica. La autorización del administrador se incorpora en HU5.

## Arquitectura

Controller → Mapper → CreateRestaurantHandler → IRestaurantServicePort → RestaurantUseCase.

El caso de uso construye y valida el restaurante antes de llamar a `IOwnerValidationPort`; después guarda mediante `IRestaurantPersistencePort`. Feign y JPA quedan en infraestructura. El UUID se genera en dominio y se conserva al persistir.

El cliente consulta `GET /api/v1/users/{id}/owner-validation`. La respuesta contractual es `{"exists":true,"owner":true}`. Un usuario inexistente devuelve `200` con ambos valores en `false`. La URL se configura con `users-service.url`, desde `USER_SERVICE_URL` en el perfil local.

## Verificación y estudio

- `.\gradlew.bat test`: pruebas unitarias y HTTP con H2 y un servidor HTTP controlado para usuarios.
- `.\gradlew.bat build`: pruebas y empaquetado.
- Reporte: `build/reports/tests/test/index.html`.
- Cobertura: `build/reports/jacoco/test/html/index.html`.
- Swagger: http://localhost:8082/swagger-ui/index.html
- Pruebas manuales: `src/main/resources/plazoleta.http`.

Las pruebas de usersservice verifican por separado el contrato HTTP real de validación. H2 no sustituye la comprobación final con ambos servicios y MySQL.

### Verificación local realizada el 21 de septiembre de 2026

Ambos servicios completaron `build`. Con ambos servicios conectados a MySQL 8.4 en Docker, se comprobó creación de propietario (201), validación por Feign y creación de restaurante (201), campos nulos (400), nombre numérico con espacios (400), propietario inexistente (400), UUID mal formado (400) y publicación de OpenAPI (200).

Se conservaron dos registros de prueba identificables:

- Propietario: `70ab4b3b-102f-42f7-af86-4d9814184235`.
- Restaurante: `06a52970-7b19-4d94-98a9-322bbc94c0a6`, nombre `HU2 Smoke 1790007843340`.

El rechazo de usuarios sin rol OWNER y las fallas del servicio remoto también se comprueban en las pruebas automatizadas.

Para estudiar HU2, sigue el caso exitoso desde el controlador hasta JPA; después revisa el rechazo del propietario y la traducción de errores de Feign. Compara cada escenario con las aserciones de `RestaurantUseCaseTest` y `RestaurantHttpIntegrationTest`.
