package com.example.bmiassignment.domain;

/**
 * Calculates and classifies BMI values without Android framework dependencies.
 */
public final class BmiCalculator {
    private static final double CM_PER_METER = 100.0;

    private BmiCalculator() {
        // Utility class.
    }

    public static BmiResult calculate(BmiInput input) {
        if (input == null) {
            throw new IllegalArgumentException("input must not be null");
        }

        requirePositiveFinite(input.weightKg, "weightKg");
        requirePositiveFinite(input.heightCm, "heightCm");

        double heightMeters = input.heightCm / CM_PER_METER;
        requirePositiveFinite(heightMeters, "heightMeters");

        double heightSquared = heightMeters * heightMeters;
        requirePositiveFinite(heightSquared, "heightSquared");

        double bmi = input.weightKg / heightSquared;
        requirePositiveFinite(bmi, "bmi");

        return new BmiResult(bmi, classify(bmi));
    }

    public static BmiCategory classify(double bmi) {
        requirePositiveFinite(bmi, "bmi");

        if (bmi < 16.0) {
            return BmiCategory.SEVERE_THINNESS;
        }
        if (bmi < 17.0) {
            return BmiCategory.MODERATE_THINNESS;
        }
        if (bmi < 18.5) {
            return BmiCategory.MILD_THINNESS;
        }
        if (bmi < 25.0) {
            return BmiCategory.NORMAL;
        }
        if (bmi < 30.0) {
            return BmiCategory.OVERWEIGHT;
        }
        if (bmi < 35.0) {
            return BmiCategory.OBESE_I;
        }
        if (bmi < 40.0) {
            return BmiCategory.OBESE_II;
        }
        return BmiCategory.OBESE_III;
    }

    private static void requirePositiveFinite(double value, String name) {
        if (!Double.isFinite(value) || value <= 0.0) {
            throw new IllegalArgumentException(name + " must be finite and greater than zero");
        }
    }
}
