FROM java:8
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} ekpir.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ekpir.jar"]

EXPOSE 8080