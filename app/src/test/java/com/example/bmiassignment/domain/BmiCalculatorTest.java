package com.example.bmiassignment.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class BmiCalculatorTest {
    private static final double BMI_DELTA = 1.0e-12;

    @Test
    public void calculate_returnsRawBmiAndCategory() {
        BmiResult result = BmiCalculator.calculate(new BmiInput(65.0, 168.0));

        assertEquals(23.030045351473927, result.bmi, BMI_DELTA);
        assertEquals(BmiCategory.NORMAL, result.category);
    }

    @Test
    public void calculate_matchesSharedExamples() {
        BmiResult normal = BmiCalculator.calculate(new BmiInput(70.0, 175.0));
        BmiResult overweight = BmiCalculator.calculate(new BmiInput(100.0, 200.0));

        assertEquals(22.857142857142858, normal.bmi, BMI_DELTA);
        assertEquals(BmiCategory.NORMAL, normal.category);
        assertEquals(25.0, overweight.bmi, 0.0);
        assertEquals(BmiCategory.OVERWEIGHT, overweight.category);
    }

    @Test
    public void calculate_classifiesAllExactBoundaries() {
        assertCalculatedCategory(16.0, BmiCategory.MODERATE_THINNESS);
        assertCalculatedCategory(17.0, BmiCategory.MILD_THINNESS);
        assertCalculatedCategory(18.5, BmiCategory.NORMAL);
        assertCalculatedCategory(25.0, BmiCategory.OVERWEIGHT);
        assertCalculatedCategory(30.0, BmiCategory.OBESE_I);
        assertCalculatedCategory(35.0, BmiCategory.OBESE_II);
        assertCalculatedCategory(40.0, BmiCategory.OBESE_III);
    }

    @Test
    public void classify_handlesValuesBelowAtAndAboveEveryBoundary() {
        assertBoundary(16.0,
                BmiCategory.SEVERE_THINNESS,
                BmiCategory.MODERATE_THINNESS,
                BmiCategory.MODERATE_THINNESS);
        assertBoundary(17.0,
                BmiCategory.MODERATE_THINNESS,
                BmiCategory.MILD_THINNESS,
                BmiCategory.MILD_THINNESS);
        assertBoundary(18.5,
                BmiCategory.MILD_THINNESS,
                BmiCategory.NORMAL,
                BmiCategory.NORMAL);
        assertBoundary(25.0,
                BmiCategory.NORMAL,
                BmiCategory.OVERWEIGHT,
                BmiCategory.OVERWEIGHT);
        assertBoundary(30.0,
                BmiCategory.OVERWEIGHT,
                BmiCategory.OBESE_I,
                BmiCategory.OBESE_I);
        assertBoundary(35.0,
                BmiCategory.OBESE_I,
                BmiCategory.OBESE_II,
                BmiCategory.OBESE_II);
        assertBoundary(40.0,
                BmiCategory.OBESE_II,
                BmiCategory.OBESE_III,
                BmiCategory.OBESE_III);
    }

    @Test
    public void classify_usesRawValueWithoutRounding() {
        assertEquals(BmiCategory.NORMAL, BmiCalculator.classify(24.999));
        assertEquals(BmiCategory.OVERWEIGHT, BmiCalculator.classify(25.0));
    }

    @Test
    public void classify_acceptsPositiveFiniteExtremes() {
        assertEquals(BmiCategory.SEVERE_THINNESS, BmiCalculator.classify(Double.MIN_VALUE));
        assertEquals(BmiCategory.OBESE_III, BmiCalculator.classify(Double.MAX_VALUE));
    }

    @Test
    public void classify_rejectsNonPositiveAndNonFiniteValues() {
        assertClassifyRejected(0.0);
        assertClassifyRejected(-1.0);
        assertClassifyRejected(Double.NaN);
        assertClassifyRejected(Double.POSITIVE_INFINITY);
        assertClassifyRejected(Double.NEGATIVE_INFINITY);
    }

    @Test
    public void calculate_rejectsNullInput() {
        assertThrows(IllegalArgumentException.class, () -> BmiCalculator.calculate(null));
    }

    @Test
    public void calculate_rejectsInvalidWeight() {
        assertCalculationRejected(0.0, 168.0);
        assertCalculationRejected(-65.0, 168.0);
        assertCalculationRejected(Double.NaN, 168.0);
        assertCalculationRejected(Double.POSITIVE_INFINITY, 168.0);
        assertCalculationRejected(Double.NEGATIVE_INFINITY, 168.0);
    }

    @Test
    public void calculate_rejectsInvalidHeight() {
        assertCalculationRejected(65.0, 0.0);
        assertCalculationRejected(65.0, -168.0);
        assertCalculationRejected(65.0, Double.NaN);
        assertCalculationRejected(65.0, Double.POSITIVE_INFINITY);
        assertCalculationRejected(65.0, Double.NEGATIVE_INFINITY);
    }

    @Test
    public void calculate_rejectsInvalidIntermediateOrResult() {
        assertCalculationRejected(65.0, Double.MIN_VALUE);
        assertCalculationRejected(65.0, Double.MAX_VALUE);
        assertCalculationRejected(Double.MAX_VALUE, 50.0);
        assertCalculationRejected(Double.MIN_VALUE, 200.0);
    }

    private static void assertCalculatedCategory(double bmi, BmiCategory expectedCategory) {
        BmiResult result = BmiCalculator.calculate(new BmiInput(bmi * 4.0, 200.0));

        assertEquals(bmi, result.bmi, 0.0);
        assertEquals(expectedCategory, result.category);
    }

    private static void assertBoundary(
            double boundary,
            BmiCategory expectedBelow,
            BmiCategory expectedAt,
            BmiCategory expectedAbove) {
        assertEquals(expectedBelow, BmiCalculator.classify(Math.nextDown(boundary)));
        assertEquals(expectedAt, BmiCalculator.classify(boundary));
        assertEquals(expectedAbove, BmiCalculator.classify(Math.nextUp(boundary)));
    }

    private static void assertClassifyRejected(double bmi) {
        assertThrows(IllegalArgumentException.class, () -> BmiCalculator.classify(bmi));
    }

    private static void assertCalculationRejected(double weightKg, double heightCm) {
        assertThrows(
                IllegalArgumentException.class,
                () -> BmiCalculator.calculate(new BmiInput(weightKg, heightCm)));
    }
}
