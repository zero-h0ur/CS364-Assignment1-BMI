package com.example.bmiassignment.ui;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bmiassignment.R;
import com.example.bmiassignment.domain.BmiInput;
import com.example.bmiassignment.model.FormSnapshot;
import com.example.bmiassignment.presentation.BmiTextFormatter;
import com.example.bmiassignment.validation.DecimalDigitsInputFilter;
import com.example.bmiassignment.validation.InputError;
import com.example.bmiassignment.validation.InputValidationResult;
import com.example.bmiassignment.validation.InputValidator;

public final class FormBinder {
    public interface Listener {
        void onCalculateRequested(BmiInput input);
        void onInputChanged();
        void onValidationFailed();
    }

    private final EditText inputWeight;
    private final EditText inputHeight;
    private final Button buttonCalculate;
    private final BmiTextFormatter formatter;
    private final Listener listener;

    private InputError currentWeightError = InputError.NONE;
    private InputError currentHeightError = InputError.NONE;

    private boolean isRestoring = false;

    public FormBinder(View root, BmiTextFormatter formatter, Listener listener) {
        this.formatter = formatter;
        this.listener = listener;

        inputWeight = root.findViewById(R.id.input_weight);
        inputHeight = root.findViewById(R.id.input_height);
        buttonCalculate = root.findViewById(R.id.button_calculate);

        /*
         * สร้างตัวกรองตามข้อกำหนดร่วมจาก InputValidator
         *
         * การอ้างค่าคงที่จาก InputValidator ช่วยป้องกันไม่ให้
         * FormBinder และ Validation ใช้จำนวนหลักไม่ตรงกัน
         */
        InputFilter decimalFilter = new DecimalDigitsInputFilter(
                InputValidator.MAX_INTEGER_DIGITS,
                InputValidator.MAX_FRACTION_DIGITS
        );

        /*
         * ใช้ตัวกรองเดียวกันกับช่องน้ำหนักและส่วนสูง
         * เพราะทั้งสองช่องมีข้อกำหนดจำนวนหลักเหมือนกัน
         */
        inputWeight.setFilters(new InputFilter[]{decimalFilter});
        inputHeight.setFilters(new InputFilter[]{decimalFilter});

        /*
         * ปิดระบบบันทึก View state อัตโนมัติ
         * เพราะโปรเจกต์ใช้ UiStateStore ดูแลการ Restore เอง
         */
        inputWeight.setSaveEnabled(false);
        inputHeight.setSaveEnabled(false);

        TextWatcher weightWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isRestoring) return;
                currentWeightError = InputError.NONE;
                inputWeight.setError(null);
                listener.onInputChanged();
            }
        };

        TextWatcher heightWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isRestoring) return;
                currentHeightError = InputError.NONE;
                inputHeight.setError(null);
                listener.onInputChanged();
            }
        };

        inputWeight.addTextChangedListener(weightWatcher);
        inputHeight.addTextChangedListener(heightWatcher);

        buttonCalculate.setOnClickListener(v -> {
            InputValidationResult result = InputValidator.validate(
                    inputWeight.getText().toString(),
                    inputHeight.getText().toString()
            );

            currentWeightError = result.weightError;
            currentHeightError = result.heightError;

            updateErrorUI();

            if (result.isValid()) {
                listener.onCalculateRequested(result.input);
            } else {
                listener.onValidationFailed();
            }
        });
    }

    private void updateErrorUI() {
        if (currentWeightError != InputError.NONE) {
            String errorMsg = formatter.inputError(currentWeightError);
            inputWeight.setError(errorMsg.isEmpty() ? null : errorMsg);
        } else {
            inputWeight.setError(null);
        }

        if (currentHeightError != InputError.NONE) {
            String errorMsg = formatter.inputError(currentHeightError);
            inputHeight.setError(errorMsg.isEmpty() ? null : errorMsg);
        } else {
            inputHeight.setError(null);
        }
    }

    public FormSnapshot snapshot() {
        return new FormSnapshot(
                inputWeight.getText().toString(),
                inputHeight.getText().toString(),
                currentWeightError,
                currentHeightError
        );
    }

    public void restore(FormSnapshot snapshot) {
        isRestoring = true;
        inputWeight.setText(snapshot.weightText);
        inputHeight.setText(snapshot.heightText);
        currentWeightError = snapshot.weightError;
        currentHeightError = snapshot.heightError;
        updateErrorUI();
        isRestoring = false;
    }
}
