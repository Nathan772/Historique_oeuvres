

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
</dependency>

```


résoudre l'erreur :

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

résoudre l'erreur :

```
BeanDefinitionStoreException: Failed to parse 
```

Il faut aller dans les paramètres maven et lancer "clean" puis cliquer sur les deux flèches
qui forme un cercle "reload all maven incremental".

```java
/*
nécessaire pourgit  résoudre :
Parameter 0 of method init in nate.company.youtube_converter.Application

required a bean of type 'nate.company.youtube_converter.siteTools.UserRepository' that could not be found.
 */
@ComponentScan({"nate/company/history_work"})
```




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


d'autres erreurs (liés au projet de M2) :



Debug :

Erreur :
"
Failed to instantiate [org.springframework.boot.web.servlet.ServletRegistrationBean]: Factory method 'h2Console' threw exception with message: Error creating bean with name 'dataSource' defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class]: Failed to instantiate [com.zaxxer.hikari.HikariDataSource]: Factory method 'dataSource' threw exception with message: Failed to load driver class 
com.mysql.cj.jdbc.Driver in either of HikariConfig class loader or Thread context classloader
"

résoudre : 

-créer le fichier persistence.xml dans resources/META-INF/persistence.xml :
persistence.xml : 

```xml 

<persistence xmlns="http://java.sun.com/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://java.sun.com/xml/ns/persistence https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd"
             version="2.0">

    <persistence-unit name="main-persistence-unit">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <exclude-unlisted-classes>false</exclude-unlisted-classes>

        <properties>
            <property name="jakarta.persistence.jdbc.url"
                      value="jdbc:h2:tcp://localhost/~/h2DB"/>

            <!-- Credentials -->
            <property name="jakarta.persistence.jdbc.user"
                      value="sa"/>
            <property name="jakarta.persistence.jdbc.password"
                      value=""/>

            <property name="jakarta.persistence.jdbc.url" value="jdbc:p6spy:h2:tcp://localhost/~/h2DB" />

            <!-- Automatic schema export -->
            <property name="jakarta.persistence.schema-generation.database.action"
                      value="drop-and-create"/>

            <!-- SQL statement logging -->
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <property name="hibernate.highlight_sql" value="true"/>

        </properties>

    </persistence-unit>

</persistence>
```

- créer resources/spy.properties :

```properties

driverlist=org.h2.Driver
appender=com.p6spy.engine.spy.appender.StdoutLogger
logMessageFormat=com.p6spy.engine.spy.appender.CustomLineFormat
customLogMessageFormat=%(category)|%(sql)

``

Il faut aussi et peut être seulement :

```

<!-- https://mvnrepository.com/artifact/com.mysql/mysql-connector-j -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>9.2.0</version>
        </dependency>
``` 
le mysql connect --> c'est peut être pas nécessaire, il faut juste clean+ compile + package je crois

avec son jar associé...

Par contre après ça il faut :

clean + compile + package dans la fenêtre de droite de Maven


La commande pour lancer H2 :

java -cp h2-2.3.232.jar org.h2.tools.Server -ifNotExists &

Si le port est déjà utilisé  pour H2 :

```shell
netstat -plten |grep java 
```

res :
```
tcp   0   0 0.0.0.0:8080   0.0.0.0:*  LISTEN   1001  76084  9488/java   
```

```
kill -9 9488
```

Il est aussi possible que vous ayez à recréer le fichier

application.properties :

```
Il est aussi possible que vous ayez à recréer le fichier :

"main/resources/application.properties"

si il n'est pas présent.

remplissez application.properties avec les infos suivantes
pour pouvoir vous connecter à la base de données :


```

spring.datasource.url=jdbc:h2:file:./data/demo
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect


```

Il faut ensuite : 

clean + compile + package


Pour résoudre : 

"org.h2.message.DbException: Log file error: "/data/demo.trace.db", cause: 
"org.h2.message.DbException: Error while creating file ""/data"""

il faut écrire :

```

