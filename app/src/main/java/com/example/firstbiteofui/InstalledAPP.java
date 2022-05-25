package com.example.firstbiteofui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class InstalledAPP extends AppCompatActivity {
    private RecyclerView installedApplications;
    private List<AppInfo> appList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installed_app);
        installedApplications = findViewById(R.id.recycleView);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("查看已安装应用的信息");
        appList=new ArrayList<>();

        PackageManager packageManager=getPackageManager();
        @SuppressLint("WrongConstant")
        List<ApplicationInfo> installList=
                packageManager.getInstalledApplications(PackageManager.PERMISSION_GRANTED);
        for(ApplicationInfo application:installList){
            Drawable tempAppIcon=application.loadIcon(packageManager);
            String tempAppName= (String) application.loadLabel(packageManager);
            AppInfo appInfo=new AppInfo(tempAppIcon,tempAppName);
            appList.add(appInfo);
        }
        installedApplications.setLayoutManager(new LinearLayoutManager(this));
        AppInfoAdapter adapter=new AppInfoAdapter(appList,this);
        installedApplications.setAdapter(adapter);

    }
}