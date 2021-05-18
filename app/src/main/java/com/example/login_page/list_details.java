package com.example.login_page;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class list_details extends Fragment {
    ListView list;
    String namess;
    private ProgressBar loading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        String namess = ((dashboard) getActivity()).getMyData().getString("name");
         namess  = SharedPrefManager.getInstance(getContext()).getUser().getUsername();
//        Toast.makeText(getContext(),namess,Toast.LENGTH_SHORT).show();
        View root = inflater.inflate(R.layout.fragment_listdetails, container, false);
        list = root.findViewById(R.id.listView);
        loading = root.findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        getJSON(namess);
        return root;

    }

    private void getJSON(final String namess) {
        class list extends AsyncTask<Void, Void, String> {
            /* access modifiers changed from: protected */
            public void onPreExecute() {
                super.onPreExecute();
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loadIntoListView(s, namess);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public String doInBackground(Void... voids) {
//                String key = namess;
                String key = SharedPrefManager.getInstance(getContext()).getUser().getUsername();
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("https://wfm.ensomerge.com/api/fetch.php?key=");
//                    sb.append("http://192.168.137.1/Android/fetch.php?key=");
                    sb.append(key);
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
        }
        list l = new list();
        l.execute();
    }

    /* access modifiers changed from: private */
    public void loadIntoListView(String json, String namess) throws Exception {
        JSONArray jsonArray = new JSONArray(json);
        String[] name = new String[jsonArray.length()];
        String[] number = new String[jsonArray.length()];
        String[] email = new String[jsonArray.length()];
        String[] disposition = new String[jsonArray.length()];
        String[] callbackdate = new String[jsonArray.length()];
        String[] remarks = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            name[i] = obj.getString("dm_name");
            number[i] = obj.getString("contact_number");
            email[i] = obj.getString("email");
            disposition[i] = obj.getString("disposition");
            callbackdate[i] = obj.getString("follow_up_date");
            remarks[i] = obj.getString("remarks");
        }
        customadapter adapter = new customadapter(getActivity(), name, number, email, disposition, callbackdate, remarks, namess);
        list.setAdapter(adapter);
    }


}
