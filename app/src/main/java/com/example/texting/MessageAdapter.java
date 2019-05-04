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

        MessageClickCallback callback;

        MessageAdapter(){
            super(new MessageCallback());
        }

        @NonNull
        @Override
        public MessageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int index) {
            Context context =  viewGroup.getContext();
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_single_message,viewGroup,false);
            final MessageHolder messageHolder = new MessageHolder(itemView);
            messageHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View arg) {
//                    Message message = getItem(index);
                    Message message = getItem(messageHolder.getAdapterPosition());
                    System.out.println(message);
                    if (callback != null){
                        callback.onMessageClick(message);
                        return true;
                    }
                    return false;
                }

            });
            return messageHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MessageHolder messageHolder, int index) {
            Message message = getItem(index);
            messageHolder.textview.setText(message.getMsg());
        }
}
