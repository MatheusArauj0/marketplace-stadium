package com.example.marketplace.domain.gateway;

import java.math.BigDecimal;

/**
 * Abstração para integração com gateways de pagamento externos.
 * Implementações: MercadoPago, Stripe, PagSeguro, etc.
 */
public interface PaymentGateway {

    PaymentGatewayResponse processPayment(PaymentGatewayRequest request);

    PaymentGatewayResponse refundPayment(String gatewayTransactionId, BigDecimal amount);

    String getProviderName();
}
