package com.example.testmaterial3

import android.app.Activity
import android.os.Bundle

class TestActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.testmaterial3.test.R.layout.activity_test)
    }
}