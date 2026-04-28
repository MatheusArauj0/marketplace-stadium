package com.example.marketplace.infrastructure.gateway;

import com.example.marketplace.domain.enums.PaymentStatus;
import com.example.marketplace.domain.gateway.PaymentGateway;
import com.example.marketplace.domain.gateway.PaymentGatewayRequest;
import com.example.marketplace.domain.gateway.PaymentGatewayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Implementação simulada do gateway MercadoPago.
 * Em produção, faria chamadas HTTP para a API do MercadoPago.
 */
@Slf4j
@Component
public class MercadoPagoGateway implements PaymentGateway {

    @Override
    public PaymentGatewayResponse processPayment(PaymentGatewayRequest request) {
        log.info("[MercadoPago] Processando pagamento: orderId={}, valor=R${}, método={}",
                request.getOrderId(), request.getAmount(), request.getMethod());

        // Simulação: aprovar pagamentos normalmente
        // Em produção: chamada HTTP POST para https://api.mercadopago.com/v1/payments
        String transactionId = "MP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        return switch (request.getMethod()) {
            case PIX -> PaymentGatewayResponse.builder()
                    .transactionId(transactionId)
                    .status(PaymentStatus.PENDING)
                    .message("PIX gerado com sucesso. Aguardando pagamento.")
                    .qrCodePix("00020126580014br.gov.bcb.pix0136" + UUID.randomUUID())
                    .build();

            case CREDIT_CARD, DEBIT_CARD -> PaymentGatewayResponse.builder()
                    .transactionId(transactionId)
                    .status(PaymentStatus.APPROVED)
                    .message("Pagamento aprovado via " + request.getMethod())
                    .build();

            default -> PaymentGatewayResponse.builder()
                    .transactionId(transactionId)
                    .status(PaymentStatus.REJECTED)
                    .message("Método de pagamento não suportado pelo gateway")
                    .build();
        };
    }

    @Override
    public PaymentGatewayResponse refundPayment(String gatewayTransactionId, BigDecimal amount) {
        log.info("[MercadoPago] Estornando pagamento: txId={}, valor=R${}",
                gatewayTransactionId, amount);

        return PaymentGatewayResponse.builder()
                .transactionId(gatewayTransactionId)
                .status(PaymentStatus.REFUNDED)
                .message("Estorno processado com sucesso")
                .build();
    }

    @Override
    public String getProviderName() {
        return "MERCADO_PAGO";
    }
}
