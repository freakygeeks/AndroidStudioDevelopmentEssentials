package com.chrysalis.explicitintent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.EditText;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        Bundle extras = getIntent().getExtras();
        if (extras == null) { return; }

        String qString = extras.getString("qString");

        final TextView textView = (TextView)findViewById(R.id.textView1);
        textView.setText(qString);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onClick(View view) {
        finish();
    }

    @Override
    public void finish() {
        Intent data = new Intent();

        EditText editText1 = (EditText)findViewById(R.id.editText1);
        String returnString = editText1.getText().toString();
        data.putExtra("returnData",returnString);

        setResult(RESULT_OK, data);
        super.finish();
    }
}
