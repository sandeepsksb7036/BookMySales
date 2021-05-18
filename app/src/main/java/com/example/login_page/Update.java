package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {

    TextView callbackdate_details;
    TextView disposition_details;
    TextView email_details;
    TextView name_details;
    TextView number_details;
    TextView remark_details;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        this.update = (Button) findViewById(R.id.update);
        this.name_details = (TextView) findViewById(R.id.name_details);
        this.number_details = (TextView) findViewById(R.id.number_details);
        this.email_details = (TextView) findViewById(R.id.email_details);
        this.disposition_details = (TextView) findViewById(R.id.disposition_details);
        this.callbackdate_details = (TextView) findViewById(R.id.callbackdate_details);
        this.remark_details = (TextView) findViewById(R.id.remarks_details);
        String name = getIntent().getStringExtra("dm_name");
        String number = getIntent().getStringExtra("dm_number");
        String email = getIntent().getStringExtra("email");
        String disposition = getIntent().getStringExtra("disposition");
        String callbackdate = getIntent().getStringExtra("callbackdate");
        String remark = getIntent().getStringExtra("remarks");
        final String lead_name = getIntent().getStringExtra("name");
        this.name_details.setText(name);
        this.number_details.setText(number);
        this.email_details.setText(email);
        this.disposition_details.setText(disposition);
        this.callbackdate_details.setText(callbackdate);
        this.remark_details.setText(remark);
        this.update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Update.this.call_update(lead_name);
            }
        });
    }

    /* access modifiers changed from: private */
    public void call_update(String lead_name) {
        getJSON(this.name_details.getText().toString(), this.number_details.getText().toString(), this.email_details.getText().toString(), this.disposition_details.getText().toString(), this.callbackdate_details.getText().toString(), this.remark_details.getText().toString(), lead_name);
    }

    private void getJSON(String name, String number, String email, String disposition, String callbackdate, String remark, String lead_name) {
        final String str = name;
        final String str2 = number;
        final String str3 = email;
        final String str4 = disposition;
        final String str5 = callbackdate;
        final String str6 = remark;
        final String str7 = lead_name;

        class update extends AsyncTask<Void, Void, String>  {
            /* access modifiers changed from: protected */
            public void onPreExecute() {
                super.onPreExecute();
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(Update.this.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }

            /* access modifiers changed from: protected */
            public String doInBackground(Void... voids) {
                StringBuilder sb = new StringBuilder();
                try {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("username", str);
                    params.put("contactnumber", str2);
                    params.put("email", str3);
                    params.put("disposition", str4);
                    params.put("callbackdate", str5);
                    params.put("remark", str6);
                    params.put("leadname", str7);
                    HttpURLConnection conn = (HttpURLConnection) new URL("https://wfm.ensomerge.com/api/update.php").openConnection();
//                    HttpURLConnection conn = (HttpURLConnection) new URL("http://192.168.137.1/Android/update.php").openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                    writer.write(Update.this.getPostDataString(params));
                    writer.flush();
                    writer.close();
                    os.close();
                    if (conn.getResponseCode() == 200) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        sb = new StringBuilder();
                        while (true) {
                            String readLine = br.readLine();
                            String response = readLine;
                            if (readLine == null) {
                                break;
                            }
                            sb.append(response);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return sb.toString();
            }
        }
        update up = new update();
        up.execute();
    }

    /* access modifiers changed from: private */
    public String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            String str = "UTF-8";
            result.append(URLEncoder.encode((String) entry.getKey(), str));
            result.append("=");
            result.append(URLEncoder.encode((String) entry.getValue(), str));
        }
        return result.toString();
    }
}
