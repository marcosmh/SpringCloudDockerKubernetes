FROM openjdk:18-jdk-alpine as builder

ARG MS_NAME=ms-usuarios

WORKDIR /app/$MS_NAME

COPY ./pom.xml /app
COPY ./$MS_NAME/.mvn ./.mvn
COPY ./$MS_NAME/mvnw .
COPY ./$MS_NAME/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./$MS_NAME/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:18-jdk-alpine

WORKDIR /app

RUN mkdir ./logs


COPY --from=builder /app/ms-usuarios/target/ms-usuarios-0.0.1-SNAPSHOT.jar .

ENV PORT 8001
EXPOSE $PORT

#ENTRYPOINT ["java","-jar","ms-usuarios-0.0.1-SNAPSHOT.jar"]

CMD ["java","-jar","ms-usuarios-0.0.1-SNAPSHOT.jar"]