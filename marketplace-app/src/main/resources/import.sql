-- Roles
INSERT INTO tb_role (id, authority) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa01','ROLE_CUSTOMER');
INSERT INTO tb_role (id, authority) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa02','ROLE_SELLER');
INSERT INTO tb_role (id, authority) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa03','ROLE_ADMIN');

-- Usuários (senha: 12345678 — BCrypt)
INSERT INTO tb_user (id, first_name, last_name, email, password, document) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1','Alex', 'Brown', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '16930196710');
INSERT INTO tb_user (id, first_name, last_name, email, password, document) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2','Maria', 'Green', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '16930196709');
INSERT INTO tb_user (id, first_name, last_name, email, password, document) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3','Carlos', 'Loja', 'carlos@loja.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '11222333000181');

-- User-Role: Alex = CUSTOMER, Maria = ADMIN + CUSTOMER, Carlos = SELLER
INSERT INTO tb_user_role (user_id, role_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa01');
INSERT INTO tb_user_role (user_id, role_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa01');
INSERT INTO tb_user_role (user_id, role_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa03');
INSERT INTO tb_user_role (user_id, role_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa02');

-- Categorias (contexto estádio)
INSERT INTO tb_category (id,name,created_at) VALUES ('11111111-1111-1111-1111-111111111111','Comidas',CURRENT_TIMESTAMP);
INSERT INTO tb_category (id,name,created_at) VALUES ('22222222-2222-2222-2222-222222222222','Bebidas',CURRENT_TIMESTAMP);
INSERT INTO tb_category (id,name,created_at) VALUES ('33333333-3333-3333-3333-333333333333','Camisas e Acessórios',CURRENT_TIMESTAMP);

-- Produtos
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1','Hambúrguer Artesanal',25.90,CURRENT_TIMESTAMP,'Hambúrguer 180g com queijo e bacon','img_hamburguer',100,'QR-001');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2','Hot Dog Completo',18.50,CURRENT_TIMESTAMP,'Hot dog com molhos e batata palha','img_hotdog',150,'QR-002');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3','Pipoca Grande',12.00,CURRENT_TIMESTAMP,'Pipoca salgada balde grande','img_pipoca',200,'QR-003');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4','Cerveja 600ml',15.00,CURRENT_TIMESTAMP,'Cerveja gelada garrafa 600ml','img_cerveja',300,'QR-004');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa5','Refrigerante 350ml',8.00,CURRENT_TIMESTAMP,'Refrigerante lata gelada','img_refri',250,'QR-005');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa6','Água Mineral 500ml',5.00,CURRENT_TIMESTAMP,'Água mineral sem gás','img_agua',400,'QR-006');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa7','Camisa Oficial 2025',299.90,CURRENT_TIMESTAMP,'Camisa oficial do time temporada 2025','img_camisa',50,'QR-007');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa8','Cachecol do Time',49.90,CURRENT_TIMESTAMP,'Cachecol oficial de torcida','img_cachecol',80,'QR-008');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa9','Boné Oficial',39.90,CURRENT_TIMESTAMP,'Boné snapback oficial','img_bone',60,'QR-009');
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa10','Combo Torcedor',35.00,CURRENT_TIMESTAMP,'Hambúrguer + cerveja 600ml','img_combo',120,'QR-010');

-- Product-Category
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1','11111111-1111-1111-1111-111111111111');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2','11111111-1111-1111-1111-111111111111');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3','11111111-1111-1111-1111-111111111111');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4','22222222-2222-2222-2222-222222222222');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa5','22222222-2222-2222-2222-222222222222');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa6','22222222-2222-2222-2222-222222222222');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa7','33333333-3333-3333-3333-333333333333');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa8','33333333-3333-3333-3333-333333333333');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa9','33333333-3333-3333-3333-333333333333');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa10','11111111-1111-1111-1111-111111111111');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa10','22222222-2222-2222-2222-222222222222');
