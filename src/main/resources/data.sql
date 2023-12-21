
CREATE TABLE brands (
   id     BIGINT AUTO_INCREMENT PRIMARY KEY,
   name   VARCHAR(50) NOT NULL
);

CREATE TABLE products (
   id     BIGINT AUTO_INCREMENT PRIMARY KEY,
   name   VARCHAR(50) NOT NULL,
   brand_id BIGINT NOT NULL,
   FOREIGN KEY(brand_id) REFERENCES brands(id)
);

CREATE TABLE prices (
   id          BIGINT AUTO_INCREMENT PRIMARY KEY,
   start_date  TIMESTAMP NOT NULL,
   end_date    TIMESTAMP NOT NULL,
   currency    VARCHAR(10) NOT NULL,
   amount      NUMERIC(15,2) NOT NULL,
   priority    BIGINT NOT NULL,
   product_id  BIGINT NOT NULL,
   FOREIGN KEY(product_id) REFERENCES products(id)
);