package com.example.falling.logindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by falling on 2016/2/23.
 */
public class MainPageActivity extends AppCompatActivity {

    private TextView mTextView;
    private LoginUtils mLoginUtils;
    private Button mButton;
    private String mUserName;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        mLoginUtils = new LoginUtils(this);

        mTextView = (TextView) findViewById(R.id.mainPage_text);
        mUserName = mLoginUtils.getLoginedUserName();
        String text =mUserName +" sigh in";
        mTextView.setText(text);

        mButton = (Button) findViewById(R.id.mainPage_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginUtils.sighOut(mUserName);
                startActivity(new Intent(MainPageActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                mLoginUtils.sighOut(mUserName);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
