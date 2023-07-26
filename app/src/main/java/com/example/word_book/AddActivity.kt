package com.example.word_book

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.word_book.databinding.ActivityAddBinding
import com.google.android.material.chip.Chip

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        binding.addButton.setOnClickListener {
            add()
        }
    }

    private fun initViews() {
        val types = listOf(
            "명사", "동사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사"
        )
        binding.typeChipGroup.apply {
            types.forEach { text ->
                addView(createChip(text))
            }
        }
    }

    private fun createChip(text: String) : Chip{
        return Chip(this).apply {
            setText(text)
            isCheckable = true
            isClickable
        }
    }

    private fun add() {
        val text = binding.textInputEditText.text.toString()
        val mean = binding.meanTextInputEditText.text.toString()
        val type = findViewById<Chip>(binding.typeChipGroup.checkedChipId).text.toString()
        val word = Word(text , mean , type)

        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.insert(word)
            runOnUiThread{
                Toast.makeText(this , "저장을 완료했습니다." , Toast.LENGTH_SHORT).show()
                finish()
            }
            val intent = Intent().putExtra("isUpdated" , true)
            setResult(RESULT_OK , intent)
        }.start()


    }
}