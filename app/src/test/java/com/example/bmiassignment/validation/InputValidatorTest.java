package com.example.bmiassignment.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class InputValidatorTest {

    @Test
    public void validInputs_ReturnsValidResult() {
        InputValidationResult result = InputValidator.validate("65", "168");
        assertTrue(result.isValid());
        assertEquals(InputError.NONE, result.weightError);
        assertEquals(InputError.NONE, result.heightError);
        assertEquals(65.0, result.input.weightKg, 0.0001);
        assertEquals(168.0, result.input.heightCm, 0.0001);
    }

    @Test
    public void validInputsWithTrimmingAndDecimals_ReturnsValidResult() {
        InputValidationResult result = InputValidator.validate("  65.5  ", " .5 ");
        assertTrue(result.isValid());
        assertEquals(65.5, result.input.weightKg, 0.0001);
        assertEquals(0.5, result.input.heightCm, 0.0001);
    }

    @Test
    public void validInputsWithPlusSign_ReturnsValidResult() {
        InputValidationResult result = InputValidator.validate("+65", "+168.5");
        assertTrue(result.isValid());
        assertEquals(65.0, result.input.weightKg, 0.0001);
        assertEquals(168.5, result.input.heightCm, 0.0001);
    }

    @Test
    public void emptyOrSpaces_ReturnsRequiredError() {
        InputValidationResult result = InputValidator.validate("", "   ");
        assertFalse(result.isValid());
        assertNull(result.input);
        assertEquals(InputError.REQUIRED, result.weightError);
        assertEquals(InputError.REQUIRED, result.heightError);

        result = InputValidator.validate(null, null);
        assertFalse(result.isValid());
        assertEquals(InputError.REQUIRED, result.weightError);
        assertEquals(InputError.REQUIRED, result.heightError);
    }

    @Test
    public void zeroOrNegative_ReturnsNonPositiveError() {
        InputValidationResult result = InputValidator.validate("0", "-168");
        assertFalse(result.isValid());
        assertNull(result.input);
        assertEquals(InputError.NON_POSITIVE, result.weightError);
        assertEquals(InputError.NON_POSITIVE, result.heightError);

        result = InputValidator.validate("-0.0", "-0.1");
        assertFalse(result.isValid());
        assertEquals(InputError.NON_POSITIVE, result.weightError);
        assertEquals(InputError.NON_POSITIVE, result.heightError);
    }

    @Test
    public void invalidFormat_ReturnsInvalidNumberError() {
        InputValidationResult result = InputValidator.validate("65,000", "1.68e2");
        assertFalse(result.isValid());
        assertNull(result.input);
        assertEquals(InputError.INVALID_NUMBER, result.weightError);
        assertEquals(InputError.INVALID_NUMBER, result.heightError);

        result = InputValidator.validate("NaN", "Infinity");
        assertFalse(result.isValid());
        assertEquals(InputError.INVALID_NUMBER, result.weightError);
        assertEquals(InputError.INVALID_NUMBER, result.heightError);

        result = InputValidator.validate("abc", "12 3");
        assertFalse(result.isValid());
        assertEquals(InputError.INVALID_NUMBER, result.weightError);
        assertEquals(InputError.INVALID_NUMBER, result.heightError);
    }

    @Test
    public void mixedValidity_ReturnsIsolatedErrors() {
        // Valid weight, invalid height
        InputValidationResult result = InputValidator.validate("65", "abc");
        assertFalse(result.isValid());
        assertNull(result.input);
        assertEquals(InputError.NONE, result.weightError);
        assertEquals(InputError.INVALID_NUMBER, result.heightError);

        // Invalid weight, valid height
        result = InputValidator.validate("-5", "168");
        assertFalse(result.isValid());
        assertNull(result.input);
        assertEquals(InputError.NON_POSITIVE, result.weightError);
        assertEquals(InputError.NONE, result.heightError);
    }
}
