package com.citrinecircle.claimkeyboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.view.inputmethod.InputMethodManager

class MainActivity : Activity() {
 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(32,48,32,24); gravity = Gravity.CENTER_HORIZONTAL }
  root.addView(TextView(this).apply { text = "Citrine Circle Claim Keyboard"; textSize = 24f })
  root.addView(TextView(this).apply { text = "1. Enable the keyboard in Android settings.\n2. Select it as your keyboard.\n3. Open TikTok and tap the comment field.\n\nUse the Switch key to return to your usual keyboard.\n\nThis keyboard has no internet permission and does not store anything you type."; textSize = 16f; setPadding(0,32,0,32) })
  root.addView(Button(this).apply { text = "1. Enable keyboard"; setOnClickListener { startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)) } })
  root.addView(Button(this).apply { text = "2. Choose keyboard"; setOnClickListener { (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).showInputMethodPicker() } })
  setContentView(root)
 }
}
