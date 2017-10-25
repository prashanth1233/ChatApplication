package com.example.prasanth.chatapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.prasanth.chatapp.R;
import com.example.prasanth.chatapp.activities.ChatBoxActivity;
import com.example.prasanth.chatapp.utils.QBChatHelper;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.core.helper.CollectionUtils;
import com.quickblox.users.model.QBUser;

import org.jivesoftware.smackx.xdatalayout.packet.DataLayout;

import java.util.ArrayList;

/**
 * Created by Prasanth on 10/9/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.SampleViewHolder> {
    private Context context;
    private QBChatDialog qbChatDialog;
    private ArrayList<QBChatMessage> qbChatMessages;

    public ChatAdapter(Context context, QBChatDialog qbChatDialog, ArrayList<QBChatMessage> qbChatMessages) {
        this.context = context;
        this.qbChatDialog = qbChatDialog;
        this.qbChatMessages = qbChatMessages;
    }


    @Override
    public ChatAdapter.SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_adapter_layout, parent, false);
        SampleViewHolder sampleViewHolder = new SampleViewHolder(view);
        return sampleViewHolder;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.SampleViewHolder holder, int position) {
        QBChatMessage qbChatMessage = qbChatMessages.get(position);
        if (isIncoming(qbChatMessage)) {
            holder.senderMessageTV.setVisibility(View.GONE);
            holder.receiverMessageTv.setVisibility(View.VISIBLE);
            holder.receiverMessageTv.setText(qbChatMessage.getBody());
        } else {
            holder.receiverMessageTv.setVisibility(View.GONE);
            holder.senderMessageTV.setVisibility(View.VISIBLE);
            holder.senderMessageTV.setText(qbChatMessage.getBody());
            if (isDelivered(qbChatMessage)) {
                holder.messageSeenStatus.setImageResource(R.drawable.delivered);
            } else if (isRead(qbChatMessage)) {
                holder.messageSeenStatus.setImageResource(R.drawable.read);
            } else {
                holder.messageSeenStatus.setImageResource(R.drawable.sent);
            }
        }

    }

    private boolean isRead(QBChatMessage qbChatMessage) {
        return !CollectionUtils.isEmpty(qbChatMessage.getDeliveredIds()) &&
                qbChatMessage.getReadIds().size() > 1;
    }

    private boolean isDelivered(QBChatMessage qbChatMessage) {
        return !CollectionUtils.isEmpty(qbChatMessage.getDeliveredIds()) &&
                qbChatMessage.getReadIds().size() > 1;
    }

    private boolean isIncoming(QBChatMessage qbChatMessage) {
        QBUser currentUser = QBChatHelper.getQBChatService().getUser();
        /*checking whether the message sent Id and currentuser id is equal*/
        return qbChatMessage.getSenderId() != null &&
                !qbChatMessage.getSenderId().equals(currentUser.getId());
    }

    @Override
    public int getItemCount() {
        return qbChatMessages.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class SampleViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout senderMessageRL;
        TextView senderMessageTV, receiverMessageTv;
        ImageView messageSeenStatus;

        public SampleViewHolder(View itemView) {
            super(itemView);
            senderMessageRL = (RelativeLayout) itemView.findViewById(R.id.senderMessageRL);
            senderMessageTV = (TextView) itemView.findViewById(R.id.senderMessageTV);
            receiverMessageTv = (TextView) itemView.findViewById(R.id.receiverMessageTV);
            messageSeenStatus = (ImageView) itemView.findViewById(R.id.messageSeenStatusIV);
        }
    }

    public void updateChatData(ArrayList<QBChatMessage> qbChatMessages) {
        this.qbChatMessages = qbChatMessages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
