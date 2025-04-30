#!/usr/bin/env python3
"""
django a sa propre structure d'importation pour les fichiers
python.
Ici on import 
"user.py."
"""
# from . import my_user
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
#import models
#from django.views.decorators.csrf import csrf_protect

# Create your views here.

#add by nathan

from django.utils.decorators import method_decorator
from django.views.decorators.csrf import ensure_csrf_cookie
from django.http import HttpResponse
from django.views import View

import json
from models import UserTable

from . import mychatbot

# source :
# https://stackoverflow.com/questions/58779929/django-url-to-specific-request-method
# https://docs.djangoproject.com/en/5.1/ref/class-based-views/base/
class ChatbotView(View):
    #@method_decorator(ensure_csrf_cookie)
    # def members(request):
        
    #     user = my_user.User("jean", "pazzword","jean@gmail.com")
    #     #return HttpResponse("Hello world!")
    #     #permet de gérer les get requests en envoyannt le user
    #     return HttpResponse(user.dumpToJson(), content_type='application/json')
    #get method for /users/



    # def home_page(request):
    #     return HttpResponse("<h1> Hello world! </h1>")

    #handle the request of
    # the user for chatbot ArtistAI
    def get(self, request, id=None):
        if request.method == "GET":
            #permet de gérer les get requests en envoyannt le user
            HttpResponse(request.GET.items())
            #works out
            jsonObject = json.loads(request.body.decode("utf-8"))
            print("on a reçu via une requête get d'un user 3 : "+jsonObject["content"])
            userRequest = jsonObject["content"]
            try:
                print("Traitement en cours par l'IA...")

                #prepare the answer of the llm
                answer = mychatbot.prepare_answer(userRequest)
                strResponse = '{"response":'+answer+'}'
                print("L'IA a fini sa réponse !")
                return HttpResponse(strResponse, content_type='application/json')

                #answer for the user
            except Exception as e:
                print("échec de la gestion de la requête du user")
                return HttpResponse("Erreur lors du traitement de votre requête, navrée !", content_type='application/json')


    
    #@csrf_exempt
    # def post(self,request):
    #     if request.method == 'POST':
    #         #HttpResponse(request.POST.items())
    #         #print("l'ensemble des users en bdd : "+str(User.objects.all()))
    #         print("on a reçu via une requête post un user : : "+ str(request.body))
    #         #return Response(status=status.HTTP_200_OK)
    #         #send response with the exact same user
    #         try:
    #             #made through import
    #             #user = UserTable(23,"JEAN", "NATHANbilingi@boby.fr","paaaasswwo", "ADMIN")
    #             #clear
    #             dico_user = json.loads(request.body)
    #             #dico_user['iduser'] = "269"
    #             #it will be replaced by autoincremental
    #             del dico_user['id']
    #             clearJson = json.dumps(dico_user)
    #             # clearJson = clearJson.replace("id","iduser")
    #             print("voici le clearedjson : "+clearJson)
    #             user2 = UserTable.objects.create(**json.loads(clearJson))
    #             #json.loads(request.body)
    #             #print("le user créé via le json est :"+str(user2))
    #             # Récupérer l'objet par son ID
    #             user = UserTable.objects.get(iduser=2)
    #             print("les infos du user qui existe déjà sont par exemple son pseudo: "+str(user.pseudo))  
    #             #save new user with new Id in database
    #             user2.save()
    #             #remove id database
    #             #user.remove()
    #             print("user sauvegardé en bdd : "+str(user2))
    #         except Exception as e:
    #             print("échec de création d'un objet de type user :( because : "+str(e))
    #         return HttpResponse(request.body, content_type='application/json')

    """this decorator is necessary 
    to prevent from csrf issue
    but it's a bad practice
    """
    """
    #handle post data
    @csrf_exempt
    def retrieveSentMember(request):
        if request.method == 'POST':
            #HttpResponse(request.POST.items())
            print(request.POST)
            return Response(status=status.HTTP_200_OK)"""


