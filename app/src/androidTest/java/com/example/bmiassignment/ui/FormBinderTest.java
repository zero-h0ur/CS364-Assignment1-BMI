package com.example.bmiassignment.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.bmiassignment.R;
import com.example.bmiassignment.domain.BmiInput;
import com.example.bmiassignment.model.FormSnapshot;
import com.example.bmiassignment.presentation.BmiTextFormatter;
import com.example.bmiassignment.validation.InputError;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FormBinderTest {

    private Context context;
    private View root;
    private EditText inputWeight;
    private EditText inputHeight;
    private Button buttonCalculate;
    private TestListener listener;
    private FormBinder formBinder;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        root = LayoutInflater.from(context).inflate(R.layout.view_bmi_form, null);

        inputWeight = root.findViewById(R.id.input_weight);
        inputHeight = root.findViewById(R.id.input_height);
        buttonCalculate = root.findViewById(R.id.button_calculate);

        listener = new TestListener();
        BmiTextFormatter formatter = new BmiTextFormatter(context);

        formBinder = new FormBinder(root, formatter, listener);
    }

    @Test
    public void validData_CallsOnCalculateRequestedOnly() {
        inputWeight.setText("65");
        inputHeight.setText("168");

        listener.reset();
        buttonCalculate.performClick();

        assertTrue(listener.onCalculateRequestedCalled);
        assertFalse(listener.onValidationFailedCalled);
        assertFalse(listener.onInputChangedCalled); // Not called on button click

        assertEquals(65.0, listener.input.weightKg, 0.0001);
        assertEquals(168.0, listener.input.heightCm, 0.0001);
    }

    @Test
    public void invalidData_CallsOnValidationFailedAndShowsError() {
        inputWeight.setText("");
        inputHeight.setText("abc");

        listener.reset();
        buttonCalculate.performClick();

        assertFalse(listener.onCalculateRequestedCalled);
        assertTrue(listener.onValidationFailedCalled);

        // Check errors are shown
        assertTrue(inputWeight.getError() != null && !inputWeight.getError().toString().isEmpty());
        assertTrue(inputHeight.getError() != null && !inputHeight.getError().toString().isEmpty());
    }

    @Test
    public void editingText_ClearsErrorAndCallsOnInputChanged() {
        // First trigger an error
        inputWeight.setText("");
        buttonCalculate.performClick();
        assertTrue(inputWeight.getError() != null);

        listener.reset();

        // Edit text
        inputWeight.setText("6");

        // Error should be cleared
        assertNull(inputWeight.getError());

        // Callback should be fired
        assertTrue(listener.onInputChangedCalled);
        assertFalse(listener.onCalculateRequestedCalled);
        assertFalse(listener.onValidationFailedCalled);
    }

    @Test
    public void restore_RestoresTextAndErrorWithoutCallbacks() {
        FormSnapshot snapshot = new FormSnapshot("65", "168", InputError.NONE, InputError.INVALID_NUMBER);

        listener.reset();

        formBinder.restore(snapshot);

        // Texts restored
        assertEquals("65", inputWeight.getText().toString());
        assertEquals("168", inputHeight.getText().toString());

        // Errors restored (height has error, weight doesn't)
        assertNull(inputWeight.getError());
        assertTrue(inputHeight.getError() != null);

        // No callbacks should have been fired
        assertFalse(listener.onCalculateRequestedCalled);
        assertFalse(listener.onValidationFailedCalled);
        assertFalse(listener.onInputChangedCalled);
    }

    @Test
    public void inputs_LimitIntegerAndFractionDigits() {
        /*
         * ค่าที่มีจำนวนเต็ม 8 หลักและทศนิยม 2 ตำแหน่ง
         * ต้องสามารถแสดงในช่องกรอกได้ครบ
         */
        inputWeight.setText("12345678.90");

        assertEquals(
                "12345678.90",
                inputWeight.getText().toString()
        );

        /*
         * เมื่อพยายามเพิ่มทศนิยมตำแหน่งที่ 3
         * DecimalDigitsInputFilter ต้องปฏิเสธตัวเลขใหม่
         * และข้อความเดิมต้องไม่เปลี่ยน
         */
        inputWeight.append("1");

        assertEquals(
                "12345678.90",
                inputWeight.getText().toString()
        );

        /*
         * จำนวนเต็ม 8 หลักเป็นค่าสูงสุดที่อนุญาต
         */
        inputHeight.setText("12345678");

        /*
         * เมื่อพยายามเพิ่มจำนวนเต็มหลักที่ 9
         * ตัวกรองต้องปฏิเสธตัวเลขใหม่
         */
        inputHeight.append("9");

        assertEquals(
                "12345678",
                inputHeight.getText().toString()
        );
    }

    private static class TestListener implements FormBinder.Listener {
        boolean onCalculateRequestedCalled = false;
        boolean onInputChangedCalled = false;
        boolean onValidationFailedCalled = false;
        BmiInput input = null;

        @Override
        public void onCalculateRequested(BmiInput input) {
            this.onCalculateRequestedCalled = true;
            this.input = input;
        }

        @Override
        public void onInputChanged() {
            this.onInputChangedCalled = true;
        }

        @Override
        public void onValidationFailed() {
            this.onValidationFailedCalled = true;
        }

        void reset() {
            onCalculateRequestedCalled = false;
            onInputChangedCalled = false;
            onValidationFailedCalled = false;
            input = null;
        }
    }
}
