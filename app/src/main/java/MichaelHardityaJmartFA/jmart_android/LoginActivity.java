package MichaelHardityaJmartFA.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView registrationPage = findViewById(R.id.text);
        registrationPage.setTextColor(Color.BLUE);
    }
    public void goToRegister(View v){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
}