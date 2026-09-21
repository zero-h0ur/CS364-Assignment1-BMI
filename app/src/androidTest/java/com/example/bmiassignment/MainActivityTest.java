package com.example.bmiassignment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.GONE;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.bmiassignment.domain.BmiCalculator;
import com.example.bmiassignment.domain.BmiInput;
import com.example.bmiassignment.domain.BmiResult;
import com.example.bmiassignment.presentation.BmiTextFormatter;
import com.example.bmiassignment.presentation.ResultText;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public final class MainActivityTest {

    @Test
    public void validInput_calculatesAndDisplaysResult() {
        ActivityScenario<MainActivity> scenario =
                ActivityScenario.launch(MainActivity.class);

        try {
            enterValidInput();
            calculate();

            assertSuccessfulResult();
        } finally {
            scenario.close();
        }
    }

    @Test
    public void editingInputAfterCalculation_clearsPreviousResult() {
        ActivityScenario<MainActivity> scenario =
                ActivityScenario.launch(MainActivity.class);

        try {
            enterValidInput();
            calculate();

            onView(withId(R.id.input_weight))
                    .perform(
                            scrollTo(),
                            replaceText("66"),
                            closeSoftKeyboard()
                    );

            onView(withId(R.id.text_result_message))
                    .perform(scrollTo())
                    .check(matches(withText(R.string.result_empty)))
                    .check(matches(isDisplayed()));

            onView(withId(R.id.text_bmi_value))
                    .check(matches(withEffectiveVisibility(GONE)));

            onView(withId(R.id.text_bmi_category))
                    .check(matches(withEffectiveVisibility(GONE)));
        } finally {
            scenario.close();
        }
    }

    @Test
    public void emptyInput_showsFieldErrorsAndKeepsResultEmpty() {
        ActivityScenario<MainActivity> scenario =
                ActivityScenario.launch(MainActivity.class);

        try {
            calculate();

            Context context = InstrumentationRegistry
                    .getInstrumentation()
                    .getTargetContext();
            String requiredError = context.getString(
                    R.string.error_required
            );

            onView(withId(R.id.input_weight))
                    .check(matches(hasErrorText(requiredError)));

            onView(withId(R.id.input_height))
                    .check(matches(hasErrorText(requiredError)));

            onView(withId(R.id.text_result_message))
                    .perform(scrollTo())
                    .check(matches(withText(R.string.result_empty)))
                    .check(matches(isDisplayed()));

            onView(withId(R.id.text_bmi_value))
                    .check(matches(withEffectiveVisibility(GONE)));

            onView(withId(R.id.text_bmi_category))
                    .check(matches(withEffectiveVisibility(GONE)));
        } finally {
            scenario.close();
        }
    }

    @Test
    public void recreate_restoresFormAndRebuildsResult() {
        ActivityScenario<MainActivity> scenario =
                ActivityScenario.launch(MainActivity.class);

        try {
            enterValidInput();
            calculate();

            scenario.recreate();

            onView(withId(R.id.input_weight))
                    .check(matches(withText("65")));

            onView(withId(R.id.input_height))
                    .check(matches(withText("168")));

            assertSuccessfulResult();
        } finally {
            scenario.close();
        }
    }

    private static void enterValidInput() {
        onView(withId(R.id.input_weight))
                .perform(
                        scrollTo(),
                        replaceText("65"),
                        closeSoftKeyboard()
                );

        onView(withId(R.id.input_height))
                .perform(
                        scrollTo(),
                        replaceText("168"),
                        closeSoftKeyboard()
                );
    }

    private static void calculate() {
        onView(withId(R.id.button_calculate))
                .perform(scrollTo(), click());
    }

    private static void assertSuccessfulResult() {
        ResultText expected = expectedResultText();

        onView(withId(R.id.text_result_message))
                .check(matches(withEffectiveVisibility(GONE)));

        onView(withId(R.id.text_bmi_value))
                .perform(scrollTo())
                .check(matches(withText(expected.valueText)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.text_bmi_category))
                .perform(scrollTo())
                .check(matches(withText(expected.categoryText)))
                .check(matches(isDisplayed()));
    }

    private static ResultText expectedResultText() {
        Context context = InstrumentationRegistry
                .getInstrumentation()
                .getTargetContext();

        BmiResult result = BmiCalculator.calculate(
                new BmiInput(65.0, 168.0)
        );

        return new BmiTextFormatter(context).format(result);
    }
}