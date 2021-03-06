# REST API
Серверная часть системы для сопровождения бизнес-процессов строительства и эксплуатации деревянных домов.

## Документация эндпоинтов
- swagger [https://app.swaggerhub.com/apis/LINA-kika/Rubcon/3.0.1]

Авторизация с помощью JWT токена (Bearer_...)

## Функционал
*Для клиента:*
- авторизация;
- просмотр списка проектов пользователя;
- просмотр документации по проекту;
- фильтрация документации по типу документа;
- поиск документа по названию;
- просмотр истории проведения мероприятий по уходу за жилым домом;
- поиск заявки на вызов мастера по названию;
- создание заявки на вызов мастера;
- просмотр истории чата между клиентом и компанией-исполнителем;
- отправка сообщения в чате с компанией-исполнителем;
- просмотр профиля клиента;
- редактирование данных клиента;
- просмотр данных клиента.

*Для администратора:*
- регистрация пользователя;
- добавление/удаление/редактирование документов;
- добавление/удаление/редактирование объектов;
- удаление/редактирование профилей пользователей;
- просмотр списка пользователей;
- просмотр списка объектов пользователя;
- фильтры для упрощённого поиска документации;
- история проведений мероприятий по уходу за жилым домом;
- поиск определённого мероприятия по уходу за домом;
- форма для обработки вызова мастера;
- список чатов с клиентами;
- отправка сообщений в выбранном чате.

