CREATE TABLE IF NOT EXISTS roles(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    role_name TEXT NOT NULL UNIQUE
);

INSERT INTO roles(role_name) VALUES
     ('ADMIN'),
     ('CUSTOMER'),
     ('SELLER');


CREATE TABLE IF NOT EXISTS products(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    product_name TEXT NOT NULL UNIQUE,
    cost INT NOT NULL,
    photo_ref TEXT NOT NULL UNIQUE,
    quantity INT NOT NULL,
    category TEXT NOT NULL,
    discount double precision
);


CREATE TABLE IF NOT EXISTS users(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    phone TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    country TEXT NOT NULL,
    profle_photo TEXT NOT NULL,
    password TEXT NOT NULL,
--     user_comments BIGINT UNIQUE
);

CREATE TABLE IF NOT EXISTS users_roles(
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY(user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
    );

CREATE TABLE IF NOT EXISTS users_products(
    user_id BIGINT,
    product_id BIGINT,
    PRIMARY KEY(user_id, product_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
    );


CREATE TABLE IF NOT EXISTS comments(
    comment_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    text TEXT,
    isAnonymous BOOLEAN,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
    );
