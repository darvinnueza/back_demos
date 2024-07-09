# Start with a base image containing java runtime
FROM openjdk:17-jdk-slim

# Information around who maintains the image
MAINTAINER eazybytes.com

# Add the application's jar to the image
COPY target/accounts-1.0.jar accounts-1.0.jar

# Execute the Application
ENTRYPOINT ["java", "-jar", "accounts-1.0.jar"]