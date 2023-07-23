package com.example.do_an.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.do_an.fragment.SettingFragment;

import com.example.do_an.R;
import com.example.do_an.ui.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {
    Button btchanglang, btsettingacc, btlogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_setting, container, false);
        btchanglang = view.findViewById(R.id.changeLang);
        btlogout = view.findViewById(R.id.logout);
        btsettingacc = view.findViewById(R.id.settingAcc);
        btchanglang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ChangeLangFragment());
            }
        });
        btsettingacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new SettingAccFragment());
            }
        });
        btlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();

            }
        });


        return view;
    }
    void loadFragment(Fragment fmNew)
    {
        FragmentTransaction fmTran = getFragmentManager().beginTransaction();
        fmTran.replace(R.id.main_fragment, fmNew);
        fmTran.addToBackStack(null);
        fmTran.commit();
    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất khỏi ứng dụng?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                Toast myToast = Toast.makeText(getContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT);
                myToast.show();
                getActivity().finishAffinity(); // kết thúc tất cả các activity và xóa khỏi stack
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}