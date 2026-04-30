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
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb2','Refrigerante 350ml',8.00,CURRENT_TIMESTAMP,'Refrigerante lata gelada','https://img.stadium.com/refri.jpg',250,'QR-102',0);
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb3','Agua Mineral 500ml',5.00,CURRENT_TIMESTAMP,'Agua mineral sem gas','https://img.stadium.com/agua.jpg',400,'QR-103',0);
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb4','Suco Natural 300ml',10.00,CURRENT_TIMESTAMP,'Suco de laranja natural','https://img.stadium.com/suco.jpg',100,'QR-104',0);
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb5','Energetico 250ml',12.00,CURRENT_TIMESTAMP,'Energetico lata gelada','https://img.stadium.com/energetico.jpg',150,'QR-105',0);
-- Camisas e Acessorios
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('cccccccc-cccc-cccc-cccc-ccccccccccc1','Camisa Oficial 2025',299.90,CURRENT_TIMESTAMP,'Camisa oficial do time temporada 2025','https://img.stadium.com/camisa.jpg',50,'QR-201',0);
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('cccccccc-cccc-cccc-cccc-ccccccccccc2','Cachecol do Time',49.90,CURRENT_TIMESTAMP,'Cachecol oficial de torcida','https://img.stadium.com/cachecol.jpg',80,'QR-202',0);
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('cccccccc-cccc-cccc-cccc-ccccccccccc3','Bone Oficial',39.90,CURRENT_TIMESTAMP,'Bone snapback oficial','https://img.stadium.com/bone.jpg',60,'QR-203',0);
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('cccccccc-cccc-cccc-cccc-ccccccccccc4','Bandeira Grande',59.90,CURRENT_TIMESTAMP,'Bandeira do time 1.5m x 1m','https://img.stadium.com/bandeira.jpg',40,'QR-204',0);
-- Combos
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('dddddddd-dddd-dddd-dddd-ddddddddddd1','Combo Torcedor',35.00,CURRENT_TIMESTAMP,'Hamburguer + cerveja 600ml','https://img.stadium.com/combo1.jpg',120,'QR-301',0);
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('dddddddd-dddd-dddd-dddd-ddddddddddd2','Combo Familia',55.00,CURRENT_TIMESTAMP,'2 hot dogs + 2 refrigerantes + pipoca','https://img.stadium.com/combo2.jpg',60,'QR-302',0);
INSERT INTO tb_product (id,name,price,date,description,img_url,quantidade,qr_code,version) VALUES ('dddddddd-dddd-dddd-dddd-ddddddddddd3','Combo Kids',22.00,CURRENT_TIMESTAMP,'Hot dog + suco + pipoca pequena','https://img.stadium.com/combo3.jpg',80,'QR-303',0);

-- ==================== PRODUCT-CATEGORY ====================
-- Comidas
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1','11111111-1111-1111-1111-111111111111');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2','11111111-1111-1111-1111-111111111111');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3','11111111-1111-1111-1111-111111111111');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4','11111111-1111-1111-1111-111111111111');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa5','11111111-1111-1111-1111-111111111111');
-- Bebidas
INSERT INTO tb_product_category (product_id,category_id) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb1','22222222-2222-2222-2222-222222222222');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb2','22222222-2222-2222-2222-222222222222');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb3','22222222-2222-2222-2222-222222222222');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb4','22222222-2222-2222-2222-222222222222');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb5','22222222-2222-2222-2222-222222222222');
-- Camisas e Acessorios
INSERT INTO tb_product_category (product_id,category_id) VALUES ('cccccccc-cccc-cccc-cccc-ccccccccccc1','33333333-3333-3333-3333-333333333333');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('cccccccc-cccc-cccc-cccc-ccccccccccc2','33333333-3333-3333-3333-333333333333');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('cccccccc-cccc-cccc-cccc-ccccccccccc3','33333333-3333-3333-3333-333333333333');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('cccccccc-cccc-cccc-cccc-ccccccccccc4','33333333-3333-3333-3333-333333333333');
-- Combos (multi-categoria)
INSERT INTO tb_product_category (product_id,category_id) VALUES ('dddddddd-dddd-dddd-dddd-ddddddddddd1','44444444-4444-4444-4444-444444444444');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('dddddddd-dddd-dddd-dddd-ddddddddddd1','11111111-1111-1111-1111-111111111111');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('dddddddd-dddd-dddd-dddd-ddddddddddd1','22222222-2222-2222-2222-222222222222');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('dddddddd-dddd-dddd-dddd-ddddddddddd2','44444444-4444-4444-4444-444444444444');
INSERT INTO tb_product_category (product_id,category_id) VALUES ('dddddddd-dddd-dddd-dddd-ddddddddddd3','44444444-4444-4444-4444-444444444444');

