package com.example.texting;

import java.util.ArrayList;
import java.util.Objects;

public class MessageView {

    private String msg;

    public MessageView(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }


    private static int lastContactId = 0;

    public static ArrayList<MessageView> createMessageList() {
        ArrayList<MessageView> messageList = new ArrayList<MessageView>();

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
        return this.msg.equals(((MessageView)other).msg);
    }
}
