package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Display extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    Button call;
    TextView callbackdates;
    TextView dispositions;
    TextView emails;
    Button history;
    TextView names;
    TextView numbers;
    TextView remarks;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        this.names = (TextView) findViewById(R.id.name_details);
        this.numbers = (TextView) findViewById(R.id.number_details);
        this.emails = (TextView) findViewById(R.id.email_details);
        this.dispositions = (TextView) findViewById(R.id.disposition_details);
        this.callbackdates = (TextView) findViewById(R.id.callbackdate_details);
        this.remarks = (TextView) findViewById(R.id.remarks_details);
        this.call = (Button) findViewById(R.id.call);
        this.update = (Button) findViewById(R.id.update);
        this.history = (Button) findViewById(R.id.history);
        final String name = getIntent().getStringExtra("dm_name");
        final String lead_name = getIntent().getStringExtra("name");
        final String number = getIntent().getStringExtra("dm_number");
        final String email = getIntent().getStringExtra("email");
        final String disposition = getIntent().getStringExtra("disposition");
        final String callbackdate = getIntent().getStringExtra("callbackdate");
        final String remark = getIntent().getStringExtra("remarks");
        this.names.setText(name);
        this.numbers.setText(number);
        this.emails.setText(email);
        this.dispositions.setText(disposition);
        this.callbackdates.setText(callbackdate);
        this.remarks.setText(remark);
        this.call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Display.this.display_call(number);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_details( name, number, email, disposition, callbackdate, remark, lead_name);
            }
        });


        this.history.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Display.this, History.class);
                intent.putExtra("number", number);
                Display.this.startActivity(intent);
            }
        });
    }

    public void display_call(String number) {
        String contact_number = number;
        Intent intent = new Intent("android.intent.action.CALL");
        StringBuilder sb = new StringBuilder();
        sb.append("tel:");
        sb.append(contact_number);
        intent.setData(Uri.parse(sb.toString()));
        String str = "android.permission.CALL_PHONE";
        if (ActivityCompat.checkSelfPermission(this, str) != 0) {
            ActivityCompat.requestPermissions(this, new String[]{str}, 1);
            return;
        }
        startActivity(intent);
    }

    public void update_details(String name, String number, String email, String disposition, String callbackdate, String remark, String lead_name) {
        Intent intent = new Intent(this, Update.class);
        intent.putExtra("dm_name", name);
        intent.putExtra("dm_number", number);
        intent.putExtra("email", email);
        intent.putExtra("disposition", disposition);
        intent.putExtra("callbackdate", callbackdate);
        intent.putExtra("remarks", remark);
        intent.putExtra("name", lead_name);
        startActivity(intent);
    }
}