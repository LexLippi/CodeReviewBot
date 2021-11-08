create table if not exists t_programming_language_alias(
    id serial primary key,
    id_programming_language int not null references t_programming_language(id),
    c_alias text not null
);

comment on table t_programming_language_alias is 'Таблица сокращений для используемых языков программирования';
comment on column t_programming_language_alias.id is 'Идентификатор сокращения языка программирования';
comment on column t_programming_language_alias.id_programming_language is 'Идентификатор языка программирования';
comment on column t_programming_language_alias.c_alias is 'Сокращение языка программирования';