package com.secret.contactapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.secret.contactapp.R;
import com.secret.contactapp.model.ModelCall;
import com.secret.contactapp.model.ModelContact;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    public LayoutInflater layoutInflater;
    private Context mContext;
    private List<ModelContact> modelContacts;

    public ContactAdapter(Context context, List<ModelContact> listCalls) {
        mContext = context;
        modelContacts = listCalls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_contact, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(modelContacts.get(position).getName());
        holder.number.setText(modelContacts.get(position).getNumber());
        holder.call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = modelContacts.get(position).getName();
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
                        .setTitle("SEND TO:" + modelContacts.get(position).getNumber())
                        .setView(message)
                        .setPositiveButton("SEND", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                smsSEND(modelContacts.get(position).getName(), message.getText().toString());
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
        return modelContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, number, call_btn, msg_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.contact_number);
            name = itemView.findViewById(R.id.contact_name);
            call_btn = itemView.findViewById(R.id.call_btn);
            msg_btn = itemView.findViewById(R.id.msg_btn);
        }
    }


    public void filterList(List<ModelContact> filterList) {
        modelContacts = (ArrayList<ModelContact>) filterList;
        notifyDataSetChanged();

    }
}