package com.example.prasanth.chatapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.prasanth.chatapp.R;
import com.example.prasanth.chatapp.interfaces.NavigateToChatUI;
import com.example.prasanth.chatapp.utils.FieldValidator;
import com.example.prasanth.chatapp.utils.PreferenceConnector;
import com.example.prasanth.chatapp.utils.QBChatHelper;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.ServiceZone;
import com.quickblox.core.StoringMechanism;
import com.quickblox.users.model.QBUser;

import org.w3c.dom.Text;

public class SignInActivity extends Activity implements OnClickListener, NavigateToChatUI {
    private EditText userNameET, passwordET;
    private Button signInButton;
    private TextView signUpTextViewLink;
    final String API_DOMAIN = "https://apicustomdomain.quickblox.com";
    final String CHAT_DOMAIN = "chatcustomdomain.quickblox.com";
    private static final String APP_ID = "61900";
    private static final String AUTH_KEY = "QSF5K9wSCY78Pvq";
    private static final String AUTH_SECRET = "y4d3HLMBzVxFm6U";
    private static final String ACCOUNT_KEY = "2FroE-YX-BsWpxnjuJox";
    private FieldValidator fieldValidatorInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();
        initFramework();

    }

    private void initListeners() {
        signUpTextViewLink.setOnClickListener(this);
        signInButton.setOnClickListener(this);
    }

    private boolean validateUserDetails() {
        fieldValidatorInstance = new FieldValidator();
        return fieldValidatorInstance.validateUserNameSpace(userNameET) &&
                fieldValidatorInstance.validatePassword(passwordET);

    }

    private void initFramework() {
        //QBSettings.getInstance().setStoringMehanism(StoringMechanism.UNSECURED);
        QBSettings.getInstance().init(SignInActivity.this, APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        QBSettings.getInstance().setEndpoints(API_DOMAIN, CHAT_DOMAIN, ServiceZone.PRODUCTION);
        QBSettings.getInstance().setZone(ServiceZone.PRODUCTION);

    }

    private void initViews() {
        userNameET = (EditText) findViewById(R.id.userNameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        signInButton = (Button) findViewById(R.id.signInButton);
        signUpTextViewLink = (TextView) findViewById(R.id.signUpTextView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signInButton:
                if (validateUserDetails()) {
                    PreferenceConnector.writeString(this, "userName", userNameET.getText().toString());
                    PreferenceConnector.writeString(this, "password", passwordET.getText().toString());
                    signInUser();
                }
                break;
            case R.id.signUpTextView:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void signInUser() {
        if (validateUserDetails()) {
            QBChatHelper.signInQBuser(SignInActivity.this, userNameET.getText().toString(), passwordET.getText().toString(), this);
        }
    }

    @Override
    public void qbSignInSuccess() {
        Intent intent = new Intent(SignInActivity.this, ChatUIActivity.class);
        startActivity(intent);
        finish();
    }
}
