package com.example.bmiassignment.ui;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.bmiassignment.R;
import com.example.bmiassignment.domain.BmiCategory;
import com.example.bmiassignment.domain.BmiResult;
import com.example.bmiassignment.presentation.ResultText;

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
    public void showResult_showsValueAndCategoryAndHidesMessage() {
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

            ResultBinder binder = new ResultBinder(root);
            binder.showEmpty("Enter weight and height");

            BmiResult result = new BmiResult(
                    22.50,
                    BmiCategory.NORMAL
            );
            ResultText text = new ResultText(
                    "22.50 kg/m²",
                    "Normal"
            );

            binder.showResult(result, text);

            assertEquals(View.GONE, message.getVisibility());
            assertEquals("", message.getText().toString());

            assertEquals(View.VISIBLE, value.getVisibility());
            assertEquals("22.50 kg/m²", value.getText().toString());
            assertEquals(
                    context.getColor(R.color.bmi_result_value),
                    value.getCurrentTextColor()
            );

            assertEquals(View.VISIBLE, category.getVisibility());
            assertEquals("Normal", category.getText().toString());
            assertEquals(
                    context.getColor(R.color.bmi_normal),
                    category.getCurrentTextColor()
            );
        });
    }

    @Test
    public void showResult_usesExpectedColorForEveryCategory() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            Context context = InstrumentationRegistry
                    .getInstrumentation()
                    .getTargetContext();

            View root = LayoutInflater.from(context)
                    .inflate(R.layout.view_bmi_result, null, false);

            TextView category = root.findViewById(
                    R.id.text_bmi_category
            );
            ResultBinder binder = new ResultBinder(root);

            assertCategoryColor(
                    context,
                    binder,
                    category,
                    BmiCategory.SEVERE_THINNESS,
                    R.color.bmi_underweight
            );
            assertCategoryColor(
                    context,
                    binder,
                    category,
                    BmiCategory.MODERATE_THINNESS,
                    R.color.bmi_underweight
            );
            assertCategoryColor(
                    context,
                    binder,
                    category,
                    BmiCategory.MILD_THINNESS,
                    R.color.bmi_underweight
            );
            assertCategoryColor(
                    context,
                    binder,
                    category,
                    BmiCategory.NORMAL,
                    R.color.bmi_normal
            );
            assertCategoryColor(
                    context,
                    binder,
                    category,
                    BmiCategory.OVERWEIGHT,
                    R.color.bmi_overweight
            );
            assertCategoryColor(
                    context,
                    binder,
                    category,
                    BmiCategory.OBESE_I,
                    R.color.bmi_obese
            );
            assertCategoryColor(
                    context,
                    binder,
                    category,
                    BmiCategory.OBESE_II,
                    R.color.bmi_obese
            );
            assertCategoryColor(
                    context,
                    binder,
                    category,
                    BmiCategory.OBESE_III,
                    R.color.bmi_obese
            );
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

    private static void assertCategoryColor(
            Context context,
            ResultBinder binder,
            TextView categoryView,
            BmiCategory category,
            @ColorRes int expectedColor
    ) {
        BmiResult result = new BmiResult(20.0, category);
        ResultText text = new ResultText(
                "20.00 kg/m²",
                category.name()
        );

        binder.showResult(result, text);

        assertEquals(
                context.getColor(expectedColor),
                categoryView.getCurrentTextColor()
        );
    }
}