INSERT INTO users (email, password, username)
VALUES('bartek@wp.pl','bartek123', 'barti');

INSERT into roles(name)
VALUES ('ROLE_USER');

INSERT into roles(name)
VALUES ('ROLE_ADMIN');

INSERT into user_roles(user_id, role_id)
VALUES (1,1);

INSERT INTO book (author, title, appuser_id, is_accepted, image_url, category)
VALUES('Zbyszek','fajna ksiazka', 1, true, 'imageUrl', 'FANTASY');

INSERT INTO rating(rate, book_id, appuser_id)
VALUES (8,1,1);
