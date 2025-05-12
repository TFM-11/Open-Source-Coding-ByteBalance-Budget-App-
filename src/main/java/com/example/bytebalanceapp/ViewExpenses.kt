package com.example.bytebalanceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.app.DatePickerDialog
import java.util.*

class ViewExpenses : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemExpenseAdapter: ItemExpense
    private lateinit var database: Database
    private lateinit var backButton: ImageButton
    private lateinit var filterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)

        database = Database(this)

        recyclerView = findViewById(R.id.rvExpenses)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val expenses = database.getAllExpenses().toMutableList()
        itemExpenseAdapter = ItemExpense(expenses)
        recyclerView.adapter = itemExpenseAdapter

        // Go back to DashboardActivity
        backButton = findViewById(R.id.btnBack)
        backButton.setOnClickListener {
            val intent = Intent(this, DashBoard::class.java)
            startActivity(intent)
            finish()
        }

        // Date range selection (basic logic using 2 DatePickers)
        filterButton = findViewById(R.id.btnSelectDateRange)
        filterButton.setOnClickListener {
            showDateRangePicker()
        }
    }

    private fun showDateRangePicker() {
        val calendar = Calendar.getInstance()

        // Start Date Picker
        DatePickerDialog(this, { _, startYear, startMonth, startDay ->
            val startDate = "${startYear}-${String.format("%02d", startMonth + 1)}-${String.format("%02d", startDay)}"

            // End Date Picker
            DatePickerDialog(this, { _, endYear, endMonth, endDay ->
                val endDate = "${endYear}-${String.format("%02d", endMonth + 1)}-${String.format("%02d", endDay)}"

                val filteredExpenses = database.getExpensesByDateRange(startDate, endDate)
                itemExpenseAdapter.updateExpenses(filteredExpenses)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }
}
