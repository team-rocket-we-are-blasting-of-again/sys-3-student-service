FROM openjdk:17-bullseye as builder
WORKDIR /build
COPY . /build/
RUN ./mvnw package -Dmaven.test.skip=true

FROM openjdk:17-bullseye
WORKDIR /app
COPY --from=builder /build/service/target/*.jar /app/application.jar

ENV STUDENTS_SERVER_PORT 8888
ENV STUDENTS_POSTGRES_HOST postgres
ENV STUDENTS_POSTGRES_PORT 5432
ENV STUDENTS_POSTGRES_DB students
ENV STUDENTS_POSTGRES_USERNAME username
ENV STUDENTS_POSTGRES_PASSWORD password
ENV STUDENTS_KAFKA_HOST kafka
ENV STUDENTS_KAFKA_PORT 9092

CMD [ "java", "-jar", "/app/application.jar" ]
