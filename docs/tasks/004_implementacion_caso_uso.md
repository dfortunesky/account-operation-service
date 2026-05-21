# TASK 4 — Implementar Flujo Real DDJJ usando MyBatis + Sybase QA

## Objetivo

Implementar el flujo funcional real del microservicio `integral-ddjj-validation-service` integrando:

* Spring Boot
* Arquitectura Hexagonal
* MyBatis
* Sybase ASE QA
* Stored Procedure existente en Integral

El servicio debe centralizar la validación de:

* Declaración Jurada (DDJJ)s
* Derivación a Auditoría Médica

reutilizando lógica existente en Sybase ASE mediante Stored Procedure.

---

# Endpoint

## GET

```http id="j7x4mq"
/integral/v1/validaciones/ddjj
```

---

# Método HTTP

| Método | Uso                                         |
| ------ | ------------------------------------------- |
| GET    | Consulta de atributos DDJJ/Auditoría Médica |

---

# Query Parameters

| Parámetro  | Tipo   | Obligatorio | Descripción            |
| ---------- | ------ | ----------- | ---------------------- |
| idCuenta   | String | Sí          | Cuenta corporativa     |
| idCompania | String | Sí          | Identificador compañía |
| idMop      | String | No          | MOP/Subcuenta          |

---

# Contrato Request

## Query Params

```http id="m4v8xq"
GET /integral/v1/validaciones/ddjj?idCuenta=24539&idCompania=1&idMop=ABC123
```

---

# Contrato Response

## HTTP 200

```json id="z8m2vk"
{
  "requiereDDJJ": true,
  "derivaAuditoriaMedica": false
}
```

---

# Campos Response

| Campo                 | Tipo    | Descripción                                      |
| --------------------- | ------- | ------------------------------------------------ |
| requiereDDJJ          | Boolean | Indica si corresponde solicitar DDJJ             |
| derivaAuditoriaMedica | Boolean | Indica si corresponde derivar a Auditoría Médica |

---

# Regla Funcional Principal

La lógica funcional debe respetar:

```text id="x2m7vp"
1. Buscar configuración específica en MOP/Subcuenta
2. Si no existe:
      heredar configuración de Cuenta
```

El MOP funciona como excepción/override de la Cuenta.

---

# Fuente de Verdad

## Base

```text id="w5m9qx"
Sybase ASE - COM_SMG
```

## Stored Procedure

```sql id="f8x2mk"
integral.dbo.get_ddjj_auditoria
```

---

# Dependencias Maven

Agregar/configurar:

```xml id="r3m8vq"
mybatis-spring-boot-starter
jconn4
springdoc-openapi
spring-boot-starter-actuator
```

---

# Configuración Sybase QA

## Host

```text id="t7m2xn"
ASEDBQA
```

## Puerto

```text id="q8x4vp"
4100
```

## Database

```text id="v1m7qk"
Integral
```

## Driver

```text id="z4x8mn"
com.sybase.jdbc4.jdbc.SybDriver
```

---

# Arquitectura Obligatoria

El flujo debe respetar:

```text id="b7m2qx"
Controller -> Port IN -> UseCase -> Port OUT -> MyBatis Adapter
```

---

# Implementaciones Requeridas

# 1. Request DTO

Crear:

```text id="n5m8vk"
DdjjValidationRequestDto
```

## Campos

```java id="p2x7mq"
private String idCuenta;
private String idCompania;
private String idMop;
```

---

# 2. Response DTO

Crear:

```text id="x7m4vp"
DdjjValidationResponseDto
```

## Campos

```java id="k8x2mn"
private Boolean requiereDDJJ;
private Boolean derivaAuditoriaMedica;
```

---

# 3. Controller

Crear:

```text id="m1v8qx"
DdjjValidationControllerAdapter
```

## Responsabilidades

* recibir request HTTP
* validar parámetros
* invocar Port IN
* devolver response JSON

---

# 4. Port IN

Crear:

```text id="j2m7vk"
DdjjValidationPortIn
```

---

# 5. UseCase

Crear:

```text id="q4x8mp"
DdjjValidationUseCase
```

## Responsabilidades

* implementar lógica funcional
* orquestar flujo
* consumir Port OUT
* mapear resultado funcional

No debe contener lógica SQL.

---

# 6. Port OUT

Crear:

```text id="w8m1qn"
DdjjValidationRepositoryPortOut
```

---

# 7. MyBatis Adapter

Crear:

```text id="d5x7mk"
DdjjValidationMybatisAdapter
```

## Responsabilidades

* implementar Port OUT
* ejecutar SP Sybase
* mapear resultados

---

# 8. Mapper MyBatis

Crear:

```text id="r7m2vx"
DdjjValidationMapper
```

---

# 9. XML Mapper

Crear:

```text id="z2x8mq"
resources/mybatis/mappers/DdjjValidationMapper.xml
```

Implementar llamada al Stored Procedure:

```sql id="x9m4vp"
execute integral.dbo.get_ddjj_auditoria
```

---

# 10. Modelo MyBatis

Crear:

```text id="f4m7xk"
adapter/mybatis/model/DdjjValidationModel
```

Utilizado para:

* parámetros del SP
* mapeo de resultados MyBatis

---

# 11. Configuración MyBatis

Crear:

```text id="n8x2vq"
MyBatisConfig
```

## Configurar

* datasource Sybase
* SqlSessionFactory
* mapper locations
* transaction manager

---

# 12. Configuración application-local.yml

Agregar:

```yaml id="m3v8qk"
spring:
  datasource:
    url:
    username:
    password:
    driver-class-name: com.sybase.jdbc4.jdbc.SybDriver
```

---

# 13. Swagger/OpenAPI

Habilitar Swagger UI.

## Endpoint esperado

```text id="k7m4xn"
/swagger-ui/index.html
```

---

# 14. Validaciones

Validar:

* idCuenta obligatorio
* idCompania obligatorio

Responder HTTP 400 si faltan parámetros.

---

# 15. Manejo Global de Errores

Extender:

```text id="v8x1mq"
GlobalExceptionHandler
```

para manejar:

* errores MyBatis
* errores Sybase
* parámetros inválidos
* excepciones genéricas

---

# Restricciones

## NO implementar todavía

* seguridad JWT
* API Manager
* Kubernetes
* Jenkins pipelines
* tracing distribuido
* observabilidad avanzada

---

# Resultado Esperado

La aplicación debe:

* levantar correctamente
* conectarse a Sybase QA
* ejecutar el SP real
* devolver JSON tipado
* respetar arquitectura hexagonal
* exponer Swagger
* manejar errores correctamente

---

# Validación

## Levantar aplicación

```bash id="q2m7vx"
.\mvnw.cmd spring-boot:run --spring.profiles.active=local
```

---

# Validar Swagger

```text id="r5x8mn"
http://localhost:8080/swagger-ui/index.html
```

---

# Validar Endpoint

```http id="t1m4vq"
GET /integral/v1/validaciones/ddjj?idCuenta=24539&idCompania=1
```

---

# Resultado Esperado

## HTTP 200

```json id="w7x2mk"
{
  "requiereDDJJ": true,
  "derivaAuditoriaMedica": false
}
```
