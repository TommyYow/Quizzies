package com.example.user.quizzies;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MyScoreActivity extends AppCompatActivity {

    ImageView scoreImg;
    TextView txtScore;
    Button btnGetPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_score);

        scoreImg = (ImageView) findViewById(R.id.scoreImg);
        txtScore = (TextView) findViewById(R.id.txtScore);
        btnGetPoint = (Button) findViewById(R.id.btnGetPoint);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser.getPhotoUrl() != null){
            String profilePicURL = currentUser.getPhotoUrl().toString();
            Picasso.with(getApplicationContext()).load(profilePicURL).into(scoreImg);
        }


        final String currentUserUID = currentUser.getUid();
        FirebaseDatabase databaseRef = FirebaseDatabase.getInstance();

        final DatabaseReference scoreRef = databaseRef.getReference().child("Users").child(currentUserUID).child("Score");
        scoreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    long score = dataSnapshot.getValue(long.class);
                    if(score < 0)
                        txtScore.setTextColor(Color.RED);
                    else
                        txtScore.setTextColor(Color.GREEN);
                    txtScore.setText(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnGetPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("fragmentToLoad", "Play");
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.putExtra("fragmentToLoad", "Play");
//        startActivity(intent);
//        finish();
    }
}
