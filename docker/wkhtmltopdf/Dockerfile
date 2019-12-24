FROM ubuntu:16.04

ENV DEBIAN_FRONTEND noninteractive

RUN sed 's/main$/main universe/' -i /etc/apt/sources.list

RUN apt-get update
RUN apt-get upgrade -y

# Download and install wkhtmltopdf
RUN apt-get install -y build-essential xorg libssl-dev libxrender-dev wget

# Install dependencies
RUN apt-get update && apt-get install -y --no-install-recommends xvfb libfontconfig libjpeg-turbo8 xfonts-75dpi fontconfig

RUN wget --no-check-certificate https://github.com/wkhtmltopdf/wkhtmltopdf/releases/download/0.12.5/wkhtmltox_0.12.5-1.xenial_amd64.deb
RUN dpkg -i wkhtmltox_0.12.5-1.xenial_amd64.deb
RUN rm wkhtmltox_0.12.5-1.xenial_amd64.deb
COPY simsun.ttc /usr/share/fonts