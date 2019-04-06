package com.example.texting;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Objects;

public class Message implements Parcelable{

    private String msg;

    private Message(Parcel in) {
        msg = in.readString();
    }


    private Message(String msg) {
        this.msg = msg;
    }

    String getMsg() {
        return msg;
    }

    static ArrayList<Message> getAll() {
        ArrayList<Message> messageList = new ArrayList<>();

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msg);
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}