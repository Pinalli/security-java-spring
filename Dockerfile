FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copia os arquivos do projeto para dentro do container
COPY .mvn/ ./.mvn/
COPY mvnw .
COPY pom.xml .
COPY src ./src
# Tornar o mvnw executável e fazer o build
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests
# Cria uma imagem final para rodar a aplicação
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Define as variáveis de ambiente para conexão com o MySQL
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/vollmed_web?createDatabaseIfNotExist
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=root
# Expondo a porta da aplicação
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]