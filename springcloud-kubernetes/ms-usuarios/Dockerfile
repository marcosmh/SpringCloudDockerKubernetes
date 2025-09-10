FROM openjdk:17.0.2

WORKDIR /app

COPY ./target/ms-usuarios-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

ENTRYPOINT ["java","-jar","ms-usuarios-0.0.1-SNAPSHOT.jar"]