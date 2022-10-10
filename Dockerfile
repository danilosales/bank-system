FROM maven:3.8.6-eclipse-temurin-17-alpine@sha256:6e208b62c49bf36c844437638f6b1a75844dd7dc018929eb13ec1ade6308b196 AS builder
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn clean package -DskipTests
RUN java -Djarmode=layertools -jar target/bank-0.0.1-SNAPSHOT.jar extract

FROM eclipse-temurin:17.0.4.1_1-jre-alpine@sha256:e1506ba20f0cb2af6f23e24c7f8855b417f0b085708acd9b85344a884ba77767
RUN apk add dumb-init
RUN mkdir /app
RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
# COPY --from=build /project/target/bank-0.0.1-SNAPSHOT.jar /app/java-application.jar
COPY --from=builder /project/dependencies/ ./app/
COPY --from=builder /project/snapshot-dependencies/ ./app/
COPY --from=builder /project/spring-boot-loader/ ./app/
COPY --from=builder /project/application/ ./app/
WORKDIR /app
RUN chown -R javauser:javauser /app
USER javauser
CMD "dumb-init" "java" "org.springframework.boot.loader.JarLauncher"
# ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]