package com.example.marketplace.application;

import com.example.marketplace.domain.enums.PaymentMethod;
import com.example.marketplace.domain.enums.PaymentStatus;
import com.example.marketplace.domain.gateway.PaymentGateway;
import com.example.marketplace.domain.gateway.PaymentGatewayRequest;
import com.example.marketplace.domain.gateway.PaymentGatewayResponse;
import com.example.marketplace.infrastructure.dto.PaymentResponse;
import com.example.marketplace.infrastructure.entities.PaymentEntity;
import com.example.marketplace.infrastructure.repository.PaymentRepository;
import com.example.marketplace.shared.exception.BusinessException;
import com.example.marketplace.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentGateway paymentGateway;

    @Transactional
    public PaymentResponse processPayment(UUID userId, UUID orderId,
                                           BigDecimal amount, PaymentMethod method,
                                           String cardToken, String pixKey) {

        // Verificar se já existe pagamento para este pedido
        paymentRepository.findByOrderId(orderId).ifPresent(existing -> {
            if (existing.getStatus() == PaymentStatus.APPROVED) {
                throw new BusinessException("Este pedido já foi pago");
            }
        });

        // Se pagamento via WALLET, não precisa do gateway
        if (method == PaymentMethod.WALLET) {
            return processWalletPayment(userId, orderId, amount);
        }

        // Processar via gateway externo
        var gatewayRequest = PaymentGatewayRequest.builder()
                .orderId(orderId)
                .userId(userId)
                .amount(amount)
                .method(method)
                .cardToken(cardToken)
                .pixKey(pixKey)
                .description("Pedido #" + orderId.toString().substring(0, 8))
                .build();

        PaymentGatewayResponse gatewayResponse = paymentGateway.processPayment(gatewayRequest);

        var payment = new PaymentEntity();
        payment.setUserId(userId);
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setMethod(method);
        payment.setStatus(gatewayResponse.getStatus());
        payment.setGatewayTransactionId(gatewayResponse.getTransactionId());
        payment.setGatewayResponse(gatewayResponse.getMessage());

        var saved = paymentRepository.save(payment);

        log.info("Pagamento processado: paymentId={}, status={}, gateway={}",
                saved.getId(), saved.getStatus(), paymentGateway.getProviderName());

        return PaymentResponse.builder()
                .id(saved.getId())
                .orderId(saved.getOrderId())
                .userId(saved.getUserId())
                .amount(saved.getAmount())
                .method(saved.getMethod())
                .status(saved.getStatus())
                .gatewayTransactionId(saved.getGatewayTransactionId())
                .qrCodePix(gatewayResponse.getQrCodePix())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    private PaymentResponse processWalletPayment(UUID userId, UUID orderId, BigDecimal amount) {
        // O débito da wallet será feito no CheckoutService (orquestrador)
        var payment = new PaymentEntity();
        payment.setUserId(userId);
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setMethod(PaymentMethod.WALLET);
        payment.setStatus(PaymentStatus.APPROVED);
        payment.setGatewayTransactionId("WALLET-" + UUID.randomUUID().toString().substring(0, 8));
        payment.setGatewayResponse("Pagamento via carteira digital");

        var saved = paymentRepository.save(payment);

        return PaymentResponse.builder()
                .id(saved.getId())
                .orderId(saved.getOrderId())
                .userId(saved.getUserId())
                .amount(saved.getAmount())
                .method(saved.getMethod())
                .status(saved.getStatus())
                .gatewayTransactionId(saved.getGatewayTransactionId())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public PaymentResponse getByOrderId(UUID orderId) {
        var payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento", orderId));

        return PaymentResponse.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .status(payment.getStatus())
                .gatewayTransactionId(payment.getGatewayTransactionId())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> getByUserId(UUID userId) {
        return paymentRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(p -> PaymentResponse.builder()
                        .id(p.getId())
                        .orderId(p.getOrderId())
                        .userId(p.getUserId())
                        .amount(p.getAmount())
                        .method(p.getMethod())
                        .status(p.getStatus())
                        .gatewayTransactionId(p.getGatewayTransactionId())
                        .createdAt(p.getCreatedAt())
                        .build())
                .toList();
    }
}
