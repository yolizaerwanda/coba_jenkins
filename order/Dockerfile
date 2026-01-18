FROM openjdk:26-ea-jdk-oracle

    # direktori
    WORKDIR /app

    # copy jar file
    COPY target/*.jar app.jar

    # expose
    EXPOSE 8083

    # run
    CMD ["java", "-jar", "app.jar"]