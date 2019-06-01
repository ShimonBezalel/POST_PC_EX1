package com.example.texting;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

import java.util.UUID;


@Entity
public class Message implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    public int user_id;

    @ColumnInfo(name = "manufacturer")
    private String manufacturer;

    @ColumnInfo(name= "model")
    private String model;

    @ColumnInfo(name= "timestamp")
    private String timestamp;

    @ColumnInfo(name= "date")
    private String date;

    @Ignore
    Message(){}

    @ColumnInfo(name= "msg")
    private String msg;

    @ColumnInfo(name= "id")
    private String id;

    private Message(Parcel in) {

        this.timestamp =    in.readString();
        this.date =    in.readString();

        this.model =        in.readString();
        this.manufacturer = in.readString();

        msg = in.readString();
        id = UUID.randomUUID().toString();
    }

    Message(String msg) {
        this.msg = msg;
        UUID uid = UUID.randomUUID();
        this.id = uid.toString();

        // timestamp + date
        this.date = new Date().toString();

        // Phone details from OS
        this.model = android.os.Build.MODEL;
        this.manufacturer = android.os.Build.MANUFACTURER;


    }

    String getMsg() {
        return msg;
    }

    String getId(){
        return this.id;
    }

    void setId(String newId){
        this.id = newId;
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

        dest.writeString(this.date);
        dest.writeString(this.timestamp);

        dest.writeString(this.model);
        dest.writeString(this.manufacturer);


    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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
