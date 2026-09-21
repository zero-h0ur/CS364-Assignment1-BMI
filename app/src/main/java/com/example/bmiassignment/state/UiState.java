package com.example.bmiassignment.state;

import com.example.bmiassignment.domain.BmiInput;
import com.example.bmiassignment.model.FormSnapshot;

public final class UiState {
    public final FormSnapshot form;
    public final BmiInput lastCalculatedInput;
    public final boolean calculationFailed;

    public UiState(FormSnapshot form, BmiInput lastCalculatedInput, boolean calculationFailed) {
        this.form = form;
        this.lastCalculatedInput = lastCalculatedInput;
        this.calculationFailed = calculationFailed;
    }
}
