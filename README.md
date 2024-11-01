# What is it about : 

This project is for now just a group of page that enable to register in database 
some new users.
The actual purpose of the project, is to enable users to create their own history of books,
movies, series, anime or manga, with the last moment you stopped them.
It would enable users to know which one they can read next.

# Future features

1- user authentification
2- add manga
3- add book
4- enable to seek among books and mangas through a mobile list.


# How to start the project :

Choose the "src" folder as the "Sources Root".
Add the dependecies that are in the folder named : 
"dependencies_for_VideoConverter"
to enable to use "SpringBoot".
If it is asked, defined them as "Jar directories".

Go to the pom.xml file, right-click in the file and choose to 
consider this file as maven project.

If you have this error :

```
BeanDefinitionStoreException: Failed to parse
```
Go to maven menu and click on "clean" and the two arrow in circle
"reload all maven incremental".
Now the Application.java should work


In order to run the project, 

1) from intelliJ :

run the "Application.java".

It enables to run the database.


3) in a terminal :
```bash 
cd Historique_oeuvres/javaForHistory_wo
rk/history_work/src/main/java/nate/company/history_work/angular/angularclient
```
run the following command : 
```bash
ng serve --open
```






