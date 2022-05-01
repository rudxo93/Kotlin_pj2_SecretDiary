package com.example.secretdiary

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {

    // MainActivity 생성 시점에 뷰가 전부 생성되지 않으므로 lazy 사용
    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1).apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2).apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val numberPicker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3).apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val btnOpen: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btnOpen)
    }

    private val btnChangePw: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btnChangePw)
    }

    private var changePasswordMode = false // 비밀번호 변경모드 상태

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 호출해서 lazy초기화 호출되도록 한다.
        numberPicker1
        numberPicker2
        numberPicker3

        // 다이어리 열기 버튼
        btnOpen.setOnClickListener {

            // 비밀번호 데이터 저장에 사용될 수 있는 방식
            // 1. 로컬 db
            // 2. 파일 (shared preferences 등) <- 여기서는 이 방식을 사용한다.
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            // 이 앱에서만 사용할 것이기 때문에 MODE_PRIVATE

            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            // 패스워드가 일치한다면?
            if(passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                // 다이어리 엑티비티 실행
                startActivity(Intent(this, DiaryActivity::class.java))
            } else { // 패스워드가 일치하지 않다면 -> 에러메세지를 보여주자
                showErrorAlertDialog()
            }
        }
    }

    private fun showErrorAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 틀렸습니다!")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}