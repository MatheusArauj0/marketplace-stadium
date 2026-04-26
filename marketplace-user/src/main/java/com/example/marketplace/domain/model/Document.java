package com.example.marketplace.domain.model;

import com.example.marketplace.domain.enums.DocumentType;

import java.util.regex.Pattern;

public record Document(DocumentType type, String value) {

    private static final Pattern ALPHANUMERIC =
            Pattern.compile("^[A-Za-z0-9]+$");

    public Document {

        if (type == null) {
            throw new IllegalArgumentException("Tipo obrigatório");
        }

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Documento vazio");
        }

        value = value.replaceAll("[^A-Za-z0-9]", "");

        if (!ALPHANUMERIC.matcher(value).matches()) {
            throw new IllegalArgumentException("Documento deve ser alfanumérico");
        }

        switch (type) {
            case CPF -> validateCPF(value);
            case CNPJ -> validateCNPJ(value);
        }
    }

    public static DocumentType of(String value) {

        String normalized = value.replaceAll("[^A-Za-z0-9]", "");

        if (normalized.length() == 11) {
            return DocumentType.CPF;
        }

        if (normalized.length() == 14) {
            return DocumentType.CNPJ;
        }

        throw new IllegalArgumentException("Documento inválido");
    }

    private void validateCPF(String value) {

        if (value.length() != 11) {
            throw new IllegalArgumentException("CPF deve ter 11 caracteres");
        }

        if (isNumeric(value) && !isValidCPF(value)) {
            throw new IllegalArgumentException("CPF inválido");
        }
    }

    private void validateCNPJ(String value) {

        if (value.length() != 14) {
            throw new IllegalArgumentException("CNPJ deve ter 14 caracteres");
        }

        if (isNumeric(value) && !isValidCNPJ(value)) {
            throw new IllegalArgumentException("CNPJ inválido");
        }
    }

    private boolean isNumeric(String value) {
        return value.matches("\\d+");
    }

    // algoritmo clássico CPF
    private boolean isValidCPF(String cpf) {

        if (cpf.chars().distinct().count() == 1) return false;

        int sum = 0;
        for (int i = 0; i < 9; i++)
            sum += (cpf.charAt(i) - '0') * (10 - i);

        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) firstDigit = 0;

        sum = 0;
        for (int i = 0; i < 10; i++)
            sum += (cpf.charAt(i) - '0') * (11 - i);

        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) secondDigit = 0;

        return firstDigit == (cpf.charAt(9) - '0') &&
                secondDigit == (cpf.charAt(10) - '0');
    }

    // algoritmo clássico CNPJ
    private boolean isValidCNPJ(String cnpj) {

        int[] weight1 = {5,4,3,2,9,8,7,6,5,4,3,2};
        int[] weight2 = {6,5,4,3,2,9,8,7,6,5,4,3,2};

        int sum = 0;
        for (int i = 0; i < 12; i++)
            sum += (cnpj.charAt(i) - '0') * weight1[i];

        int firstDigit = sum % 11 < 2 ? 0 : 11 - (sum % 11);

        sum = 0;
        for (int i = 0; i < 13; i++)
            sum += (cnpj.charAt(i) - '0') * weight2[i];

        int secondDigit = sum % 11 < 2 ? 0 : 11 - (sum % 11);

        return firstDigit == (cnpj.charAt(12) - '0') &&
                secondDigit == (cnpj.charAt(13) - '0');
    }
}