# webmanager (Тестовое задание)
## Back-end
***
- Для доступа к базе используется Hinernate
- Для сохранения данных используется база embedded H2. Очишается при каждом старте сервера.
- При каждом обновлении стартовой страницы проверяется кол-во запсией в базе, если их менее 3-х, то добаляется необходимое количество. 
- Конфигурация БД находится в HibernateConfig, база H2 с легкостью заменяется на другую. Настройки для БД, меняюся в resources/hibernate.properties.

## Front-end 
***
- Используется AngularJS
- - Открытие узла происходит с заданой 2-х секундной задержкой 
- - Добавление узла второго уровня возможно после открытия его узла первого уровня
### Demo: http://www.upump.info/manager/
