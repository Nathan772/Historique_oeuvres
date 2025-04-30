installer django :

https://www.w3schools.com/django/django_install_django.php

--> regardez aussi les pages suivantes...

créer un projet django :

```shell
django-admin startproject my_movie_review
```
"my_tennis_club"

--> nom du projet "my_movie_review"

lancer le serveur django :

python3 manage.py runserver

résoudre :

"You have 18 unapplied migration(s). Your project may not work properly until you apply the migrations for app(s): admin, auth, contenttypes, sessions. Run 'python manage.py migrate' to apply them."

--> https://stackoverflow.com/questions/43718536/django-python-manage-py-migrate-does-nothing-at-all

python3 manage.py makemigrations
python3 manage.py migrate

créer une app :

```shell
python3 manage.py startapp users
```


La vue "views.py" présente dans le dossier "users" automatiquement créé par la commande précédente, est juste un fichier python
contenant des fonctions qui vont permettre 
de répondre aux requêtes https 
associées à des adresses précises,
et cela va renvoyer des réponses,
comme des documents html.

le fichier :

"urls.py" associé à une application (ici le dossier users)
est lui-même associé des chemins vers des vues.
Le fichier "urls.py" doit être écrit et créé entièrement par vous-mêmes.

il y a aussi un dossier par défaut un fichier
"urls.py" associé au projet "my_movie_review".
Il faut aussi le configuré, même si il y a déjà des infos présents
dedans.

on ajoutera 


```python
"path('', include('users.urls')),"
```

dans  :

```python 

urlpatterns = [
    path('', include('users.urls')),
    path('admin/', admin.site.urls),
]
```

attention : 

qd vous créerez votre page django,
il y a aura bien le 
"hello world" créé à l'@ :

'http://127.0.0.1:8000/users/'

mais vous aurez une erreur sur la page 
de base :

"http://127.0.0.1:8000/"




"https://stackoverflow.com/questions/38920003/how-to-return-data-from-an-api-in-django"

-> renvoyer des données via django associées à une url


les imports de fichiers django en python :

```
from . import my_user
```

si le projet vous renvoie un
[object object]:

1)
pensez à installer 

```shell

pip install django.cors.headers
```

2)

dans my_movie_review < my_movie_review < settings.py :

```

INSTALLED_APPS = [
    [...]
    'corsheaders', --> ajoutez cette ligne
]

MIDDLEWARE = [
    [...]
    'corsheaders.middleware.CorsMiddleware',
    'django.middleware.common.CommonMiddleware',
] --> les deux dernières lignes



https://stackoverflow.com/questions/78275172/origin-checking-failed-http-localhost8000-does-not-match-any-trusted-origi

[...]

#fin du fichier
#--- propre (secure)
CORS_ORIGIN_ALLOW_ALL = True


#+++ propre

ALLOWED_HOSTS = ["http://localhost:4200", "127.0.0.1"]
SECURE_PROXY_SSL_HEADER = ('HTTP_X_FORWARDED_PROTO', 'https')
CSRF_TRUSTED_ORIGINS = ["http://localhost:4200"]
CSRF_ALLOWED_ORIGINS = ["http://localhost:4200"]
CORS_ORIGINS_WHITELIST = ["http://localhost:4200"]
```


résoudre :

"Fix TypeError: Converting circular structure to JSON in JS/TS"


```ts

getCircularReplacer() {
    const seen = new WeakSet();
    return (key : any, value : any) => {
        if (typeof value === "object" && value !== null) {
            if (seen.has(value)) {
                return;
            }
            seen.add(value);
        }
        return value;
    };
}
```

puis :

```angular
this.obj = JSON.stringify(this.obj, this.getCircularReplacer())
```

avec "obj" qui contient l'objet json récupéré.


en cas d'erreur du type:

```

Forbidden (Origin checking failed - http://localhost:4200 does not match any trusted origins.): /users/
[30/Jan/2025 17:00:18] "POST /users/ HTTP/1.1" 403 2569

```

source :

https://forum.djangoproject.com/t/origin-checking-failed-with-ssl-https/20158/16

allez dans settings.py, puis :

