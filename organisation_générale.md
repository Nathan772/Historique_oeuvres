Pour angular :


lancer le projet :
0) lancer le fichier Application.java depuis intellij
1) il faut se placer dans angularclient
2) tapez : ng serve --open

-npm install -g @angular/cli@1.7.4

lancer cette commande dans le terminal, dans le dossier du projet, pour générer automatiquement les différentes pages webs angular.

---> résoudre l'erreur :  "npm error code EACCES while doing npm install":

    -https://docs.npmjs.com/resolving-eacces-permissions-errors-when-installing-packages-globally 

    --> notamment nvm install node

    --> résoudre l'erreur : "npm warn deprecated inflight@1.0.6: This module is not supported, and leaks memory. Do not use it. ":

    - https://github.com/jestjs/jest/issues/15087

    ---> commande : npm install jest@next

    - https://github.com/sveltejs/kit/issues/12258

    ---> commande : npx svelte-migrate@latest svelte-4

        --> si la commande Sass est demandée dans les msgs d'erreur :

            -installer sass en utilisant : 

                    --> "npm install -g sass"

                        --> résoudre le pb: 
            
                            --> https://stackoverflow.com/questions/52979927/npm-warn-checkpermissions-missing-write-access-to-usr-local-lib-node-modules

    résoudre l'erreur : 

    "npm ERR! gyp info it worked if it ends with ok
    npm ERR! gyp verb cli [
    npm ERR! gyp verb cli   '/home/nathanb/.nvm/versions/node/v16.20.2/bin/node',"

    ---> "npm install -g @angular/cli" plutôt que "npm install -g @angular/cli@1.7.4"


Lancer la commande : "ng new angularclient" dans un dossier que vous aurez prévu dans votre projet pour stocker les fichiers angular 
(nommé comme vous le souhaitez) afin de générer les fichiers associés à angular automatiquement.

-les fichiers dans le dossier angular.angularclient, ont tous été autogénérés.
Seul une partie des fichiers contenus dans "src" ne sont pas autogénérés.

La commande : "ng generate class user" se lance dans le dossier autogénérés angularclient.

dans le dossier "angular", le dossier "service" a été créé par moi-même.

Par la suite j'ai lancé la commande "ng generate service user-service", pour créer les fichiers associés au service de l'utilisateur.

---> résoudre : 

    -An unhandled exception occurred: Cannot find module '@angular-devkit/schematics/tools'
    -https://stackoverflow.com/questions/53940350/error-cannot-find-module-angular-devkit-schematics-and-cannot-find-module

J'ai aussi dû créer un répertoire : "user_list" à la main et lancer depuis ce répertoire la commande :

"ng generate component user-list"

Pour créer un composant chargé d'afficher la list de user dans le répertoire user_list.

Si vous tombez sur le msg suivant :

"La commande « ng » n'a pas été trouvée, mais peut être installée avec :

sudo apt install ng-common"

ne le faites pas, cette installation cause un bug. Il doit y avoir une autre cause de votre 
erreur, lancez un autre terminal et relancer votre commande par exemple.

A la place il faut tout réinstaller :

-" sudo apt-get install npm" 
- "sudo npm install -g n"
- "sudo n latest"
- "sudo npm install -g @angular/cli"

Pour résoudre :

- ~~ "An unhandled exception occurred: Cannot find module cannot find rxjs"

-->  npm i rxjs@7.5.2

- An unhandled exception occurred: Cannot find module '@angular-devkit/architect/node'

-->  passer par yarn pour instaler devkit/archtiect/node :
    et faire les ajouts dans le dossier : "/usr/local/lib/node_modules" --
    (https://www.hostinger.fr/tutoriels/comment-installer-yarn)
    -sudo npm install --global yarn
    -yarn add @angular-devkit/architect
    https://listr2.kilic.dev/listr/installation.html
    -yarn add listr2
    ou
    -npm i listr2


Pour les installations de angualar :

npm/yarn, les faire dans le dossier source de angular nommé ici, "angular".
Si une install npm ne marche pas, essayez de la faire avec yarn (généralement tout marche avec yarn). Si le package 
n'est pas trouvable directement en lien "yarn", vous pouvez copier texte npm du package et juste remplacer le début par : 
"yarn add [nom du package utilisé pour npm]" et généralement ça marche tout aussi bien

remarque pour angular :

certains message d'erreurs
peuvent n'avoir aucun rapport avec
la vraie cause de l'erreur,
c'est donc au user de trouver en tatonnant
et en cherchant ce qui est inhabituel.
J'ai ainsi eux un msg
qui parlait d'une accolade,
alors que les causes de l'erreur étaient
la présence du mot "var" avant le nom
de la variable et aussi un
constructeur de service qui n'utilisait pas
le mot "this" + le paramètre
service qui était privé

Quand il y a des problèmes d'affichage, penser à regarder les logs pour plus d'infos.

Pour ce qui est du problème où angular ne reconnait pas une autre page que index,
verifier que le fichier main.ts est de la forme :

```typescript

import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import {AppModule} from './app.module';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {enableProdMode} from '@angular/core';

platformBrowserDynamic().bootstrapModule(AppModule)

```

Cela permet de gérer les pages multiples.
Contrairement à l'autre format qui est en mode standalone (c'est à dire une seule page autorisée).

Si il y a un problème avec rxjs alors qu'il est installé avec yarn et 
npm, alors tenter de supprimer rxjs pour le réinstaller, depuis le dossier
angular/angularclient : et si ça produit une erreur avec architect/node,
alors supprimer manuellement en allant directement dans le fichier 
associé, les lignes qui contiennent architect/Node, il peut causer des 
bugs à l'installation.

```bash

yarn add rxjs

```

essayez aussi de désinstaller rxjs pour yarn et pour npm.
Puis de réinstaller si nécessaire.

Autre chose, il est possible qu'au lancement du projet, votre page charge 
mais celle-ci détecte une erreur dans une portion du code angular, alors qu'il n'est pas 
censé en avoir à cet endroit qui est supposé être sûr.
Dans ce cas, c'est peut être un bug d'angular, supprimer puis remettez la partie problématique, cela pourrait refonctionner dans certains cas (c'est déjà arrivé).

Pour updater les données de la bdd de façon persistante, il faut modifier le fichier
"application.properties" : 

```properties

spring.application.name=youtube-converter
spring.datasource.url=jdbc:mysql://localhost:3306/votreBDD
spring.datasource.username=votre_nom_utilisateur
spring.datasource.password=votre_mdp
spring.jpa.hibernate.ddl-auto=update
```

voir : 

https://www.geeksforgeeks.org/spring-boot-how-to-access-database-using-spring-data-jpa/

Il faut aussi modifier le pom.xml pour permettre de connecter la base:
```xml
<!-- https://mvnrepository.com/artifact/com.mysql/mysql-connector-j -->
		<!--
		nécessaire pour résoudre l'erreur :

		Failed to load driver class com.mysql.jdbc.Driver
		liée aux modifications de application.properties
		lié à la bdd
		-->

		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<version>9.0.0</version>
		</dependency>
```


Pour générer automatiquement des fichiers :

1) on crée le dossier associé au composant puis...

2) ... selon les besoin :

-ng generate service user-service
-ng generate component user-form
-ng generate component user-list
-ng generate class user


ng generate component videoDL-form
ng generate service videoDLService

ng generate component videoDLPage


Pour utiliser des fichiers javascript dans angular projet,
il faut ajouter dans le fichier angular.json le path vers 
le fichier javascript, dans la partie "scripts": [].

Il faut le faire pour chaque fichier script, nouveau.

voir : https://medium.com/@Codeible/adding-loading-and-using-javascript-in-angular-3281ea4b056b

pour plus de détails



Pour lancer le swagger : 

- lancer le fichier application.java 

- se rendre sur la page : http://localhost:8080/swagger-ui/index.html

- n'oubliez pas les dépendances : 

```xml

<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency

```
