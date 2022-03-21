FROM adoptopenjdk/openjdk11:latest
RUN mkdir /opt/app
COPY getir-0.0.1-SNAPSHOT-spring-boot.jar /opt/app
CMD ["java", "-jar", "/opt/app/getir-0.0.1-SNAPSHOT-spring-boot.jar"]