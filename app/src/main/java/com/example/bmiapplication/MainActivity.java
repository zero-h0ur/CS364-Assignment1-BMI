package com.example.bmiapplication;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bmiapplication.model.FormSnapshot;
import com.example.bmiapplication.state.UiState;
import com.example.bmiapplication.state.UiStateStore;
import com.example.bmiapplication.validation.InputError;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // MOCK INTEGRATION for Member D to test state restoration
        // Note: The actual architecture dictates Member A writes this and uses FormBinder
        UiState state = UiStateStore.restore(savedInstanceState);
        EditText weight = findViewById(R.id.input_weight);
        EditText height = findViewById(R.id.input_height);
        
        if (weight != null) weight.setText(state.form.weightText);
        if (height != null) height.setText(state.form.heightText);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        
        // MOCK INTEGRATION for Member D to test state restoration
        EditText weight = findViewById(R.id.input_weight);
        EditText height = findViewById(R.id.input_height);
        
        FormSnapshot mockSnapshot = new FormSnapshot(
                weight != null ? weight.getText().toString() : "",
                height != null ? height.getText().toString() : "",
                InputError.NONE, InputError.NONE
        );
        
        UiState state = new UiState(mockSnapshot, null, false);
        UiStateStore.save(outState, state);
    }
}