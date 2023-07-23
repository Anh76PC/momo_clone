package com.example.do_an.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.database.CarDataSource;
import com.example.do_an.R;
import com.example.do_an.database.UserDataSource;
import com.example.do_an.database.UserInfoDataSource;
import com.example.do_an.model.CarInfo;
import com.example.do_an.model.User;
import com.example.do_an.model.UserInfo;

public class Register2Activity extends AppCompatActivity {
    ImageButton imbbackRECtn;
    EditText edpassRE, edpassRExncnt;
    ImageView ivSHPassREcnt, ivSHPassXNREcnt;
    Button checkPassRecnt, btdkyREcnt;
    TextView dkpasstrueRE, dk8kituRE, dkchuhoavathRE, dksoRE;
    private UserDataSource mDataSource;
    private UserInfoDataSource mInfoDataSource;
    private CarDataSource mCarDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        edpassRE = findViewById(R.id.edpassRECon);
        imbbackRECtn = findViewById(R.id.imbbackRECon);
        edpassRExncnt = findViewById(R.id.edpassRExnCon);
        ivSHPassREcnt = findViewById(R.id.ivSHPassRECon);
        ivSHPassXNREcnt = findViewById(R.id.ivSHPassXNRECon);
        checkPassRecnt = findViewById(R.id.checkPassReCon);
        btdkyREcnt = findViewById(R.id.btdkyRECon);
        dkpasstrueRE = findViewById(R.id.dkpasstrueRECon);
        dk8kituRE = findViewById(R.id.dk8kituRECon);
        dkchuhoavathRE = findViewById(R.id.dkchuhoavathRECon);
        dksoRE = findViewById(R.id.dksoRECon);
        mDataSource = new UserDataSource(this);
        mInfoDataSource = new UserInfoDataSource(this);
        mCarDataSource = new CarDataSource(this);
        mDataSource.open();
        mInfoDataSource.open();
        mCarDataSource.open();
        String Username = getIntent().getStringExtra("username");
        imbbackRECtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register2Activity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ivSHPassREcnt.setImageResource(R.drawable.hidepass);
        ivSHPassXNREcnt.setImageResource(R.drawable.hidepass);
        ivSHPassREcnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePasswordVisibility(edpassRE, ivSHPassREcnt);
            }
        });

        ivSHPassREcnt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ivSHPassREcnt.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.SRC_ATOP);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    ivSHPassREcnt.clearColorFilter();
                }
                return false;
            }
        });
        //
        ivSHPassXNREcnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePasswordVisibility(edpassRExncnt, ivSHPassXNREcnt);
            }
        });

        ivSHPassREcnt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ivSHPassXNREcnt.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.SRC_ATOP);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    ivSHPassXNREcnt.clearColorFilter();
                }
                return false;
            }
        });
        checkPassRecnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPass();
            }
        });
        btdkyREcnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = Username;
                String text = edpassRE.getText().toString();
                String text2 = edpassRExncnt.getText().toString();
                if (text.length() >= 8 && text.matches(".*\\d+.*") && text.matches("(?=.*[A-Z])(?=.*[a-z]).+") && text.equals(text2)) {
                    User newUser = mDataSource.createUser(username, text);
                    CarInfo newCar = mCarDataSource.createCar(null, null, null, username);
                    UserInfo newUserInfo = mInfoDataSource.createUserInfo(username, null, null, null, null);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("password", text);
                    startActivity(intent);
                    Toast myToast = Toast.makeText(getApplicationContext(), "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT);
                    myToast.show();
                } else {
                    Toast myToast = Toast.makeText(getApplicationContext(), "Mật khẩu chưa thoả mãn các điều kiện", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataSource.close();
        mCarDataSource.close();
        mInfoDataSource.close();
    }

    private void togglePasswordVisibility(EditText password, ImageView showPassword) {
        int inputType = password.getInputType();
        if (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPassword.setImageResource(R.drawable.hidepass);
        } else {
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPassword.setImageResource(R.drawable.showpass);
        }
        password.setSelection(password.length());
    }

    private void checkPass() {
        String text = edpassRE.getText().toString();
        String text2 = edpassRExncnt.getText().toString();
        Drawable drawable = getResources().getDrawable(R.drawable.round_check_24);
        Drawable drawable2 = getResources().getDrawable(R.drawable.baseline_close_24);
        dk8kituRE.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
        dksoRE.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
        dkchuhoavathRE.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
        dkpasstrueRE.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
        if (text.length() >= 8)
            dk8kituRE.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        else
            dk8kituRE.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);

        if (text.matches(".*\\d+.*"))
            dksoRE.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        else
            dksoRE.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);

        if (text.matches("(?=.*[A-Z])(?=.*[a-z]).+"))
            dkchuhoavathRE.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        else
            dkchuhoavathRE.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
        if (text.equals(text2))
            dkpasstrueRE.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        else
            dkpasstrueRE.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
    }
}