package com.example.do_an.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.do_an.R;
import com.example.do_an.database.UserDataSource;
import com.example.do_an.model.User;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    ImageButton imbbackRE;
    EditText edphone;
    Button btdkyctn;
    CheckBox cbcheck;
    private UserDataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        imbbackRE = findViewById(R.id.imbbackRE);
        edphone = findViewById(R.id.edphone);
        btdkyctn = findViewById(R.id.btdkyctn);
        cbcheck = findViewById(R.id.cbcheck);
        mDataSource = new UserDataSource(this);
        mDataSource.open();

        imbbackRE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btdkyctn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = edphone.getText().toString().trim();
                if (phone == "" || phone.length() != 10) {
                    Toast myToast = Toast.makeText(getApplicationContext(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT);
                    myToast.show();
                } else if (!cbcheck.isChecked()) {
                    Toast myToast = Toast.makeText(getApplicationContext(), "Vui lòng đồng ý với điều khoản và chính sách của chúng tôi", Toast.LENGTH_SHORT);
                    myToast.show();
                } else if (checkPhone(phone)) {
                    Toast myToast = Toast.makeText(getApplicationContext(), "Số điện thoại này đã tồn tại", Toast.LENGTH_SHORT);
                    myToast.show();
                } else {
                    Intent i = new Intent(RegisterActivity.this, Register2Activity.class);
                    i.putExtra("username", phone);
                    startActivity(i);
                    mDataSource.close();
                }

            }
        });

    }

    private boolean checkPhone(String phone) {
        List<User> users = mDataSource.getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(phone))
                return true;
        }
        return false;
    }
}