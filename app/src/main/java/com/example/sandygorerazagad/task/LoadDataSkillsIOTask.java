package com.example.sandygorerazagad.task;

import android.os.AsyncTask;

import com.example.sandygorerazagad.interfaces.NetworkResponseListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoadDataSkillsIOTask extends AsyncTask<String,String,String> {

    private NetworkResponseListener networkResponseListener;

    public LoadDataSkillsIOTask(NetworkResponseListener networkResponseListener){
        this.networkResponseListener=networkResponseListener;
    }

    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client=new OkHttpClient();
        client.retryOnConnectionFailure();
        client.newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .build();

        //Calling Demo Data APi
        Request request=new  Request.Builder().url("https://gadsapi.herokuapp.com/api/skilliq").build();

        Response response=null;

        try {
            response=client.newCall(request).execute();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        if(response!=null && response.isSuccessful()){
            try{
                if(response.body()!=null){
                    return response.body().string();
                }
                else{
                    return  null;
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null){
            networkResponseListener.SuccessData(s);
        }
        else{
            networkResponseListener.FailedData();
        }
    }
}
