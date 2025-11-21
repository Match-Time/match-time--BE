FROM bellsoft/liberica-openjdk-debian:17 AS builder
WORKDIR /workspace

# Gradle 설정 복사 (의존성 캐시용)
COPY gradlew build.gradle settings.gradle /workspace/
COPY gradle /workspace/gradle
RUN apt-get update && apt-get install -y dos2unix && dos2unix gradlew
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

# 소스 복사 후 빌드
COPY src /workspace/src
RUN ./gradlew bootJar --no-daemon


# docker build -t matchtime-java17 .
# docker run -d -p 8080:8080 --name matchtime matchtime-java17
