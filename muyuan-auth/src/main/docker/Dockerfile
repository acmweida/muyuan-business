FROM openjdk:8-jdk-alpine
VOLUME /home/docker/muyuan/logs:/root/logs
RUN apk add --update ttf-dejavu fontconfig
ADD muyuan-auth-0.0.1.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]