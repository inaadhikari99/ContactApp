package com.secret.contactapp.adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.secret.contactapp.R;
import com.secret.contactapp.model.ModelCall;

import java.util.List;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.ViewHolder> {
    public LayoutInflater layoutInflater;
    private Context mContext;
    private List<ModelCall> modelCalls;

    public CallAdapter(Context context, List<ModelCall> listCalls) {
        mContext = context;
        modelCalls = listCalls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_calls, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(modelCalls.get(position).getName());
        holder.date.setText(modelCalls.get(position).getDate());
        holder.duration.setText("duration:" + "" + modelCalls.get(position).getDuration() + "second");
        holder.number.setText(modelCalls.get(position).getNumber());

        holder.call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = modelCalls.get(position).getNumber();
                Log.e("number", num);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +num));
                mContext.startActivity(intent);

            }
        });






        holder.msg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText message = new EditText(view.getContext());
                message.setHint("Type your message here..");
                message.setText("hello,how are you?");
                final AlertDialog.Builder send_sms = new AlertDialog.Builder(view.getContext());
                send_sms.setIcon(R.drawable.ic_baseline_message_24)
                        .setTitle("SEND TO:" + modelCalls.get(position).getName())
                        .setView(message)
                        .setPositiveButton("SEND", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                smsSEND(modelCalls.get(position).getNumber(),message.getText().toString());
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                send_sms.show();
            }
        });


    }

    private void smsSEND(String phone, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, message, null, null);
            Toast.makeText(mContext, "Mesaage sent successfully to " + phone, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(mContext, "Something went wrong ", Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public int getItemCount() {
        return modelCalls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, duration, date, number;
        Button call_btn, msg_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.contact_number);
            date = itemView.findViewById(R.id.date);
            duration = itemView.findViewById(R.id.duration);
            name = itemView.findViewById(R.id.name);
            call_btn = itemView.findViewById(R.id.call_btn);
            msg_btn = itemView.findViewById(R.id.msg_btn);

        }
    }
}