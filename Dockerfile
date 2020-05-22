FROM mcr.microsoft.com/java/jdk:8u242-zulu-ubuntu-18.04

RUN apt-get update
RUN apt-get install -y scala
RUN echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
RUN apt-get update
RUN apt-get install sbt -y

RUN mkdir -p /home/java
WORKDIR /home/java
RUN sbt new sbt/scala-seed.g8 --name=smoke
WORKDIR /home/java/smoke
RUN sbt run

ADD ./hang-repro/ /home/java/repro
WORKDIR /home/java/repro
RUN sbt run