spring.datasource.url=jdbc:h2:file:./data/demo
```

dans application.properties et pas 

```
spring.datasource.url=jdbc:h2:file:/data/demo
```

source: 
https://stackoverflow.com/questions/64756454/why-does-spring-boot-with-embedded-h2-throw-a-org-h2-message-dbexception-error




résoudre: 

"Caused by: org.h2.jdbc.JdbcSQLSyntaxErrorException: Séquence "USER_TABLE_SEQ" non trouvée
Sequence "USER_TABLE_SEQ" not found; SQL statement:"

il faut retirer de application.properties :

```

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

```

voir :

https://stackoverflow.com/questions/51949922/spring-boot-2-migration-org-h2-jdbc-jdbcsqlexception-sequence-not-found

résoudre :

"Table "USER_TABLE" not found (this database is empty); SQL statement:"

--> 

solution 1 (générer manuellement les tables): 


https://stackoverflow.com/questions/74510923/h2-in-memory-database-jdbcsqlsyntaxerrorexception-table-not-found-this-databa



solution 2 (générer automatiquement les tables) :

```properties

# Config bdd H2
server.port=8080
server.address=localhost
spring.datasource.url=jdbc:h2:./src/main/resources/db/db

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=nathanb
spring.datasource.password=password

#je crois que c'est cette ligne qui génère automatiquement les tables
spring.jpa.hibernate.ddl-auto=update

# Activer la console H2
spring.h2.console.enabled=true
#spring.h2.console.enabled=false
spring.h2.console.path=/h2-console
spring.h2.console.settings.trace=false
spring.h2.console.settings.web-allow-others=true

```

N'oubliez pas de: 

clean + compile + package + run project





résoudre : 

"Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.exception.JDBCConnectionException: Unable to open JDBC 
Connection for DDL execution [Mauvais nom d'utilisateur ou mot de passe"

Il faut créer le fichier :

src/main/resources/persistence.xml :

```

<persistence xmlns="http://java.sun.com/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://java.sun.com/xml/ns/persistence https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd"
             version="2.0">

    <persistence-unit name="main-persistence-unit">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <exclude-unlisted-classes>false</exclude-unlisted-classes>

        <properties>
            <property name="jakarta.persistence.jdbc.url"
                      value="jdbc:h2:tcp://localhost/~/h2DB"/>

            <!-- Credentials -->
            <property name="jakarta.persistence.jdbc.user"
                      value="sa"/>
            <property name="jakarta.persistence.jdbc.password"
                      value=""/>

            <property name="jakarta.persistence.jdbc.url" value="jdbc:p6spy:h2:tcp://localhost/~/h2DB" />

            <!-- Automatic schema export -->
            <property name="jakarta.persistence.schema-generation.database.action"
                      value="drop-and-create"/>

            <!-- SQL statement logging -->
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <property name="hibernate.highlight_sql" value="true"/>

        </properties>

    </persistence-unit>

</persistence>


```

et définir le "user value=" et "password value=" avec les mêmes 
valeurs que celles que vous avez choisi dans application.properties.

Aussi, il faut supprimer le dossier db dans ressources, sinon cela 
maintiendra le conflit sur le mdp et le user.
Laissez-le se recréer automatiquement.


Attention, si vous voulez créer un nouveau user avec un nouveau mot de passe,
il faudra configurer comme dans cette page à priori (je ne l'ai pas
fait car cela risque de faire perdre du temps notamment pour certaines infos
que je ne connais pas : server_IP, h2_port) :

https://www.ibm.com/docs/en/tnpm/1.4.4?topic=database-creating-user-password-h2


Pour persister via h2 mais avec erreur:

```properties

#old data

#spring.datasource.url=jdbc:h2:file:./data/demo
#spring.datasource.driverClassName=org.h2.Driver
#spring.datasource.username=sa
#spring.datasource.password=password
#spring.h2.console.enabled=true
#spring.jpa.defer-datasource-initialization=true


#new data


# Config bdd H2
server.port=8080
server.address=localhost
#enregistre dans des fichiers de façon persistante
#mais n'affiche pas sur la fenêtre de h2
#spring.datasource.url=jdbc:h2:./src/main/resources/db/db

#il faut cibler la bdd de la version browser
# la version du browser
# correspond à celle visible sur le site web de h2:
# jdbc:h2:tcp//localhost/~/h2DB
# c'est aussi celle écrite dans persistence.xml
# voir : https://stackoverflow.com/questions/61797029/why-h2-database-doesnt-show-my-table-in-the-browser

