package com.example.crypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private EditText takeprofit, stoploss, orderamount, coinName, minimum, maximum, times, interval, selling_qty;
    private Button sendDatabtn, order_history, emergency_sell;

    String takepat, stopat, avgbuy, numberbuy = "0", nextb, rsiat, cpat, sellit = "0", total_selling, nhistory;
    int rd_flag = 0;


    // creating a variable for
    // our Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference databaseReference;
    DatabaseReference databaseReference_emergency;
    DatabaseReference database_reference_replies;
    DatabaseReference database_price;

    //radio button
    RadioGroup radioGroup;
    RadioButton radioBut;


    // variable for Text view.
    private TextView retrieveTV, second;

    information information;

    String numbers, statusonoff;
    String quant;

    ToggleButton stbutton;
    TextView tpat, slat, next_buy, numbuy, btc_price, usdt_price, average_buy, cp_reply, rsi_reply, response, quantity;

    String final_balance;
    RelativeLayout relativeLayout;
    String setStatus = "null";
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch sw;
    String buyornot = "";
    TextView bbtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = inflater.inflate(R.layout.list_item, null);
        bbtext = vi.findViewById(R.id.buy_sell_list);

        numbers = getIntent().getStringExtra("number");
        takeprofit = findViewById(R.id.takeProfit);
        stoploss = findViewById(R.id.stopLoss);
        orderamount = findViewById(R.id.orderAmount);
        coinName = findViewById(R.id.coinName);
        minimum = findViewById(R.id.minimum);
        maximum = findViewById(R.id.maximum);
        times = findViewById(R.id.times);
        interval = findViewById(R.id.interval);
        next_buy = findViewById(R.id.next_buy_replies);
        cp_reply = findViewById(R.id.closing_price_replies);
        rsi_reply = findViewById(R.id.rsi_value_replies);
        response = findViewById(R.id.response_replies);
        information = new information();
        sendDatabtn = findViewById(R.id.idBtnSendData);
        order_history = findViewById(R.id.orderhistory);
        emergency_sell = findViewById(R.id.emergency_sell);
        quantity = findViewById(R.id.quantity);
        selling_qty = findViewById(R.id.sell_qty);

        // btc and usdt live price
        btc_price = findViewById(R.id.btcprice_main);
        usdt_price = findViewById(R.id.usdtprice_main);
        relativeLayout = findViewById(R.id.layout_re);

        //radio buttons
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioBut = (RadioButton) findViewById(R.id.auto);

        //show data from database
        tpat = findViewById(R.id.take_profit_replies);
        slat = findViewById(R.id.stop_loss_replies);
        numbuy = findViewById(R.id.numberOfbuy);
        average_buy = findViewById(R.id.average_buy);


        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get
        // reference for our database
        // .
        databaseReference = firebaseDatabase.getReference("bot_" + numbers);
        databaseReference_emergency = firebaseDatabase.getReference("emergency");
        database_reference_replies = firebaseDatabase.getReference("bot_replies_" + numbers);
        database_price = firebaseDatabase.getReference("phase1");


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_LONG).show();

                if (radioButton.getText().equals("Auto")) {
                    rd_flag = 1;
                    selling_qty.setVisibility(View.GONE);
                    total_selling = quant;
                }
                if (radioButton.getText().equals("Manual")) {
                    rd_flag = 2;
                    selling_qty.setVisibility(View.VISIBLE);


                }

            }
        });

        sw = (Switch) findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Toast.makeText(MainActivity.this, "on", Toast.LENGTH_SHORT).show();
                    statusonoff = "on";

                } else {
                    // The toggle is disabled
                    Toast.makeText(MainActivity.this, "off", Toast.LENGTH_SHORT).show();
                    statusonoff = "off";
                }
            }
        });

        order_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nhistory.equals("0")){
                    Snackbar snackbar = Snackbar.make(relativeLayout, "No previous history",
                            Snackbar.LENGTH_LONG);
                    View snackBarview = snackbar.getView();
                    snackBarview.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.red));
                    snackbar.show();
                }else{
                    Intent i = new Intent(MainActivity.this, orderHistory.class);
                    i.putExtra("number", numbers);
                    startActivity(i);
                }


            }
        });

        emergency_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rd_flag != 0) {
                    if (rd_flag == 2) {
                        total_selling = selling_qty.getText().toString();
                    }
                    if (Integer.parseInt(numberbuy) == 0) {
                        Snackbar snackbar = Snackbar.make(relativeLayout, "You have no buys yet",
                                Snackbar.LENGTH_LONG);
                        View snackBarview = snackbar.getView();
                        snackBarview.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.red));
                        snackbar.show();
                    } else {
                        Toast.makeText(getApplicationContext(), total_selling, Toast.LENGTH_LONG).show();
                        sellit = "sell";
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // inside the method of on Data change we are setting
                                // our object class to our database reference.
                                // data base reference will sends data to firebase.
                                databaseReference_emergency.child("sell").setValue(sellit);
                                databaseReference_emergency.child("quant").setValue(total_selling);

                                Snackbar snackbar = Snackbar.make(relativeLayout, "Sell requested successfully",
                                        Snackbar.LENGTH_LONG);
                                View snackBarview = snackbar.getView();
                                snackBarview.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.green));
                                snackbar.show();

                                // after adding this data we are showing toast message.
