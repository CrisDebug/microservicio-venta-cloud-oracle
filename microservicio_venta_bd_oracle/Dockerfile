# Imagen base con Java 17 (Debian-based para mayor compatibilidad con Oracle)
FROM eclipse-temurin:17-jdk

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el JAR y la carpeta Wallet
COPY target/microservicios-venta-bd-oracle-0.0.1-SNAPSHOT.jar app.jar
COPY Wallet_BDY2201 ./Wallet_BDY2201

# Ajustamos permisos de la carpeta Wallet
RUN chmod -R 755 /app/Wallet_BDY2201

# Variable de entorno para que Oracle JDBC encuentre el Wallet
ENV TNS_ADMIN=/app/Wallet_BDY2201

# Exponemos el puerto del microservicio
EXPOSE 9090

# Comando para ejecutar el microservicio
ENTRYPOINT ["java", "-jar", "app.jar"]