#erreur + pas persistant
#spring.datasource.url=jdbc:h2:mem:h2DB
#tenative
#fonctionnel pour la persistance mais écrit
# no suitable driver for dans le navigateur
spring.datasource.url=jdbc:h2:tcp://localhost/~/h2DB

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update

# Activer la console H2
spring.h2.console.enabled=true
#spring.h2.console.enabled=false
spring.h2.console.path=/h2DB/
spring.h2.console.settings.trace=false
spring.h2.console.settings.web-allow-others=true
```

Si on a le message :

"Connexion localhost refusée"

pensez à lancer H2 avant de run Application.java.


Résoudre :

```
No suitable driver found for 08001/0
java.sql.SQLException: No suitable driver found for
    at java.sql/java.sql.DriverManager.getConnection(DriverManager.java:707)
    at java.sql/java.sql.DriverManager.getConnection(DriverManager.java:230)
    at org.h2.util.JdbcUtils.getConnection(JdbcUtils.java:338)
    at org.h2.server.web.WebServer.getConnection(WebServer.java:811)
    at org.h2.server.web.WebApp.login(WebApp.java:1038)
    at org.h2.server.web.WebApp.process(WebApp.java:226)
    at org.h2.server.web.WebApp.processRequest(WebApp.java:176)
    at org.h2.server.web.WebThread.process(WebThread.java:154)
    at org.h2.server.web.WebThread.run(WebThread.java:103)
```

Allez sur java 21, la version 23 est trop haut level pour h2.

"Class has been compiled by a more recent version of the Java Environment"

Pensez à switcher java 21 partout :

-au niveau du pom.xml

-au niveau de la structure du projet :
    - sdks et module et 
    
- clean + compile + package

état final de persistence.xml :

```xml

<persistence xmlns="http://java.sun.com/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://java.sun.com/xml/ns/persistence https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd"
             version="2.0">

    <persistence-unit name="main-persistence-unit">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <exclude-unlisted-classes>false</exclude-unlisted-classes>

        <properties>
            <!-- proper path but needs java21 -->
            <!-- it seems to work with version 23 too... so???-->
            <property name="jakarta.persistence.jdbc.url"
                      value="jdbc:h2:tcp://localhost/~/h2DB"/>

            <!--
            not the proper path  for h2
            <property name="jakarta.persistence.jdbc.url"
                      value="jdbc:h2:mem:h2DB"/>

                      -->

            <!-- Credentials -->
            <property name="jakarta.persistence.jdbc.user"
                      value="sa"/>
            <property name="jakarta.persistence.jdbc.password"
                      value=""/>

            <!--not the proper path for h2:
            <property name="jakarta.persistence.jdbc.url" value="jdbc:p6spy:h2:mem:h2DB" />
            -->
            <!-- proper path but needs java21-->

            <property name="jakarta.persistence.jdbc.url" value="jdbc:p6spy:h2:tcp://localhost/~/h2DB" />

            <!-- Automatic schema export -->
            <property name="jakarta.persistence.schema-generation.database.action"
                      value="drop-and-create"/>

            <!-- SQL statement logging -->
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <property name="hibernate.highlight_sql" value="true"/>

        </properties>

    </persistence-unit>

</persistence>

```

état final de application.properties :

```properties 





#new data

# Config bdd H2
server.port=8080
server.address=localhost
#register in files but not in h2
#spring.datasource.url=jdbc:h2:./src/main/resources/db/db

#il faut cibler la bdd de la version browser
# la version du browser
# correspond à celle visible sur le site web de h2:
# jdbc:h2:tcp//localhost/~/h2DB
# c'est aussi celle écrite dans persistence.xml
# voir : https://stackoverflow.com/questions/61797029/why-h2-database-doesnt-show-my-table-in-the-browser

#have to be run with JAVA 21 NOT 23 otherwise : "driver error"...
#it seems to laso work with 23 version so.. ???
spring.datasource.url=jdbc:h2:tcp://localhost/~/h2DB

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update

# Activer la console H2
#spring.h2.console.enabled=true
spring.h2.console.enabled=false
spring.h2.console.path=/h2DB/
spring.h2.console.settings.trace=false
spring.h2.console.settings.web-allow-others=true

