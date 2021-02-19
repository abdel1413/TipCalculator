package tipcalculator.example.tipcalculator;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.tipcalculator.R;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity implements TextWatcher, SeekBar.OnSeekBarChangeListener{


    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    //declare variables for the widgets
    private EditText editTextBillAmount;
    private TextView textViewBillAmount;
    private TextView textView;
    private TextView tipAmount;
    private TextView totalAmount;
    private TextView tip;
    private TextView total;
    private SeekBar sb;

    //declare the variables for the calculations
    private double billAmount = 0.00;
    private double percent = .15;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.tipcalculator.R.layout.activity_main);


        editTextBillAmount = (android.widget.EditText)findViewById(com.example.tipcalculator.R.id.editText_BillAmount);
        editTextBillAmount.addTextChangedListener((android.text.TextWatcher) this);

        textView = findViewById(com.example.tipcalculator.R.id.textView);
        tipAmount = (android.widget.TextView)findViewById(com.example.tipcalculator.R.id.TipAmount);

        totalAmount = findViewById(com.example.tipcalculator.R.id.TotalAmount);
        tip = findViewById(R.id.Tip);
        total = findViewById(R.id.Total);
        sb = findViewById(com.example.tipcalculator.R.id.seekBar);
        sb.setOnSeekBarChangeListener((android.widget.SeekBar.OnSeekBarChangeListener)this);
        textView.setText(percentFormat.format(percent));

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        calculate();
    }


    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        try {
            Log.d("MainActivity", "inside onTextChanged method: charSequence= " + charSequence);


            billAmount = Double.parseDouble(charSequence.toString()) ;
            Log.d("MainActivity", "Bill Amount = " + billAmount);


            calculate();

        } catch(Exception e){
         
            totalAmount.setText(currencyFormat.format(0));
            tipAmount.setText(currencyFormat.format(0));
            billAmount = 0;

        }
    }



    @Override
    public void afterTextChanged(Editable editable) {
        calculate();

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        percent = progress *.01;
        textView.setText(percentFormat.format(progress * .01));
        calculate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        percent = seekBar.getProgress() * .01;
        calculate();
    }


    private void calculate() {
        try {
            Log.d("MainActivity", "inside calculate method");

            // format percent and display in percentTextView
            textView.setText(percentFormat.format(percent));

            // calculate the tip and total
            double tip1 = billAmount * percent;

            tipAmount.setText(currencyFormat.format(tip1));

            //use the tip example to do the same for the Total
            double total1 = billAmount + tip1;

            totalAmount.setText(currencyFormat.format(total1));
        } catch(Exception e) {
            android.util.Log.d("MainActivity", "calculate: Cannot divide by 0");
        }

    }

}
