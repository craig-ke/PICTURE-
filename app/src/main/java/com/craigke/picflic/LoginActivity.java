package com.craigke.picflic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.registerTextView) TextView mRegisterTextView;
    @BindView(R.id.LoginButton) Button mPasswordLoginButton;
    @BindView(R.id.editLoginEmailAddress) EditText mEmailEditText;
    @BindView(R.id.editLoginPassword) EditText mPasswordEditText;

    private FirebaseAuth mAuth;
    private ProgressDialog mAuthProgressDialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            ButterKnife.bind(this);

            mAuth = FirebaseAuth.getInstance();
            mRegisterTextView.setOnClickListener(this);
            mPasswordLoginButton.setOnClickListener(this);
            createAuthProgressDialog();
//            listen for changes in the current authentication state. If a user is authenticated, the app will navigate to the MainActivity
            mAuthListener = new FirebaseAuth.AuthStateListener() {

                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }

            };

        }

    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);
    }

    @Override
        public void onClick(View view) {
            if (view == mRegisterTextView) {
                Intent intent = new Intent(LoginActivity.this,SignUp.class);
                startActivity(intent);
                finish();
            }
            if (view == mPasswordLoginButton) {
                loginWithPassword();
            }
        }
//This method does prompt the user to input a  valid password
    private void loginWithPassword() {
            String email = mEmailEditText.getText().toString().trim();
            String password = mPasswordEditText.getText().toString().trim();

        if (email.equals("")) {
        mEmailEditText.setError("Please enter your email");
        return;
    }
        if (password.equals("")) {
        mPasswordEditText.setError("Password cannot be blank");
        return;
        }
//        this listener is responsible for determining when the authentication attempt is complete to line 100
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mAuthProgressDialog.dismiss();
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}