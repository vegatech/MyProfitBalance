package com.capcenter.ec.myprofitbalance.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.capcenter.ec.myprofitbalance.R;

public class menuActivity extends AppCompatActivity {
    CardView cardviewmnu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    cardviewmnu = (CardView) findViewById(R.id.card_view_Egresos);



       // Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

       /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
}
