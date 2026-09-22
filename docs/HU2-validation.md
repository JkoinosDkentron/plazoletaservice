# Validacion HU2: crear restaurante

## Criterios de aceptacion

- Campos obligatorios: `RestaurantHttpIntegrationTest` rechaza cada campo ausente sin guardar ni consultar usuarios.
- Nombre: pruebas de dominio y HTTP rechazan nombres solo numericos; `RestaurantBoundaryTest` cubre espacios Unicode alrededor del numero.
- NIT: pruebas de dominio y HTTP rechazan caracteres no numericos.
- Telefono: pruebas de dominio y HTTP cubren formato, prefijo opcional y limite de 13 caracteres.
- Propietario: pruebas del caso de uso y HTTP rechazan usuarios inexistentes o con otro rol.
- Persistencia: la prueba HTTP valida todos los campos guardados y el identificador generado.

## Arquitectura y contrato externo

Flujo: controller -> mapper -> command -> handler -> puerto de dominio -> caso de uso -> puertos de salida.
`HexagonalArchitectureTest` comprueba independencia del dominio, direccion de dependencias, handlers sin Spring y ausencia de ciclos entre capas.
La integracion HTTP usa H2 y un servidor de usuarios simulado. Cubre respuestas incompletas, JSON invalido y errores externos como HTTP 503.
La autorizacion por autenticacion corresponde a HU5; HU2 comprueba existencia y rol del propietario asignado.

## Ejecucion

Desde `plazoletaservice`, ejecutar `.\gradlew.bat build` con Java 25.
Consultar `build/reports/tests/test/index.html` y `build/reports/jacoco/test/html/index.html`.
No existe un umbral automatico de cobertura configurado.

SonarLint/SonarQube for IDE se ejecuta manualmente en IntelliJ; revisar sus hallazgos por separado antes de dar ese criterio por aprobado.

## Evidencia de estabilizacion

Build completo: 63 pruebas, 0 fallos, 0 errores y 0 omitidas.
Prueba local del 21 de septiembre de 2026 con ambos servicios y MySQL: propietario HTTP 201, validacion true/true, restaurante HTTP 201, JSON mal formado HTTP 400 y OpenAPI HTTP 200.
Registros de prueba: propietario `fca5668b-618a-4110-9b1b-ec68e9a78b72`; restaurante `b366cf53-8221-4a77-b6f5-c89861b9eae4` (`HU2 Smoke 1790039802617`).
