package nguyenpham.com.musicstar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private ImageButton btnLoginGoogle;
    private Button btnSignIn,btnForgetPass,btnCreateAccount;
    private TextView txtWelcome;
    private LoginButton btnLoginFacebook;
    String TAG = "FirebaseAuth";

    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        addControls();
        addEvents();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void addEvents() {
        btnLoginFacebook.setReadPermissions("email", "public_profile");
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               // Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
               // Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
              //  Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user= firebaseAuth.getCurrentUser();
                if(user!= null)
                {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    Uri uri = user.getPhotoUrl();

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("NAME",name);
                    returnIntent.putExtra("EMAIL",email);
                    returnIntent.putExtra("IMAGE",uri.toString());
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }else{
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    private void addControls() {
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnForgetPass = (Button) findViewById(R.id.btnForgetPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        txtWelcome = (TextView) findViewById(R.id.txtWelcome);
        btnLoginFacebook = (LoginButton) findViewById(R.id.btnLoginFacebook);
        btnLoginGoogle = (ImageButton) findViewById(R.id.btnLoginGoogle);
        Typeface type = Typeface.createFromAsset(getAssets(),"font/VNI-Disney.ttf");
        txtWelcome.setTypeface(type);

        mAuth = FirebaseAuth.getInstance();

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
