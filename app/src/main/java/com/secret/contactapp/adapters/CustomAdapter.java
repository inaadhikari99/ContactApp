package com.secret.contactapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends FragmentPagerAdapter {
    private List<Fragment> listFragment=new ArrayList<>();
    private List<String> listTitles=new ArrayList<>();

    public CustomAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }
    public CharSequence getPageTitle(int position){
        return listTitles.get(position);
    }
    public void addFragment(Fragment fragment,String title){
        listFragment.add(fragment);
        listTitles.add(title);

    }
}
