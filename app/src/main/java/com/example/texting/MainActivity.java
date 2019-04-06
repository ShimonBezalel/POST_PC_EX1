package com.example.texting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private  MessageAdapter adapter = new MessageAdapter();
    private ArrayList<Message> messageList = new ArrayList<>(Message.getAll());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve activity objects
        final RecyclerView messageView = findViewById(R.id.messageView);
        ImageButton send = (ImageButton) findViewById(R.id.sendButton);
        final EditText editText = (EditText) findViewById(R.id.textInput);
        editText.setText("");

        messageView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        messageView.setAdapter(adapter);
        adapter.submitList(messageList);
        System.out.println(savedInstanceState);
        if (savedInstanceState != null) {
            editText.setText(savedInstanceState.getString("msg_holder"));
            messageList = savedInstanceState.getParcelableArrayList("msg_holder");
            adapter.submitList(messageList);
        }
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Message newMessage = new Message(editText.getText().toString());
                ArrayList<Message> copyMessages = new ArrayList<>(messageList);
                copyMessages.add(newMessage);
                messageList = copyMessages;
                adapter.submitList(messageList);
                editText.setText("");
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        final EditText editText = (EditText) findViewById(R.id.textInput);
        savedInstanceState.putString("text", String.valueOf(editText.getText()));
        savedInstanceState.putParcelableArrayList("msg_holder", messageList);
        super.onSaveInstanceState(savedInstanceState);

    }
}
