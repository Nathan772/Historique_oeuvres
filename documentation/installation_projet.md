Pour angular (front) et java (back) :

A)

Tout d'abord, il vous faut créer votre propre base de données en locale associée au projet et mettre à jour le fichier application.properties : 

1) history-work > src > main > resources > application.properties :

Regardez le texte fléché dans les parties ci-dessous et suivez les indications associées.
Si il n'y a pas de texte fléché, laissez les instructions telles qu'elles sont dans le fichier.

```

spring.application.name=Historique_oeuvres 


spring.datasource.url=jdbc:mysql://localhost:3306/historyDB --> à la place de "historyDB" mettez
le nom de la base données que vous voulez pour stocker les données (vous pouvez l'appeler "historyDB")
spring.datasource.username=nathanb --> à la place de "nathanb", mettez le nom du compte que vous utilisez pour mysql et que vous avez utilisé pour stocker la base de données choisie pour "historyDB".
spring.datasource.password=Orez_2456- -> mettez le mot de passe que vous utilisez pour accéder à ce même compte pour mysql


spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl


```

2) sur mysql :

a) créez votre compte + le mot de passe associé
b) créez la base de données que vous avez choisi pour remplacer historyDB
c) rdv dans et lancez : SqlForHistorique_oeuvres > bddinit.sql

cela permettra de préparer votre base de données.

B)


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



---> résoudre : 

    -An unhandled exception occurred: Cannot find module '@angular-devkit/schematics/tools'
    -https://stackoverflow.com/questions/53940350/error-cannot-find-module-angular-devkit-schematics-and-cannot-find-module


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



essayez aussi de désinstaller rxjs pour yarn et pour npm.
Puis de réinstaller si nécessaire.



