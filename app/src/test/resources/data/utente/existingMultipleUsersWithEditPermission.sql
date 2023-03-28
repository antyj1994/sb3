insert into utente(id, email, username, password) values
(0, 'user@user.com', 'user', '$2a$12$6dvrlZSCu5RsJzfxEcz.Mez7tT78jrFaIdg3cplga3Hq4zmRvjmpa'),
(1, 'anotheruser@user.com', 'anotherUser', '$2a$12$6dvrlZSCu5RsJzfxEcz.Mez7tT78jrFaIdg3cplga3Hq4zmRvjmpa');

insert into permesso(id, nome, descrizione) values
(0, 'EDIT', 'this permission let`s you edit users'),
(1, 'NONEDIT', 'this permission let`s you edit users');

insert into permessoutente(id_utente, id_permesso) values (0, 0), (1, 0);