##API Description

###RentController - управление заметками

* Method GET: ```http://services.nesterenya.com/ads/rent/all``` - вернет тестовый набор из трех заметок

* Method GET: ```http://services.nesterenya.com/ads/rent/test_parsed```- распарсит сайт и вернет последних 10 заметок (пока их никуда не сохраняет, поэтому работает не быстро)

* *new*

* Method GET ```http://services.nesterenya.com/ads/rent/count/{количество карточек на странице}``` - вернет количество страниц с объявлениями

* Method GET ```http://services.nesterenya.com/ads/get/{количество карточек на странице}/{номер страницы}``` - вернет указанную страницу, (пока сортировка выполняется по дате)


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


