package com.example.bmiassignment.state;

import android.os.Bundle;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.bmiassignment.domain.BmiInput;
import com.example.bmiassignment.model.FormSnapshot;
import com.example.bmiassignment.validation.InputError;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UiStateStoreTest {

    @Test
    public void testRestore_nullBundle_returnsInitialState() {
        UiState state = UiStateStore.restore(null);

        assertNotNull(state);
        assertNotNull(state.form);
        assertEquals("", state.form.weightText);
        assertEquals("", state.form.heightText);
        assertEquals(InputError.NONE, state.form.weightError);
        assertEquals(InputError.NONE, state.form.heightError);

        assertNull(state.lastCalculatedInput);
        assertFalse(state.calculationFailed);
    }

    @Test
    public void testSaveAndRestore_emptyState() {
        UiState initialState = UiStateStore.restore(null);
        Bundle bundle = new Bundle();

        UiStateStore.save(bundle, initialState);
        UiState restoredState = UiStateStore.restore(bundle);

        assertNotNull(restoredState);
        assertEquals("", restoredState.form.weightText);
        assertEquals("", restoredState.form.heightText);
        assertEquals(InputError.NONE, restoredState.form.weightError);
        assertEquals(InputError.NONE, restoredState.form.heightError);
        assertNull(restoredState.lastCalculatedInput);
        assertFalse(restoredState.calculationFailed);
    }

    @Test
    public void testSaveAndRestore_withData() {
        FormSnapshot form = new FormSnapshot("65", "168", InputError.NONE, InputError.NONE);
        BmiInput input = new BmiInput(65.0, 168.0);
        UiState state = new UiState(form, input, false);

        Bundle bundle = new Bundle();
        UiStateStore.save(bundle, state);

        UiState restoredState = UiStateStore.restore(bundle);

        assertNotNull(restoredState);
        assertEquals("65", restoredState.form.weightText);
        assertEquals("168", restoredState.form.heightText);
        assertEquals(InputError.NONE, restoredState.form.weightError);
        assertEquals(InputError.NONE, restoredState.form.heightError);

        assertNotNull(restoredState.lastCalculatedInput);
        assertEquals(65.0, restoredState.lastCalculatedInput.weightKg, 0.001);
        assertEquals(168.0, restoredState.lastCalculatedInput.heightCm, 0.001);
        assertFalse(restoredState.calculationFailed);
    }

    @Test
    public void testSaveAndRestore_calculationFailed() {
        FormSnapshot form = new FormSnapshot("65", "-10", InputError.NONE, InputError.NON_POSITIVE);
        UiState state = new UiState(form, null, true);

        Bundle bundle = new Bundle();
        UiStateStore.save(bundle, state);

        UiState restoredState = UiStateStore.restore(bundle);

        assertNotNull(restoredState);
        assertEquals("65", restoredState.form.weightText);
        assertEquals("-10", restoredState.form.heightText);
        assertEquals(InputError.NONE, restoredState.form.weightError);
        assertEquals(InputError.NON_POSITIVE, restoredState.form.heightError);

        assertNull(restoredState.lastCalculatedInput);
        assertTrue(restoredState.calculationFailed);
    }
}
