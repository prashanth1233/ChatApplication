package com.example.prasanth.chatapp.utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.example.prasanth.chatapp.interfaces.NavigateToChatUI;
import com.example.prasanth.chatapp.interfaces.NavigeToSignInInterface;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.lang.reflect.Array;
import java.util.ArrayList;

import models.UserDetialsModel;

public class QBChatHelper {

    public static QBChatService qbChatService;
    private static ProgressDialog progressDialog;

    public static QBChatService getQBChatService() {
        if (qbChatService == null) {
            qbChatService = QBChatService.getInstance();
            return qbChatService;
        }
        return qbChatService;
    }

    public static void signUpQBUser(final Context context, UserDetialsModel userDetialsModel, final NavigeToSignInInterface navigeToSignInInterface) {
       /* ArrayList<String>userTags=new ArrayList();
        userTags.add("Prashanth");*/
        final QBUser user = new QBUser();
        user.setLogin(userDetialsModel.getUsername().toString());
        user.setPassword(userDetialsModel.getPassword().toString());
        user.setPhone(userDetialsModel.getMobileNumber().toString());
        /*user.setTags((StringifyArrayList<String>) userTags);*/
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        QBUsers.signUp(user).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                navigeToSignInInterface.qbSignUpSuccess();
                CommonMethods.getToast(context, "SignUp Successfull");
                progressDialog.cancel();
            }

            @Override
            public void onError(QBResponseException e) {
                CommonMethods.getToast(context, "SignUp Failed");
                progressDialog.cancel();
            }
        });
    }

    public static void signInQBuser(final Context context, String userName, String password, final NavigateToChatUI navigateToChatUI) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        final QBUser user = new QBUser(userName, password);

        QBAuth.createSession(user).performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
                user.setId(qbSession.getUserId());
                QBChatHelper.getQBChatService().login(user, new QBEntityCallback() {
                    @Override
                    public void onSuccess(Object o, Bundle bundle) {
                        navigateToChatUI.qbSignInSuccess();
                        CommonMethods.getToast(context, "SignIn Successfull");
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        if (e.getMessage().equalsIgnoreCase("login has already been taken")) {
                            navigateToChatUI.qbSignInSuccess();
                            CommonMethods.getToast(context, "SignIn Successfull");
                            progressDialog.dismiss();
                        } else {
                            CommonMethods.getToast(context, "SignIn Failed");
                            progressDialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onError(QBResponseException e) {
                CommonMethods.getToast(context, "Session Creation failed");
                progressDialog.dismiss();
            }
        });
    }

}
