package com.example.do_an.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.do_an.database.CarHisDataSource;
import com.example.do_an.R;
import com.example.do_an.database.UserInfoDataSource;
import com.example.do_an.ui.ChiTietLichActivity;
import com.example.do_an.model.HisApdater;
import com.example.do_an.model.HisCar;
import com.example.do_an.model.HisCarDateComparator;
import com.example.do_an.model.UserInfo;
import com.example.do_an.ui.InfoUserActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements HisApdater.UserCallback {
    LinearLayout LLhello, LLhis;
    TextView tvNamehome;
    RecyclerView rvListC;
    ArrayList<HisCar> listHisCar;
    HisApdater hisApdater;
    CarHisDataSource carHisDataSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("usernamE", "");
       // LLhello = view.findViewById(R.id.LLhello);
      //  LLhis = view.findViewById(R.id.LLHis);
      //  rvListC = view.findViewById(R.id.rvlist);
       // tvNamehome = view.findViewById(R.id.tvNamehome);
        setTV(username);
        LLhello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), InfoUserActivity.class);
                startActivity(i);
            }
        });
        carHisDataSource = new CarHisDataSource(getContext());
        carHisDataSource.open();
        List<HisCar> hisCars = carHisDataSource.getAllHisCar(username);
        if(!hisCars.isEmpty())
        {
            for (HisCar hisCar : hisCars) {
                Log.d("ad", "1");
                if (!checkDateTime(hisCar.getDateFix())) {
                    Log.d("ad", "2");
                    hisCar.setStatus("Đã hoàn thành");
                    carHisDataSource.updateHisCarStatus(hisCar, "Đã hoàn thành");
                }
                else
                {
                    hisCar.setStatus("Chưa hoàn thành");
                    carHisDataSource.updateHisCarStatus(hisCar, "Chưa hoàn thành");
                }
            }
            carHisDataSource.close();
        }
        listHisCar = new ArrayList<>(hisCars);
        Collections.sort(listHisCar, new HisCarDateComparator());
        hisApdater = new HisApdater(listHisCar, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvListC.setAdapter(hisApdater);
        rvListC.setLayoutManager(linearLayoutManager);
        return view;
    }

    private void setTV(String username) {
        UserInfoDataSource mInfoDataSource = new UserInfoDataSource(getContext());
        mInfoDataSource.open();
        List<UserInfo> userInfos = mInfoDataSource.getAllUserInfos();
        for (UserInfo userInfo : userInfos) {
            if (userInfo.getPhone().equals(username)) {
                if (userInfo.getName() == null || userInfo.getName().equals("Chưa thiết lập"))
                    tvNamehome.setText("Xin chào, ");
                else
                    tvNamehome.setText("Xin chào, " + userInfo.getName());
            }
        }
        mInfoDataSource.close();
    }


    public HomeFragment() {
        super();
    }


    @Override
    public void onItemClick(String id) {
        Intent intent = new Intent(getActivity(), ChiTietLichActivity.class);
        intent.putExtra("id", id);
      //  intent.putExtra("admin", "0");
        startActivity(intent);
    }

    private boolean checkDateTime(String date) {
        String[] arrOfDate = date.split("/"); // Cắt chuỗi bởi dấu /
        final Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        int currentDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int selectedYear = Integer.valueOf(arrOfDate[2]);
        int selectedMonth = Integer.valueOf(arrOfDate[1]);
        int selectedDayOfMonth = Integer.valueOf(arrOfDate[0]);
        if (date.equals(""))
            return false;
        if (selectedYear > currentYear ||
                (selectedYear == currentYear && selectedMonth > currentMonth) ||
                (selectedYear == currentYear && selectedMonth == currentMonth && selectedDayOfMonth >= currentDayOfMonth) ||
                (selectedYear == currentYear && selectedMonth == currentMonth && selectedDayOfMonth >= currentDayOfMonth)) {
            return true;
        } else
            return false;
    }
}