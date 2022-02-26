create table if not exists person(
    id       serial primary key not null,
    login    varchar(200),
    password varchar(200)
);

insert into person (login, password) values ('parsentev', '123');
insert into person (login, password) values ('ban', '123');
insert into person (login, password) values ('ivan', '123');