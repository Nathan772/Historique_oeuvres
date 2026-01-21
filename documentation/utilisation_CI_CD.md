Problème :

Difficulté sur Github actions, erreur lors du lancement de la pipeline CI.

Solution :

Regardez dans les erreurs la partie "Caused by" (recherche par mots-clés) pour comprendre en détail la cause de l'erreur.


exemple :

Caused by: org.springframework.beans.factory.BeanInitializationException: Could not load properties
	at org.springframework.context.support.PropertySourcesPlaceholderConfigurer.postProcessBeanFactory(PropertySourcesPlaceholderConfigurer.java:169) ~[spring-context-6.1.12.jar:6.1.12]
	at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:363) ~[spring-context-6.1.12.jar:6.1.12]
	at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:189) ~[spring-context-6.1.12.jar:6.1.12]
	at org.springframework.context.support.AbstractApplicationContext.invokeBeanFactoryPostProcessors(AbstractApplicationContext.java:789) ~[spring-context-6.1.12.jar:6.1.12]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:607) ~[spring-context-6.1.12.jar:6.1.12]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:754) ~[spring-boot-3.3.3.jar:3.3.3]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:456) ~[spring-boot-3.3.3.jar:3.3.3]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:335) ~[spring-boot-3.3.3.jar:3.3.3]
	at org.springframework.boot.test.context.SpringBootContextLoader.lambda$loadContext$3(SpringBootContextLoader.java:137) ~[spring-boot-test-3.3.3.jar:3.3.3]
	at org.springframework.util.function.ThrowingSupplier.get(ThrowingSupplier.java:58) ~[spring-core-6.1.12.jar:6.1.12]
	at org.springframework.util.function.ThrowingSupplier.get(ThrowingSupplier.java:46) ~[spring-core-6.1.12.jar:6.1.12]
	at org.springframework.boot.SpringApplication.withHook(SpringApplication.java:1463) ~[spring-boot-3.3.3.jar:3.3.3]
	at org.springframework.boot.test.context.SpringBootContextLoader$ContextLoaderHook.run(SpringBootContextLoader.java:553) ~[spring-boot-test-3.3.3.jar:3.3.3]
	at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:137) ~[spring-boot-test-3.3.3.jar:3.3.3]
	at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:108) ~[spring-boot-test-3.3.3.jar:3.3.3]
	at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:225) ~[spring-test-6.1.12.jar:6.1.12]
	at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:152) ~[spring-test-6.1.12.jar:6.1.12]
	... 73 common frames omitted
Caused by: java.io.FileNotFoundException: javaForHistory_work/history_work/src/.env


Ici le problème est le fichier ".env" qui n'est pas trouvé par le programme car il est censé seulemnt exister en local. Il fallait donc retirer les utilisations/appels explicites du fichier ".env" dans le programme.

