#!/usr/bin/env python3
#import required packages
import json

class User:
  def __init__(self, pseudo, password,email):
    self.pseudo = pseudo
    self.password = password
    self.id = 0
    self.email = email
    self.category = "average"

  """
  this method creates a json of the
  object.
  """
  def dumpToJson(self)->str:
    """
    way to create json:
    https://www.geeksforgeeks.org/convert-class-object-to-json-in-python/
    """
    return json.dumps(self.__dict__)


if __name__ == "__main__":
    print("This is the user.py file.")