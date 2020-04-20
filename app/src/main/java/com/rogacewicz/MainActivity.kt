package com.rogacewicz

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar

import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : AppCompatActivity() {

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
