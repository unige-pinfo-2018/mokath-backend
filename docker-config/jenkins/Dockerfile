FROM jenkins/jenkins:lts

# Insert the list of plugins to install
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
# Install the plugins found on the above liste
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt
