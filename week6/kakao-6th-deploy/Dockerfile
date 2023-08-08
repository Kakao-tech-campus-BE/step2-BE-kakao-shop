FROM gradle:7.3.1-jdk17 as build

WORKDIR /home/gradle/project

COPY . .

RUN echo "systemProp.http.proxyHost=krmp-proxy.9rum.cc\nsystemProp.http.proxyPort=3128\nsystemProp.https.proxyHost=krmp-proxy.9rum.cc\nsystemProp.https.proxyPort=3128" > /root/.gradle/gradle.properties

RUN gradle clean build

# Stage 2: Run the application
FROM eclipse-temurin:17-jre

WORKDIR /opt/techcampus

COPY --from=build /home/gradle/project/build/libs/kakao-1.0.jar .

ENV DATABASE_URL=jdbc:mariadb://localhost:3306/kakao

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "kakao-1.0.jar"]