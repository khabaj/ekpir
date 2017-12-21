FROM java:8
VOLUME /tmp
ARG JAR_FILE
ADD ./target/${JAR_FILE} auth-service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/auth-service.jar"]

EXPOSE 8080