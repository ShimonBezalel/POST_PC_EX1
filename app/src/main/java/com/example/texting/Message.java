package com.example.texting;

import java.util.ArrayList;
import java.util.Objects;

public class Message {

    private String msg;

    public Message(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }


    private static int lastContactId = 0;

    public static ArrayList<Message> getAll() {
        ArrayList<Message> messageList = new ArrayList<Message>();

        // testing the list
        messageList.add(new Message("hey"));
        messageList.add(new Message("whatsapp"));


        return messageList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg);
    }

    @Override
    public boolean equals(Object other) {
//        if (this == other) return true;
        if (other == null ||this.getClass() != other.getClass()) return false;
        return this.msg.equals(((Message)other).msg);
    }
}
