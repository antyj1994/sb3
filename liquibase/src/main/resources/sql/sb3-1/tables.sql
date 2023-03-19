create sequence sb3.utente_sequence start 1 increment 1;
create sequence sb3.permesso_sequence start 1 increment 1;

create table sb3.utente(
    id integer primary key,
    email varchar unique not null,
    username varchar not null,
    password varchar not null
);

create table sb3.permesso(
    id integer primary key,
    nome varchar not null,
    descrizione varchar
);

create table sb3.permessoutente(
    id_utente integer not null,
    id_permesso integer not null,
    constraint id_utente_fkey foreign key (id_utente) references sb3.utente(id),
    constraint id_permesso_fkey foreign key (id_permesso) references sb3.permesso(id),
    primary key (id_utente, id_permesso)
);