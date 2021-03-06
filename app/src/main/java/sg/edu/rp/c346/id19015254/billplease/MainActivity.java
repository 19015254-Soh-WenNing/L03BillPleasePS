package sg.edu.rp.c346.id19015254.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    Button splitBT;
    Button resetBT;
    TextView totalTV;
    TextView eachTV;
    EditText amtET;
    EditText paxET;
    EditText discET;
    ToggleButton svsTB;
    ToggleButton gstTB;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splitBT = findViewById(R.id.splitBT);
        resetBT = findViewById(R.id.resetBT);
        totalTV = findViewById(R.id.totalTV);
        eachTV = findViewById(R.id.eachTV);
        amtET = findViewById(R.id.amtET);
        paxET = findViewById(R.id.paxET);
        discET = findViewById(R.id.discET);
        svsTB = findViewById(R.id.svsTB);
        gstTB = findViewById(R.id.gstTB);
        rg = findViewById(R.id.rg);

        splitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amt = amtET.getText().toString();
                String pax = paxET.getText().toString();
                String disc = discET.getText().toString();

                Double amt1 = 0.0;
                Integer pax1 = 0;
                Integer disc1 = 0;

                Double total = 0.0;
                Double each = 0.0;
                int checked = rg.getCheckedRadioButtonId();

                if (amt.isEmpty() == true || pax.isEmpty() == true || disc.isEmpty() == true) {
                    Toast.makeText(MainActivity.this, "Please input all fields", Toast.LENGTH_LONG).show();
                }
                else {
                    amt1 = Double.parseDouble(amtET.getText().toString());
                    pax1 = Integer.parseInt(paxET.getText().toString());
                    disc1 = Integer.parseInt(discET.getText().toString());

                    if (amt1 < 0) {
                        totalTV.setText("Please enter appropriate amount!");
                        totalTV.setTextColor(Color.RED);
                    }
                    else if (pax1 < 0) {
                        totalTV.setText("Please enter appropriate number of pax!");
                        totalTV.setTextColor(Color.RED);
                    }
                    else if (disc1 < 0) {
                        totalTV.setText("Please enter appropriate discount!");
                        totalTV.setTextColor(Color.RED);
                    }
                    else {
                        if (gstTB.isChecked() == true && svsTB.isChecked() == true) {
                            amt1 = amt1 - (amt1 * (disc1 * 0.01));
                            total = amt1 + (amt1 * 0.1) + (amt1 * 0.07);
                            each = total / pax1;
                        } else if (gstTB.isChecked() == false && svsTB.isChecked() == true) {
                            amt1 = amt1 - (amt1 * (disc1 * 0.01));
                            total = amt1 + (amt1 * 0.1);
                            each = total / pax1;
                        } else if (gstTB.isChecked() == true && svsTB.isChecked() == false) {
                            amt1 = amt1 - (amt1 * (disc1 * 0.01));
                            total = amt1 + (amt1 * 0.07);
                            each = total / pax1;
                        } else if (gstTB.isChecked() == false && svsTB.isChecked() == false) {
                            total = amt1 - (amt1 * (disc1 * 0.01));
                            each = total / pax1;
                        }
                    }
                }
                if (checked == R.id.cashRB) {
                    totalTV.setText("Total Bill: $" + String.format("%.2f", total));
                    eachTV.setText("Each Pays: $" + String.format("%.2f", each) + " in cash");
                    eachTV.setTextColor(Color.BLACK);
                } else if (checked == R.id.pnRB) {
                    totalTV.setText("Total Bill: $" + String.format("%.2f", total));
                    eachTV.setText("Each Pays: $" + String.format("%.2f", each) + " via PayNow to 912345678");
                    eachTV.setTextColor(Color.BLACK);
                } else {
                    eachTV.setTextColor(Color.RED);
                    totalTV.setText("Please choose payment method first!");
                }
            }
        });

        resetBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalTV.setText("");
                eachTV.setText("");
                eachTV.setTextColor(Color.BLACK);
                amtET.setText("");
                paxET.setText("");
                discET.setText("");
                svsTB.setChecked(false);
                gstTB.setChecked(false);
                rg.check(R.id.cashRB);
            }
        });
    }
}