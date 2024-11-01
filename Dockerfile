FROM eclipse-temurin:17-jdk-jammy as build
WORKDIR /app
COPY . /app
RUN ./gradlew clean build --no-daemon

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/build/libs/api-franchise.jar /app/api-franchise.jar
ENTRYPOINT ["java", "-jar", "/app/api-franchise.jar"]
