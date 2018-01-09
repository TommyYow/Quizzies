package com.example.user.quizzies;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;

import butterknife.BindView;

public class LoginActivity extends AppCompatActivity {


    private static final int RC_SIGN_IN = 100;
    private static final String GOOGLE_TOS_URL = "https://www.google.com/policies/terms/";
    private static final String GOOGLE_PRIVACY_POLICY_URL = "https://www.google.com/policies/privacy/";
    private FirebaseAuth mAuth;

    @BindView(R.id.root)
    View mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        //if the user is signed in, launch homepage, else launch the sign in (AuthUI) Activity
        if (auth.getCurrentUser() != null) {
            Intent i = new Intent(getApplicationContext(), Main2Activity.class);
            startActivity(i);
            finish();
            return;
        } else {
            signIn();
        }
    }

    public void signIn() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(AuthUI.getDefaultTheme())
                        //.setLogo(R.drawable.icon)
                        .setAvailableProviders(
                                Arrays.asList(
                                        //new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                        //new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                                        //new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
                                )
                        )
                        .setTosUrl(GOOGLE_TOS_URL)
                        .setPrivacyPolicyUrl(GOOGLE_PRIVACY_POLICY_URL)
                        .setIsSmartLockEnabled(false, true)
                        .setAllowNewEmailAccounts(true)
                        .build(),
                RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode, data);
            return;
        }

        showSnackbar(R.string.unknown_response);
    }

    @MainThread
    private void handleSignInResponse(int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        // Successfully signed in
        if (resultCode == RESULT_OK) {
            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
            startActivity(intent);
            finish();
            return;
        } else {
            // Sign in failed
            if (response == null) {
                // User pressed back button
                //showSnackbar(R.string.sign_in_cancelled);
                finish();
                return;
            }

            if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                showSnackbar(R.string.no_internet_connection);
                return;
            }

            if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                showSnackbar(R.string.unknown_error);
                return;
            }
        }

        showSnackbar(R.string.unknown_sign_in_response);
    }

    private void showSnackbar(@StringRes int errorMessageRes) {
        Snackbar.make(findViewById(android.R.id.content), errorMessageRes, Snackbar.LENGTH_LONG).show();
    }
}
