package com.example.texting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import androidx.recyclerview.widget.RecyclerView;

interface MessageClickCallback {
    void onMessageClick(Message message);
}

class MessageHolder extends RecyclerView.ViewHolder {
    final TextView textview;
    MessageHolder( View itemView) {
        super(itemView);
        textview = itemView.findViewById(R.id.textView);
    }
}


class  MessageCallback extends DiffUtil.ItemCallback<Message> {

    @Override
    public boolean areItemsTheSame(@NonNull Message message, @NonNull Message otherMessage) {
        return message.getMsg().equals(otherMessage.getMsg());
    }


    @Override
    public boolean areContentsTheSame(@NonNull Message message, @NonNull Message otherMessage) {
        return message.equals(otherMessage);
    }
}



public class MessageAdapter extends ListAdapter<Message, MessageHolder> {

        public MessageClickCallback callback;

        MessageAdapter(){
            super(new MessageCallback());
        }

        @NonNull
        @Override
        public MessageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            Context context =  viewGroup.getContext();
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_single_message,viewGroup,false);
            return new MessageHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MessageHolder messageHolder, int i) {
            Message message = getItem(i);
            messageHolder.textview.setText(message.getMsg());
        }
}
