package com.example.bmiassignment.validation;

import com.example.bmiassignment.domain.BmiInput;

public final class InputValidationResult {
    public final BmiInput input;
    public final InputError weightError;
    public final InputError heightError;

    public InputValidationResult(BmiInput input, InputError weightError, InputError heightError) {
        this.input = input;
        this.weightError = weightError;
        this.heightError = heightError;
    }

    public boolean isValid() {
        return input != null && weightError == InputError.NONE && heightError == InputError.NONE;
    }
}
