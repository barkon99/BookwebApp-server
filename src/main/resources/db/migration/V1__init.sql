CREATE TABLE users (
 id serial PRIMARY KEY,
 email VARCHAR ( 255 ) UNIQUE NOT NULL,
 password VARCHAR ( 255 ) NOT NULL,
 username VARCHAR ( 255 ) UNIQUE NOT NULL
);

CREATE TABLE roles (
id serial PRIMARY KEY,
name varchar ( 255 ) NOT NULL
);

CREATE TABLE user_roles (
    user_id INT Not NULL,
    role_id INT NOT NULL,
    FOREIGN  key(user_id)
    references users(id),
    FOREIGN  key(role_id)
    references roles(id)
);

CREATE TABLE book (
   id serial PRIMARY KEY,
   author VARCHAR ( 255 ) NOT NULL,
   title VARCHAR ( 255 ) NOT NULL,
   appuser_id INT  NOT NULL,
   is_accepted boolean NOT NULL,
   image_url varchar( 255 ),
   category varchar ( 255 ),
   FOREIGN  key(appuser_id)
   references users(id)
);

CREATE TABLE rating (
    id serial PRIMARY KEY,
    rate INT NOT NULL,
    book_id INT NOT NULL,
    appuser_id INT NOT NULL,
    FOREIGN  key(book_id)
    references book(id),
    FOREIGN  key(appuser_id)
    references users(id)
);


