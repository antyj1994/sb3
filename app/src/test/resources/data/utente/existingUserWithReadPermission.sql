insert into utente(id, email, username, password) values (0, 'user@user.com', 'user', '$2a$12$6dvrlZSCu5RsJzfxEcz.Mez7tT78jrFaIdg3cplga3Hq4zmRvjmpa');

insert into permesso(id, nome, descrizione) values (0, 'READ', 'this permission let`s you read users data');

insert into permessoutente(id_utente, id_permesso) values (0, 0);