FROM jenkins/jenkins:latest

USER root
RUN apt-get update \
      && apt-get install -y sudo \
      && apt-get install -y libltdl7 \
      && rm -rf /var/lib/apt/lists/* 
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers
 
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt