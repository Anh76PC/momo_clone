package com.example.do_an.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.MainActivity;
import com.example.do_an.R;
import com.example.do_an.database.UserDataSource;
import com.example.do_an.admin.AdminActivity;
import com.example.do_an.model.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button btlogin;
    TextView tvquenpass, tvdky;
    ImageView ivSHpassLoginmk;
    EditText eddtlogin, edmklogin;
    private UserDataSource mDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        // Khởi tạo đối tượng SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        // Lưu thông tin username vào SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        btlogin = findViewById(R.id.btdnlogin);
        tvdky = findViewById(R.id.tvdklogin);
        ivSHpassLoginmk = findViewById(R.id.ivSHPassLoginMK);
//        eddtlogin = findViewById(R.id.eddtlogin);
        edmklogin = findViewById(R.id.edmklogin);
        mDataSource = new UserDataSource(this);
        mDataSource.open();
        String Username = getIntent().getStringExtra("username");
        String Password = getIntent().getStringExtra("password");
//        eddtlogin.setText(Username);
//        edmklogin.setText(Password);
        ivSHpassLoginmk.setImageResource(R.drawable.hidepass);
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, AdminActivity.class);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                String username = eddtlogin.getText().toString();
                String password = edmklogin.getText().toString();
                if(username.equals("1111") && password.equals("1111"))
                {
                    editor.putString("usernamE", "1111"); // "username" là tên key, username là giá trị
                    editor.apply(); // Lưu lại thay đổi
                    Log.d("check", "aaa");
                    startActivity(i);
                    finish();
                    return;
                }
                List<User> users = mDataSource.getAllUsers();
                for (User user : users) {
                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        editor.putString("usernamE", user.getUsername()); // "username" là tên key, username là giá trị
                        editor.putString("passworD", user.getPassword()); // "username" là tên key, username là giá trị
                        editor.apply(); // Lưu lại thay đổi
                        Toast myToast = Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT);
                        myToast.show();
                        startActivity(intent);
                        finish();
                        return;
                    }
                }
                    Toast myToast = Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT);
                    myToast.show();
                }
        });
        tvdky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        ivSHpassLoginmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePasswordVisibility(edmklogin, ivSHpassLoginmk);
            }
        });

        ivSHpassLoginmk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ivSHpassLoginmk.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.SRC_ATOP);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    ivSHpassLoginmk.clearColorFilter();
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataSource.close();
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
}