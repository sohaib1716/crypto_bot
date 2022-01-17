package com.example.crypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class select_bot extends AppCompatActivity {


    LinearLayout bot_1,bot_2,bot_3;
    TextView usdt_price,btc_price;

    //firebase databse
    FirebaseDatabase firebaseDatabase;
    //Database Reference for Firebase.
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bot);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("phase1");
        bot_1 = findViewById(R.id.bot1);
        bot_2 = findViewById(R.id.bot2);
        bot_3 = findViewById(R.id.bot3);

        //text
        usdt_price = findViewById(R.id.usdtprice);
        btc_price = findViewById(R.id.btcprice);


        bot_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(select_bot.this, MainActivity.class);
                i.putExtra("number", "1");
                startActivity(i);
            }
        });

        bot_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(select_bot.this, MainActivity.class);
                i.putExtra("number", "2");
                startActivity(i);
            }
        });

        // GOOGLE PLAY APP SIGNING SHA-1 KEY:- 65:5D:66:A1:C9:31:85:AB:92:C6:A2:60:87:5B:1A:DA:45:6E:97:EA


        bot_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(select_bot.this, MainActivity.class);
                i.putExtra("number", "3");
                startActivity(i);
            }
        });

        getdata();

    }


    private void getdata() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String balance = snapshot.child("balance").getValue(String.class);
                String btc = snapshot.child("closing").getValue(String.class);

                usdt_price.setText(balance);
                btc_price.setText(btc);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(select_bot.this, "Fail to get data." + error, Toast.LENGTH_SHORT).show();
            }
        });


    }

}