create database shopapp;
use shopapp;

create table users(
	id int primary key auto_increment,
    fullname varchar(100) default '',
	phone_number varchar(20) not null,
    address varchar(200) default '',
    password varchar(200) default '',
    date_of_birth date,
    is_active tinyint(1) default 1,
    facebook_account_id int default 0,
    google_account_id int default 0,
    created_at datetime,
    updated_at datetime
);
ALTER TABLE users ADD COLUMN role_id INT;

CREATE TABLE roles(
    id INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL 
);
ALTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles (id);

create table tokens(
	id int primary key auto_increment,
    token varchar(255) unique not null,
    token_type varchar(50) not null,
    expiration_date datetime,
    revoked tinyint(1) not null,
    expired tinyint(1) not null,
    user_id int,
	foreign key(user_id) references users(id)
);

-- hỗ trợ đăng nhập từ Facebook và Google
CREATE TABLE social_accounts(
    id INT PRIMARY KEY AUTO_INCREMENT,
    provider VARCHAR(20) NOT NULL COMMENT 'Tên nhà social network',
    provider_id VARCHAR(50) NOT NULL,
    email VARCHAR(150) NOT NULL COMMENT 'Email tài khoản',
    name VARCHAR(100) NOT NULL COMMENT 'Tên người dùng',
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Bảng danh mục sản phẩm(Category)
CREATE TABLE categories(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(100) NOT NULL DEFAULT '' COMMENT 'Tên danh mục, vd: đồ điện tử'
);

-- Bảng chứa sản phẩm(Product): "laptop macbook air 15 inch 2023", iphone 15 pro,...
CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(350) not null COMMENT 'Tên sản phẩm',
    price FLOAT NOT NULL CHECK (price >= 0),
    thumbnail VARCHAR(300) DEFAULT '',
    description LONGTEXT,
    created_at DATETIME,
    updated_at DATETIME,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE product_images(
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT fk_product_images_product_id 
        FOREIGN KEY (product_id) 
        REFERENCES products (id) ON DELETE CASCADE,
    image_url VARCHAR(300)
);

-- Đặt hàng - orders
CREATE TABLE orders(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(id),
    fullname VARCHAR(100) DEFAULT '',
    email VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(20) NOT NULL,
    address VARCHAR(200) NOT NULL,
    note VARCHAR(100) DEFAULT '',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20),
    total_money FLOAT CHECK(total_money >= 0)
);

ALTER TABLE orders ADD COLUMN `shipping_method` VARCHAR(100);
ALTER TABLE orders ADD COLUMN `shipping_address` VARCHAR(200);
ALTER TABLE orders ADD COLUMN `shipping_date` DATE;
ALTER TABLE orders ADD COLUMN `tracking_number` VARCHAR(100);
ALTER TABLE orders ADD COLUMN `payment_method` VARCHAR(100);
-- xóa 1 đơn hàng => xóa mềm => thêm trường active
ALTER TABLE orders ADD COLUMN active TINYINT(1);
-- Trạng thái đơn hàng chỉ đc phép nhận "một số giá trị cụ thể"
ALTER TABLE orders 
MODIFY COLUMN status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled') 
COMMENT 'Trạng thái đơn hàng';

CREATE TABLE order_details(
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES products (id),
    price FLOAT CHECK(price >= 0),
    number_of_products INT CHECK(number_of_products > 0),
    total_money FLOAT CHECK(total_money >= 0),
    color VARCHAR(20) DEFAULT ''
);

ALTER TABLE orders
MODIFY COLUMN total_money FLOAT DEFAULT 0,
MODIFY COLUMN shipping_method VARCHAR(100),
MODIFY COLUMN tracking_number VARCHAR(100),
MODIFY COLUMN payment_method VARCHAR(100),
MODIFY COLUMN shipping_address VARCHAR(200) DEFAULT '',
MODIFY COLUMN shipping_date DATE,
MODIFY COLUMN active TINYINT(1) DEFAULT 1;

SET SQL_SAFE_UPDATES = 1;

UPDATE orders
SET total_money = 0,
    shipping_method = '',
    tracking_number = '',
    payment_method = '',
    shipping_address = '',    
    active = 1
WHERE total_money IS NULL
   OR shipping_method IS NULL
   OR tracking_number IS NULL
   OR payment_method IS NULL
   OR shipping_address IS NULL
   OR shipping_date IS NULL
   OR active IS NULL;

