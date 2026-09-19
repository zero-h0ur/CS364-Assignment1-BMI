package com.example.bmiapplication.state;

import android.os.Bundle;

import com.example.bmiapplication.domain.BmiInput;
import com.example.bmiapplication.model.FormSnapshot;
import com.example.bmiapplication.validation.InputError;

public final class UiStateStore {
    private static final String KEY_WEIGHT_TEXT = "bmi_state.weight_text";
    private static final String KEY_HEIGHT_TEXT = "bmi_state.height_text";
    private static final String KEY_WEIGHT_ERROR = "bmi_state.weight_error";
    private static final String KEY_HEIGHT_ERROR = "bmi_state.height_error";

    private static final String KEY_HAS_LAST_CALCULATED_INPUT = "bmi_state.has_last_input";
    private static final String KEY_LAST_WEIGHT_KG = "bmi_state.last_weight_kg";
    private static final String KEY_LAST_HEIGHT_CM = "bmi_state.last_height_cm";
    
    private static final String KEY_CALCULATION_FAILED = "bmi_state.calculation_failed";

    public static void save(Bundle outState, UiState state) {
        if (outState == null || state == null) return;
        
        outState.putString(KEY_WEIGHT_TEXT, state.form.weightText);
        outState.putString(KEY_HEIGHT_TEXT, state.form.heightText);
        outState.putString(KEY_WEIGHT_ERROR, state.form.weightError.name());
        outState.putString(KEY_HEIGHT_ERROR, state.form.heightError.name());
        
        if (state.lastCalculatedInput != null) {
            outState.putBoolean(KEY_HAS_LAST_CALCULATED_INPUT, true);
            outState.putDouble(KEY_LAST_WEIGHT_KG, state.lastCalculatedInput.weightKg);
            outState.putDouble(KEY_LAST_HEIGHT_CM, state.lastCalculatedInput.heightCm);
        } else {
            outState.putBoolean(KEY_HAS_LAST_CALCULATED_INPUT, false);
        }
        
        outState.putBoolean(KEY_CALCULATION_FAILED, state.calculationFailed);
    }

    public static UiState restore(Bundle savedState) {
        if (savedState == null) {
            return getInitialState();
        }

        String weightText = savedState.getString(KEY_WEIGHT_TEXT, "");
        String heightText = savedState.getString(KEY_HEIGHT_TEXT, "");
        
        InputError weightError = parseInputError(savedState.getString(KEY_WEIGHT_ERROR));
        InputError heightError = parseInputError(savedState.getString(KEY_HEIGHT_ERROR));
        
        FormSnapshot form = new FormSnapshot(weightText, heightText, weightError, heightError);
        
        BmiInput lastInput = null;
        boolean hasLastInput = savedState.getBoolean(KEY_HAS_LAST_CALCULATED_INPUT, false);
        if (hasLastInput) {
            double lastWeight = savedState.getDouble(KEY_LAST_WEIGHT_KG, -1);
            double lastHeight = savedState.getDouble(KEY_LAST_HEIGHT_CM, -1);
            if (lastWeight > 0 && lastHeight > 0) {
                lastInput = new BmiInput(lastWeight, lastHeight);
            }
        }
        
        boolean calculationFailed = savedState.getBoolean(KEY_CALCULATION_FAILED, false);
        
        return new UiState(form, lastInput, calculationFailed);
    }

    private static UiState getInitialState() {
        return new UiState(
            new FormSnapshot("", "", InputError.NONE, InputError.NONE),
            null,
            false
        );
    }

    private static InputError parseInputError(String name) {
        if (name == null || name.isEmpty()) return InputError.NONE;
        try {
            return InputError.valueOf(name);
        } catch (IllegalArgumentException e) {
            return InputError.NONE;
        }
    }
}