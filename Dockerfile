FROM openjdk:8u151-jdk-alpine3.7
RUN mkdir /home/bigcloneeval
ADD ./ijadataset /home/bigcloneeval/ijadataset
ADD ./bin /home/bigcloneeval/bin
ADD ./libs /home/bigcloneeval/libs
ADD ./commands /home/bigcloneeval/commands
ADD ./toolsdb /home/bigcloneeval/toolsdb
ADD target/bigcloneeval.detection.war /home/bigcloneeval/bigcloneeval.detection.war
RUN apk update && \
	apk upgrade && \
	apk add bash vim
EXPOSE 8080
WORKDIR /home/bigcloneeval
ENTRYPOINT ["/bin/bash"]