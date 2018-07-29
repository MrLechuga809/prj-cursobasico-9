package com.eleo95.magic8;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

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
        final AlertDialog.Builder aDialog = new AlertDialog.Builder(this);
        aDialog.setTitle(R.string.app_name);
        aDialog.setMessage(R.string.exit);
        aDialog.setPositiveButton(R.string.response9, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        aDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        aDialog.create();
        Objects.requireNonNull(
                aDialog.show()
                .getWindow())
            .getDecorView()
            .getBackground()
            .setColorFilter(
                    new LightingColorFilter(0xFF000000,0xFF454545));


    }




}
