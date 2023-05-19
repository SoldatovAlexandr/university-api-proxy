# university-api-proxy

API прокси сервис для мобильного приложения для студентов ОмГТУ. Разрабатывался в рамках бакалаврской дипломной работы.

Сервис необходим для предоставления единого API клиенту. Часть информации приходится парсить с сайта, часть в других
сервисах по API.

Запустить в докере:

`docker-compose build`

`docker-compose up`

Переменные окружения необходимые для запуска проекта:

* OMSTU_SCHEDULE_URL - https://rasp.omgtu.ru/api/
* OMSTU_AUTH_URL - https://omgtu.ru/
* OMSTU_EDUCAB_URL - http://up.omgtu.ru/
