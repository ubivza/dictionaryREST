# Use the openjdk(deprecated) 21 imaje as the base image
FROM amazoncorretto:21
VOLUME /tmp
COPY /build/libs/dictionaryREST-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
#         host port/container port
#docker run -p 8080:8080 dictrest:1.0 to make localhost machine port work with containers port
#to make container see another containers ports we need to run this command
#docker network create app-network (name of network)


# Create a new app directory for my app files
#RUN mkdir /app

# Copy the app files from host machine to image filesystem
#COPY build /app

# Set the directory to executing future commands
#WORKDIR /app

# Specify container habit on start
#CMD java /app/classes/java/main/ru/aleksandr/dictionaryrest/DictionaryRestApplication.class