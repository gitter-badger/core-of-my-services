##API Description

###ImageController - для управлением загрузкой изображений (ВНИМАНИЕ - пока изображения не сохряняются в БД, они временно кешируются)

логика работы с этим API проста, при добавлении записи сохраняешь изображения (перед тем как сохранить запись), и получаешь для каждого изображения ID - который сохраняешь для каждой заметки, а потом для отображения обращаешься к изображению по этому ID например ```http://services.nesterenya.com/images/get/55b3634e8025742ac0d88ec5```

* Method GET: ```http://services.nesterenya.com/images/upload``` - просто говорит что нужно использовать POST запрос

* Method POST: ```http://services.nesterenya.com/images/upload``` - нужно послать файл, в случае ошибки вернет текст, если изображение сохранилось, вернет id изображения ```55b3625a8025742ac0d88ec4``` id нужен для последующего доступа к изображениям   (file upload, как делать с помощью angular тут написано http://stackoverflow.com/questions/13963022/angularjs-how-to-implement-a-simple-file-upload-with-multipart-form) простейший пример загрузки ```http://services.nesterenya.com/upload.html```

* Method GET: ```http://services.nesterenya.com/images/get/{id}``` - вернуть изображение по ID (если изображения нету, то сгенерируется случайное изображение)  - например ```http://services.nesterenya.com/images/get/55b3634e8025742ac0d88ec5``` 

* Method GET: ```http://services.nesterenya.com/images/test/{id}``` - вернуть случайное изображение с ID (Для отладки)  - например ```http://services.nesterenya.com/images/test/55b3634e8025742ac0d88ec5``` 

###RentController - управление заметками

* Method POST: ``` http://services.nesterenya.com/rent/view/{id} ``` - увеличить просмотр

* Method POST: ``` http://services.nesterenya.com/rent/add ``` - добавить запись, минимальный объект ```json {"description":"bla bla bla!!!!", "address":"gomel", "contacts": "+485789476"} ``` (это обязательные поля). Можно добавить следующие поля при отправки: ```json {"address":"gomel ","cost":"190$","roomCount":2,"description":"bla bla","contacts":"+375 (29) 561-77","images": ["55be461b9f5f3023847a9d0a","55be461b9f5f3023847a9d0b"]} ```

* Method GET: ```http://services.nesterenya.com/rent/all``` - вернет тестовый набор из трех заметок

* Method GET: ```http://services.nesterenya.com/one/{id}``` - вернуть одну заметку по id

* Method GET ```http://services.nesterenya.com/rent/count/{количество карточек на странице}``` - вернет количество страниц с объявлениями

* Method GET ```http://services.nesterenya.com/get/{количество карточек на странице}/{номер страницы} [sortBy=| date,-date,roomCount,-roomCount]``` - вернет указанную страницу, (пока сортировка выполняется по дате)

метод принимает не обязательный параметр sortBy с допустимыми date,-date,roomCount,-roomCount - знак минус для сортировки в обратном порядке, по умолчанию сортировка по -date , если не был указан парамет или указан не допустимый

### WishController API (Для управления пожеланиями):

* Method POST: ```services.nesterenya.com/wish/add``` - для добавления заметки, параметр передается в теле запроса (json/application) с объектом ```json{"text":"And this"}``` или ```{"text":"And this", "likes": 0}``` ответ в таком виде, тоже объявление, только с ID:
```json
{
"id": "55b0d9965cb4e31d3c64553e",
"text": "And this",
"likes": 0
}
```
Если переданы не правильные аргументы будет ошибка 400 (с сообщением bad arguments)

* Method GET: ```services.nesterenya.com/wish/all``` -для получения всех пожеланий GET:
* Method GET: ```services.nesterenya.com/api/wish/get/{id}``` -
для получения одного пожелания по id **example**: ```services.nesterenya.com/wish/get/55b0d9965cb4e31d3c64553e```

* Method POST: ```services.nesterenya.com/wish/like/{id}``` - лайкнуть запись **example**: ```services.nesterenya.com/wish/like/55b0d9965cb4e31d3c64553e```

* Method POST: ```services.nesterenya.com/wish/dislike/{id}``` -
для дизлайка **example**: ```services.nesterenya.com/wish/dislike/55b0d9965cb4e31d3c64553e```


