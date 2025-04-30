from django.urls import path
from . import views

"""
the path
chatbot refers
to the folder named
"chatbot"
"""

app_name = "moview_review"

urlpatterns = [
    path('chatbot/',views.ChatbotView.as_view(http_method_names=['get'])),
    #path('users/',views.UserView.as_view(http_method_names=['post'])),
    #path('register/',views.UserView.as_view(http_method_names=['post'])), 
    #name="addUser"),
]