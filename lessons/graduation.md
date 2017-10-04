## Graduation project

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.

-----------------------------
<a href="http://blog.mwaysolutions.com/2014/06/05/10-best-practices-for-better-restful-api/">10 Best Practices for Better RESTful API</a>

P.S.: Make sure everything works with latest version that is on github :)

P.P.S.: Asume that your API will be used by a frontend developer to build frontend on top of that.

-----------------------------
### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Рекомендации
- **читаем ТЗ ОЧЕНЬ внимательно, НЕ надо ничего своего туда домысливать и творчески изменять**
- **тщательно считайте количество обращений в базу на каждый запрос**
- **тщательно считайте количество запросов в вашем API для отображения нужной информации**
- **учитывайте, что пользователей может быть ооочень много, а админов- мало**
- в проекте (и тестовом задании на работу) в отличии от нашего учебного topjava оставляйте только необходимый для работы приложения код, ничего лишнего:
  - НЕ надо делать разные профили базы и работы с ней. 
  - НЕ надо делать абстрактных контроллеров на всякий случай. 
  - НЕ надо делать классов репозиториев, если там нет ничего, кроме делегирования. 
  - Из потребностей приложения (которую надо самим придумать) реализовывать только очевидные сценарии. Те.- НИЧЕГО ЛИШНЕГО. 
  - НЕ надо все бездумно кэшировать
- базу лучше взять без установки (H2 или HSQLDB)
- по возможности сделать JUnit тесты
- уделяйте внимание обработке ошибок
- далаем REST API в соответствии с <a href="http://blog.mwaysolutions.com/2014/06/05/10-best-practices-for-better-restful-api/">концепцией REST</a>, **с учетом иерархии принадлежности объектов**
- не смешивайте TO и Entity вместе. Лучше всего, если они будут независимыми друг от друга.
- если приложению в объекте требуется только его id, используйте reference (как мы при сохранении еды вставляем туда юзера)
- [Use for money in java app](http://stackoverflow.com/a/43051227/548473)
- **Историю еды и голосований лучше сделать. Нужно различать базовые вещи, которые закладываются в архитектуру приложения и неочевидные доработки к ТЗ, которых лучше не делать.**

> Совершенство достигнуто не тогда, когда нечего добавить, а тогда, когда нечего отнять

_Антуан де Сент-Экзюпери_

