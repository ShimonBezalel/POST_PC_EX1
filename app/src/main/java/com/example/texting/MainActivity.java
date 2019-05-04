package com.example.texting;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.ArrayList;
import android.util.Log;
import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements MessageClickCallback {

    private static String EMPTY_MESSAGE = "";
    private static String ALERT_BAD_INPUT = "Cannot send illegal message!";

    private  MessageAdapter adapter = new MessageAdapter();
    private MessageDB database = MessageDB.getInstance(this);
    private ArrayList<Message> messageList  = new ArrayList<>(database.dataObj().selectAll());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate", String.format("messages size: %d", this.messageList.size() ));

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
                ArrayList<Message> copyOfMessages = new ArrayList<>(messageList);
                database.dataObj().insert(newMessage);
                copyOfMessages.add(newMessage);
                adapter.submitList(copyOfMessages);
                editText.setText("");
            }
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        final EditText editText = (EditText) findViewById(R.id.textInput);
        savedInstanceState.putString("text", String.valueOf(editText.getText()));
        savedInstanceState.putParcelableArrayList("msg_holder", messageList);
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    public void onMessageClick(final Message message) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener(){
            public void onClick( DialogInterface dialogInterface, int arg) {
                ArrayList<Message> copyOfMessages = new ArrayList<>(messageList);
                copyOfMessages.remove(message);
                database.dataObj().delete(message);
                adapter.submitList(copyOfMessages);
            }
        };
        new AlertDialog.Builder(this)
                .setTitle("Delete Message")
                .setMessage("Are you sure?")
                .setPositiveButton(android.R.string.yes, listener)
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
