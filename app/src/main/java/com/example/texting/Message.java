package com.example.texting;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.UUID;


@Entity
public class Message implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    public int user_id;

    @ColumnInfo(name= "text_message")
    private String msg;
    private UUID message_id;

    private Message(Parcel in) {
        msg = in.readString();
    }


    Message(String msg) {
        this.msg = msg;
        this.message_id = UUID.randomUUID();
    }

    String getMsg() {
        return msg;
    }

    String getId(){
        return this.message_id.toString();
    }

    static ArrayList<Message> getAll() {
        return new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null ||this.getClass() != other.getClass()) return false;
        return this.msg.equals(((Message)other).msg);
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

    @Override
    public int describeContents() {
        return 0;
    }


}
