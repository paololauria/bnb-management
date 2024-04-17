# Usa un'immagine di base di Docker Hub che includa il runtime Java
FROM openjdk:11-jdk

# Imposta la directory di lavoro all'interno del container
WORKDIR /bnb

# Copia il JAR compilato del tuo backend nella directory del container
COPY target/security-0.1.0.jar /bnb/security-0.1.0.jar

# Esponi la porta su cui il tuo backend ascolterà le richieste HTTP
EXPOSE 8080

# Comando da eseguire quando il container viene avviato
CMD ["java", "-jar", "security-0.1.0.jar"]
