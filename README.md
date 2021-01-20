# Exchange-Rates-WebApplication
Cервис, который обращается к сервису курсов валют, и отдает gif в ответ

## Описание

Cервис запрашивает курс доллара по отношению к рублю по состоянию на сегодня и на вчера (UTC).
Если курс стал выше, то показывает gif с тегом 'rich', если ниже с тегом 'broke'.

Курс валют запрашивается с openexchangerates.org, изображения gif берутся в случайном порядке с giphy.com.

Стек технологий:
- Система автоматической сборки - Gradle
- Фреймворк Spring Boot 2, Java
- Для работы с HTTP клиентами используется Feign
- Тестирование реализовано с помощью встроенной в Spring Boot системы, написаны системные и модульные тесты
- Валюта, адреса внешних сервисов и параметры запросов вынесены в настройки
- Сборка и запуск приложения происходит посредством Docker контейнера


## Инструкция по запуску приложения
1. Скопировать проект на локальный диск
2. В терминале перейти в корневую директорию проекта и выполнить команду:
```console
    sudo docker build . --tag exchange_rates_app
```
3. После сообщения об успешной сборке выполнить команду:
```console
    sudo docker run -p 8080:8080 exchange_rates_app
```
4. После запуска приложения открыть браузер и в строке адресса ввести:

    http://localhost:8080/

На странице будет отображена информация о динамике рублевого курса за прошедшие сутки. 
При наличии динамики страница будет содержать изображение GIF с тэгом "rich" или "broke".
При неизменном курсе, изображение GIF будет отсутсвовать.
