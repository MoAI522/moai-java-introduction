FROM amazoncorretto:8

#apt packages
RUN yum install -y \
  git \
  ca-certificates \
  build-essential \
  tar

#add ssh secret-key
COPY .dummy .ssh/* /root/.ssh/

#add .bashrc
COPY .bashrc /root/
RUN curl -o "/root/git-prompt.sh" https://raw.githubusercontent.com/git/git/master/contrib/completion/git-prompt.sh
