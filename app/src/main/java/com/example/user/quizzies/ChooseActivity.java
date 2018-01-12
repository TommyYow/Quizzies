package com.example.user.quizzies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends Activity {

    Button btnChoosePlay;
    Button btnChooseScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        btnChoosePlay = (Button) findViewById(R.id.btnChoosePlay);
        btnChooseScan = (Button) findViewById(R.id.btnChooseScan);

        btnChoosePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("fragmentToLoad", "Play");
                startActivity(intent);
                finish();
            }
        });

        btnChooseScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("fragmentToLoad", "Scan");
                startActivity(intent);
                finish();
            }
        });
    }
}
