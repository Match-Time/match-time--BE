# =========================
# Builder 단계
# =========================
FROM bellsoft/liberica-openjdk-debian:17 AS builder

# 작업 디렉토리
WORKDIR /workspace

# Gradle Wrapper 및 설정 복사
COPY gradlew build.gradle settings.gradle /workspace/
COPY gradle /workspace/gradle

RUN apt-get update && apt-get install -y dos2unix && dos2unix gradlew
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

COPY src /workspace/src

# 소스 코드 복사
COPY src /workspace/src

# Gradle 빌드 (dependencies 단계 제거, bootJar만 실행)
# --no-daemon 옵션으로 Gradle 데몬 사용하지 않음
RUN ./gradlew clean bootJar --no-daemon

# =========================
# 런타임 단계
# =========================
FROM bellsoft/liberica-openjdk-debian:17

# 빌드 산출물(JAR) 복사
COPY --from=builder /workspace/build/libs/*.jar /app.jar

# 포트 설정
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]



# docker build -t matchtime-java17 .
# docker run -d -p 8080:8080 --name matchtime matchtime-java17
