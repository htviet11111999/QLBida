package com.example.bida.ChatBot

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bida.Menu_KH
import com.example.bida.R
import com.example.bida.fragment_kh.QRCode
import kotlinx.android.synthetic.main.chatbot.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ChatMain : AppCompatActivity() {
    private val adapterChatBot = AdapterChatBot()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatbot)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.5:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(APIService::class.java)

        rvChatList.layoutManager = LinearLayoutManager(this)
        rvChatList.adapter = adapterChatBot
        btnThoat_chat.setOnClickListener{
            val intent = Intent(this, Menu_KH::class.java)
            intent.putExtra("number", Menu_KH.sdt)
            startActivity(intent)
        }
        btnSend.setOnClickListener {
            if(etChat.text.isNullOrEmpty()){
                Toast.makeText(this@ChatMain, "Please enter a text", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            adapterChatBot.addChatToList(ChatModel(etChat.text.toString()))
            apiService.chatWithTheBit(etChat.text.toString()).enqueue(call)
            etChat.text.clear()
        }
    }

    private val call = object  : Callback<ChatResponse>{
        override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
            if(response.isSuccessful &&  response.body()!= null){
                adapterChatBot.addChatToList(ChatModel(response.body()!!.chatBotReply, true))
            }else{
                Toast.makeText(this@ChatMain, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }
        override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
            Toast.makeText(this@ChatMain, "That bai"+t.message, Toast.LENGTH_LONG).show()
        }

    }
}