package com.secret.contactapp.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.secret.contactapp.MainActivity;
import com.secret.contactapp.R;
import com.secret.contactapp.adapters.CallAdapter;
import com.secret.contactapp.adapters.ContactAdapter;
import com.secret.contactapp.dbutil.DBHelper;
import com.secret.contactapp.model.ModelCall;
import com.secret.contactapp.model.ModelContact;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FragmentContact extends Fragment {
    private View view;
    private RecyclerView rv;
    EditText edit_btn;
    FloatingActionButton add;
    List<ModelContact> list = new ArrayList<>();
    ContactAdapter adapter;

    public FragmentContact() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        rv = view.findViewById(R.id.rv_contact);
        edit_btn = view.findViewById(R.id.edit_btn);
        add = view.findViewById(R.id.add_contacts);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        rv.setLayoutManager(layoutManager);
        adapter = new ContactAdapter(getContext(), getContact());
        rv.setAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog();
            }
        });

        return view;


    }

    private void onCreateDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        View v = getLayoutInflater().inflate(R.layout.activity_add, null);
        alert.setView(v);
        EditText name = v.findViewById(R.id.name_ed);
        EditText number = v.findViewById(R.id.number_ed);
        Button save = v.findViewById(R.id.btn_save);
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!name.getText().toString().isEmpty() && !number.getText().toString().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name.getText().toString());
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, number.getText().toString());
                    if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "There is no app that support this action ", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getContext(), "please fill all the fields", Toast.LENGTH_SHORT).show();
                }

                alertDialog.dismiss();
            }


        });
    }


    @SuppressLint("NotifyDataSetChanged")
    private List<ModelContact> getContact() {
        List<ModelContact> list = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
        Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME );
        int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int number = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            list.add(new ModelContact(cursor.getString(number), cursor.getString(nameIndex)));

        }

        cursor.close();
        return list;

    }

}
