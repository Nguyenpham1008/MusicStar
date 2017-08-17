package nguyenpham.com.musicstar;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    ImageButton btnLoginFacebook,btnLoginGoogle;
    Button btnSignIn,btnForgetPass,btnCreateAccount;
    TextView txtWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControls() {
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnForgetPass = (Button) findViewById(R.id.btnForgetPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        txtWelcome = (TextView) findViewById(R.id.txtWelcome);
        Typeface type = Typeface.createFromAsset(getAssets(),"font/VNI-Disney.ttf");
        txtWelcome.setTypeface(type);

    }
}
