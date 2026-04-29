-- =====================================================
-- STADIUM MARKETPLACE - MASSA DE DADOS PARA TESTES
-- =====================================================

-- ==================== ROLES ====================
INSERT INTO tb_role (id, authority) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa01','ROLE_CUSTOMER');
INSERT INTO tb_role (id, authority) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa02','ROLE_SELLER');
INSERT INTO tb_role (id, authority) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa03','ROLE_ADMIN');

-- ==================== USUARIOS ====================
-- Torcedor 1: João Silva (CUSTOMER) | Email: joao@email.com | Senha: Torcedor@123
INSERT INTO tb_user (id, first_name, last_name, email, password, document) VALUES ('10000000-0000-0000-0000-000000000001','Joao', 'Silva', 'joao@email.com', '$2b$10$kQcZrhYYYfqWrEwxQ7s9g.hNL4F5dfDPPLaaphpZ9RHdgtSvWZGDy', '52998224725');

-- Torcedor 2: Maria Santos (CUSTOMER) | Email: maria@email.com | Senha: Maria@123
INSERT INTO tb_user (id, first_name, last_name, email, password, document) VALUES ('10000000-0000-0000-0000-000000000002','Maria', 'Santos', 'maria@email.com', '$2b$10$kO0YdLOfVrMI.U7HbOJ1wuQ4DeNSfa7nCTzqB/BaKh4wKKHrHxLR.', '71428793860');

-- Torcedor 3: Pedro Oliveira (CUSTOMER) | Email: pedro@email.com | Senha: Pedro@123
INSERT INTO tb_user (id, first_name, last_name, email, password, document) VALUES ('10000000-0000-0000-0000-000000000003','Pedro', 'Oliveira', 'pedro@email.com', '$2b$10$SVlG6rW8J/5nKmjUN4TRq.buiQbYm/cJGTKia4NckmObkoYl8rjLa', '83219045637');

-- Lojista: Carlos Lanchonete (SELLER) | Email: carlos@loja.com | Senha: Lojista@123
INSERT INTO tb_user (id, first_name, last_name, email, password, document) VALUES ('20000000-0000-0000-0000-000000000001','Carlos', 'Lanchonete', 'carlos@loja.com', '$2b$10$vmbPDfq3xUnth9CfqXGK0eS6ArgrSjw7d9p.bKMTqds0wP3rnRQ.O', '11222333000181');

-- Lojista: Ana Bebidas (SELLER) | Email: ana@bebidas.com | Senha: Lojista@123
INSERT INTO tb_user (id, first_name, last_name, email, password, document) VALUES ('20000000-0000-0000-0000-000000000002','Ana', 'Bebidas', 'ana@bebidas.com', '$2b$10$vmbPDfq3xUnth9CfqXGK0eS6ArgrSjw7d9p.bKMTqds0wP3rnRQ.O', '44555666000199');

-- Admin: Matheus Admin (ADMIN + CUSTOMER) | Email: admin@stadium.com | Senha: Admin@123
INSERT INTO tb_user (id, first_name, last_name, email, password, document) VALUES ('30000000-0000-0000-0000-000000000001','Matheus', 'Admin', 'admin@stadium.com', '$2b$10$kIMNk8FS3acO1KVUP0LbVOZtXrK942xV4c6p..80nRnGtLhfSDJce', '98765432100');

-- ==================== USER-ROLE ====================
-- Torcedores = CUSTOMER
INSERT INTO tb_user_role (user_id, role_id) VALUES ('10000000-0000-0000-0000-000000000001', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa01');
INSERT INTO tb_user_role (user_id, role_id) VALUES ('10000000-0000-0000-0000-000000000002', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa01');
INSERT INTO tb_user_role (user_id, role_id) VALUES ('10000000-0000-0000-0000-000000000003', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa01');
-- Lojistas = SELLER
INSERT INTO tb_user_role (user_id, role_id) VALUES ('20000000-0000-0000-0000-000000000001', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa02');
INSERT INTO tb_user_role (user_id, role_id) VALUES ('20000000-0000-0000-0000-000000000002', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa02');
-- Admin = ADMIN + CUSTOMER
INSERT INTO tb_user_role (user_id, role_id) VALUES ('30000000-0000-0000-0000-000000000001', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa03');
INSERT INTO tb_user_role (user_id, role_id) VALUES ('30000000-0000-0000-0000-000000000001', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa01');

-- ==================== CATEGORIAS ====================
INSERT INTO tb_category (id,name,created_at) VALUES ('11111111-1111-1111-1111-111111111111','Comidas',CURRENT_TIMESTAMP);
INSERT INTO tb_category (id,name,created_at) VALUES ('22222222-2222-2222-2222-222222222222','Bebidas',CURRENT_TIMESTAMP);
INSERT INTO tb_category (id,name,created_at) VALUES ('33333333-3333-3333-3333-333333333333','Camisas e Acessorios',CURRENT_TIMESTAMP);
INSERT INTO tb_category (id,name,created_at) VALUES ('44444444-4444-4444-4444-444444444444','Combos',CURRENT_TIMESTAMP);

-- ==================== PRODUTOS ====================
-- Comidas
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1','Hamburguer Artesanal',25.90,CURRENT_TIMESTAMP,'Hamburguer 180g com queijo e bacon','https://img.stadium.com/hamburguer.jpg',100,'QR-001',0);
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2','Hot Dog Completo',18.50,CURRENT_TIMESTAMP,'Hot dog com molhos e batata palha','https://img.stadium.com/hotdog.jpg',150,'QR-002',0);
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3','Pipoca Grande',12.00,CURRENT_TIMESTAMP,'Pipoca salgada balde grande','https://img.stadium.com/pipoca.jpg',200,'QR-003',0);
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4','Churrasquinho',15.00,CURRENT_TIMESTAMP,'Espetinho de carne bovina 120g','https://img.stadium.com/churrasquinho.jpg',80,'QR-004',0);
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa5','Pastel de Carne',10.00,CURRENT_TIMESTAMP,'Pastel frito crocante recheio carne','https://img.stadium.com/pastel.jpg',120,'QR-005',0);
-- Bebidas
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb1','Cerveja 600ml',15.00,CURRENT_TIMESTAMP,'Cerveja gelada garrafa 600ml','https://img.stadium.com/cerveja.jpg',300,'QR-101',0);
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb2','Refrigerante 350ml',8.00,CURRENT_TIMESTAMP,'Refriger