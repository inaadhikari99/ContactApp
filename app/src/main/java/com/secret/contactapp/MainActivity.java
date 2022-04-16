package com.secret.contactapp;

import com.google.android.material.tabs.TabLayout;
import com.secret.contactapp.adapters.CustomAdapter;
import com.secret.contactapp.fragments.FragmentCalls;
import com.secret.contactapp.fragments.FragmentContact;
import com.secret.contactapp.model.ModelContact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private final int[] ICONS = {R.drawable.ic_baseline_contacts_24, R.drawable.ic_baseline_phone_24};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        CustomAdapter customAdapter = new CustomAdapter(getSupportFragmentManager());

        customAdapter.addFragment(new FragmentContact(), "Contacts");
        customAdapter.addFragment(new FragmentCalls(), "Call Logs");
        viewPager.setAdapter(customAdapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setIcon(ICONS[i]);

        }
        askPermissions();
    }


    private void askPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
        }
    }
}