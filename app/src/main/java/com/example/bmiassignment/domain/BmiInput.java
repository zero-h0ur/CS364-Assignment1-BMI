package com.example.bmiassignment.domain;

/**
 * Input values for a BMI calculation.
 */
public final class BmiInput {
    public final double weightKg;
    public final double heightCm;

    public BmiInput(double weightKg, double heightCm) {
        this.weightKg = weightKg;
        this.heightCm = heightCm;
    }
}
