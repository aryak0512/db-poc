-- /* create the table */
CREATE TABLE products (
    id INTEGER not null,
    name VARCHAR(255) not null,
    price DECIMAL,
    primary key (id)
);

-- /* Inserting few tuples */
INSERT INTO products(id, name, price) VALUES (1, 'Iphone 15', 699.00);
INSERT INTO products(id, name, price) VALUES (2, 'Iphone 15 plus', 799.00);
INSERT INTO products(id, name, price) VALUES (3, 'Iphone 15 pro', 899.00);