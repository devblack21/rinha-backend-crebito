FROM amazoncorretto:17-alpine-jdk AS base

LABEL MAINTENER="Marcos Vinicius A.Rocha (DevBlack21)"

ENV PORT=20000

ARG JAR_FILE=target/crebito*.jar

ENV TZ=America/Sao_Paulo

ADD ${JAR_FILE} app.jar

ADD app.sh /

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN chmod 0755 /app.sh

CMD sh app.sh

EXPOSE ${PORT}