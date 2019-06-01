package com.example.texting;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity implements MessageClickCallback {

    private static String EMPTY_MESSAGE = "";
    String USER_NAME = "USER_NAME";
    private static String ALERT_BAD_INPUT = "Cannot send illegal message!";


    private  MessageAdapter adapter = new MessageAdapter();
    private MessageDB localDatabase;
    private ArrayList<Message> messageList ;
    private FirebaseFirestore firestore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        this.firestore = FirebaseFirestore.getInstance();
        this.localDatabase = MessageDB.getInstance(this);
        this.firestore.collection("messages").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot result = task.getResult();
                            if (result != null){
                                List<DocumentSnapshot> documents = result.getDocuments();
                                for (DocumentSnapshot document: documents){
                                    if (document != null) {
                                        Log.d("firestore", "DocumentSnapshot data: " + document.getData());
                                        for (String field: new String[]{"id", "msg"}){
                                            if (document.get(field) == null){
                                                Log.d("firestore",
                                                        String.format("Bad remote database entry. Missing field %s", field));
                                            }
                                        }
                                        String id = document.get("id").toString();
                                        // Check if item already exists in local db, else add
                                        if (localDatabase.dataObj().getItemId(id).size() == 0){
                                            String message = document.get("msg").toString();
                                            Message remoteMessage = new Message(message);
                                            remoteMessage.setId(id);
                                            localDatabase.dataObj().insert(remoteMessage);
                                            Log.d("firestore", String.format("Added %s to local.", message));
                                            Log.d("firestore" ,localDatabase.dataObj().selectAll().toString());
                                        }
                                    } else {
                                        Log.d("firestore", "No such document");
                                    }
                                }
                            }

                        } else {
                            Log.d("firestore", "get failed with ", task.getException());
                        }
                    }
                });


        this.messageList = new ArrayList<>(this.localDatabase.dataObj().selectAll());
        this.adapter.callback = this;
        Log.e("onCreate", String.format("messages size: %d", this.messageList.size() ));

        setContentView(R.layout.activity_main);
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userName = sp.getString(USER_NAME, null);
        TextView welcome_message = (TextView) findViewById(R.id.welcome_msg);
        if( userName != null){
            welcome_message.setText("Welcome " + userName + "!");
        }
        else{
            welcome_message.setText("Welcome Guest!");
        }

        // Retrieve activity objects
        final RecyclerView messageView = findViewById(R.id.messageView);
        ImageButton send = (ImageButton) findViewById(R.id.sendButton);
        final EditText editText = (EditText) findViewById(R.id.textInput);
        editText.setText("");

        messageView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        messageView.setAdapter(this.adapter);
        this.adapter.submitList(this.messageList);
        if (savedInstanceState != null) {
            editText.setText(savedInstanceState.getString("msg_holder"));
            this.messageList = savedInstanceState.getParcelableArrayList("msg_holder");
            this.adapter.submitList(this.messageList);
        }
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Message newMessage = new Message(editText.getText().toString());
                Toast emptyMsg = Toast.makeText(getApplicationContext(), ALERT_BAD_INPUT, Toast.LENGTH_SHORT);
                if (newMessage.getMsg().equals("")){ // Attempting to send empty message
                    emptyMsg.show();
                    // No further alterations to app state need to be made
                    return;
                }
                Map<String, Object> newMessageObj = new HashMap<>();
                newMessageObj.put("msg", newMessage.getMsg());
                newMessageObj.put("id", newMessage.getId());
                // Add to existing document
                firestore.collection("messages").add(newMessageObj)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.i("firestone", String.format("added %s", newMessage.getMsg()));

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("firestone", String.format("failed to add %s:\n%s", newMessage.getMsg(), e));
                            }
                        });
                messageList.add(newMessage);
                ArrayList<Message> copyOfMessages = new ArrayList<>(messageList);
                localDatabase.dataObj().insert(newMessage);
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
//        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener(){
//            public void onClick( DialogInterface dialogInterface, int arg) {
//                messageList.remove(message);
//                ArrayList<Message> copyOfMessages = new ArrayList<>(messageList);
////                copyOfMessages.remove(message);
//                localDatabase.dataObj().delete(message);
//                adapter.submitList(copyOfMessages);
//            }
//        };
        Intent intent = new Intent(this, ViewMessage.class);
        intent.putExtra("MESSAGE", (Parcelable) message);
        startActivity(intent);

//        new AlertDialog.Builder(this)
//                .setTitle("Deleting Message")
//                .setMessage("Are you sure? Cannot undo delete.")
//                .setPositiveButton(android.R.string.yes, listener)
//                .setNegativeButton(android.R.string.no, null)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
    }

}
