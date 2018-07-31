package com.eleo95.magic8;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button startBtn = findViewById(R.id.sButton);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder aDialog;
        //Manejo del style si es menor a Sdk20
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH){
            aDialog = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
        }else{
            aDialog = new AlertDialog.Builder(this, R.style.DarkDialogTheme);
        }

        aDialog.setTitle(R.string.exitTitle);
        aDialog.setMessage(R.string.exit);
        aDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        aDialog.setPositiveButton(R.string.response9, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        Dialog diag = aDialog.show();
        TextView txtvw = diag.findViewById(android.R.id.message);
        txtvw.setInputType(R.font.product_sans);
        txtvw.setTextColor(getResources().getColor(R.color.blanco));
        aDialog.create();


    }




}
