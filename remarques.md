Le site qui sert de référence :

- https://www.baeldung.com/spring-boot-angular-web 

--> donne les instructions pour installer spring.

Pour installer spring : 

- télécharger les jar associés aux versions de spring que vous souhaitez utiliser ou récupérez ceux du dossier 

VideoConverter/Dependeciens_for_video_converter

Ils permettront de résoudre:

- If you want an embedded database (H2, HSQL or Derby), please put it on the classpath




- les jars sont sur le site de maven : https://mvnrepository.com/  > leurs références sont directement accessibles
- ajouter les jars au build de votre projet pour que cela soit aussi reconnu dans le fichier pom.xml (si vous ne le faites pas, ils ne seront pas reconnus et
apparaitront en rouge) : https://www.geeksforgeeks.org/how-to-add-external-jar-file-to-an-intellij-idea-project/



-Sur le fichier pom.xml :

1) faire clique droite "add as maven project" (je ne me souviens plus du nom précis)

--> https://stackoverflow.com/questions/19245737/how-to-execute-maven-project-through-pom-xml-file

2) appuyez sur le marteau du build


-si l'ajout des jars est bien faite, il n'est pas nécessaire de lancer un quelconque builder ou grader de maven, on constate que les parties qui apparaissaient en rouge 
dans le fichier XML, reprennent une couleur dites "normale" (noir) et le fait d'écrire les annotations ne pose plus problèmes dans les fichiers


Pour angular :

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

--> yarn add rxjs

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


Remarque pour la bdd :

spring ne semble reconnaitre les noms de table qui ne sont écrits qu'en minuscule,
comme le recommande la convention.
Ainsi si le @Table(name="nomTable") contient des majuscules,
elles seront transformées en minuscule.
Ainsi, si la table existe déjà, mais porte une majuscule, alors une nouvelle table
sera créée et sera écrite en minuscule.

Pour voir comment préciser l'utilisateur et la bdd choisie, 
allez voir dans le fichier angular_organisation.md


Adresse déjà utilisée :

trouver : 

lsof -ti :$PORT

kill: 

kill $(lsof -ti :$PORT)

Pour tester les requêtes avec POSTMAN avant de les tester
avec le site :

1) faire un ajout : https://toolsqa.com/postman/post-request-in-postman/
