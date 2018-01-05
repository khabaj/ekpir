FROM java:8
VOLUME /tmp
ARG JAR_FILE
ADD ./target/${JAR_FILE} config-server.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/config-server.jar"]
