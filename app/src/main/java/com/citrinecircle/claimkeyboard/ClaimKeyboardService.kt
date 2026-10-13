package com.citrinecircle.claimkeyboard

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.view.ViewGroup

class ClaimKeyboardService : InputMethodService() {
 private val rows = listOf(listOf("A","B"), listOf("C","D"), listOf("E","FF","G"), listOf("H","I","J"), listOf("K","M","N"), listOf("O","P","Q"))
 private fun dp(n:Int)=(n*resources.displayMetrics.density).toInt()
 override fun onCreateInputView(): View {
  val root=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(dp(4),dp(7),dp(4),dp(8)); setBackgroundColor(Color.rgb(28,36,44)) }
  for (labels in rows) {
   val row=LinearLayout(this).apply { orientation=LinearLayout.HORIZONTAL }
   for (label in labels) {
    val b=Button(this).apply {
     text=label; textSize=20f; setTextColor(Color.rgb(28,36,44)); typeface=Typeface.DEFAULT_BOLD
     isAllCaps=false; minHeight=0; minimumHeight=0; minWidth=0; minimumWidth=0
     background=GradientDrawable().apply { setColor(Color.rgb(242,232,211)); cornerRadius=dp(7).toFloat() }
     setOnClickListener { currentInputConnection?.commitText(label,1) }
    }
    row.addView(b,LinearLayout.LayoutParams(0,dp(47),1f).apply { setMargins(dp(3),dp(3),dp(3),dp(3)) })
   }
   root.addView(row,LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT))
  }
  val controls=LinearLayout(this).apply { orientation=LinearLayout.HORIZONTAL }
  fun control(label:String, weight:Float, action:()->Unit) {
   controls.addView(Button(this).apply { text=label; isAllCaps=false; textSize=16f; setOnClickListener { action() } }, LinearLayout.LayoutParams(0,dp(49),weight))
  }
  control("Switch",1f) { (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).showInputMethodPicker() }
  control("Delete",1f) { currentInputConnection?.deleteSurroundingText(1,0) }
  control("Space",1f) { currentInputConnection?.commitText(" ",1) }
  control("Send ↵",1.25f) {
   val action = currentInputEditorInfo?.imeOptions?.and(EditorInfo.IME_MASK_ACTION) ?: EditorInfo.IME_ACTION_NONE
   val connection = currentInputConnection
   if (action != EditorInfo.IME_ACTION_NONE && action != EditorInfo.IME_ACTION_UNSPECIFIED) {
    connection?.performEditorAction(action)
   } else {
    connection?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
    connection?.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
   }
  }
  root.addView(controls)
  return root
 }
}
