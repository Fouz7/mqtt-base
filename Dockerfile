FROM openjdk:17
VOLUME /tmp
COPY target/demo.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
