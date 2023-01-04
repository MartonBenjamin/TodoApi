# TodoApi
Beadandó feladat EKKE Keretrendszer alapú programozás tantárgyhoz

A projekt beállításoknál szükséges definiálni néhány környezeti változót:

DB_USER=hhwztjtg;
DB_PASSWORD=uHe2kH6EYXZR_aLWa6aq5YNMtHXU1KrP;

EMAIL_USER=google email;
EMAIL_PASSWORD=google api password;

Application.properties nem tartalmaz kényes adatot, így mellékeltem.
Mellékeltem továbbá a postman teszt requestek listáját is.

Az alkalmazás felhasználókhoz rendeli a TODO-kat, amik elvégezendő feladatok.
Az adatbázis a TODO, User, Role, TodoCategory táblákból áll, amiket CRUD műveletek megvalósításra kerültek.

Jogosultság kezelése Spring Security segítségével:

Admin éri el:
  "/admin/role/**"
  "/user/modify", "user/delete", "user/admin/getUsers"
  "/todo/category/add", "/todo/category/modify", "todo/category/delete/**"
User éri el:
  "/todo/**" (Saját todoin tud csak CRUD műveleteket végezni)
 Mindenki eléri:
  "/user/register", "/user/", "/user/reg", "/user/add"
  "/todo/category/get" (Todo kategóriák lekérdezése)
