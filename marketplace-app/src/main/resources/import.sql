INSERT INTO tb_user (id, first_name, last_name, email, password, document) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1','Alex', 'Brown', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '16930196710');
INSERT INTO tb_user (id, first_name, last_name, email, password, document) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2','Maria', 'Green', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '16930196709');
INSERT INTO tb_role (id, authority) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3','ROLE_OPERATOR');
INSERT INTO tb_role (id, authority) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4','ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3');
INSERT INTO tb_user_role (user_id, role_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3');
INSERT INTO tb_user_role (user_id, role_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4');


INSERT INTO tb_category (id,name,created_at) VALUES ('11111111-1111-1111-1111-111111111111','Books',CURRENT_TIMESTAMP);
INSERT INTO tb_category (id,name,created_at) VALUES ('22222222-2222-2222-2222-222222222222','Electronics',CURRENT_TIMESTAMP);
INSERT INTO tb_category (id,name,created_at) VALUES ('33333333-3333-3333-3333-333333333333','Computers',CURRENT_TIMESTAMP);

INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1','Product 1',100.0,CURRENT_TIMESTAMP,'Lorem ipsum','img1',10,'QR-001');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2','Product 2',110.0,CURRENT_TIMESTAMP,'Lorem ipsum','img2',11,'QR-002');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3','Product 3',120.0,CURRENT_TIMESTAMP,'Lorem ipsum','img3',12,'QR-003');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4','Product 4',130.0,CURRENT_TIMESTAMP,'Lorem ipsum','img4',13,'QR-004');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa5','Product 5',140.0,CURRENT_TIMESTAMP,'Lorem ipsum','img5',14,'QR-005');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa6','Product 6',150.0,CURRENT_TIMESTAMP,'Lorem ipsum','img6',15,'QR-006');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa7','Product 7',160.0,CURRENT_TIMESTAMP,'Lorem ipsum','img7',16,'QR-007');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa8','Product 8',170.0,CURRENT_TIMESTAMP,'Lorem ipsum','img8',17,'QR-008');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa9','Product 9',180.0,CURRENT_TIMESTAMP,'Lorem ipsum','img9',18,'QR-009');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa10','Product 10',190.0,CURRENT_TIMESTAMP,'Lorem ipsum','img10',19,'QR-010');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa11','Product 11',200.0,CURRENT_TIMESTAMP,'Lorem ipsum','img11',20,'QR-011');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa12','Product 12',210.0,CURRENT_TIMESTAMP,'Lorem ipsum','img12',21,'QR-012');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa13','Product 13',220.0,CURRENT_TIMESTAMP,'Lorem ipsum','img13',22,'QR-013');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa14','Product 14',230.0,CURRENT_TIMESTAMP,'Lorem ipsum','img14',23,'QR-014');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa15','Product 15',240.0,CURRENT_TIMESTAMP,'Lorem ipsum','img15',24,'QR-015');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa16','Product 16',250.0,CURRENT_TIMESTAMP,'Lorem ipsum','img16',25,'QR-016');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa17','Product 17',260.0,CURRENT_TIMESTAMP,'Lorem ipsum','img17',26,'QR-017');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa18','Product 18',270.0,CURRENT_TIMESTAMP,'Lorem ipsum','img18',27,'QR-018');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa19','Product 19',280.0,CURRENT_TIMESTAMP,'Lorem ipsum','img19',28,'QR-019');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa20','Product 20',290.0,CURRENT_TIMESTAMP,'Lorem ipsum','img20',29,'QR-020');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa21','Product 21',300.0,CURRENT_TIMESTAMP,'Lorem ipsum','img21',30,'QR-021');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa22','Product 22',310.0,CURRENT_TIMESTAMP,'Lorem ipsum','img22',31,'QR-022');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa23','Product 23',320.0,CURRENT_TIMESTAMP,'Lorem ipsum','img23',32,'QR-023');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa24','Product 24',330.0,CURRENT_TIMESTAMP,'Lorem ipsum','img24',33,'QR-024');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa25','Product 25',340.0,CURRENT_TIMESTAMP,'Lorem ipsum','img25',34,'QR-025');

INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1','11111111-1111-1111-1111-111111111111');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2','22222222-2222-2222-2222-222222222222');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3','33333333-3333-3333-3333-333333333333');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4','33333333-3333-3333-3333-333333333333');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa5','11111111-1111-1111-1111-111111111111');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa6','33333333-3333-3333-3333-333333333333');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa7','33333333-3333-3333-3333-333333333333');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa8','33333333-3333-3333-3333-333333333333');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa9','33333333-3333-3333-3333-333333333333');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa10','33333333-3333-3333-3333-333333333333');