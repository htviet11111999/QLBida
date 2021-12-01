package com.example.bida

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class BidaSplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
           var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
}