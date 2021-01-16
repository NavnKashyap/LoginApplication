package com.loginapplication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.loginapplication.jsonparser.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;


public class HttpsRequest {
    private String match;
    private Context ctx;
    private JSONObject jObject;


    public HttpsRequest(String match, JSONObject jObject, Context ctx) {
        this.match = match;
        this.jObject = jObject;
        this.ctx = ctx;


    }

  

    public void stringPostJson(final String TAG, final Helper h) {
        AndroidNetworking.post(Consts.BASE_URL + match)
                .addJSONObjectBody(jObject)
                .setTag("test")
                .addHeaders("Content-Type", "application/json")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e(TAG, " response body --->" + response.toString());
                        Log.e(TAG, " response body --->" + jObject.toString());
                        JSONParser jsonParser = new JSONParser(ctx, response);
                        if (jsonParser.RESULT) {
                            try {
                                h.backResponse(jsonParser.RESULT, jsonParser.MESSAGE, response);
                            } catch (JSONException e) {
                                Toast.makeText(ctx, R.string.login_failed, Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        } else {

                            Toast.makeText(ctx, R.string.login_failed, Toast.LENGTH_SHORT).show();
                            try {
                                h.backResponse(jsonParser.RESULT, jsonParser.MESSAGE, null);
                            } catch (JSONException e) {
                                Toast.makeText(ctx, R.string.login_failed, Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtils.pauseProgressDialog();

                        Toast.makeText(ctx, R.string.login_failed, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, " error body --->" + anError.getErrorBody() + " error msg --->" + anError.getMessage());
                    }
                });
    }

}