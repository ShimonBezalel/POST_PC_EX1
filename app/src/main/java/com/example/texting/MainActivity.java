package com.example.texting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.ArrayList;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static String EMPTY_MESSAGE = "";
    private static String ALERT_BAD_INPUT = "Cannot send illegal message!";

    private  MessageAdapter adapter = new MessageAdapter();
    private ArrayList<Message> messageList = new ArrayList<>(Message.getAll());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate", "test logger: "+ 0);

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
                Toast emptyMsg = Toast.makeText(getApplicationContext(), ALERT_BAD_INPUT, Toast.LENGTH_SHORT);
                if (newMessage.getMsg().equals("")){ // Attempting to send empty message
                    emptyMsg.show();
                    // No further alterations to app state need to be made
                    return;
                }
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
