# 🐾 Microservicio Venta CRUD — Tienda de Artículos para Mascotas

Este microservicio gestiona las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para las ventas de artículos en una tienda de mascotas.  
Está desarrollado con Java y Spring Boot, y utiliza una base de datos Oracle configurada como contenedor Docker local (XE21c).

Incluye soporte para **HATEOAS** en las respuestas REST y se validará mediante **pruebas de integración con JUnit** y **Postman**.

Repositorio: https://github.com/CrisDebug/microservicio-venta-bd-oracle.git

---

## 🧰 Tecnologías Utilizadas

- **Lenguaje:** Java 17  
- **Framework:** Spring Boot 3.1.5  
- **Arquitectura:** API RESTful con soporte HATEOAS  
- **Persistencia:** Spring Data JPA  
- **Base de Datos:** Oracle (JDBC driver `ojdbc8`)  
- **Validación:** Jakarta Validation API  
- **Documentación de la API:** Swagger / OpenAPI (Springdoc 2.2.0)  
- **Reducción de código repetido:** Lombok  
- **Testing:** JUnit + Spring Boot Test (pruebas de integración)  
- **Hot Reload (solo desarrollo):** Spring Boot DevTools  
- **Gestión de dependencias / compilación:** Maven

---

## 🚀 Configuración del proyecto

La aplicación está configurada para ejecutarse en el puerto **9090** (en lugar del puerto por defecto 8080).

La conexión a la base de datos Oracle se realiza con las siguientes propiedades definidas en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
spring.datasource.username=UsuarioTiendaMascotas
spring.datasource.password=**************   # (oculto por seguridad)
spring.jpa.database-platform=org.hibernate.dialect.Oracle12cDialect
spring.jpa.hibernate.ddl-auto=create

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=9090

# Compilación con Maven Wrapper

Puedes compilar y ejecutar el proyecto con Maven Wrapper:

```bash
# Compilar el proyecto
./mvnw clean install

# Ejecutar la aplicación
./mvnw spring-boot:run
