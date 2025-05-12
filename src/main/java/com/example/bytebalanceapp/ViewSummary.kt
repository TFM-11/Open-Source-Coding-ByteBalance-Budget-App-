package com.example.bytebalanceapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import androidx.appcompat.app.AlertDialog
import android.content.DialogInterface


class ViewSummary : AppCompatActivity() {

    private lateinit var db: Database
    private lateinit var rvSummary: RecyclerView
    private lateinit var tvDateRange: TextView
    private lateinit var btnBack: ImageButton
    private lateinit var btnSelectDateRange: TextView
    private lateinit var btnSelectMonth: Button  // Add the month select button

    private var startDate: String? = null
    private var endDate: String? = null
    private var selectedMonth: String? = null  // Store selected month

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_summary)

        db = Database(this)
        rvSummary = findViewById(R.id.rvSummary)
        tvDateRange = findViewById(R.id.tvDateRange)
        btnBack = findViewById(R.id.btnBack)
        btnSelectDateRange = findViewById(R.id.btnSelectDateRange)
        btnSelectMonth = findViewById(R.id.btnSelectMonth)  // Initialize the month select button

        tvDateRange.text = "Showing all time expenses"

        // Load all-time summary initially
        val summaryList = db.getCategoryTotals()
        rvSummary.layoutManager = LinearLayoutManager(this)
        rvSummary.adapter = ViewSummaryAdapter(summaryList)

        // Back Button
        btnBack.setOnClickListener {
            finish()
        }

        // Date Range Button
        btnSelectDateRange.setOnClickListener {
            showDateRangePicker()
        }

        // Select Month Button
        btnSelectMonth.setOnClickListener {
            showMonthPicker()
        }
    }

    private fun showDateRangePicker() {
        val calendar = Calendar.getInstance()

        // First Date Picker for Start Date
        DatePickerDialog(this, { _, startYear, startMonth, startDay ->
            startDate = String.format("%04d-%02d-%02d", startYear, startMonth + 1, startDay)

            // Second Date Picker for End Date
            DatePickerDialog(this, { _, endYear, endMonth, endDay ->
                endDate = String.format("%04d-%02d-%02d", endYear, endMonth + 1, endDay)

                tvDateRange.text = "Expenses from $startDate to $endDate"
                loadFilteredSummary()

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).apply {
                setTitle("Select End Date")
                show()
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).apply {
            setTitle("Select Start Date")
            show()
        }
    }

    private fun showMonthPicker() {
        val months = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )

        val selectedMonthIndex = months.indexOf(selectedMonth).takeIf { it >= 0 } ?: 0

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select a Month")
        builder.setSingleChoiceItems(months.toTypedArray(), selectedMonthIndex) { dialogInterface: DialogInterface, which: Int ->
            selectedMonth = months[which]
            tvDateRange.text = "Budget goals for $selectedMonth"
            loadFilteredSummaryForMonth(selectedMonth!!)
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }



    private fun loadFilteredSummary() {
        if (startDate != null && endDate != null) {
            val filteredSummaryList = db.getCategoryTotals(startDate!!, endDate!!)
            rvSummary.adapter = ViewSummaryAdapter(filteredSummaryList)
        }
    }

    private fun loadFilteredSummaryForMonth(month: String) {
        if (month.isNotEmpty()) {
            val budgetGoalList = db.getBudgetGoalsForMonth(month)  // You need to implement this method in your DB
            rvSummary.adapter = BudgetGoalAdapter(budgetGoalList)

            if (budgetGoalList.isEmpty()) {
                Toast.makeText(this, "No budget goals found for $month", Toast.LENGTH_SHORT).show()
            } else {
                rvSummary.adapter = BudgetGoalAdapter(budgetGoalList)
            }

        } else {
            Toast.makeText(this, "No month selected", Toast.LENGTH_SHORT).show()
        }
    }
}
