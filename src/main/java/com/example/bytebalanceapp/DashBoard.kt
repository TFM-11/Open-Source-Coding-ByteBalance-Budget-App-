package com.example.bytebalanceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class DashBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        val btnAddExpense = findViewById<Button>(R.id.btnAddExpense)
        val btnViewExpenses = findViewById<Button>(R.id.btnViewExpenses)
        val btnAddCategory = findViewById<Button>(R.id.btnAddCategory)
        val btnSetGoals = findViewById<Button>(R.id.btnSetGoals)
        val btnSummary = findViewById<Button>(R.id.btnSummary)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        btnBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnAddExpense.setOnClickListener {
            startActivity(Intent(this, AddExpense::class.java))
        }

        btnViewExpenses.setOnClickListener {
            startActivity(Intent(this, ViewExpenses::class.java))
        }

        btnAddCategory.setOnClickListener {
            startActivity(Intent(this, AddCategory::class.java))
        }

        btnSetGoals.setOnClickListener {
            startActivity(Intent(this, SetBudgetGoals::class.java))
        }

        btnSummary.setOnClickListener {
            startActivity(Intent(this, ViewSummary::class.java))
        }
    }
}