```

Pour résoudre les erreurs :

"NULL not allowed for column "NOMCOLMUNID" ID ; SQL statement:" 

"Caused by: org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException: NULL non permis pour la colonne
"
-->

```java

/*supprimer cette ligne*/
@GeneratedValue(strategy=GenerationType.IDENTITY, ge) 

```

et remplacer par 

```java

@GeneratedValue(strategy = GenerationType.AUTO)

```

ajouter au début de la class 

```java
@Component
```

résoudre : 

"Caused by: org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException: 
Violation d'index unique ou clé primaire: "PRIMARY KEY ON PUBLIC."

--> il faut vous assurer que vous possédez pour votre class
au - 1 constructeur qui prend en paramètre tous les champs de la class
(dont l'id).



problème  : 


cross origin permet de résoudre le problème de :

```java
@RestController
@RequestMapping
@CrossOrigin("*")
public class UserController {}
```


No 'Access-Control-Allow-Origin'.

solution plus propre : https://stackoverflow.com/questions/44697883/can-you-completely-disable-cors-support-in-spring

```
/*
enables to handle security properly
https://stackoverflow.com/questions/44697883/can-you-completely-disable-cors-support-in-spring
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }
}
```

Une autre possbilité est l'utilisation des fichiers de sécurité : 

"SecurityConfig"
"AuthController"



créent une sécurité renforcées qui requiert une authentification.
Mettez en commentaire l'entierté de ces fichiers hormis la déclaration de la class.
Sinon vous aurez des bugs avec le controller qui fera des doubles appels si 
vous ne commentez pas les champs et les méthodes.


il faut aussi mettre dans application.properties :

```properties 

spring.thymeleaf.enabled =false

```

pour désactiver thymeleaf


erreur :

"Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Failed to initialize dependency 'dataSourceScriptDatabaseInitializer' of LoadTimeWeaverAware bean 'entityManagerFactory': Error creating bean with name 'dataSourceScriptDatabaseInitializer' defined in class path resource [org/springframework/boot/autoconfigure/sql/init/DataSourceInitializationConfiguration.class]: Unsatisfied dependency expressed through method 'dataSourceScriptDatabaseInitializer' parameter 0: Error creating bean with name 'dataSource' defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class]: Failed to instantiate [com.zaxxer.hikari.HikariDataSource]: Factory method 'dataSource' threw exception with message: URL must start with 'jdbc'"

solution pensez à mettre cette fonction dans le main 

de Application.java :

```java

@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        /*
        it loads .env file
         */
         /*
        it loads .env file
         */
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        configurer.setLocation(new FileSystemResource("javaForHistory_work/history_work/src/.env"));
        return configurer;
    }
```

pb :

"Could not resolve placeholder 'HOST_EMAIL_USERNAME' in value "${HOST_EMAIL_USERNAME}""



pb : 

" Error creating bean with name 'emailServiceImpl': Injection of autowired dependencies failed" 

" Could not resolve placeholder 'spring.mail.username' in value "${spring.mail.username}" "

solution :


Vérifiez que le champ : 

"spring.mail.username" n'est pas commenté



pb : 

"movie.YEARofRelease is null while is primitive (setter)"

solution : 

vérifiez les getters et setters de la class movie,
assurez vous qu'ils sont bien nommez, qu'ils utilisent des types primitifs tout comme la classe elle-même


exporter des variables d'environnement : 

créez un .env et placez-le dans : 

src >

écrives les variables qui vous intéressent (attention il ne faut pas espacer les = des noms des variables) :

```xml
HOST_EMAIL_USERNAME=blabla
HOST_EMAIL_PASSWORD=blibli

...
```

envoyez les variables dans l'environnement :

```shell
export $(grep -v '^#' .env | xargs)
```

à l'endroit où se trouve le .env



vérifiez que les variables sont bien exportées : 

```
printenv
```

aide :

https://www.digitalocean.com/community/tutorials/how-to-read-and-set-environmental-and-shell-variables-on-linux

maintenant vous pouvez les utiliser dans properties en faisant :

spring.mail.username=${HOST_EMAIL_USERNAME}


n'utilisez pas le champs de properties pour l'import vers java,
utilisez direcent la variable d'environnement :


```java
@Value("${HOST_EMAIL_USERNAME}")   // Takes the value inside the application.properties file
    private String sender;
