FROM ghcr.io/navikt/baseimages/temurin:21

ADD eux-journal-webapp/target/eux-journal.jar /app/app.jar
