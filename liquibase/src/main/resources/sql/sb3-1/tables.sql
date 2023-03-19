create sequence sb3.utente_sequence start 1 increment 1;
create sequence sb3.permesso_sequence start 1 increment 1;

create table sb3.utente(
    email varchar primary key,
    username varchar not null,
    password varchar not null
);

create table sb3.permesso(
    id integer primary key,
    nome varchar not null,
    descrizione varchar
);

create table sb3.permessoutente(
    email_utente varchar not null,
    id_permesso integer not null,
    constraint email_utente_fkey foreign key (email_utente) references sb3.utente(email),
    constraint id_permesso_fkey foreign key (id_permesso) references sb3.permesso(id),
    primary key (email_utente, id_permesso)
);