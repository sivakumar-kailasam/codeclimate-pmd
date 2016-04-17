FROM java

MAINTAINER Sivakumar Kailasam 

# Groovy setup, defined multiple RUN steps for better caching resulting in quicker builds
RUN cd /tmp 
RUN wget http://dl.bintray.com/groovy/maven/apache-groovy-binary-2.4.6.zip 
RUN unzip apache-groovy-binary-2.4.6.zip 
RUN mv groovy-2.4.6 /groovy 
RUN rm apache-groovy-binary-2.4.6.zip

# Set Groovy path
ENV GROOVY_HOME /groovy
ENV PATH $GROOVY_HOME/bin/:$PATH

RUN groupadd app -g 9000
RUN useradd -g 9000 -u 9000 -r -s /bin/false app 

# Codeclimate specific setup
VOLUME /code
WORKDIR /code
COPY . /usr/src/app

USER app

CMD ["/usr/src/app/bin/pmd.groovy", "--codeFolder=/code","--configFile=/config.json"]
