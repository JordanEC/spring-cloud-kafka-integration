#FROM openjdk:8-jdk-alpine
FROM openjdk:8
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8081
## Add the wait script to the image
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /wait
RUN chmod +x /wait
## Launch the wait tool and then your application
CMD sh -c "/wait && java -jar /app.jar"
#ENTRYPOINT ["java","-jar","/app.jar"]