ALLOWED_HOSTS = ["http://localhost:4200", "127.0.0.1"]
SECURE_PROXY_SSL_HEADER = ('HTTP_X_FORWARDED_PROTO', 'https')
CSRF_TRUSTED_ORIGINS = ["http://localhost:4200"]
CSRF_ALLOWED_ORIGINS = ["http://localhost:4200"]
CORS_ORIGINS_WHITELIST = ["http://localhost:4200"]

mettez en commentaire : 

"#security but too powerful cannot handle for now
    #'django.middleware.csrf.CsrfViewMiddleware',"

dans :

"MIDDLEWARE = [
    'django.middleware.security.SecurityMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
    #security but too powerful cannot handle for now
    #'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
    'corsheaders.middleware.CorsMiddleware',
    'django.middleware.common.CommonMiddleware',
]"

création de views comme des class (modèle standard):

https://stackoverflow.com/questions/58779929/django-url-to-specific-request-method
https://docs.djangoproject.com/en/5.1/ref/class-based-views/base/


Pour les cookies qui permettent d'utiliser cstf :

```shell

yarn add ngx-cookie-service@11.0.2
```

résoudre : 

"- no module named rest_framework"


```shell
pip3 install djangorestframework
```

comment réutiliser une base de données existante : 

"https://www.reddit.com/r/djangolearning/comments/1c86aec/connecting_django_to_an_existing_database/"

"First you need to add the DB info to your settings.py file. Something like this:

DATABASES = { 'default': { 'ENGINE': 'django.db.backends.mysql', 'NAME': 'myDBname', 'USER': 'myUser', 'PASSWORD': 'myPass','HOST': '127.0.0.1', 'PORT': '3306', } }"


il faut aussi installer mysql client :


https://stackoverflow.com/questions/74730749/how-to-install-mysqlclient-python-library-in-linux

--> préparer python pour mysqlclient : 

```python

sudo apt-get install python3-dev default-libmysqlclient-dev build-essential
```

```

pip install mysqlclient

```

dans __init__.py : 

```

import pymysql

pymysql.install_as_MySQLdb()

```

pb : 

"BASE_DIR = NameError: name 'Path' is not defined django"

solution : 

"BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))"

pb : 

recréer les classes à partir de la base de données.

solution :

"python3 manage.py inspectdb > models.py" --> pour cela il faut configurer la bdd


pb :

"You have 18 unapplied migration(s). Your project may not work properly until you apply the migrations for app(s): admin, auth, contenttypes, sessions.
Run 'python manage.py migrate' to apply them."

solution : 

#'default': { 'ENGINE': 'django.db.backends.mysql', 'NAME': 'historyDB', 'USER': 'nathanb', 'PASSWORD': 'Orez_2456', 'HOST': '127.0.0.1', 'PORT': '3306', }

retirez cette ligne avec votre bdd ajoutée manuellement, vous la remetrez quand vous aurez bien tout configuré.

En attendant, remettez cette ligne :
"
DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.sqlite3',
        'NAME': BASE_DIR +"/"+'db.sqlite3',
    }
    #'default': { 'ENGINE': 'django.db.backends.mysql', 'NAME': 'historyDB', 'USER': 'nathanb', 'PASSWORD': 'Orez_2456', 'HOST': '127.0.0.1', 'PORT': '3306', }
}"

Pour importer des class par une bdd, il faut lancer : 

"python3 manage.py runserver" 

depuis la racine :

"my_movie_review"

Ensuite on doit lancer dans le même dossier :

python3 manage.py runserver

et normalement cela corrigera aussi : 

"You have 18 unapplied migration(s). Your project may not work properly until you apply the migrations for app(s): admin, auth, contenttypes, sessions.
Run 'python manage.py migrate' to apply them."


pour résoudre :




ajoutez pour chaque modèle généré :

```py
class UserTable(models.Model):
    iduser = models.BigAutoField(primary_key=True)
    pseudo = models.CharField(unique=True, max_length=255, blank=True, null=True)
    email = models.CharField(unique=True, max_length=255, blank=True, null=True)
    password = models.CharField(max_length=255, blank=True, null=True)
    category = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'user_table'
        ### cette ligne avec le nom du répertoire de models : my_moview_review
        app_label = 'my_movie_review'
```