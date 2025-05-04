package com.dev.eatsight.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.posapp.R
import com.example.posapp.databinding.ActivityDashboardBinding
import com.example.posapp.util.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    @Inject
    lateinit var sessionManager: SessionManager

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupOutletSpinner()
        setupDatePicker()
        setupBottomNavigation()

        // Initialize with current date
        updateDisplayDate()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        binding.tvToolbarTitle.text = "Dashboard"
    }

    private fun setupOutletSpinner() {
        // Example outlet list - replace with actual data from your backend
        val outlets = listOf("All Outlets", "Outlet 1", "Outlet 2", "Outlet 3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, outlets)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOutlet.adapter = adapter
    }

    private fun setupDatePicker() {
        binding.datePicker.text = dateFormat.format(calendar.time)

        binding.datePicker.setOnClickListener {
            showDatePicker()
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_dashboard -> {
                    // Already on dashboard
                    true
                }
                R.id.navigation_products -> {
                    // Navigate to products screen
                    // startActivity(Intent(this, ProductsActivity::class.java))
                    true
                }
                R.id.navigation_transactions -> {
                    // Navigate to transactions screen
                    // startActivity(Intent(this, TransactionsActivity::class.java))
                    true
                }
                R.id.navigation_settings -> {
                    // Navigate to settings screen
                    // startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Set dashboard as selected
        binding.bottomNavigation.selectedItemId = R.id.navigation_dashboard
    }

    private fun showDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                updateDisplayDate()
                loadTransactionsForDate()
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun updateDisplayDate() {
        binding.datePicker.text = dateFormat.format(calendar.time)
    }

    private fun loadTransactionsForDate() {
        // This would fetch transactions for the selected date from your API
        // For now, we'll just show placeholder data
        binding.tvTransactionCount.text = "0"
        binding.tvGrossSales.text = "Rp 0"
        binding.tvNetSales.text = "Rp 0"
        binding.tvNoTransactions.visibility = android.view.View.VISIBLE
    }
}