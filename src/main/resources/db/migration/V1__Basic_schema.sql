create table if not exists t_user (
    id           serial primary key,
    id_discord   text not null,
    c_nickname   text not null,
    c_first_name text null,
    c_surname    text null,
    c_email      text null
);

comment on table t_user is 'Таблица пользователей';
comment on column t_user.id is 'Идентификатор пользователя';
comment on column t_user.id_discord is 'Идентификатор пользователя в Discord';
comment on column t_user.c_nickname is 'Никнейм пользователя в Discord';
comment on column t_user.c_first_name is 'Имя пользователя';
comment on column t_user.c_surname is 'Фамилия пользователя';
comment on column t_user.c_email is 'Электронная почта пользователя';

create table if not exists t_reviewer(
    id      serial primary key,
    id_user int not null references t_user(id)
);

comment on table t_reviewer is 'Таблица ревьюеров';
comment on column t_reviewer.id is 'Идентификатор ревьюера';
comment on column t_reviewer.id_user is 'Идентификатор пользователя, связанного с ревьюером';

create table if not exists t_student(
    id      serial primary key,
    id_user int not null references t_user(id)
);

comment on table t_student is 'Таблица учеников';
comment on column t_student.id is 'Идентификатор ученика';
comment on column t_student.id_user is 'Идентификатор пользователя, связанного с учеником';

create table if not exists t_programming_language(
    id     serial primary key,
    c_name text not null
);

comment on table t_programming_language is 'Таблица используемых языков программирования';
comment on column t_programming_language.id is 'Идентификатор языка программирования';
comment on column t_programming_language.c_name is 'Название языка программирования';

create table if not exists t_reviewer_programming_language(
    id                      serial primary key,
    id_reviewer             int not null references t_reviewer(id),
    id_programming_language int not null references t_programming_language(id)
);

comment on table t_reviewer_programming_language is 'Таблица языки программирования ревьюеров';
comment on column t_reviewer_programming_language.id is 'Идентификатор языка программирования ревьюера';
comment on column t_reviewer_programming_language.id_reviewer is 'Идентификатор ревьюера';
comment on column t_reviewer_programming_language.id_programming_language is 'Идентификатор языка программирования';

create table if not exists t_session(
    id                      serial primary key,
    id_reviewer             int null references t_reviewer(id),
    id_student              int not null references t_student(id),
    id_programming_language int not null references t_programming_language(id),
    c_status                varchar(1) not null
);

comment on table t_session is 'Таблица сеансов между учеником и ревьюером';
comment on column t_session.id is 'Идентификатор сеанса';
comment on column t_session.id_reviewer is 'Идентификатор ревьюера';
comment on column t_session.id_programming_language is
    'Идентификатор языка программирования, для проверки кода на котором создан сеанс';
comment on column t_session.id_student is 'Идентификатор студента';
comment on column t_session.c_status is 'Статус сеанса';

create table if not exists t_task(
    id                      serial primary key,
    c_text                  text not null,
    c_review_text           text null,
    c_status                varchar(1) not null,
    id_session              int not null references t_session(id)
);

comment on table t_task is 'Таблица задач, присланных для проверки';
comment on column t_task.id is 'Идентификатор задачи';
comment on column t_task.c_text is 'Код на языке программирования, специализированном в сеансе';
comment on column t_task.c_review_text is 'Текст ревью';
comment on column t_task.c_status is 'Статус задачи';
comment on column t_task.id_session is 'Идентификатор сеанса, к которому относится данная посылка кода';

create table if not exists t_admin(
    id      serial primary key,
    id_user int not null references t_user(id)
);

comment on table t_admin is 'Таблица администраторов wall-e бота';
comment on column t_admin.id is 'Идентификатор администратора';
comment on column t_admin.id_user is 'Идентификатор пользователя, связанного с администратором';