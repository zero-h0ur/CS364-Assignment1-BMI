package com.example.bmiassignment.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorRes;

import com.example.bmiassignment.R;
import com.example.bmiassignment.domain.BmiCategory;
import com.example.bmiassignment.domain.BmiResult;
import com.example.bmiassignment.presentation.ResultText;

import java.util.Objects;

public final class ResultBinder {

    private final TextView resultMessage;
    private final TextView bmiValue;
    private final TextView bmiCategory;

    public ResultBinder(View root) {
        Objects.requireNonNull(root, "root");

        resultMessage = requireTextView(
                root,
                R.id.text_result_message
        );

        bmiValue = requireTextView(
                root,
                R.id.text_bmi_value
        );

        bmiCategory = requireTextView(
                root,
                R.id.text_bmi_category
        );
    }

    public void showEmpty(String message) {
        showMessage(message, R.color.bmi_result_neutral);
    }

    public void showResult(BmiResult result, ResultText text) {
        Objects.requireNonNull(result, "result");
        Objects.requireNonNull(text, "text");

        resultMessage.setText(null);
        resultMessage.setVisibility(View.GONE);

        bmiValue.setText(text.valueText);
        bmiValue.setTextColor(
                bmiValue.getContext().getColor(
                        R.color.bmi_result_value
                )
        );
        bmiValue.setVisibility(View.VISIBLE);

        bmiCategory.setText(text.categoryText);
        bmiCategory.setTextColor(
                bmiCategory.getContext().getColor(
                        colorForCategory(result.category)
                )
        );
        bmiCategory.setVisibility(View.VISIBLE);
    }

    public void showError(String message) {
        showMessage(message, R.color.bmi_result_error);
    }

    private void showMessage(
            String message,
            @ColorRes int colorResource
    ) {
        Objects.requireNonNull(message, "message");

        clearResult();

        resultMessage.setText(message);
        resultMessage.setTextColor(
                resultMessage.getContext().getColor(colorResource)
        );
        resultMessage.setVisibility(View.VISIBLE);
    }

    private void clearResult() {
        bmiValue.setText(null);
        bmiValue.setVisibility(View.GONE);

        bmiCategory.setText(null);
        bmiCategory.setVisibility(View.GONE);
    }

    @ColorRes
    private static int colorForCategory(BmiCategory category) {
        switch (category) {
            case SEVERE_THINNESS:
            case MODERATE_THINNESS:
            case MILD_THINNESS:
                return R.color.bmi_underweight;

            case NORMAL:
                return R.color.bmi_normal;

            case OVERWEIGHT:
                return R.color.bmi_overweight;

            case OBESE_I:
            case OBESE_II:
            case OBESE_III:
                return R.color.bmi_obese;

            default:
                throw new IllegalArgumentException(
                        "Unsupported BMI category: " + category
                );
        }
    }

    private static TextView requireTextView(
            View root,
            int viewId
    ) {
        View view = root.findViewById(viewId);

        if (!(view instanceof TextView)) {
            throw new IllegalArgumentException(
                    "Required TextView is missing: " + viewId
            );
        }

        return (TextView) view;
    }
}