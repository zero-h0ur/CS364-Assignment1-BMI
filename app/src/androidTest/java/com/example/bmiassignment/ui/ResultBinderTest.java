package com.example.bmiassignment.ui;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.bmiassignment.R;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public final class ResultBinderTest {

    @Test
    public void showEmpty_showsMessageAndClearsPreviousResult() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            Context context = InstrumentationRegistry
                    .getInstrumentation()
                    .getTargetContext();

            View root = LayoutInflater.from(context)
                    .inflate(R.layout.view_bmi_result, null, false);

            TextView message = root.findViewById(
                    R.id.text_result_message
            );
            TextView value = root.findViewById(
                    R.id.text_bmi_value
            );
            TextView category = root.findViewById(
                    R.id.text_bmi_category
            );

            value.setText("23.03 kg/m²");
            value.setVisibility(View.VISIBLE);
            category.setText("Normal");
            category.setVisibility(View.VISIBLE);

            ResultBinder binder = new ResultBinder(root);
            binder.showEmpty("Enter weight and height");

            assertEquals(
                    "Enter weight and height",
                    message.getText().toString()
            );
            assertEquals(View.VISIBLE, message.getVisibility());
            assertEquals(
                    context.getColor(R.color.bmi_result_neutral),
                    message.getCurrentTextColor()
            );

            assertEquals(View.GONE, value.getVisibility());
            assertEquals("", value.getText().toString());

            assertEquals(View.GONE, category.getVisibility());
            assertEquals("", category.getText().toString());
        });
    }

    @Test
    public void showError_showsErrorMessageAndColor() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            Context context = InstrumentationRegistry
                    .getInstrumentation()
                    .getTargetContext();

            View root = LayoutInflater.from(context)
                    .inflate(R.layout.view_bmi_result, null, false);

            ResultBinder binder = new ResultBinder(root);
            binder.showError("Invalid input");

            TextView message = root.findViewById(
                    R.id.text_result_message
            );
            TextView value = root.findViewById(
                    R.id.text_bmi_value
            );
            TextView category = root.findViewById(
                    R.id.text_bmi_category
            );

            assertEquals(
                    "Invalid input",
                    message.getText().toString()
            );
            assertEquals(View.VISIBLE, message.getVisibility());
            assertEquals(
                    context.getColor(R.color.bmi_result_error),
                    message.getCurrentTextColor()
            );

            assertEquals(View.GONE, value.getVisibility());
            assertEquals(View.GONE, category.getVisibility());
        });
    }
}