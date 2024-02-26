package com.itsjames.android_demo_lib;


import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MyParser {
    private Context mContext;
    private RequestQueue mRequestQueue;

    public MyParser(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);
    }

    public interface ParseJSONListener {
        void onParseComplete(List<ExampleItem> exampleList);
        void onError(VolleyError error);
    }

    public void parseJSON(String url, final ParseJSONListener listener) {
        final List<ExampleItem> exampleList = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String creatorName = hit.getString("user");
                                String imageUrl = hit.getString("webformatURL");
                                int likeCount = hit.getInt("likes");

                                exampleList.add(new ExampleItem(imageUrl, creatorName, likeCount));
                            }

                            listener.onParseComplete(exampleList);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, error -> error.printStackTrace());
        mRequestQueue.add(request);
    }
}