FROM openjdk:15

ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

WORKDIR opt/spring_boot

COPY /target/goldhirsch.jar goldhirsch_docker.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 5005
EXPOSE 8080

CMD java ${ADDITIONAL_OPTS} -jar goldhirsch_docker.jar --spring.profiles.active=${PROFILE}