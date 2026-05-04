package com.crispus.znest

import android.os.Bundle
import android.widget.Button
import com.crispus.znest.ChatResponse
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import com.crispus.znest.Message
import retrofit2.Response

import com.crispus.znest.ChatRequest


class ChatSupportActivity : AppCompatActivity() {



    private lateinit var recyclerView: RecyclerView
    private lateinit var input: EditText
    private lateinit var sendBtn: Button

    private val messages = mutableListOf<Message>()
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatsupport)

        recyclerView = findViewById(R.id.chatRecycler)
        input = findViewById(R.id.messageInput)
        sendBtn = findViewById(R.id.sendBtn)

        adapter = ChatAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Default bot message
        messages.add(Message("Hello! How can I help you today?", false))
        adapter.notifyDataSetChanged()

        sendBtn.setOnClickListener {
            val text = input.text.toString().trim()

            if (text.isNotEmpty()) {

                // User message
                messages.add(Message(text, true))
                adapter.notifyItemInserted(messages.size - 1)
                recyclerView.scrollToPosition(messages.size - 1)
                input.text.clear()

                val request = ChatRequest(text)

                RetrofitClient.instance.sendMessage(request)
                    .enqueue(object : Callback<ChatResponse> {

                        override fun onResponse(
                            call: Call<ChatResponse>,
                            response: Response<ChatResponse>
                        ) {
                            val reply = if (response.isSuccessful) {
                                response.body()?.reply ?: "No response"
                            } else {
                                "Server error"
                            }

                            messages.add(Message(reply, false))
                            adapter.notifyItemInserted(messages.size - 1)
                            recyclerView.scrollToPosition(messages.size - 1)
                        }

                        override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                            messages.add(Message("Error: ${t.message}", false))
                            adapter.notifyItemInserted(messages.size - 1)
                            recyclerView.scrollToPosition(messages.size - 1)
                        }


                    })
            }

        }


    }

}
