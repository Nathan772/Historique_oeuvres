from django.urls import path
from . import views

"""
le path 
"users" fait référence
 au dossier "users"
"""

app_name = "moview_review"

urlpatterns = [
    path('users/',views.UserView.as_view(http_method_names=['get'])),
    #path('users/',views.UserView.as_view(http_method_names=['post'])),
    path('register/',views.UserView.as_view(http_method_names=['post'])), 
    #name="addUser"),
]