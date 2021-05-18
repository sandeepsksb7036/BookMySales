package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class History extends AppCompatActivity {
    TextView history_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        String number = getIntent().getStringExtra("number");
        this.history_detail = (TextView) findViewById(R.id.detail);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        Window window = getWindow();
        double d = (double) width;
        Double.isNaN(d);
        int i = (int) (d * 0.85d);
        double d2 = (double) height;
        Double.isNaN(d2);
        window.setLayout(i, (int) (d2 * 0.7d));
        getJSON(number);
    }

    private void getJSON(final String number) {
        class history extends AsyncTask<Void, Void, String> {
            /* access modifiers changed from: protected */
            public void onPreExecute() {
                super.onPreExecute();
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(String s) {
                super.onPostExecute(s);
//                Toast.makeText(History.this.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    History.this.loadIntoListView(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public String doInBackground(Void... voids) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("https://wfm.ensomerge.com/api/dataa.php?customer_number=");
//                    sb.append("http://192.168.137.1/Android/dataa.php?customer_number=");
                    sb.append(number);
                    HttpURLConnection con = (HttpURLConnection) new URL(sb.toString()).openConnection();
                    StringBuilder sb2 = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        String json = readLine;
                        if (readLine == null) {
                            return sb2.toString().trim();
                        }
                        sb2.append(json);
                        sb2.append("\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }history h = new history();
        h.execute();
    }

    /* access modifiers changed from: private */
    public void loadIntoListView(String json) throws Exception {
        JSONArray jsonArray = new JSONArray(json);
        String[] remark = new String[jsonArray.length()];
        String[] date = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            remark[i] = obj.getString("remarks");
            date[i] = obj.getString("date");
            this.history_detail.setMovementMethod(new ScrollingMovementMethod());
            this.history_detail.append("*");
            this.history_detail.append(date[i]);
            this.history_detail.append(" => ");
            this.history_detail.append(remark[i]);
            this.history_detail.append("\n\n");
        }
    }
}