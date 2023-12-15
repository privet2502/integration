# Используем официальный образ OpenJDK 17
FROM arm64v8/openjdk:17-ea-16-jdk

# Копируем JAR-файл Spring приложения в контейнер
COPY target/integration-0.0.1-SNAPSHOT.jar app.jar

# Запускаем Spring приложение при старте контейнера
CMD ["java", "-jar", "/app.jar"]