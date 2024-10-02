## Task HTTP CRUD server for social network by Ivan Sosniuk

**Опції файлів скриптів для запуску програми:**
 - **Запуск portable runnable jar:**
    - launch-jar.bat
    - launch-jar-in-bash.sh

**Директорія запуску(коренева папка проєкту) повинна містити файли users.json та tokens** з інформацією про користувачів та токенами для автентифікації/авторизації. У ці файли можна додавати нових користувачів та токени, що задають окреме право на кожну дію. Приклади присутні в файлах проєкту. 

HTTP запити здійснюються за портом 4567  на **/users**
Запити працюють на localhost, *локальну та глобальну  IP адреси(за відповідних мережевих налаштувань)*
Запит мусить містити заголовок Authorization з токеном з відповідними правами з файлу tokens та request body(json для надсилання інформації про користувачів)
**Далі приклади запитів для CRUD дій у PowerShell:**

 - **GET - отримати дані всіх юзерів(/users)** 
 - Invoke-WebRequest -Uri "http://localhost:4567/users" -Method Get -Headers @{ Authorization = 'apXDHN7DV4MV9lrTTouIEzGs0uyaDmau' } -UseBasicParsing
   
 - **GET - отримати дані одного користувача(/users/`НІКНЕЙМ`)**
 - Invoke-WebRequest -Uri "http://localhost:4567/users/MrVanekTop"   
   -Method Get -Headers @{ Authorization = 'apXDHN7DV4MV9lrTTouIEzGs0uyaDmau' } -UseBasicParsing
 - **POST - додати одного користувача(/users)**
 - Invoke-WebRequest -Uri "http://localhost:4567/users" -Method Post
   -Headers @{ Authorization = 'apXDHN7DV4MV9lrTTouIEzGs0uyaDmau' } -Body "{"nickname":"HeartCore", "name":"Ivan", "age":"17", friends:["Spudei1","Spudei2"]} " -UseBasicParsing
 - **PUT - редагувати одного наявного користувача(/users)**
 - Invoke-WebRequest -Uri "http://localhost:4567/users" -Method Put
   -Headers @{ Authorization = 'apXDHN7DV4MV9lrTTouIEzGs0uyaDmau' } -Body "{'nickname':'MrVanekTop', 'name':'Alex', 'age':'18', friends:['Ivan']}" -UseBasicParsing
 - **DELETE - видалити одного наявного користувача(/users/`НІКНЕЙМ`)**
 - Invoke-WebRequest -Uri "http://localhost:4567/users/MrVanekTop"
   -Method Delete -Headers @{ Authorization = 'apXDHN7DV4MV9lrTTouIEzGs0uyaDmau' } -UseBasicParsing

Основний код написано на Java 8. Для API ендпойнтів використовував фреймворк Spark.

**У випадку виникнення питань до коду звертайтеся в телеграм:
@heart_and_core**
