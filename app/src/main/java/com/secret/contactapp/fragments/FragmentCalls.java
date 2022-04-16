package com.secret.contactapp.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.secret.contactapp.R;
import com.secret.contactapp.adapters.CallAdapter;
import com.secret.contactapp.model.ModelCall;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentCalls extends Fragment {
    private View view;
    private RecyclerView rv_call;

    public FragmentCalls() {

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_call, container, false);
        rv_call = view.findViewById(R.id.rv_call);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        rv_call.setLayoutManager(layoutManager);
        CallAdapter adapter = new CallAdapter(getContext(), getCallLogs());
        rv_call.setAdapter(adapter);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<ModelCall> getCallLogs() {
        List<ModelCall> list = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CALL_LOG}, 1);
        }
        Cursor cursor = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null);
        int nameIndex = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Date date1 = new Date(Long.parseLong(cursor.getString(date)));
            list.add(new ModelCall(cursor.getString(number), cursor.getString(duration), date1.toString(), cursor.getString(nameIndex)));

        }
        return list;

    }
}
