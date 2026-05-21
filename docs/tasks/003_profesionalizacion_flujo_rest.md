# TASK 3 — Profesionalización Flujo REST Base

## Objetivo

Profesionalizar el flujo HTTP del servicio `integral-ddjj-validation-service` implementando DTOs, responses tipadas y manejo básico global de errores respetando la arquitectura hexagonal definida.

La tarea debe consolidar el flujo:

```text id="m4v8x2"
Controller -> Port IN -> UseCase -> Response DTO
```

---

# Objetivos Técnicos

* Separar modelos HTTP del dominio
* Evitar responses primitivas (`String`)
* Estandarizar respuestas REST
* Preparar el proyecto para futuros endpoints
* Incorporar manejo global de excepciones

---

# Implementaciones Requeridas

---

# 1. Crear estructura DTOs

Crear:

```text id="r2x7mq"
application/dto/request
```

y:

```text id="w8n4kp"
application/dto/response
```

---

# 2. Crear Response DTO

Implementar:

```text id="y5m2vn"
PingResponseDto
```

## Campos

```java id="z7k1qx"
private String message;
```

---

# 3. Modificar UseCase

Actualizar `PingUseCase` para devolver:

```java id="k4x9pn"
PingResponseDto
```

en lugar de:

```java id="v1m7qr"
String
```

---

# 4. Modificar Port IN

Actualizar:

```text id="x8q4zn"
PingPortIn
```

para trabajar con DTOs tipados.

---

# 5. Modificar Controller

Actualizar:

```text id="t3m8wv"
PingControllerAdapter
```

para:

* devolver `ResponseEntity`
* devolver DTO tipado
* responder HTTP 200

## Response Esperada

```json id="n9x4pk"
{
  "message": "ping"
}
```

---

# 6. Implementar Manejo Global de Errores

Crear package:

```text id="q7m2vx"
config/exception
```

---

# 7. Crear GlobalExceptionHandler

Implementar:

```text id="w1x8mq"
GlobalExceptionHandler
```

utilizando:

```java id="m8v4pk"
@ControllerAdvice
```

Debe capturar:

* Exception genérica

---

# 8. Crear ErrorResponse DTO

Implementar:

```text id="d4q7xn"
ErrorResponseDto
```

## Campos mínimos

```java id="t8x5mk"
private String message;
private LocalDateTime timestamp;
```

---

# Restricciones

## NO implementar todavía

* MyBatis
* Sybase
* Swagger
* Docker
* Kubernetes
* Logging avanzado
* TraceId
* Interceptors
* Seguridad

La tarea debe enfocarse únicamente en consolidar el flujo REST y la estructura profesional básica.

---

# Resultado Esperado

La aplicación debe:

* compilar correctamente
* mantener arquitectura hexagonal
* devolver responses JSON tipadas
* manejar errores globalmente
* exponer `/ping`

---

# Endpoint Esperado

## GET

```text id="k5m1vx"
/ping
```

## Response HTTP 200

```json id="b2n8qp"
{
  "message": "pong"
}
```

---

# Validación

Ejecutar:

```bash id="p7x4mv"
mvn clean install
```

Luego:

```bash id="v3m9xq"
mvn spring-boot:run
```

Validar:

```text id="f8q2wn"
http://localhost:8080/ping
```

---

# Resultado Esperado en Consola

```text id="z5m8xk"
Started IntegralDdjjValidationServiceApplication
```
