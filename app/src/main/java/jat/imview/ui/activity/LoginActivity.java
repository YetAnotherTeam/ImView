package jat.imview.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import jat.imview.R;
import jat.imview.service.SendServiceHelper;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static String LOG_TAG = "MyLoginActivity";
    private TextView mEmailTextView;
    private TextView mPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailTextView = (TextView) findViewById(R.id.email);
        mPasswordTextView = (TextView) findViewById(R.id.password);

        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.back_button).setOnClickListener(this);
        findViewById(R.id.sign_up).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.login_button:
                String email = mEmailTextView.getText().toString();
                String password = mPasswordTextView.getText().toString();
                if (email.length() > 0 && password.length() > 0) {
                    SendServiceHelper.getInstance(this).requestLogin(email, password);
                } else {
                    Toast.makeText(getApplicationContext(), "Заполните, пожалуйста, все поля", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sign_up:
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }

    }
}
