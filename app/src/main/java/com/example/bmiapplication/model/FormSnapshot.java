package com.example.bmiapplication.model;

import com.example.bmiapplication.validation.InputError;

public final class FormSnapshot {
    public final String weightText;
    public final String heightText;
    public final InputError weightError;
    public final InputError heightError;

    public FormSnapshot(String weightText, String heightText, InputError weightError, InputError heightError) {
        this.weightText = weightText;
        this.heightText = heightText;
        this.weightError = weightError;
        this.heightError = heightError;
    }
}