# 📊 Definición de estructura de las APIs

Este documento establece la estructura de cada API, definiendo los roles y responsabilidades de cada componente de los microservicios de APIs RESTful utilizando una arquitectura de capas.

---

## 🧽 Capas

| Capa | Propósito | Entrada | Salida |
| ----- | ------- | ----- | ------ |
| **Controller** | Endpoints HTTP, validación de requests, HATEOAS | DTOs        | DTOs con HATEOAS   |
| **DTO**        | Data Transfer Object (contrato de API)           | -           | -                   |
| **Assembler**  | Realiza conversiones entre DTOs y Models         | DTO ⇄ Model | DTO ⇄ Model + Links |
| **Service**    | Realiza la lógica de negocio               | Models      | Models              |
| **Model**      | JPA Entity, representación de dominio             | -           | -                   |
| **Repository** | Acceso a Database con Spring Data JPA / ORM         | Models      | Models              |

---

## 📉 Responsabilidades de los Componentes

### 📌 Controller

- Maneja las solicitudes (request / response) HTTP.
- Define los endpoints de la API.
- Valida los body de las requests mediante annotations en los DTOs (`@Valid`).
- Delega la acción de negocio a la capa de Servicio.
- Utiliza el Assembler para convertir DTO a Models y viceversa.
- Incluye links HATEOAS en las respuestas (mediante el Assembler).

### 📌 DTO (Data Transfer Object)

- Representa un recorte del Model de la entidad que se expone en la API.
- Se utiliza para desacoplar el contrato de la API con la estructura de la entidad en la Base de Datos / ORM.
- Incluye las annotations de documentación de Swagger/OpenAPI.
- Incluye las annotations de validación (`@NotNull`, `@Email`, `@Positive`, etc).
- Libera al Model de la responsabilidad de contener otras annotations que las empleadas para JPA / ORM.

### 📌 Assembler

- Realiza conversiones entre `DTO` y `Model`.
- Extiende la clase `RepresentationModelAssemblerSupport` para agregar los links HATEOAS.
- Ayuda a mantener el código del Controller y del Service limpio.
- Aplica formato a input / output para normalizar el contenido de las APIs.

### 📌 Service

- Implementa la lógica de negocio.
- Intercambia información con los repositorios
- Opera con modelos de dominio (no DTOs).
- Puede funcionar como publisher de eventos de RabbitMQ.

### 📌 Model (Entity)

- Entidad de JPA que representa una tabla en la base de datos.
- Usada internamente en los servicios y repositorios.
- No es expuesta directamente a través de la API.
- La duplicidad DTO / Model permite expandir el modelo de la base de datos de forma independiente a la definición de las respuestas del Controller y viceversa.

### 📌 Repository

- Entiende las interfaces `JpaRepository` o `CrudRepository`.
- Maneja la persistencia de datos.
- No posee lógica de negocio, sólo queries a la Base de Datos.

---

## ✅ Ejemplo de flujo de una request (POST - Crear un recurso)

1. El **Controller** recibe una solicitud `POST /entity` con `@Valid @BodyRequest EntityDTO`.
2. Se valida el request body mediante las anotaciones del **DTO**.
3. El **Assembler** convierte el **DTO** a **Model** `EntityDTO → Entity`.
4. El **Service** crea la entidad y la persiste mediante el **Repository**.
5. Retorna el **Model** al **Controller**.
6. **Assembler** mapea el **DTO** a **Model** agregando HATEOAS `Entity → EntityDTO + links`.
7. **Controller** retorna la response.
