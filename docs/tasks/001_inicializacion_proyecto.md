# TASK — Inicialización Proyecto Spring Boot

## Objetivo

Crear el proyecto base `integral-ddjj-validation-service` utilizando Spring Boot 3.2.2 y Java 17.

---

# Configuración del Proyecto

## Project

```text id="6mwz1v"
Maven
```

## Language

```text id="el3r9l"
Java
```

## Spring Boot

```text id="xj7b35"
3.2.2
```

## Group

```text id="9e8y5j"
ar.com.swissmedical.integral
```

## Artifact

```text id="djjh2w"
integral-ddjj-validation-service
```

## Name

```text id="ynx4l6"
integral-ddjj-validation-service
```

## Package Name

```text id="6w4j7f"
ar.com.swissmedical.integral.ddjj
```

## Packaging

```text id="y4d5qk"
jar
```

## Java

```text id="x4r9vn"
17
```

---

# Dependencias Iniciales

Agregar únicamente:

* Spring Web
* Lombok
* Spring Boot DevTools

---

# Implementación Inicial

Crear un controller simple para validar que la aplicación levanta correctamente.

## Endpoint

```text id="h0pj7n"
GET /ping
```

## Respuesta Esperada

```json id="7e8k5m"
{
  "message": "pong"
}
```

---

# Resultado Esperado

El proyecto debe:

* Compilar correctamente
* Levantar localmente
* Exponer endpoint `/ping`
* Responder correctamente desde navegador o Postman

---

# Validación

## Levantar aplicación

```bash id="1n9v5f"
mvn spring-boot:run
```

o desde IntelliJ/VSCode:

```text id="7q4w8k"
Run IntegralDdjjValidationServiceApplication
```

---

## Probar endpoint

```text id="y8m3vf"
http://localhost:8080/ping
```

---

# Resultado Esperado en Consola

```text id="h2k9zf"
Started IntegralDdjjValidationServiceApplication
```
