alter table t_user add column id_chat bigint null;

create table if not exists t_telegram_request (
    id                  serial primary key,
    id_user             int not null references t_user(id),
    c_telegram_nickname text not null
);