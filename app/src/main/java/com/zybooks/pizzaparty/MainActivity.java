package com.zybooks.pizzaparty;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.text.TextWatcher;
import android.text.Editable;


public class MainActivity extends AppCompatActivity {

    public final static int SLICES_PER_PIZZA = 8;

    private EditText mNumAttendEditText;
    private TextView mNumPizzasTextView;
    private Spinner mHungrySpinner;

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate was called");


        mNumAttendEditText = findViewById(R.id.num_attend_edit_text);
        mNumPizzasTextView = findViewById(R.id.num_pizzas_text_view);
        mHungrySpinner = findViewById(R.id.hungry_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hunger_levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHungrySpinner.setAdapter(adapter);


        mHungrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mNumPizzasTextView.setText("Total Pizzas: 0");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        mNumAttendEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                mNumPizzasTextView.setText("Total Pizzas: 0");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public void calculateClick(View view) {


        int numAttend;
        try {
            String numAttendStr = mNumAttendEditText.getText().toString();
            numAttend = Integer.parseInt(numAttendStr);
        }
        catch (NumberFormatException ex) {
            numAttend = 0;
        }


        String selectedHungerLevel = mHungrySpinner.getSelectedItem().toString();
        PizzaCalculator.HungerLevel hungerLevel = PizzaCalculator.HungerLevel.RAVENOUS;
        if (selectedHungerLevel.equals(getString(R.string.light))) {
            hungerLevel = PizzaCalculator.HungerLevel.LIGHT;
        } else if (selectedHungerLevel.equals(getString(R.string.medium))) {
            hungerLevel = PizzaCalculator.HungerLevel.MEDIUM;
        }

        PizzaCalculator calc = new PizzaCalculator(numAttend, hungerLevel);
        int totalPizzas = calc.getTotalPizzas();


        String totalText = getString(R.string.total_pizzas_num, totalPizzas);
        mNumPizzasTextView.setText(totalText);
    }


}


