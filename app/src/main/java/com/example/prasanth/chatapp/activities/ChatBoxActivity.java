package com.example.prasanth.chatapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.prasanth.chatapp.R;
import com.example.prasanth.chatapp.adapters.ChatAdapter;
import com.example.prasanth.chatapp.utils.CommonMethods;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.utils.DialogUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.server.Performer;
import com.quickblox.users.model.QBUser;

import org.jivesoftware.smack.SmackException;

import java.util.ArrayList;

public class ChatBoxActivity extends AppCompatActivity implements View.OnClickListener {

    private QBUser qbUser;
    private QBChatDialog qbChatDialog;
    private TextView enterMessageBox;
    private ImageButton sendMsgBtn;
    private ArrayList<QBChatMessage> qbChatMessages;
    private ChatAdapter chatAdapter;
    private RecyclerView chatUIRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        initViews();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            qbUser = (QBUser) getIntent().getExtras().get("qbUser");
            createChatDialog(qbUser);
        }
    }

    public void initViews() {
        enterMessageBox = (TextView) findViewById(R.id.textEnterBox);
        sendMsgBtn = (ImageButton) findViewById(R.id.sendMessageBtn);
        sendMsgBtn.setOnClickListener(this);
    }

    public void createChatDialog(QBUser qbUser) {
        QBChatDialog qbChatDialog = DialogUtils.buildPrivateDialog(qbUser.getId());
        Performer<QBChatDialog> chatDialog = QBRestChatService.createChatDialog(qbChatDialog);
        chatDialog.performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                ChatBoxActivity.this.qbChatDialog = qbChatDialog;
                CommonMethods.getToast(ChatBoxActivity.this, "Chat Created");
                Performer<Integer> integerPerformer = QBRestChatService.getDialogMessagesCount(qbChatDialog.getDialogId());
                integerPerformer.performAsync(new QBEntityCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer integer, Bundle bundle) {
                        CommonMethods.getToast(ChatBoxActivity.this, "Chat dialog created");
                       /* loadMessages(qbChatDialog)*/
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        CommonMethods.getToast(ChatBoxActivity.this, "Chat Dialog created error");
                    }
                });
            }

            @Override
            public void onError(QBResponseException e) {
                CommonMethods.getToast(ChatBoxActivity.this, "Chat Created Error");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMessageBtn:
                QBChatMessage qbChatMessage = new QBChatMessage();
                qbChatMessage.setMarkable(true);
                qbChatMessage.setBody(enterMessageBox.getText().toString());
                qbChatMessage.setDateSent(System.currentTimeMillis() / 1000);
                qbChatMessage.setProperty("save_to_history", "1");
                if (qbChatMessage != null) {
                    if (qbChatDialog != null) {
                        try {
                            qbChatDialog.sendMessage(qbChatMessage);
                            qbChatMessages.add(qbChatMessage);
                            if (chatAdapter != null) {
                                chatAdapter.updateChatData(qbChatMessages);
                            } else {
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatBoxActivity.this);
                                chatUIRecyclerView.setLayoutManager(linearLayoutManager);
                                ChatAdapter chatAdapter = new ChatAdapter(this, qbChatDialog, qbChatMessages);
                                chatUIRecyclerView.setAdapter(chatAdapter);
                            }
                        } catch (SmackException.NotConnectedException e) {
                            e.printStackTrace();
                        }

                    }
                }
                break;
        }

    }
}
