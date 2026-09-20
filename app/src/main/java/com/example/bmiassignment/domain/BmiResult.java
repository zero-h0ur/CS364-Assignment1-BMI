package com.example.bmiassignment.domain;

import java.util.Objects;

/**
 * Raw BMI value and its classification.
 */
public final class BmiResult {
    public final double bmi;
    public final BmiCategory category;

    public BmiResult(double bmi, BmiCategory category) {
        this.bmi = bmi;
        this.category = Objects.requireNonNull(category, "category");
    }
}
