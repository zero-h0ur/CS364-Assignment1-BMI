package com.example.bmiassignment.presentation;

import java.util.Objects;

public final class ResultText {

    public final String valueText;
    public final String categoryText;

    public ResultText(String valueText, String categoryText) {
        this.valueText = Objects.requireNonNull(valueText, "valueText");
        this.categoryText = Objects.requireNonNull(categoryText, "categoryText");
    }
}