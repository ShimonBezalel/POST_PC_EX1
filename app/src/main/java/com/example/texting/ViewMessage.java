package com.example.texting;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
import android.os.Bundle;


public class ViewMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);
        TextView model =            (TextView) findViewById(R.id.view_model);
        TextView manifacturerar =   (TextView) findViewById(R.id.view_manufacturer);
        TextView content =          (TextView) findViewById(R.id.view_content);
        TextView timestamp =        (TextView) findViewById(R.id.view_timestamp);

        // Set display parameters with details for this message
        Message message =  getIntent().getParcelableExtra("MESSAGE");
        model.setText(message.getModel());
        manifacturerar.setText(message.getManufacturer());
        content.setText(message.getMsg());
        timestamp.setText(message.getTimestamp());

    }
}
