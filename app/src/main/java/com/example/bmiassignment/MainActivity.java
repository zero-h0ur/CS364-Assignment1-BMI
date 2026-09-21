package com.example.bmiassignment;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bmiassignment.domain.BmiCalculator;
import com.example.bmiassignment.domain.BmiInput;
import com.example.bmiassignment.domain.BmiResult;
import com.example.bmiassignment.presentation.BmiTextFormatter;
import com.example.bmiassignment.state.UiState;
import com.example.bmiassignment.state.UiStateStore;
import com.example.bmiassignment.ui.FormBinder;
import com.example.bmiassignment.ui.ResultBinder;

public class MainActivity extends AppCompatActivity
        implements FormBinder.Listener {

    private FormBinder formBinder;
    private ResultBinder resultBinder;
    private BmiTextFormatter formatter;

    private BmiInput lastCalculatedInput;
    private boolean calculationFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View root = findViewById(R.id.main);
        applySystemBarInsets(root);

        formatter = new BmiTextFormatter(this);
        resultBinder = new ResultBinder(root);
        formBinder = new FormBinder(root, formatter, this);

        restoreState(savedInstanceState);
    }

    @Override
    public void onCalculateRequested(BmiInput input) {
        calculateAndShow(input);
    }

    @Override
    public void onInputChanged() {
        showEmptyState();
    }

    @Override
    public void onValidationFailed() {
        showEmptyState();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        UiState state = new UiState(
                formBinder.snapshot(),
                lastCalculatedInput,
                calculationFailed
        );
        UiStateStore.save(outState, state);

        super.onSaveInstanceState(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        UiState state = UiStateStore.restore(savedInstanceState);

        formBinder.restore(state.form);
        lastCalculatedInput = state.lastCalculatedInput;
        calculationFailed = state.calculationFailed;

        if (lastCalculatedInput != null) {
            calculateAndShow(lastCalculatedInput);
            return;
        }

        if (calculationFailed) {
            showCalculationError();
            return;
        }

        resultBinder.showEmpty(formatter.emptyMessage());
    }

    private void calculateAndShow(BmiInput input) {
        try {
            BmiResult result = BmiCalculator.calculate(input);

            lastCalculatedInput = input;
            calculationFailed = false;

            resultBinder.showResult(
                    result,
                    formatter.format(result)
            );
        } catch (IllegalArgumentException exception) {
            showCalculationError();
        }
    }

    private void showEmptyState() {
        lastCalculatedInput = null;
        calculationFailed = false;
        resultBinder.showEmpty(formatter.emptyMessage());
    }

    private void showCalculationError() {
        lastCalculatedInput = null;
        calculationFailed = true;
        resultBinder.showError(
                formatter.calculationErrorMessage()
        );
    }

    private void applySystemBarInsets(View root) {
        ViewCompat.setOnApplyWindowInsetsListener(
                root,
                (view, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );
                    view.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );
                    return insets;
                }
        );
    }
}