package com.crispus.znest

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams

import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity


import org.json.JSONArray
import org.json.JSONObject

class ApiHelper(var context: Context) {

    //POST
    fun post(api: String, params: RequestParams) {
        Toast.makeText(context, "Please wait for response", Toast.LENGTH_LONG).show()
        val client = AsyncHttpClient()

        client.post(api, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONObject?
            ) {
                Toast.makeText(context, "Response: $response", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {
                Log.e("API_ERROR", responseString ?: "Unknown error", throwable)
                Toast.makeText(context, "Error: $responseString", Toast.LENGTH_LONG).show()
            }
        })
    }

    // LOGIN
    fun post_login(api: String, params: RequestParams) {
        Toast.makeText(context, "Please wait for response", Toast.LENGTH_LONG).show()
        val client = AsyncHttpClient()

        client.post(api, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONObject?
            ) {
                if (response != null && response.has("user")) {
                    val user = response.getJSONObject("user")

                    val username = user.optString("username")
                    val email = user.optString("email")

                    val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    prefs.edit()
                        .putString("username", username)
                        .putString("email", email)
                        .apply()

                    Toast.makeText(context, "Welcome $username", Toast.LENGTH_LONG).show()

                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                } else {
                    val message = response?.optString("message")
                    Toast.makeText(context, "$message", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {
                Log.e("LOGIN_ERROR", responseString ?: "Unknown error", throwable)
                Toast.makeText(context, "Error: $responseString", Toast.LENGTH_LONG).show()
            }
        })
    }

    // LOAD PRODUCTS
    fun loadProducts(url: String, recyclerView: RecyclerView, progressBar: ProgressBar? = null) {
        progressBar?.visibility = View.VISIBLE

        recyclerView.layoutManager = GridLayoutManager(context, 2)

        val client = AsyncHttpClient()

        client.get(context, url, null, "application/json", object : JsonHttpResponseHandler() {

            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONArray
            ) {
                progressBar?.visibility = View.GONE

                val productList = ProductAdapter.fromJsonArray(response)
                recyclerView.adapter = ProductAdapter(productList)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {
                progressBar?.visibility = View.GONE
                Log.e("PRODUCT_ERROR", responseString ?: "Unknown error", throwable)
                Toast.makeText(context, "Failed to load products", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // GET
    fun get(api: String, callBack: CallBack) {
        val client = AsyncHttpClient()

        client.get(context, api, null, "application/json",
            object : JsonHttpResponseHandler() {

                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONArray
                ) {
                    callBack.onSuccess(response)
                }

                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    callBack.onSuccess(response)
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseString: String?,
                    throwable: Throwable?
                ) {
                    callBack.onFailure(responseString)
                }
            })
    }

    // PUT
    fun put(api: String, jsonData: JSONObject) {
        Toast.makeText(context, "Please Wait for response", Toast.LENGTH_LONG).show()

        val client = AsyncHttpClient()
        val body = StringEntity(jsonData.toString())

        client.put(context, api, body, "application/json",
            object : JsonHttpResponseHandler() {

                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    Toast.makeText(context, "Response $response", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    throwable: Throwable?,
                    errorResponse: JSONObject?
                ) {
                    Log.e("PUT_ERROR", "Error", throwable)
                    Toast.makeText(context, "Error Occurred", Toast.LENGTH_LONG).show()
                }
            })
    }

    // DELETE
    fun delete(api: String, jsonData: JSONObject) {
        Toast.makeText(context, "Please Wait for response", Toast.LENGTH_LONG).show()

        val client = AsyncHttpClient()
        val body = StringEntity(jsonData.toString())

        client.delete(context, api, body, "application/json",
            object : JsonHttpResponseHandler() {

                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    Toast.makeText(context, "Response $response", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    throwable: Throwable?,
                    errorResponse: JSONObject?
                ) {
                    Log.e("DELETE_ERROR", "Error", throwable)
                    Toast.makeText(context, "Error Occurred", Toast.LENGTH_LONG).show()
                }
            })
    }

    interface CallBack {
        fun onSuccess(result: JSONArray?)
        fun onSuccess(result: JSONObject?)
        fun onFailure(result: String?)
    }
}