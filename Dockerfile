# Etapa de build
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copia apenas arquivos essenciais para otimizar cache
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Baixar dependências antes de copiar código-fonte
RUN ./mvnw -B dependency:go-offline

# Agora, copia o código-fonte
COPY src ./src

# Compila a aplicação
RUN ./mvnw -B clean package -DskipTests

# Etapa final - apenas o necessário para rodar a aplicação
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia apenas o JAR gerado
COPY --from=build /app/target/*.jar app.jar

# Definir variáveis de ambiente (evite definir senhas diretamente aqui)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/vollmed_web?createDatabaseIfNotExist
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=root

# Expor a porta
EXPOSE 8080

# Comando de execução
CMD ["java", "-jar", "app.jar"]
