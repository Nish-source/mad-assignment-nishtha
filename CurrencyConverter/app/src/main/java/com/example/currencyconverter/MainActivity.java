package com.example.currencyconverter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    EditText amount;
    Spinner fromCurrency, toCurrency;
    TextView result;

    String[] currencies = {"INR", "USD", "EUR", "JPY"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.amount);
        fromCurrency = findViewById(R.id.fromCurrency);
        toCurrency = findViewById(R.id.toCurrency);
        result = findViewById(R.id.result);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, currencies);

        fromCurrency.setAdapter(adapter);
        toCurrency.setAdapter(adapter);
    }

    public void convert(View view) {
        double input = Double.parseDouble(amount.getText().toString());

        String from = fromCurrency.getSelectedItem().toString();
        String to = toCurrency.getSelectedItem().toString();

        double inINR = convertToINR(from, input);
        double finalAmount = convertFromINR(to, inINR);

        result.setText("Result: " + finalAmount);
    }

    private double convertToINR(String currency, double amount) {
        switch (currency) {
            case "USD": return amount * 93.03;
            case "EUR": return amount * 107.25;
            case "JPY": return amount * 0.58;
            default: return amount;
        }
    }

    private double convertFromINR(String currency, double amount) {
        switch (currency) {
            case "USD": return amount / 93.03;
            case "EUR": return amount / 107.25;
            case "JPY": return amount / 0.58;
            default: return amount;
        }
    }

    public void openSettings(View view) {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }

    private void loadTheme() {
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        boolean darkMode = prefs.getBoolean("darkMode", false);

        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}