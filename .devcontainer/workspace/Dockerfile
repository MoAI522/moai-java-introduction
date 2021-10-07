FROM amazoncorretto:8

#yum packages
RUN yum install -y \
  git \
  ca-certificates \
  build-essential \
  tar \
  xorg-x11-apps-7.7-7.amzn2.0.1.x86_64

#add ssh secret-key
COPY .dummy .ssh/* /root/.ssh/

#add .bashrc
COPY .bashrc /root/
RUN curl -o "/root/git-prompt.sh" https://raw.githubusercontent.com/git/git/master/contrib/completion/git-prompt.sh

#x11 setup
ENV DISPLAY host.docker.internal:0.0
