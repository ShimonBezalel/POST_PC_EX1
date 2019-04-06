package com.example.texting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private  MessageRecyclerUtils.MessageAdapter adapter = new MessageRecyclerUtils.MessageAdapter();
    private ArrayList<Message> messageList = new ArrayList<Message>(Message.getAll());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve activity objects
        final RecyclerView messageView = findViewById(R.id.messageView);
        ImageButton send = (ImageButton) findViewById(R.id.sendButton);
        final EditText editText = (EditText) findViewById(R.id.textInput);

        messageView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        messageView.setAdapter(adapter);
        adapter.submitList(messageList);



    }
}
