FROM amazoncorretto:11
MAINTAINER se-timofeev@icloud.com
COPY target/partnerslk-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]