Lancer docker pour lancer angular avec ses installations :


créer containeur avec image avec un nom précis pour java 23 :

```bash

docker container create -i -t --name museeoeuvrecontainer openjdk:23-jdk

```


```bash
sudo docker build -t museeoeuvrecontainer .        
```
-->
(cette commande n'est pas nécessaire si la précédente a été faite)

créer un container et une image à partir d'une image :


```bash
docker run -p 80:80 openjdk:23-jdk
```



(le nom du container doit être en lowercase)

Explication :

on créé un container :

avec le port : 80 -->port local

80  --> port à l'intérieur du container

avec l'image : 

mcr.microsoft.com/dotnet/aspnet

(image propre à c#)

sources :

https://www.youtube.com/watch?v=1073xKuYNaY

et 

chatgpt


supprimer tous les containers docker existants :

```bash
docker rm -fv $(docker ps -aq)
```


pb :

lister et tuer qui écoute le port 80.

solution: 

```bash

sudo lsof -i:80
```

```bash

sudo kill numberPortAssociatedToRoot

```

voir les container créés mais non démarrés et ceux qui sont morts :

```bash

docker ps -a

```
lancer un container créé mais non démarré :

```bash

docker start container_name

```

voir les containers actifs : 

```bash

docker ps

```


comment créer un fichier docker (indispensable pour que les containers ne se lancent pas dans le vide) : 

https://docs.docker.com/get-started/docker-concepts/building-images/writing-a-dockerfile/


erreur :

"ERROR: failed to solve: failed to read dockerfile: open Dockerfile: no such file or directory"

solution :

vérifiez que votre fichier docker se nomme bien : "Dockerfile" la casse compte !!


exemple de fichier docker fonctionnel pour lancer c# et angular :

```Dockerfile

#name of the image chosen
FROM openjdk:23-jdk
#where the application will live
#and where future run command will run from
WORKDIR .


FROM openjdk:23-jdk

# Install the application dependencies
#copy host to working directory data

# Copy in the source code
#COPY src ./src
#COPY . .
EXPOSE 3000
# Setup an app user so the container doesn't run as the root user
#useless here
#RUN useradd app
#USER app

#the command to run front-end (angular) app
#CMD ["./angular/ng", "serve", "--open", "0.0.0.0", "--port", "8080"]


#equivalent to "cd", in order to go to the angular 
# file content
WORKDIR /angular

# source : https://www.docker.com/blog/docker-best-practices-choosing-between-run-cmd-and-entrypoint/
# These commands are executed during the image build process, and each RUN instruction creates a new layer in the Docker image. 
# For example, if you create an image that requires specific software or libraries installed, you would use RUN to execute the necessary installation commands.
#in our case we will install angular last version


# Install Node.js and Yarn
#RUN 
#yarn add @angular/cli

#already exists in global environment thus cause error...
#RUN npm install --global yarn



#INSTALL YARN (BEGIN)

#install yarn directly
#IT'S Compulsory to enable docker to download node
FROM node:22.9.0

#Install some dependencies

WORKDIR /usr/app

#precise that the install has to work in the environment of the folder "./angular"
COPY ./angular /usr/app

#it must be installed exactly here
RUN npm install

# Install curl and apt-transport-https to handle https sources

RUN apt-get update && apt-get install -y \
    curl \
    apt-transport-https \
    lsb-release \
    gnupg
# Install yarn using the official Yarn repo
RUN curl -sL https://dl.yarnpkg.com/debian/pubkey.gpg | apt-key add - && \
    echo "deb https://dl.yarnpkg.com/debian/ stable main" | tee /etc/apt/sources.list.d/yarn.list && \
    apt-get update && \
    apt-get install -y yarn

#INSTALL YARN (END)
#COPY ./ /angular

#INSTALL ANGULAR tools (BEGIN)
RUN yarn add @angular-devkit/architect 
RUN yarn add @angular/cli@19.2.7 
#RUN npm install -g npm@11.3.0

#WORKDIR /angular

#COPY package.json .

#RUN npm install --dev
#RUN npm i -g @angular/cli
RUN npm update

#go to the angular project part
WORKDIR ./angular

#start is equivalent
#for ng serve --host 0.0.0.0 (look at package.json in scipts)
#RUN npm start --host 

RUN npm start



```


problème :

lancer une image docker qui est visible avec : 

```bash
docker ps -a

```

solution :

```bash 

docker build -t museeoeuvrecontainer .

```

avec vigilant_jang == le nom de l\'image visible avec .

```bash
docker ps -a
```

problème :

"idealTree" already exists"

solution :

```Dockerfile
#install yarn directly
#IT'S Compulsory to enable docker to download node
FROM node:22.9.0

#Install some dependencies

WORKDIR /usr/app
COPY ./ /usr/app

#it must be installed exactly here
RUN npm install
```

il faut placer le install exactement ici, c'est à dire, 
pas loin après le "FROM node..."



problème :

"You seem to not be depending on "@angular/core". This is an error."


solution :

"npm install @angular/core"



résoudre problème :

```Dockerfile

RUN npm update
```

--> npm warn deprecated
solution :

https://stackoverflow.com/questions/78527247/have-used-npm-create-vitelatest-but-after-finishing-the-compilations-am-getting

problème :

"angular se lance mais 
le site est inaccessible dans l'url"

solution :

dans le fichier package.json de "angular/angular-client" :

```

"scripts": {
    "ng": "ng",
    "start": "ng serve --host 0.0.0.0", --> cette ligne
    "build": "ng build",
    "watch": "ng build --watch --configuration development",
    "test": "ng test"
  },

```

problème :

le building du container prend beaucoup trop de temps.

Solution :

vérifiez que vous êtes bien toujours connectés à internet.
si vous ne l'êtes pas : reconnecté vous, puis quittez et relancer le building.

```bash
docker run -p 4200:80 --name museeoeuvrecontainer openjdk:23-jdk
```

problème :

"supprimer les images dockers qui prennent de la place."

solution :

```shell

docker images


docker rmi fd484f19954f-->  fd484f19954f === img id

```