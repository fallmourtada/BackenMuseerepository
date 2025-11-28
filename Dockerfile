# Image de base Java 17
FROM openjdk:17-jdk-slim

# Répertoire de travail dans le conteneur
WORKDIR /app

# Copier le JAR Maven buildé
COPY target/*.jar app.jar

# Exposer le port de l'application
EXPOSE 8087

# Lancer l'application
ENTRYPOINT ["java","-jar","app.jar"]
