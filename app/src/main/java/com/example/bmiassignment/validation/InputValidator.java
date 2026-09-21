package com.example.bmiassignment.validation;

import com.example.bmiassignment.domain.BmiInput;

public final class InputValidator {

    public static InputValidationResult validate(String weightText, String heightText) {
        InputError weightError = validateField(weightText);
        InputError heightError = validateField(heightText);

        BmiInput input = null;
        if (weightError == InputError.NONE && heightError == InputError.NONE) {
            double weight = Double.parseDouble(weightText.trim());
            double height = Double.parseDouble(heightText.trim());
            input = new BmiInput(weight, height);
        }

        return new InputValidationResult(input, weightError, heightError);
    }

    private static InputError validateField(String text) {
        if (text == null) {
            return InputError.REQUIRED;
        }
        String trimmed = text.trim();
        if (trimmed.isEmpty()) {
            return InputError.REQUIRED;
        }

        if (!trimmed.matches("^[+-]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)$")) {
            return InputError.INVALID_NUMBER;
        }

        try {
            double value = Double.parseDouble(trimmed);
            if (Double.isNaN(value) || Double.isInfinite(value)) {
                return InputError.INVALID_NUMBER;
            }
            if (value <= 0) {
                return InputError.NON_POSITIVE;
            }
        } catch (NumberFormatException e) {
            return InputError.INVALID_NUMBER;
        }

        return InputError.NONE;
    }
}