//                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // if the data is not added or it is cancelled then
                                // we are displaying a failure toast message.
                                Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else{
                    Snackbar snackbar = Snackbar.make(relativeLayout, "Please select method",
                            Snackbar.LENGTH_LONG);
                    View snackBarview = snackbar.getView();
                    snackBarview.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.red));
                    snackbar.show();
                }
            }
        });


        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting text from our edittext fields.
                String takeProfit = takeprofit.getText().toString();
                String stopLoss = stoploss.getText().toString();
                String orderAmount = orderamount.getText().toString();
                String coin = coinName.getText().toString().toLowerCase();
                String max = maximum.getText().toString();
                String min = minimum.getText().toString();
                String timestobuy = times.getText().toString();
                String intervalP = interval.getText().toString();

                // below line is for checking weather the
                // edittext fields are empty or not.
                if (takeProfit.isEmpty() && stopLoss.isEmpty() && orderAmount.isEmpty()
                        && coin.isEmpty() && max.isEmpty() && min.isEmpty()
                        && timestobuy.isEmpty() && intervalP.isEmpty()) {
                    // if the text fields are empty
                    // then show the below message.
                    Snackbar snackbar = Snackbar.make(relativeLayout, "Please fill all details",
                            Snackbar.LENGTH_LONG);
                    View snackBarview = snackbar.getView();
                    snackBarview.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.purple_700));
                    snackbar.show();
//                    Toast.makeText(MainActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add
                    // data to our database.
                    if ((Integer.parseInt(orderAmount) * Integer.parseInt(timestobuy)) > Float.parseFloat(final_balance)) {
                        // Create a snackbar
                        Snackbar snackbar = Snackbar.make(relativeLayout, "You have no enough balance",
                                Snackbar.LENGTH_LONG);
                        View snackBarview = snackbar.getView();
                        snackBarview.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.red));
                        snackbar.show();
                    } else {
                        if (Integer.parseInt(orderAmount) < 10) {
                            Snackbar snackbar = Snackbar.make(relativeLayout, "Order amount must be greater then 10$",
                                    Snackbar.LENGTH_LONG);
                            View snackBarview = snackbar.getView();
                            snackBarview.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.red));
                            snackbar.show();
                        } else {
                            addDatatoFirebase(takeProfit, stopLoss, orderAmount, coin, min, max, timestobuy, intervalP, statusonoff);
                        }
                    }

                }
            }
        });
        getdata();
    }


    private void getdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tp = snapshot.child("takeProfit").getValue(String.class);
                String sL = snapshot.child("stopLoss").getValue(String.class);
                String oA = snapshot.child("orderAmount").getValue(String.class);
                String co = snapshot.child("coinName").getValue(String.class);
                String ma = snapshot.child("maximum").getValue(String.class);
                String mi = snapshot.child("minimum").getValue(String.class);
                String timt = snapshot.child("timesbuy").getValue(String.class);
                String inte = snapshot.child("percentInterval").getValue(String.class);
                String respo = snapshot.child("response").getValue(String.class);
                setStatus = snapshot.child("status").getValue(String.class);


                //check if status is on or off
                try {
                    if (setStatus.equals("on")) {
                        sw.setChecked(true);
                    } else {
                        sw.setChecked(false);
                    }
                } catch (Exception e) {
                    Log.e("status", "status not");
                    sw.setChecked(false);
                }


                takeprofit.setText(tp);
                stoploss.setText(sL);
                orderamount.setText(oA);
                coinName.setText(co);
                maximum.setText(ma);
                minimum.setText(mi);
                times.setText(timt);
                interval.setText(inte);
                response.setText(respo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Fail to get data." + error, Toast.LENGTH_SHORT).show();
            }
        });

        database_reference_replies.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //child bot_replies_(number)
                quant = snapshot.child("selling_quantity").getValue(String.class);
                takepat = snapshot.child("takeprofitat").getValue(String.class);
                stopat = snapshot.child("stoplossat").getValue(String.class);
                avgbuy = snapshot.child("averagebuyat").getValue(String.class);
                numberbuy = snapshot.child("nob").getValue(String.class);
                nextb = snapshot.child("intervalat").getValue(String.class);
                rsiat = snapshot.child("rsiat").getValue(String.class);
                cpat = snapshot.child("cpat").getValue(String.class);
                nhistory = snapshot.child("nofhistory").getValue(String.class);


                quantity.setText(quant);
                tpat.setText(takepat);
                slat.setText(stopat);
                average_buy.setText(avgbuy);
                numbuy.setText(numberbuy);
                next_buy.setText(nextb);
                rsi_reply.setText(rsiat);
                cp_reply.setText(cpat);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Fail to get data." + error, Toast.LENGTH_SHORT).show();
            }
        });


        database_price.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final_balance = snapshot.child("balance").getValue(String.class);
                String btc = snapshot.child("closing").getValue(String.class);

                usdt_price.setText(final_balance);
                btc_price.setText(btc);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Fail to get data." + error, Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void addDatatoFirebase(String takep, String stopp, String ordera,
                                   String coinn, String mini, String maxi, String timess, String intervalto, String status) {
        // below 3 lines of code is used to set
        // data in our object class.
        information.setTakeProfit(takep);
        information.setStopLoss(stopp);
        information.setOrderAmount(ordera);
        information.setCoinName(coinn);
        information.setMinimum(mini);
        information.setMaximum(maxi);
        information.setStatus(status);
        information.setTimesbuy(timess);
        information.setPercentInterval(intervalto);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(information);

                Snackbar snackbar = Snackbar.make(relativeLayout, "Saved changes succesfully",
                        Snackbar.LENGTH_LONG);
                View snackBarview = snackbar.getView();
                snackBarview.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.green));
                snackbar.show();

                // after adding this data we are showing toast message.
//                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}