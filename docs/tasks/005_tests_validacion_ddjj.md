# Task: Implementación de Tests Unitarios — Endpoint DDJJ Validation

## Objetivo

Implementar la batería completa de tests unitarios para el endpoint:

```http
GET /integral/v1/validaciones/ddjj
```

El endpoint consulta validaciones de DDJJ y Auditoría Médica utilizando el stored procedure:

```sql
get_ddjj_auditoria
```

La implementación debe cubrir controller, service, validaciones, manejo de errores y mocking de integración con base de datos.

---

# Contexto técnico

## Stack

- Java 17
- Spring Boot 3.2
- Maven
- Arquitectura Hexagonal
- MyBatis
- Sybase ASE
- JUnit 5
- Mockito
- MockMvc

---

# Endpoint actual

## Request

### Query Params

| Parámetro | Tipo | Requerido |
|---|---|---|
| idCompania | Integer | Sí |
| idCuenta | Integer | Sí |
| idMop | String | No |


---

## Response esperada

```json
{
  "requiereDDJJ": true/false,
  "derivaAuditoriaMedica": true/false
}
```

---

# Objetivos de testing

Se deben implementar tests unitarios para:

- Controller
- Service
- Validaciones
- Manejo de errores
- Mapping de respuestas
- Conversión de datos provenientes de Sybase

---

# Casos a cubrir

## Controller Tests

### Happy Path

Validar:

- HTTP 200
- estructura JSON correcta
- content-type correcto
- serialización response DTO

---

## Validaciones

### Debe fallar cuando:

#### idCompania es null

Esperado:

```http
400 BAD REQUEST
```

---

#### idCuenta e idMop son null

Esperado:

```http
400 BAD REQUEST
```

---

#### parámetros inválidos

Casos:

- strings vacíos
- formatos inválidos
- tipos incorrectos

---

# Service Tests

## Caso exitoso

Mockear repository/mapper para devolver:

```java
ddjj = "S"
auditoria = "S"
```

Esperado:

```json
{
  "requiereDDJJ": true,
  "derivaAuditoriaMedica": true
}
```

---

## Casos funcionales

### DDJJ = S

```json
{
  "requiereDDJJ": true
}
```

---

### DDJJ = N

```json
{
  "requiereDDJJ": false
}
```

---

### Auditoría = S

```json
{
  "derivaAuditoriaMedica": true
}
```

---

### Auditoría = N

```json
{
  "derivaAuditoriaMedica": false
}
```

---

# Casos borde

Validar:

- valores NULL provenientes de Sybase
- response vacía
- modo operación inexistente
- fallback por cuenta
- trimming de `CHAR(6)` en `idModoOperacion`

---

# Mocking requerido

Se debe mockear:

- Mapper MyBatis
- Repository Adapter
- ejecución del SP

NO debe existir conexión real a Sybase durante los tests unitarios.

---

# Librerías esperadas

## Dependencias

- spring-boot-starter-test
- junit-jupiter
- mockito-core
- mockito-junit-jupiter
- assertj-core

---

# Cobertura mínima

Objetivo:

```text
>= 80%
```

sobre:

- controller
- service
- adapters

---

# Estructura sugerida

```text
src/test/java
 ├── controller
 │    └── DdjjValidationControllerTest
 │
 ├── service
 │    └── DdjjValidationServiceTest
 │
 ├── mapper
 │    └── DdjjValidationMapperTest
 │
 └── fixtures
      └── DdjjValidationFixture
```

---

# Requisitos técnicos

## Controller

Usar:

```java
@WebMvcTest
```

y:

```java
MockMvc
```

---

## Service

Usar:

```java
@ExtendWith(MockitoExtension.class)
```

---

# Criterios de aceptación

- [ ] Tests unitarios implementados
- [ ] Validaciones cubiertas
- [ ] Manejo de errores cubierto
- [ ] Mocking correctamente implementado
- [ ] Sin dependencia real a DB
- [ ] `mvn test` ejecutando correctamente
- [ ] Cobertura mínima alcanzada

---

# Comando esperado

```bash
mvn test
```

