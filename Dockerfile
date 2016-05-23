FROM java:8-jre

MAINTAINER Sivakumar Kailasam 

# Groovy setup, defined multiple RUN steps for better caching resulting in quicker builds
RUN cd /tmp && \
     wget http://dl.bintray.com/groovy/maven/apache-groovy-binary-2.4.6.zip && \ 
	 unzip apache-groovy-binary-2.4.6.zip && \ 
	 mv groovy-2.4.6 /groovy  && \ 
	 rm apache-groovy-binary-2.4.6.zip

# Set Groovy path
ENV GROOVY_HOME /groovy
ENV PATH $GROOVY_HOME/bin/:$PATH

RUN groupadd app -g 9000 && useradd -g 9000 -u 9000 -r -s /bin/false app

# Codeclimate specific setup
VOLUME /code
WORKDIR /code
COPY . /usr/src/app

USER app

CMD ["/usr/src/app/bin/pmd.groovy", "--codeFolder=/code","--configFile=/config.json"]
