package com.example.prasanth.chatapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prasanth.chatapp.R;
import com.example.prasanth.chatapp.interfaces.NavigeToSignInInterface;
import com.example.prasanth.chatapp.utils.FieldValidator;
import com.example.prasanth.chatapp.utils.QBChatHelper;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.w3c.dom.Text;

import models.UserDetialsModel;

public class SignUpActivity extends Activity implements View.OnClickListener, NavigeToSignInInterface {

    private EditText userName, password, reEnterPassword, mobileNumber, emailAddress;
    private Button signUpButton;
    private FieldValidator fieldValidatorInstance;
    private TextView signInLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        intiViews();
    }

    public void intiViews() {
        userName = (EditText) findViewById(R.id.signUpUserNameET);
        password = (EditText) findViewById(R.id._signUpPasswordET);
        reEnterPassword = (EditText) findViewById(R.id._signUpReenterPasswordET);
        mobileNumber = (EditText) findViewById(R.id._mobileNumberET);
        emailAddress = (EditText) findViewById(R.id.emailAddressET);
        signInLink = (TextView) findViewById(R.id.signInPageLink);
        signUpButton = (Button) findViewById(R.id.SignUpButton);

        signUpButton.setOnClickListener(this);
        signInLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SignUpButton:
                if (validateUserDetails()) {
                    signUpUser();
                }
                break;
            case R.id.signInPageLink:
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            default:
                break;
        }
    }

    public void signUpUser() {
        UserDetialsModel userDetialsModel = new UserDetialsModel();
        userDetialsModel.setUsername(userName.getText().toString());
        userDetialsModel.setPassword(password.getText().toString());
        userDetialsModel.setMobileNumber(mobileNumber.getText().toString());
        userDetialsModel.setEmail(emailAddress.getText().toString());
        QBChatHelper.signUpQBUser(SignUpActivity.this, userDetialsModel, this);
    }


    private boolean validateUserDetails() {
        fieldValidatorInstance = new FieldValidator();
        return fieldValidatorInstance.validateUserNameSpace(userName) &&
                fieldValidatorInstance.validatePassword(password) &&
                fieldValidatorInstance.validatePhoneNumberSpace(mobileNumber) &&
                fieldValidatorInstance.validateEmailAddress(emailAddress) &&
                fieldValidatorInstance.validatePasswordMatch(password, reEnterPassword);
    }

    @Override
    public void qbSignUpSuccess() {
        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}
