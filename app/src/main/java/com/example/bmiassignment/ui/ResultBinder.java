package com.example.bmiassignment.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorRes;

import com.example.bmiassignment.R;

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