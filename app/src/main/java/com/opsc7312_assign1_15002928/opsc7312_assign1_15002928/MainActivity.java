package com.opsc7312_assign1_15002928.opsc7312_assign1_15002928;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.GoogleMap;

public class MainActivity extends AppCompatActivity {

       private Button btnToQuestion1;
    private Button btnToQuestion2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToQuestion1 = (Button) findViewById(R.id.btnQuestion1);
        btnToQuestion1 = (Button) findViewById(R.id.btnQuestion1);


    }

    public void btnClick_btnToQuestion1(View view){
        Intent intent = new Intent(this, Question1a.class);
        startActivity(intent);
    }

    public void btnClick_btnToQuestion2(View view){
        Intent intent = new Intent(this, Question2.class);
        startActivity(intent);
    }

}
