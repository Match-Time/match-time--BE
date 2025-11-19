FROM bellsoft/liberica-openjdk-debian:17 AS builder
WORKDIR /workspace

COPY gradlew build.gradle settings.gradle /workspace/
COPY gradle /workspace/gradle
COPY src /workspace/src

RUN apt-get update && apt-get install -y dos2unix && dos2unix gradlew
RUN chmod +x gradlew

RUN ./gradlew clean bootJar --no-daemon


FROM bellsoft/liberica-openjdk-debian:17

COPY --from=builder /workspace/build/libs/*.jar /app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]

# docker build -t matchtime-java17 .
# docker run -d -p 8080:8080 --name matchtime matchtime-java17