package com.example.crypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class orderHistory extends AppCompatActivity {


    //recyccler view adapter initalization
    List<Listdata> order_history;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    //database
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    String buyornot = "";
    TextView bbtext;
    String numbers;


    //shared preference
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String jsonPreferences;
    List<Listdata> productFromShared = new ArrayList<>();

    //textview
    TextView no_order_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history2);
        numbers = getIntent().getStringExtra("number");

        no_order_history = findViewById(R.id.order);

        //shared preference
        sharedPreferences = this.getSharedPreferences("crypto_bot", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("bot_replies_" + numbers);


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = inflater.inflate(R.layout.list_item, null);
        bbtext = vi.findViewById(R.id.buy_sell_list);


        //recycler view initialization
        order_history = new ArrayList<Listdata>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getHistory();


    }

    private void getHistory() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String nn = snapshot.child("nofhistory").getValue(String.class);

                //child bot_replies of child history
                for (int i = 1; i < Integer.parseInt(nn) + 1; i++) {
                    Log.e("integer", String.valueOf(i));
                    try {
                        String bs_d = snapshot.child("history" + String.valueOf(i)).child("buysell").getValue(String.class);
                        String fee_d = snapshot.child("history" + String.valueOf(i)).child("fee").getValue(String.class);
                        String filled_d = snapshot.child("history" + String.valueOf(i)).child("filled").getValue(String.class);
                        String b_price = snapshot.child("history" + String.valueOf(i)).child("price").getValue(String.class);
                        String tot = snapshot.child("history" + String.valueOf(i)).child("total").getValue(String.class);
                        String times = snapshot.child("history" + String.valueOf(i)).child("time").getValue(String.class);

                        bbtext.setTextColor(Color.parseColor("#f5f5f5"));

                        if (!buyornot.equals(b_price)) {
                            buyornot = b_price;
                            order_history.add(new Listdata(bs_d, b_price, filled_d, fee_d, tot, times));

                            setDataFromSharedPreferences(order_history, "order_" + numbers);

                            Collections.reverse(order_history);
                            mAdapter = new ListAdapter(orderHistory.this, order_history);
                            mAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(mAdapter);
                        }
                    } catch (Exception e) {
                        Log.e("exception error", String.valueOf(e));
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(orderHistory.this, "Fail to get data." + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataFromSharedPreferences(List<Listdata> politicsnews, String datatype) {
        Gson gson = new Gson();
        String jsonCurProduct = gson.toJson(politicsnews);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("urlData", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString(datatype, jsonCurProduct);
        editor.commit();
    }

    @SuppressLint("LongLogTag")
    private List<Listdata> addList() {
//        Log.e("frag_shared", frag_shared + "123");
        Gson gson = new Gson();
        SharedPreferences sharedPref = this.getSharedPreferences("urlData", MODE_PRIVATE);
        jsonPreferences = sharedPref.getString("order_" + numbers, "");

        Type type = new TypeToken<List<Listdata>>() {
        }.getType();
        productFromShared = gson.fromJson(jsonPreferences, type);

        return productFromShared;
    }

    public void updateTimeOnEachSecond() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }, 0, 1000);
    }


}