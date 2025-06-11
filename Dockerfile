FROM openjdk:17-slim
WORKDIR /app
COPY . /app
RUN apt-get update && apt-get install -y maven
RUN mvn clean install -DskipTests -B
CMD ["mvn", "test", "-B"]
