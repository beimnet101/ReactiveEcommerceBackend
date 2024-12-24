CREATE TABLE products (
                          product_id SERIAL PRIMARY KEY, -- Auto-incremented primary key
                          name VARCHAR(255) NOT NULL,   -- Product name
                          description TEXT,             -- Product description
                          price DECIMAL(10, 2) NOT NULL, -- Product price with two decimal places
                          quantity INT NOT NULL         -- Product quantity
);