create table if not exists persons(
    id       serial primary key not null,
    login    varchar(200),
    password varchar(200)
);

create table if not exists employees(
    id serial primary key,
    name      varchar(50),
    surname   varchar(50),
    inn       int,
    hire_date timestamp
);

create table if not exists employees_persons(
    employee_id int references employees(id),
    person_id   int references persons(id)
);

insert into persons (login, password) values ('parsentev', '123');
insert into persons (login, password) values ('ban', '123');
insert into persons (login, password) values ('ivan', '123');
insert into persons (login, password) values ('vasya', '123');

insert into employees (name, surname, inn, hire_date)
values ('emp1', 'emp1', 1111, '2022-02-28 10:00:00'),
       ('emp2', 'emp2', 2222, '2022-02-28 10:01:00'),
       ('emp3', 'emp3', 3333, '2022-02-28 10:02:00');


insert into employees_persons (employee_id, person_id) values (1, 1);
insert into employees_persons (employee_id, person_id) values (1, 2);
insert into employees_persons (employee_id, person_id) values (2, 3);
insert into employees_persons (employee_id, person_id) values (3, 4);