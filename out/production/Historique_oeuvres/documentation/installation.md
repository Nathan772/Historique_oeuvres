Pour angular :


lancer le projet:
0) lancer le fichier Application.java depuis intellij
1) il faut se placer dans angularclient
2) tapez : ng serve --open (lancer angular)

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

Pour associer du javascript à un fichier vous devrez l'ajouter dans les fichiers du projet dans le fichier

"/angular/client/angular.json":

```

"scripts": ["path/du/fichier.js"]

```

(voir ci-dessous)

Lorsque le projet est partagé entre plusieurs développeurs, vous aurez 
peut-être à recréer le fichier angular/angularclient/angular.json :

```
{
  "$schema": "./node_modules/@angular/cli/lib/config/schema.json",
  "version": 1,
  "newProjectRoot": "projects",
  "projects": {
    "angularclient": {
      "projectType": "application",
      "schematics": {
        "@schematics/angular:component": {
          "style": "scss"
        }
      },
      "root": "",
      "sourceRoot": "src",
      "prefix": "app",
      "architect": {
        "build": {
          "builder": "@angular-devkit/build-angular:application",
          "options": {
            "outputPath": "dist/angularclient",
            "index": "src/index.html",
            "browser": "src/main.ts",
            "polyfills": [
              "zone.js"
            ],
            "tsConfig": "tsconfig.app.json",
            "inlineStyleLanguage": "scss",
            "assets": [
              {
                "glob": "**/*",
                "input": "public"
              }
            ],
            "styles": [
              "src/styles.scss"
            ],
            "scripts": ["./src/user/cards/movie_user_card/movie-user-card.js"]
          },
          "configurations": {
            "production": {
              "budgets": [
                {
                  "type": "initial",
                  "maximumWarning": "500kB",
                  "maximumError": "1MB"
                },
                {
                  "type": "anyComponentStyle",
                  "maximumWarning": "2kB",
                  "maximumError": "4kB"
                }
              ],
              "outputHashing": "all"
            },
            "development": {
              "optimization": false,
              "extractLicenses": false,
              "sourceMap": true
            }
          },
          "defaultConfiguration": "production"
        },
        "serve": {
          "builder": "@angular-devkit/build-angular:dev-server",
          "configurations": {
            "production": {
              "buildTarget": "angularclient:build:production"
            },
            "development": {
              "buildTarget": "angularclient:build:development"
            }
          },
          "defaultConfiguration": "development"
        },
        "extract-i18n": {
          "builder": "@angular-devkit/build-angular:extract-i18n"
        },
        "test": {
          "builder": "@angular-devkit/build-angular:karma",
          "options": {
            "polyfills": [
              "zone.js",
              "zone.js/testing"
            ],
            "tsConfig": "tsconfig.spec.json",
            "inlineStyleLanguage": "scss",
            "assets": [
              {
                "glob": "**/*",
                "input": "public"
              }
            ],
            "styles": [
              "src/styles.scss"
            ],
            "scripts": ["./src/user/cards/movie_user_card/movie-user-card.js"]
          }
        }
      }
    }
  }
}
```

en cas d'erreur du type : 

"bind adresse déjà utilisée" :

lsof -i:8080 (avec 8080 le port utilisé)

puis "kill LePidAssocié" 


