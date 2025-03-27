# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


# class Genre(models.Model):
#     idgenre = models.BigAutoField(primary_key=True)
#     name = models.CharField(max_length=200, blank=True, null=True)

#     class Meta:
#         managed = False
#         db_table = 'genre'
#         app_label = 'my_movie_review'


# class Hasgenremanga(models.Model):
#     idhasgenreman = models.BigAutoField(db_column='idHasGenreMan', primary_key=True)  # Field name made lowercase.
#     idgenre = models.ForeignKey(Genre, models.DO_NOTHING, db_column='idgenre')
#     idmanga = models.ForeignKey('Manga', models.DO_NOTHING, db_column='idmanga')

#     class Meta:
#         managed = False
#         db_table = 'hasgenremanga'
#         app_label = 'my_movie_review'


# class Hasgenremovie(models.Model):
#     idhasgenremovie = models.BigAutoField(db_column='idHasGenreMovie', primary_key=True)  # Field name made lowercase.
#     idgenre = models.ForeignKey(Genre, models.DO_NOTHING, db_column='idgenre')
#     idmovie = models.ForeignKey('Manga', models.DO_NOTHING, db_column='idmovie')

#     class Meta:
#         managed = False
#         db_table = 'hasgenremovie'
#         app_label = 'my_movie_review'


# class Manga(models.Model):
#     idmanga = models.BigAutoField(primary_key=True)
#     title = models.CharField(max_length=300)

#     class Meta:
#         managed = False
#         db_table = 'manga'
#         app_label = 'my_movie_review'


# class Movie(models.Model):
#     idmovie = models.BigAutoField(primary_key=True)
#     title = models.CharField(max_length=255, blank=True, null=True)
#     yearofrelease = models.PositiveIntegerField(db_column='yearOfRelease', blank=True, null=True)  # Field name made lowercase.
#     director = models.CharField(max_length=255, blank=True, null=True)
#     imdbid = models.CharField(db_column='imdbID', unique=True, max_length=255, blank=True, null=True)  # Field name made lowercase.
#     year_of_release = models.IntegerField()

#     class Meta:
#         managed = False
#         db_table = 'movie'
#         app_label = 'my_movie_review'


# class MovieSeq(models.Model):
#     next_val = models.BigIntegerField(blank=True, null=True)

#     class Meta:
#         managed = False
#         db_table = 'movie_SEQ'
#         app_label = 'my_movie_review'


# class MovieSeq(models.Model):
#     next_val = models.BigIntegerField(blank=True, null=True)

#     class Meta:
#         managed = False
#         db_table = 'movie_seq'


# class Readmanga(models.Model):
#     idreadman = models.BigAutoField(primary_key=True)
#     iduser = models.ForeignKey('UserTable', models.DO_NOTHING, db_column='iduser')
#     idmanga = models.ForeignKey(Manga, models.DO_NOTHING, db_column='idmanga')
#     current_state = models.CharField(max_length=200)
#     lastupdate = models.DateTimeField(db_column='lastUpdate')  # Field name made lowercase.
#     lastchapterread = models.PositiveIntegerField(db_column='lastChapterRead', blank=True, null=True)  # Field name made lowercase.
#     lastvolumeread = models.PositiveIntegerField(db_column='lastVolumeRead', blank=True, null=True)  # Field name made lowercase.

#     class Meta:
#         managed = False
#         db_table = 'readmanga'
#         unique_together = (('iduser', 'idmanga'),)


class User(models.Model):
    iduser = models.BigIntegerField(primary_key=True)
    category = models.CharField(max_length=255, blank=True, null=True)
    email = models.CharField(max_length=255, blank=True, null=True)
    password = models.CharField(max_length=255, blank=True, null=True)
    pseudo = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'user'
        app_label = 'my_movie_review'


class UserSeq(models.Model):
    next_val = models.BigIntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'user_seq'
        app_label = 'my_movie_review'

#models are necessary to enable save in database
class UserTable(models.Model):
    iduser = models.BigAutoField(primary_key=True)
    pseudo = models.CharField(unique=True, max_length=255, blank=True, null=True)
    email = models.CharField(unique=True, max_length=255, blank=True, null=True)
    password = models.CharField(max_length=255, blank=True, null=True)
    category = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'user_table'
        app_label = 'my_movie_review'

#it's highly recommended to let django create their own models
# and not using __init__
    #it seems unnecessary
    # def __init__(self, json_user_as_dict:dict):
    #     self.iduser = json_user_as_dict["id"]
    #     self.pseudo = json_user_as_dict["pseudo"]
    #     self.email = json_user_as_dict["email"]
    #     self.password = json_user_as_dict["password"]
    #     self.category = json_user_as_dict["category"]

class UsersSeq(models.Model):
    next_val = models.BigIntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'users_seq'
        app_label = 'my_movie_review'


# class WatchMovie(models.Model):
#     idwatch_movie = models.BigIntegerField(primary_key=True)
#     current_state = models.CharField(max_length=255, blank=True, null=True)
#     idmovie = models.BigIntegerField()
#     iduser = models.BigIntegerField()
#     last_moment = models.TimeField(blank=True, null=True)
#     last_update = models.DateField(blank=True, null=True)
#     id_user = models.BigIntegerField()

#     class Meta:
#         managed = False
#         db_table = 'watch_movie'


# class WatchMovieSeq(models.Model):
#     next_val = models.BigIntegerField(blank=True, null=True)

#     class Meta:
#         managed = False
#         db_table = 'watch_movie_seq'


# class Watchmovie(models.Model):
#     idwatchmovie = models.BigAutoField(primary_key=True)
#     iduser = models.ForeignKey(UserTable, models.DO_NOTHING, db_column='iduser')
#     idmovie = models.ForeignKey(Movie, models.DO_NOTHING, db_column='idmovie')
#     currentstate = models.CharField(db_column='currentState', max_length=255, blank=True, null=True)  # Field name made lowercase.
#     lastupdate = models.DateTimeField(db_column='lastUpdate', blank=True, null=True)  # Field name made lowercase.
#     lastmoment = models.TimeField(db_column='lastMoment')  # Field name made lowercase.
#     current_state = models.CharField(max_length=255, blank=True, null=True)
#     last_moment = models.TimeField(blank=True, null=True)
#     last_update = models.DateTimeField(blank=True, null=True)

#     class Meta:
#         managed = False
#         db_table = 'watchmovie'
#         unique_together = (('iduser', 'idmovie'),)


# class WatchmovieSeq(models.Model):
#     next_val = models.BigIntegerField(blank=True, null=True)

#     class Meta:
#         managed = False
#         db_table = 'watchmovie_SEQ'


# class WatchmovieSeq(models.Model):
#     next_val = models.BigIntegerField(blank=True, null=True)

#     class Meta:
#         managed = False
#         db_table = 'watchmovie_seq'
