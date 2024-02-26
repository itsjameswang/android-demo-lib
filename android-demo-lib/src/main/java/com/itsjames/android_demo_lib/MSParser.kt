package com.itsjames.android_demo_lib

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException


class MSParser(private val mContext: Context) {
    private val mRequestQueue: RequestQueue

    init {
        mRequestQueue = Volley.newRequestQueue(mContext)
    }

    interface ParseJSONListener {
        fun onParseComplete(exampleList: List<ExampleItem>?)
        fun onError(error: VolleyError?)
    }

    fun parseJSON(url: String?, listener: ParseJSONListener) {
        val exampleList: MutableList<ExampleItem> = ArrayList()
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val jsonArray = response.getJSONArray("hits")
                    for (i in 0 until jsonArray.length()) {
                        val hit = jsonArray.getJSONObject(i)
                        val creatorName = hit.getString("user")
                        val imageUrl = hit.getString("webformatURL")
                        val likeCount = hit.getInt("likes")
                        exampleList.add(ExampleItem(imageUrl, creatorName, likeCount))
                    }
                    listener.onParseComplete(exampleList)
                } catch (e: JSONException) {
                    throw RuntimeException(e)
                }
            }
        ) { error: VolleyError -> error.printStackTrace() }
        mRequestQueue.add(request)
    }
}