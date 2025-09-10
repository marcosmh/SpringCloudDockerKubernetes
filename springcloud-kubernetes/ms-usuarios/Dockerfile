FROM openjdk:17.0.2

#WORKDIR /app
WORKDIR /app/ms-usuarios

COPY ./pom.xml /app
COPY ./ms-usuarios .

RUN ./mvnw clean package -DskipTests

EXPOSE 8001

ENTRYPOINT ["java","-jar","./target/ms-usuarios-0.0.1-SNAPSHOT.jar"]