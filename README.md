# Demo Spring Boot Bank Application
Банк заказывает разработку автоматизированной системы. Задача системы - хранить данные о клиентах банка (ФИО, адрес), счетах клиентов и совершенных транзакциях (пополнение счета/перевод со счета на счет/снятие со счета) и предоставлять доступ к этим данным. Количество клиентов банка - 10 миллионов, количество транзакций – 1 миллион в сутки.

Необходимо организовать систему хранения и API с реализацией следующей функциональности:
- Получение списка всех счетов клиента
- Получение информации по конкретному счету
- Снятие денег со счета
- Пополнение счета
- Перевод денег со счета на счет

## Используемые технологии
- JDK (Java SE Development Kit), версии [8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- Spring Boot v2.2
- HikariCP в качестве пула соединений JDBC
- PostgreSQL v12
- HSQLDB для юнит-тестов
- JMeter 5.1.1 для тестирования производительности

## Настройка БД
- адрес и порт сервера БД: localhost:5434
- название БД: sberbankdemosystem
- пользователь: postgres
- пароль: 1425 (указано в настройках для простоты запуска вместо передачи в виде параметра при запуске приложения)

## Запуск приложения локально

Сборка проекта происходит при помощи Maven. Вы можете собрать jar-файл и запустить его из командной строки:

```
git clone https://github.com/Sublimee/DemoSberbankApp.git
cd DemoSberbankApp
mvn package
cd target
java -jar demosberbankapp-0.0.1-SNAPSHOT.jar
```

## Оборудование и окружение для проведения тестирования
Тестирование проводилось на ПК со следующими характеристиками:
- процессор AMD Phenom II X6 1090T 3.2 GHz
- 24 GB RAM
- OS Windows
Разрабатываемое приложение и тестирующее ПО (JMeter) разворачивались в одном окружении. Тестирование проводилось в режиму GUI.

## Результаты тестирования производительности
Каждая строка таблицы соответствует определенному типу запроса. Результаты тестирования производительности API на описанном стенде показали среднюю пропускную способность на уровне 183 запроса/сек.
Увеличение времени тестирования не влияет на показатель производительности.

Ссылка для просмотра: https://ibb.co/dbjSp5f