```

Pour les mails il faut mettre dans application.java: 


```java
 @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        /*
        it loads .env file
         */
        System.out.println("Working Directory = " + System.getProperty("user.dir")); ///juste des tests
        configurer.setLocation(new FileSystemResource("src/main/java/.env")); //path du .env
        return configurer;
    }
```

ces lignes : 

```java

 @Bean
    CommandLineRunner init(UserRepository userRepository, CartService cartService, BookService bookService, BookRepository bookRepository, OrderService orderService,
                           FrenchAddressRepository frenchAddressRepository, MailService mailService) {
        return args -> {
            //créé des users randoms et les persists en base

//            Stream.of("RyanGosling", "MarlonB", "BobbyB ").forEach(name -> {
//                        var user = new User(name, name+"Family", name, new BCryptPasswordEncoder().encode("ADAM$2456"), name + "@gmail.com"
//                        );
//                        var frenchAddress = new FrenchAddress(user.getPseudo().length(), "Auguste Rodin", "Champs-Sur-Marne", "77420", user);
//
//                        userRepository.save(user);
//                        frenchAddressRepository.save(frenchAddress);
//                    });
//
//                        var lastJKBook = bookRepository.save(bookService.searchBookByAuthor("J.K. Rowling").getLast());
//                        var firstJKBook = (bookRepository.save(bookService.searchBookByAuthor("J.K. Rowling").getFirst()));
//
//                        System.out.println("les books valent : "+lastJKBook+ " et le deuxième book: "+firstJKBook);
//////
////                        //montre les users
////
////                        System.out.println("L'ensemble des users en bdd : \n");
////                        userRepository.findAll().forEach(user -> System.out.println(user));
//            var user1 = userRepository.findAll().iterator().next();
//           System.out.println("on vérifie que les infos ont été correctmeent récupérées pour les mails :"+Mail.getUniqueInstance().getDeveloperEmailAddress());
//        };
            // }


            /**
             * necessary for mailing
             * @return
             */

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
//        /*
//        it loads .env file
//         */
//        System.out.println("Working Directory = " + System.getProperty("user.dir"));
//        configurer.setLocation(new FileSystemResource("src/main/resources/.env"));
//        return configurer;
//    }
            System.out.println("on teste que le mail du chef est bien envoyé par le .env : "+mailService.getDeveloperEmailAddress()
            +" et le mot de passe :"+mailService.getSmtpPassword());

        };
    }
```

doivent permettre de d'afficher correctement le mdp et le mail du développeur à l'activation de application.java.


résoudre le problème :

"failed to lazily initialize a collection of role: nate.company.history_work.siteTools.user.User.watchMovies: could not initialize proxy - no Session"

solution :


faites le mappedBy de cette façon :

```java

//fichier 1 
@ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(
            name = "user_watch_movie",
            joinColumns = @JoinColumn(name = "movieid"),
            inverseJoinColumns = @JoinColumn(name = "userid"))
    private Set<User> isWatchedBy = new HashSet<User>();


//fichier 2 

 @ManyToMany(mappedBy="isWatchedBy")
    //@JoinColumn(name = "idmovie")
    private Set<Movie> watchMovies = new HashSet<>();

```



Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Cannot invoke "org.hibernate.mapping.ToOne.getReferencedEntityName()" because "toOne" is null

pb :

failed to lazily initialize a collection of role: nate.company.history_work.siteTools.user.User.watchMovies: could not initialize proxy - no Session

solution : 

ne créez pas de nouvelles données et n'en chargez pas depuis la partie "Application.java",
cela entre en conflit avec le lazy qui prépare les données plus tard et qui donc rend impossible l'utilisation des repositories à ce moment-là

résoudre :

"user-form.component.ts:117 
 POST http://localhost:8080/userSearch 405 (Method Not Allowed)"

 --> côté angular 


 solution :

vérifiez que vous avez bien créé un controller avec ce path là (c'est peut être un ancien path).

pb : 


Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.

solution  : 

MAVEN > CLEAN + COMPILE 