# Let's specify the base image
FROM openjdk:11

# Let's create a directory called app
WORKDIR /app

# Let's copy our artifact into the container
COPY ./target/book-service-1.0.0.jar .

# Let's execute the artifact
CMD java -jar -Dspring.profiles.active=prod book-service-1.0.0.jar

# Let's expose the port 
EXPOSE 8081
