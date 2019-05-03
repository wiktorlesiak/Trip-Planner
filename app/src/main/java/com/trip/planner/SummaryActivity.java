package com.trip.planner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SummaryActivity extends AppCompatActivity {

    TextView distance, fBMR, mBMR;
    Button backButton, finishButton;
    Spinner sumS;
    double mets;
    EditText dur;
    int duration;
    String femaleFormatted, maleFormatted, distanceFormatted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_summary);
        sumS = (Spinner) findViewById(R.id.metSpinner);
        fBMR = (TextView)findViewById(R.id.fBMR);
        mBMR = (TextView)findViewById(R.id.mBMR);
        distance = (TextView)findViewById(R.id.distance);

        dur = (EditText) findViewById(R.id.dur);

        backButton = (Button)findViewById(R.id.backButton);
        finishButton = (Button)findViewById(R.id.finishButton);

        Intent intent = getIntent();
        Double maleBMR = intent.getDoubleExtra("mBMR", 0);
        Double femaleBMR = intent.getDoubleExtra("fBMR", 0);
        Float dist = intent.getFloatExtra("distance", 0);

        maleFormatted = String.format("%.0f", maleBMR);
        femaleFormatted = String.format("%.0f", femaleBMR);
        distanceFormatted = String.format("%.2f", dist);

        fBMR.setText(maleFormatted);
        mBMR.setText(femaleFormatted);
        distance.setText(distanceFormatted + "KM");
        //source: https://community.plu.edu/~chasega/met.html
        String names[] = {"Please select PACE",
                "Bicycling, stationary, 50 watts, very light effort",
                "Bicycling, < 16 kmh, leisure, to work or for pleasure",
                "Bicycling 16-19 kmh, leisure slow, light effort",
                "Bicycling 19.3-22.3 kmh leisure, moderate effort",
                "Bicycling 22.5-25.5 kmh, racing or recreational, fast, vigorous effort",
                "Bicycling 25.7-30.5 kmh, racing/not drafting or >30.5 kmh drafting, very fast, general racing",
                "Bicycling >32 kmh, racing, not drafting",};

        ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, names);

        sumS.setAdapter(adapter);

        sumS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position)
                {
                    case 0:
                        mets = 0;
                        break;
                    case 1:
                        mets = 3;
                        break;
                    case 2:
                        mets = 4;
                        break;
                    case 3:
                        mets = 6;
                        break;
                    case 4:
                        mets = 8;
                        break;
                    case 5:
                        mets = 10;
                        break;
                    case 6:
                        mets = 12;
                        break;
                    case 7:
                        mets = 16;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SummaryActivity.this, MapsActivity.class));
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mets == 0) {
                    Toast.makeText(SummaryActivity.this, "Please select pace before continuing...", Toast.LENGTH_SHORT).show();
                }else if(mets > 0){
                    //startActivity(new Intent(HomeActivity.this, MapsActivity.class));

                }
                if(TextUtils.isEmpty(dur.getText())){
                    dur.setError("Time is required");
                    dur.requestFocus();
                }else{
                    String stringDuration = dur.getText().toString();
                    duration = Integer.parseInt(stringDuration);
                    Toast.makeText(SummaryActivity.this, "It's working", Toast.LENGTH_SHORT).show();
                    dur.setError(null);

                }
            }
        });

    }



}