-- ==================== WALLETS ====================
-- Joao com R$150,00 de saldo
INSERT INTO tb_wallet (id, user_id, balance, created_at, updated_at, version) VALUES ('a0000000-0000-0000-0000-000000000001', '10000000-0000-0000-0000-000000000001', 150.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
-- Maria com R$200,00 de saldo
INSERT INTO tb_wallet (id, user_id, balance, created_at, updated_at, version) VALUES ('a0000000-0000-0000-0000-000000000002', '10000000-0000-0000-0000-000000000002', 200.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
-- Pedro com R$50,00 de saldo
INSERT INTO tb_wallet (id, user_id, balance, created_at, updated_at, version) VALUES ('a0000000-0000-0000-0000-000000000003', '10000000-0000-0000-0000-000000000003', 50.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
-- Admin com R$500,00 de saldo
INSERT INTO tb_wallet (id, user_id, balance, created_at, updated_at, version) VALUES ('a0000000-0000-0000-0000-000000000004', '30000000-0000-0000-0000-000000000001', 500.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);

-- ==================== TRANSACOES DA WALLET ====================
-- Recargas do Joao
INSERT INTO tb_wallet_transaction (id, wallet_id, type, amount, balance_after, description, reference_id, created_at) VALUES ('b0000000-0000-0000-0000-000000000001', 'a0000000-0000-0000-0000-000000000001', 'CREDIT', 200.00, 200.00, 'Recarga de saldo via PIX', null, CURRENT_TIMESTAMP);
INSERT INTO tb_wallet_transaction (id, wallet_id, type, amount, balance_after, description, reference_id, created_at) VALUES ('b0000000-0000-0000-0000-000000000002', 'a0000000-0000-0000-0000-000000000001', 'DEBIT', 50.00, 150.00, 'Pedido #ord00001', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeee01', CURRENT_TIMESTAMP);
-- Recargas da Maria
INSERT INTO tb_wallet_transaction (id, wallet_id, type, amount, balance_after, description, reference_id, created_at) VALUES ('b0000000-0000-0000-0000-000000000003', 'a0000000-0000-0000-0000-000000000002', 'CREDIT', 300.00, 300.00, 'Recarga de saldo via PIX', null, CURRENT_TIMESTAMP);
INSERT INTO tb_wallet_transaction (id, wallet_id, type, amount, balance_after, description, reference_id, created_at) VALUES ('b0000000-0000-0000-0000-000000000004', 'a0000000-0000-0000-0000-000000000002', 'DEBIT', 100.00, 200.00, 'Pedido #ord00002', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeee02', CURRENT_TIMESTAMP);

-- ==================== PEDIDOS ====================
-- Pedido 1: Joao comprou hamburguer + cerveja (PAID, ja retirou)
INSERT INTO tb_order (id, user_id, total, status, pickup_code, created_at, updated_at, version) VALUES ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeee01', '10000000-0000-0000-0000-000000000001', 40.90, 'PICKED_UP', 'PICKUP-JO001ABC', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
-- Pedido 2: Maria comprou combo familia + agua (READY_FOR_PICKUP)
INSERT INTO tb_order (id, user_id, total, status, pickup_code, created_at, updated_at, version) VALUES ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeee02', '10000000-0000-0000-0000-000000000002', 60.00, 'READY_FOR_PICKUP', 'PICKUP-MA002DEF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
-- Pedido 3: Joao comprou camisa oficial (PAID, aguardando preparo)
INSERT INTO tb_order (id, user_id, total, status, pickup_code, created_at, updated_at, version) VALUES ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeee03', '10000000-0000-0000-0000-000000000001', 299.90, 'PAID', 'PICKUP-JO003GHI', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
-- Pedido 4: Pedro comprou pipoca + refri (PENDING, aguardando pagamento PIX)
INSERT INTO tb_order (id, user_id, total, status, pickup_code, created_at, updated_at, version) VALUES ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeee04', '10000000-0000-0000-0000-000000000003', 20.00, 'PENDING', 'PICKUP-PE004JKL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);

-- ==================== ITENS DOS PEDIDOS ====================
-- Pedido 1 (Joao): Hamburguer + Cerveja
INSERT INTO tb_order_item (id, order_id, product_id, product_name, product_img_url, quantity, price) VALUES ('c0000000-0000-0000-0000-000000000001', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeee01', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Hamburguer Artesanal', 'https://img.stadium.com/hamburguer.jpg', 1, 25.90);
INSERT INTO tb_order_item (id, order_id, product_id, product_name, product_img_url, quantity, price) VALUES ('c0000000-0000-0000-0000-000000000002', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeee01', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb1', 'Cerveja 600ml', 'https://img.stadium.com/cerveja.jpg', 1, 15.00);
-- Pedido 2 (Maria): Combo Familia + Agua
INSERT INTO tb_order_item (id, order_id, product_id, product_name, product_img_url, quantity, price) VALUES ('c0000000-0000-0000-0000-000000000003', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeee02', 'dddddddd-dddd-dddd-dddd-ddddddddddd2', 'Combo Familia', 'https://img.stadium.com/combo2.jpg', 1, 55.00);
INSERT INTO tb_order_item (id, order_id, product_id, product_name, product_img_url, quantity, price) VALUES ('c0000000-0000-0000-0000-000000000004', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeee02', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb3', 'Agua Mineral 500ml', 'https://img.stadium.com/agua.jpg', 1, 5.00);
-- Pedido 3 (Joao): Camisa Oficial
INSERT INTO tb_order_item (id, order_id, product_id, product_name, product_img_url, quantity, price) VALUES ('c0000000-0000-0000-0000-000000000005', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeee03', 'cccccccc-cccc-cccc-cccc-ccccccccccc1', 'Camisa Oficial 2025', 'https://img.stadium.com/camisa.jpg', 1, 299.90);
-- Pedido 4 (Pedro): Pipoca + Refri
INSERT INTO tb_order_item (id, order_id, product_id, product_name, product_img_url, quantity, price) VALUES ('c0000000-0000-0000-0000-000000000006', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeee04', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3', 'Pipoca Grande', 'https://img.stadium.com/pipoca.jpg', 1, 12.00);
INSERT INTO tb_order_item (id, order_id, product_id, product_name, product_img_url, quantity, price) VALUES ('c0000000-0000-0000-0000-000000000007', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeee04', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb2', 'Refrigerante 350ml', 'https://img.stadium.com/refri.jpg', 1, 8.00);

-- ==================== PAGAMENTOS ====================
-- Pagamento Pedido 1 (Joao - WALLET - APPROVED)
INSERT INTO tb_payment (id, order_id, user_id, amount, method, status, gateway_transaction_id, gateway_response, created_at, updated_at, version) VALUES ('d0000000-0000-0000-0000-000000000001', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeee01', '10000000-0000-0000-0000-000000000001', 40.90, 'WALLET', 'APPROVED', null, 'Pagamento via wallet aprovado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
-- Pagamento Pedido 2 (Maria - WALLET - APPROVED)
INSERT INTO tb_payment (id, order_id, user_id, amount, method, status, gateway_transaction_id, gateway_response, created_at, updated_at, version) VALUES ('d0000000-0000-0000-0000-000000000002', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeee02', '10000000-0000-0000-0000-000000000002', 60.00, 'WALLET', 'APPROVED', null, 'Pagamento via wallet aprovado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
-- Pagamento Pedido 3 (Joao - CREDIT_CARD - APPROVED)
INSERT INTO tb_payment (id, order_id, user_id, amount, method, status, gateway_transaction_id, gateway_response, created_at, updated_at, version) VALUES ('d0000000-0000-0000-0000-000000000003', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeee03', '10000000-0000-0000-0000-000000000001', 299.90, 'CREDIT_CARD', 'APPROVED', 'MP-TXN-20260428-001', 'Cartao aprovado via MercadoPago', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
-- Pagamento Pedido 4 (Pedro - PIX - PENDING)
INSERT INTO tb_payment (id, order_id, user_id, amount, method, status, gateway_transaction_id, gateway_response, created_at, updated_at, version) VALUES ('d0000000-0000-0000-0000-000000000004', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeee04', '10000000-0000-0000-0000-000000000003', 20.00, 'PIX', 'PENDING', 'MP-PIX-20260428-001', 'QR Code PIX gerado - aguardando pagamento', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
