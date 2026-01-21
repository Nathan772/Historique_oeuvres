#name of the image chosen
FROM eclipse-temurin:23-jdk
#where the application will live
#and where future run command will run from
WORKDIR .


FROM eclipse-temurin:23-jdk

# Install the application dependencies
#copy host to working directory data

# Copy in the source code
#COPY src ./src
#COPY . .
EXPOSE 4200 49153
# Setup an app user so the container doesn't run as the root user
#useless here
#RUN useradd app
#USER app

#the command to run front-end (angular) app
#CMD ["./angular/ng", "serve", "--open", "0.0.0.0", "--port", "8080"]


#WORKDIR is equivalent for "cd", in order to go to the angular 
# file content
WORKDIR javaForHistory_work/java/src/main/java/nate/company/java/angular/angularclient

# source : https://www.docker.com/blog/docker-best-practices-choosing-between-run-cmd-and-entrypoint/
# These commands are executed during the image build process, and each RUN instruction creates a new layer in the Docker image. 
# For example, if you create an image that requires specific software or libraries installed, you would use RUN to execute the necessary installation commands.
#in our case we will install angular last version



#INSTALL YARN (BEGIN)

#install yarn directly
#IT'S Compulsory to enable docker to download node
FROM node:22.12.0

#Install some dependencies

WORKDIR /usr/app

#precise that the install has to work in the environment of the folder "./angular"
COPY ./javaForHistory_work/history_work/src/main/java/nate/company/history_work/angular/angularclient /usr/app

#it must be installed exactly here
RUN npm install

# Install curl and apt-transport-https to handle https sources

RUN apt-get update && apt-get install -y \
    curl \
    apt-transport-https \
    lsb-release \
    gnupg

#INSTALL YARN (END)
#COPY ./ /angular

#INSTALL ANGULAR tools (BEGIN)

RUN npm i @angular-devkit/architect
RUN npm i @angular/cli@19.2.19

RUN npm update


#run java's back-end 
#WORKDIR ~/Bureau/Bureau/Bureau/Perso/projets_développement_informatique/Historique_oeuvres
#CMD ["./mvnw","spring-boot:run"]

#go to the angular project part
WORKDIR ./javaForHistory_work/java/src/main/java/nate/company/java/angular/angularclient

#start is equivalent
#for ng serve --host 0.0.0.0 (look at package.json in scipts)
RUN npm start




#https://jonathanantoine.medium.com/hosting-an-angular-application-inside-a-docker-container-with-nginx-b10f3f0a4c26#:~:text=This%20also%20applies%20to%20docker,use%20ng%20serve%20in%20production.
#you mustn't run it here... (see upper link)
#CMD ["npm" "start"]
