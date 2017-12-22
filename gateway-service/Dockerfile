FROM java:8
VOLUME /tmp
ARG JAR_FILE
ADD ./target/${JAR_FILE} gateway-service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/gateway-service.jar"]
