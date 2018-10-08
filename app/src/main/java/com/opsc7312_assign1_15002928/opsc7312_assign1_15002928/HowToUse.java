package com.opsc7312_assign1_15002928.opsc7312_assign1_15002928;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class HowToUse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use);

        TextView txtV_htu = (TextView) findViewById(R.id.txtV_howToUseText) ;

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }

        String bundleData = bundle.getString("optionHowToUse");
        try {
            if(bundleData.equals("Q1")){
                txtV_htu.setText(R.string.htu_q1);
            }else if(bundleData.equals("Q2")){
                txtV_htu.setText(R.string.htu_q2);
            }
        }catch (NullPointerException exc){
            exc.printStackTrace();
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
    }

    public void btnClick_done(View view){
        finish();
    }
}
