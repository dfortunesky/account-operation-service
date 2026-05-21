# TASK 2 — Implementación Arquitectura Hexagonal Base

## Objetivo

Implementar la estructura base de arquitectura hexagonal del proyecto `integral-ddjj-validation-service` respetando el flujo:

```text id="7m2q8v"
Controller -> Port IN -> UseCase -> Port OUT -> Adapter
```

El objetivo es dejar preparada la base arquitectónica del servicio manteniendo separación de responsabilidades y desacoplamiento entre negocio e infraestructura.

---

# Arquitectura Definida

## Estilo Arquitectónico

```text id="8v5d1x"
Hexagonal Architecture (Ports & Adapters)
```

## Principios

* La lógica de negocio debe vivir únicamente en los UseCases.
* Los Controllers no deben contener lógica de negocio.
* Los UseCases no deben depender de frameworks.
* La infraestructura debe implementarse mediante adapters.
* Los UseCases deben depender de interfaces (Ports).

---

# Estructura de Carpetas

```text id="z4p7kn"
src/main/java/ar/com/swissmedical/integral/ddjj
│
├── adapter
│   ├── controller
│   │   └── PingControllerAdapter.java
│   │
│   └── mybatis
│       ├── mappers
│       └── model
│
├── application
│   ├── port
│   │   ├── in
│   │   │   └── PingPortIn.java
│   │   │
│   │   └── out
│   │       └── PingRepositoryPortOut.java
│   │
│   ├── usecase
│   │   └── PingUseCase.java
│   │
│   └── validator
│
├── config
│
└── domain
    ├── model
    ├── exception
    └── helper
```

---

# Implementación Requerida

## Controller

Crear:

```text id="9f6x1r"
PingControllerAdapter
```

Responsabilidad:

* exponer endpoint REST
* consumir Port IN
* devolver response HTTP

No debe contener lógica de negocio.

---

## Port IN

Crear interfaz:

```text id="4m8q2w"
PingPortIn
```

Responsabilidad:

* definir contrato de entrada de la aplicación

---

## UseCase

Crear:

```text id="2x7k5p"
PingUseCase
```

Responsabilidad:

* implementar Port IN
* contener lógica funcional del caso de uso

---

## Port OUT

Crear interfaz:

```text id="8w3n6v"
PingRepositoryPortOut
```

Responsabilidad:

* definir contratos de acceso a infraestructura

No implementar lógica real todavía.

---

# Endpoint Esperado

## GET

```text id="7n2v5x"
/ping
```

## Response

```json id="4k1m8z"
{
  "message": "ping"
}
```

---

# Restricciones

## NO implementar todavía

* MyBatis
* conexión Sybase
* Swagger
* Docker
* Kubernetes
* interceptors
* traceId
* handlers complejos
* lógica de persistencia

La tarea solamente debe dejar preparada la arquitectura base.

---

# Resultado Esperado

La aplicación debe:

* compilar correctamente
* levantar localmente
* respetar flujo hexagonal
* exponer `/ping`
* mantener separación correcta de capas

---

# Validación

Ejecutar:

```bash id="8r5k1m"
mvn clean install
```

Luego:

```bash id="5x7p2n"
mvn spring-boot:run
```

Validar:

```text id="3q8v1w"
http://localhost:8080/ping
```

---

# Resultado Esperado en Consola

```text id="1m4x8k"
Started IntegralDdjjValidationServiceApplication
```
