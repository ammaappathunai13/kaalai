package com.vjsm.sports.kaalai;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class About_Our_Comapany extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_about__our__comapany,container,false);

        TextView version=v.findViewById(R.id.version);

        String result="";
        try{
            result=getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(),0).versionName;
            result=result.replaceAll("[a-zA-Z]|-","");
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
         version.setText("Version :"+result);
        return v;
    }

}
