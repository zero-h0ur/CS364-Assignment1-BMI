package com.example.bmiassignment.presentation;

import android.content.Context;

import com.example.bmiassignment.R;
import com.example.bmiassignment.domain.BmiCategory;
import com.example.bmiassignment.domain.BmiResult;
import com.example.bmiassignment.validation.InputError;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public final class BmiTextFormatter {

    private final Context context;

    public BmiTextFormatter(Context context) {
        this.context = Objects.requireNonNull(context, "context");
    }

    public ResultText format(BmiResult result) {
        Objects.requireNonNull(result, "result");

        Locale locale = context.getResources()
                .getConfiguration()
                .getLocales()
                .get(0);

        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setGroupingUsed(false);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);

        BigDecimal roundedBmi = BigDecimal.valueOf(result.bmi)
                .setScale(2, RoundingMode.HALF_UP);

        String formattedBmi = numberFormat.format(roundedBmi);

        String valueText = context.getString(
                R.string.bmi_value_format,
                formattedBmi
        );

        String categoryText = context.getString(
                categoryResource(result.category)
        );

        return new ResultText(valueText, categoryText);
    }

    public String inputError(InputError error) {
        Objects.requireNonNull(error, "error");

        switch (error) {
            case NONE:
                return "";

            case REQUIRED:
                return context.getString(R.string.error_required);

            case INVALID_NUMBER:
                return context.getString(R.string.error_invalid_number);

            case NON_POSITIVE:
                return context.getString(R.string.error_non_positive);

            default:
                throw new IllegalArgumentException(
                        "Unsupported input error: " + error
                );
        }
    }

    public String emptyMessage() {
        return context.getString(R.string.result_empty);
    }

    public String calculationErrorMessage() {
        return context.getString(R.string.error_calculation);
    }

    private int categoryResource(BmiCategory category) {
        Objects.requireNonNull(category, "category");

        switch (category) {
            case SEVERE_THINNESS:
                return R.string.category_severe_thinness;

            case MODERATE_THINNESS:
                return R.string.category_moderate_thinness;

            case MILD_THINNESS:
                return R.string.category_mild_thinness;

            case NORMAL:
                return R.string.category_normal;

            case OVERWEIGHT:
                return R.string.category_overweight;

            case OBESE_I:
                return R.string.category_obese_i;

            case OBESE_II:
                return R.string.category_obese_ii;

            case OBESE_III:
                return R.string.category_obese_iii;

            default:
                throw new IllegalArgumentException(
                        "Unsupported BMI category: " + category
                );
        }
    }
}