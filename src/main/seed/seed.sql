create table Admin
(
    id       varchar(50) primary key,
    username varchar(50) not null unique,
    password varchar(50) not null
);

create table Product
(
    id    varchar(50) primary key,
    name  VARCHAR(100)   NOT NULL,
    brand VARCHAR(50)    NOT NULL,
    price DECIMAL(12, 2) NOT NULL,
    stock INT            NOT NULL
);

create table Customer
(
    id      varchar(50) primary key,
    name    VARCHAR(100) NOT NULL,
    phone   VARCHAR(20),
    email   VARCHAR(100) unique,
    address VARCHAR(255)
);

create table Invoice
(
    id           varchar(50) primary key,
    customer_id  varchar(50),
    created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(12, 2) NOT NULL,
    foreign key (customer_id) references Customer (id)
);


create table Invoice_Detail
(
    id         varchar(50) primary key,
    invoice_id varchar(50),
    product_id varchar(50),
    quantity   INT            NOT NULL,
    unit_price DECIMAL(12, 2) NOT NULL,
    foreign key (invoice_id) references Invoice (id),
    foreign key (product_id) references Product (id)
);

INSERT INTO Admin (id, username, password)
VALUES ('ADM001', 'admin', 'admin123'),
       ('ADM002', 'manager', 'manager123'),
       ('ADM003', 'staff01', 'staff123');

INSERT INTO Product (id, name, brand, price, stock)
VALUES ('P001', 'iPhone 15 Pro Max 256GB', 'Apple', 34990000.00, 50),
       ('P002', 'iPhone 15 128GB', 'Apple', 22990000.00, 100),
       ('P003', 'Samsung Galaxy S24 Ultra 512GB', 'Samsung', 33990000.00, 40),
       ('P004', 'Samsung Galaxy Z Fold5', 'Samsung', 40990000.00, 20),
       ('P005', 'Xiaomi 14 5G', 'Xiaomi', 22990000.00, 35),
       ('P006', 'Xiaomi Redmi Note 13', 'Xiaomi', 4890000.00, 150),
       ('P007', 'OPPO Reno11 F 5G', 'OPPO', 8990000.00, 60),
       ('P008', 'OPPO Find N3 Flip', 'OPPO', 22990000.00, 25),
       ('P009', 'iPhone 13 128GB', 'Apple', 13990000.00, 80),
       ('P010', 'Samsung Galaxy A54 5G', 'Samsung', 8490000.00, 90),
       ('P011', 'Realme 11 Pro+', 'Realme', 11990000.00, 45),
       ('P012', 'Vivo V29e 5G', 'Vivo', 8990000.00, 55);

INSERT INTO Customer (id, name, phone, email, address)
VALUES ('C001', 'Nguyen Van An', '0901234567', 'an.nguyen@example.com',
        '123 Le Loi, District 1, HCMC'),
       ('C002', 'Tran Thi Bich', '0909876543', 'bich.tran@example.com',
        '456 Nguyen Trai, District 5, HCMC'),
       ('C003', 'Le Van Cuong', '0912345678', 'cuong.le@example.com', '789 Tran Hung Dao, Da Nang'),
       ('C004', 'Pham Thi Dung', '0987654321', 'dung.pham@example.com',
        '321 Xuan Thuy, Cau Giay, Hanoi'),
       ('C005', 'Hoang Van Em', '0933333333', 'em.hoang@example.com', '654 Lach Tray, Hai Phong'),
       ('C006', 'Vu Thi Phuong', '0944444444', 'phuong.vu@example.com',
        '987 Cach Mang Thang 8, Can Tho');

INSERT INTO Invoice (id, customer_id, created_at, total_amount)
VALUES ('INV001', 'C001', '2023-11-15 10:30:00', 34990000.00),
       ('INV002', 'C002', '2023-12-20 14:15:00', 17980000.00),
       ('INV003', 'C001', '2024-01-10 09:45:00', 4890000.00),
       ('INV004', 'C003', '2024-02-14 18:20:00', 45980000.00),
       ('INV005', 'C004', '2024-03-08 08:00:00', 22990000.00),
       ('INV006', 'C002', '2024-04-30 11:11:00', 67980000.00),
       ('INV007', 'C005', '2024-05-01 15:30:00', 8990000.00),
       ('INV008', 'C006', '2024-06-01 19:45:00', 13990000.00),
       ('INV009', 'C001', '2025-01-05 10:00:00', 33990000.00),
       ('INV010', 'C003', '2025-01-15 14:20:00', 74970000.00),
       ('INV011', 'C004', '2025-01-20 16:50:00', 8490000.00),
       ('INV012', 'C002', '2025-02-01 09:10:00', 22990000.00);

INSERT INTO Invoice_Detail (id, invoice_id, product_id, quantity, unit_price)
VALUES ('DET001', 'INV001', 'P001', 1, 34990000.00),
       ('DET002', 'INV002', 'P007', 2, 8990000.00),
       ('DET003', 'INV003', 'P006', 1, 4890000.00),
       ('DET004', 'INV004', 'P002', 2, 22990000.00),
       ('DET005', 'INV005', 'P005', 1, 22990000.00),
       ('DET006', 'INV006', 'P003', 2, 33990000.00),
       ('DET007', 'INV007', 'P012', 1, 8990000.00),
       ('DET008', 'INV008', 'P009', 1, 13990000.00),
       ('DET009', 'INV009', 'P003', 1, 33990000.00),
       ('DET010', 'INV010', 'P001', 1, 34990000.00),
       ('DET011', 'INV010', 'P002', 1, 22990000.00),
       ('DET012', 'INV010', 'P009', 1, 16990000.00),
       ('DET013', 'INV011', 'P010', 1, 8490000.00),
       ('DET014', 'INV012', 'P008', 1, 22990000.00);
