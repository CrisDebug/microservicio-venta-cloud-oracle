# 🐾 Microservicio Venta CRUD — Tienda de Artículos para Mascotas

Este microservicio gestiona las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para las ventas de artículos en una tienda de mascotas.  
Está desarrollado con **Java y Spring Boot**, y utiliza **Oracle Autonomous Database en la nube**, accediendo mediante **Wallet** desde un contenedor Docker.

Incluye soporte para **HATEOAS** en las respuestas REST y se valida mediante **pruebas de integración con JUnit** y **Postman**.

Repositorio: [https://github.com/CrisDebug/microservicio-venta-cloud-oracle.git](https://github.com/CrisDebug/microservicio-venta-cloud-oracle.git)

---

## 🧰 Tecnologías Utilizadas

- **Lenguaje:** Java 17  
- **Framework:** Spring Boot 3.1.5  
- **Arquitectura:** API RESTful con soporte HATEOAS  
- **Persistencia:** Spring Data JPA  
- **Base de Datos:** Oracle Autonomous Database (Cloud) con **Wallet**  
- **Driver JDBC:** ojdbc11  
- **Validación:** Jakarta Validation API  
- **Documentación de la API:** Swagger / OpenAPI (Springdoc 2.2.0)  
- **Reducción de código repetido:** Lombok  
- **Testing:** JUnit + Spring Boot Test (pruebas unitarias e integración)  
- **Contenerización:** Docker + Docker Compose  
- **Gestión de dependencias / compilación:** Maven

---

## 🚀 Configuración del proyecto

La aplicación está configurada para ejecutarse en el puerto **9090** (en lugar del puerto por defecto 8080).

La conexión a la base de datos Oracle se realiza usando **Wallet**, configurando la variable de entorno `TNS_ADMIN` dentro del contenedor:

```bash
export TNS_ADMIN=/app/Wallet_BDY2201

spring.datasource.url=jdbc:oracle:thin:@bdy2201_high
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.jpa.database-platform=org.hibernate.dialect.Oracle12cDialect
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=9090
