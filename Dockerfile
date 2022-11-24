FROM openjdk:11

EXPOSE 8080:8080

ENV APP_PATH="/usr/src/app"

RUN mkdir -p $APP_PATH

WORKDIR ${APP_PATH}

ADD docker/start_for_docker.sh ${APP_PATH}/start_for_docker.sh

ADD target/*.jar ${APP_PATH}/book-api-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["sh","start_for_docker.sh"]
