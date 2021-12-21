FROM jenkins/jenkins:lts-jdk11

ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
ENV CASC_JENKINS_CONFIG /usr/share/jenkins/init.jcasc.d/
ENV JENKINS_REF /usr/share/jenkins/ref

# Setup plugins to be installed
COPY --chown=jenkins:jenkins plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f $JENKINS_REF/plugins.txt

#Setup JCasc code for Jenkins server automation
#Setup Seed job for DSL
COPY *.yaml $CASC_JENKINS_CONFIG

USER root

RUN apt update && apt install -y lsb-release \
    software-properties-common \
    apt-transport-https \
    && rm -rf /var/lib/apt/lists/*

RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add - \
    && add-apt-repository "deb [arch=amd64] \
    https://download.docker.com/linux/debian $(lsb_release -cs) stable" \
    && apt update && apt install -y docker-ce-cli \
    && rm -rf /var/lib/apt/lists/*

USER jenkins