FROM openjdk:8-jdk-alpine
VOLUME /home/docker/muyuan/logs:/root/logs
ADD muyuan-gateway-0.0.1.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]