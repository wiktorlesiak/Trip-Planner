package com.trip.planner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FinishActivity extends AppCompatActivity {
    TextView weightkg,bmrMale, bmrFemale;
    double maleBMR, femaleBMR, mets, BMRFINAL, finalmale, finalfemale;
    Float dist;
    int duration, weig;
    Button calc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        weightkg = (TextView) findViewById(R.id.weightkg);
        bmrMale = (TextView) findViewById(R.id.bmrMaleF);
        bmrFemale = (TextView) findViewById(R.id.bmrFemaleF);
        calc = (Button) findViewById(R.id.calc);



        Intent intent = getIntent();
        maleBMR = intent.getDoubleExtra("mB", 0);
        femaleBMR = intent.getDoubleExtra("fB", 0);
        dist = intent.getFloatExtra("distTT", 0);
        mets = intent.getDoubleExtra("metts", 0);
        weig = intent.getIntExtra("weig", 0);
        duration = intent.getIntExtra("durrr", 0);

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BMRFINAL = (mets * (weig) * 3.5 / 200) * (duration);
                weightkg.setText(Double.toString(BMRFINAL));
                //(MET * (weight) * 3.5 / 200) * (time of activity)

                //CALORIES MALE
                finalmale = maleBMR + BMRFINAL;
                finalfemale = femaleBMR + BMRFINAL;
                String maleFormatted = String.format("%.0f", finalmale);
                String femaleFormatted = String.format("%.0f", finalfemale);

                bmrMale.setText(maleFormatted);

                bmrFemale.setText(femaleFormatted);

            }
        });

    }
}
