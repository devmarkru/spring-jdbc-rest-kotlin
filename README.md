# spring-jdbc-rest-kotlin
Пример rest-сервиса, написанный на **Kotlin** с использованием **Spring JDBC API**.

## Взаимодействие с БД
Для работы данного примера требуется БД **postgres** и таблица со следующей структурой:

```sql
create table cargo (
    id serial primary key,
    title varchar not null,
    passenger_count int
);
```

### Полезные ссылки
* [Restful-сервис на Spring JDBC и Kotlin](https://devmark.ru/article/restful-spring-jdbc-api-kotlin).
* [Новости проекта](https://t.me/+RjrPWNUEwf8wZTMy) и короткие заметки.
* Ещё больше статей по разработке ПО вы можете найти на моём сайте [devmark.ru](https://devmark.ru/).
