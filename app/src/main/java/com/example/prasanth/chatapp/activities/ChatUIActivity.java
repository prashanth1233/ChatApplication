package com.example.prasanth.chatapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.prasanth.chatapp.R;
import com.example.prasanth.chatapp.adapters.AllUsersUIAdapter;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBPagedRequestBuilder;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

public class ChatUIActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_ui);

        progressDialog=new ProgressDialog(ChatUIActivity.this);
        progressDialog.show();

        recyclerView=(RecyclerView)findViewById(R.id.all_user_app);
        RecyclerView.LayoutManager layoutmanager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutmanager);
        initChatService();
        retreiveUsersByIds();
    }

    public void retreiveUsersByIds() {

        QBPagedRequestBuilder pagedRequestBuilder = new QBPagedRequestBuilder();
        pagedRequestBuilder.setPage(1);
        pagedRequestBuilder.setPerPage(1000);


    QBUsers.getUsers(pagedRequestBuilder).performAsync(new QBEntityCallback<ArrayList<QBUser>>() {
                @Override
                public void onSuccess(ArrayList<QBUser> qbUsers, Bundle bundle) {
                    AllUsersUIAdapter usersAdapter = new AllUsersUIAdapter(ChatUIActivity.this, qbUsers,ChatUIActivity.this);
                    recyclerView.setAdapter(usersAdapter);
                    progressDialog.dismiss();
                }

                @Override
                public void onError(QBResponseException e) {

                }
            }

    );
        }

    private void initChatService() {

        QBChatService.setDebugEnabled(true);
        QBChatService.setDefaultPacketReplyTimeout(10000);

    /*    *//*configuring chat socket*//*
        QBChatService.ConfigurationBuilder chatServiceConfigBuilder = new QBChatService.ConfigurationBuilder();
        chatServiceConfigBuilder.setSocketTimeout(60);
        chatServiceConfigBuilder.setKeepAlive(true);
        chatServiceConfigBuilder.setUseTls(true);
        QBChatService.setConfigurationBuilder(chatServiceConfigBuilder);*/

    }

    @Override
    public void onClick(View view) {
        QBUser qbuser= (QBUser) view.getTag();
        if(qbuser!=null)
        {
          Intent intent=new Intent(ChatUIActivity.this,ChatBoxActivity.class);
            intent.putExtra("qbUser",qbuser);
            startActivity(intent);
        }
    }
}
