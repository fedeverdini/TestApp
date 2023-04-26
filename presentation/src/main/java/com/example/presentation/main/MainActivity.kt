package com.example.presentation.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.presentation.R
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.group.GroupsFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph)
    }

    override fun onResume() {
        setSupportActionBar(binding.toolbar)
        super.onResume()
    }

    fun setToolBarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.teamsFragment -> showExitDialog()
            R.id.groupFragment -> navController.navigate(
                GroupsFragmentDirections.fromGroupFragmentToTeamsFragmentAction()
            )

            else -> navController.popBackStack()
        }
    }

    private fun showExitDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.close_app_title))
            .setMessage(getString(R.string.close_app_confirmation_message))
            .setPositiveButton(
                getString(R.string.yes),
                DialogInterface.OnClickListener { _, _ -> finish() })
            .setNegativeButton(
                getString(R.string.no),
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                })
            .show()
    }
}