FROM gcr.io/distroless/java25
ENV TZ="Europe/Oslo"
COPY eux-journal-webapp/target/eux-journal.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
