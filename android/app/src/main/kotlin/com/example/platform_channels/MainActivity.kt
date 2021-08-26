package com.example.platform_channels

import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import io.flutter.plugin.common.EventChannel

class MainActivity: FlutterActivity() {

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine){
        EventChannel(flutterEngine.dartExecutor.binaryMessenger, "locationStatusStream")
                .setStreamHandler(LocationStreamHandler(this))
        super.configureFlutterEngine(flutterEngine)

    }

    private  fun getLevel():Int {
        val batt:Int
        if(VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP){
            val battManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            batt = battManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

        }else {
            val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            batt = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL,-1)*100 / intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1)
        }
        return batt
    }
}
