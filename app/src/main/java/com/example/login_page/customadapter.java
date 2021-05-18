package com.example.login_page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class customadapter extends ArrayAdapter {

    private static final int REQUEST_CALL = 1;
    private final String[] callbackdate;
    private final String[] contact_number;
    private final Context context;
    private final String[] disposition;
    private final String[] dm_name;
    private final String[] email;
    private final String namess;
    private final String[] remarks;

    public customadapter(Context context, String[] name, String[] number, String[] email, String[] disposition, String[] callbackdate, String[] remarks, String namess) {
        super(context,R.layout.row, R.id.textview1, name);
        this.context = context;
        this.dm_name = name;
        this.contact_number = number;
        this.email = email;
        this.disposition = disposition;
        this.callbackdate = callbackdate;
        this.remarks = remarks;
        this.namess = namess;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = LayoutInflater.from(getContext()).inflate(R.layout.row, null, true);
        TextView mycontact = (TextView) row.findViewById(R.id.textview2);
        ImageView call = (ImageView) row.findViewById(R.id.phone);
        ImageView details = (ImageView) row.findViewById(R.id.details);
        ((TextView) row.findViewById(R.id.textview1)).setText(this.dm_name[position]);
        mycontact.setText(this.contact_number[position]);
        String[] strArr = this.contact_number;
        final String hash = strArr[position];
        final String names = this.dm_name[position];
        final String numbers = strArr[position];
        final String emails = this.email[position];
        final String dispositions = this.disposition[position];
        final String callbackdates = this.callbackdate[position];
        final String remark = this.remarks[position];
        final String lead_name = this.namess;
        call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                customadapter.this.call(hash, names);
            }
        });

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detail(names,numbers,emails,dispositions,callbackdates,remark,lead_name);
            }
        });
        return row;
    }

    /* access modifiers changed from: private */
    public void detail(String names, String numbers, String emails, String dispositions, String callbackdates, String remark, String lead_name) {
        Intent intent = new Intent(this.context, Display.class);
        intent.putExtra("dm_name", names);
        intent.putExtra("dm_number", numbers);
        intent.putExtra("emails", emails);
        intent.putExtra("disposition", dispositions);
        intent.putExtra("callbackdate", callbackdates);
        intent.putExtra("remarks", remark);
        intent.putExtra("name", lead_name);
        this.context.startActivity(intent);
    }

    /* access modifiers changed from: private */
    public void call(String hash, String name) {
        String contact = hash;
        String str = name;
        Intent intent = new Intent("android.intent.action.CALL");
        StringBuilder sb = new StringBuilder();
        sb.append("tel:");
        sb.append(contact);
        intent.setData(Uri.parse(sb.toString()));
        String str2 = "android.permission.CALL_PHONE";
        if (ActivityCompat.checkSelfPermission(this.context, str2) != 0) {
            ActivityCompat.requestPermissions((Activity) this.context, new String[]{str2}, 1);
            return;
        }
        this.context.startActivity(intent);
    }
}
