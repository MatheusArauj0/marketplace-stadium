package com.example.marketplace.domain.gateway;

import com.example.marketplace.domain.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentGatewayResponse {

    private String transactionId;
    private PaymentStatus status;
    private String message;
    private String qrCodePix;      // QR code para pagamento PIX
}
