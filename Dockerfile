FROM amazoncorretto:11
MAINTAINER se-timofeev@icloud.com
COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]