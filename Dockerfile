FROM openjdk:17-bullseye as builder
WORKDIR /build
COPY . /build/
RUN ./mvnw package -Dmaven.test.skip=true

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /build/service/target/*.jar /app/application.jar
CMD [ "java", "-jar", "/app/application.jar" ]
