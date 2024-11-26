

-les fichiers dans le dossier angular.angularclient, ont tous été autogénérés.
Seul une partie des fichiers contenus dans "src" ne sont pas autogénérés.

La commande : "ng generate class user" se lance dans le dossier autogénérés angularclient.

dans le dossier "angular", le dossier "service" a été créé par moi-même.

Par la suite j'ai lancé la commande "ng generate service user-service", pour créer les fichiers associés au service de l'utilisateur.


J'ai aussi dû aller dans un répertoire et lancer depuis ce répertoire la commande :

"ng generate component user-list"

"ng generate component movie-search"

Pour créer un composant chargé d'afficher la list de user dans le répertoire user_list.

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


Autre chose, il est possible qu'au lancement du projet, votre page charge
mais celle-ci détecte une erreur dans une portion du code angular, alors qu'il n'est pas
censé en avoir à cet endroit qui est supposé être sûr.
Dans ce cas, c'est peut être un bug d'angular, supprimer puis remettez la partie problématique, cela pourrait refonctionner dans certains cas (c'est déjà arrivé).

Pour updater les données de la base de donnée (bdd) de façon persistante, il faut modifier le fichier
(non?)"out/production/Historique_oeuvres/main/resources/application.properties"
(oui)javaForHistory_work/history_work/src/main/resources/application.properties

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
-ng generate component user-entrance
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


résoudre l'erreur :

```
BeanDefinitionStoreException: Failed to parse 
```

Il faut aller dans les paramètres maven et lancer "clean" puis cliquer sur les deux flèches
qui forme un cercle "reload all maven incremental".




Utilisation de get ou post du controller :


- get : sert pour les requêtes qui récupèreront seulement des infos et qui ne modifieront pas l'état de la base de données.

- post : celles qui modifieront au contraire l'état de la base en faisant des ajouts par exemple.

https://stackoverflow.com/questions/67262447/which-http-status-code-to-return-if-there-is-no-data-found-from-database-in-rest

---> code retour http lorsqu'il n'y a pas de données :



utiliser get avec des paramètres :

https://www.baeldung.com/spring-request-param

	-200 OK avec length == 0 pour indiquer qu'il n'y a rien



Remarque : si la requête GET/POST semble ne pas se lancer, pensez à subscribe sur votre
appel de https dans la partie angular.

Pour permettre le renommage des colonnes, il faut écrire :
```
spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```
Dans le fichier applicaition.properties



Pour éviter que les pages perdent les données lorsqu'elles sont rafraichies, il faut les mettre dans le local storage/le session storage,
https://stackoverflow.com/questions/62834093/angular-data-loss-when-page-refresh/
https://dev-academy.com/angular-session-storage/
:

```angular

    handleDetails(value) {
      localStorage.setItem('key', value);
      }
```