FROM ubuntu:latest

ENV DEBIAN_FRONTEND "noninteractive"

#apt packages
RUN apt-get update -y && \
  apt-get install -y --no-install-recommends \
  git \
  ssh \
  ca-certificates \
  build-essential \
  tar \
  curl \
  x11-apps \
  libgtk-3-0 \
  language-pack-ja-base \
  language-pack-ja \
  python3 \
  python3-pip

RUN curl -o "/root/bellsoft-jdk11.deb" https://download.bell-sw.com/java/11.0.12+7/bellsoft-jdk11.0.12+7-linux-amd64-full.deb && \
  apt install -y /root/bellsoft-jdk11.deb

RUN pip install -r /workspace/app/requirements.txt

#add ssh secret-key
COPY .dummy .ssh/* /root/.ssh/

#add .bashrc
COPY .bashrc /root/
RUN curl -o "/root/git-prompt.sh" https://raw.githubusercontent.com/git/git/master/contrib/completion/git-prompt.sh

#x11 setup
ENV DISPLAY host.docker.internal:0.0
