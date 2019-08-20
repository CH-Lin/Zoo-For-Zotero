package com.mickstarify.zooforzotero.SyncSetup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.mickstarify.zooforzotero.R
import com.mickstarify.zooforzotero.SyncSetup.ZoteroAPISetup.ZoteroAPISetup

class SyncSetupView : AppCompatActivity(), SyncSetupContract.View {
    override fun createUnsupportedAlert() {
        val alert = AlertDialog.Builder(this@SyncSetupView)
        alert.setIcon(R.drawable.ic_error_black_24dp)
        alert.setTitle("Unsupported Syncing Option")
        alert.setMessage("Sorry I have not yet implemented this syncing option yet!")
        alert.setPositiveButton("Ok") { _, _ ->
            val button_proceed = findViewById<Button>(R.id.btn_sync_proceed)
            button_proceed.isEnabled = false
        }
        alert.show()
    }

    override fun startZoteroAPIActivity() {
        val intent = Intent(this, ZoteroAPISetup::class.java)
        startActivity(intent)
    }

    var selected_provider = SyncOption.Unset

    override fun initUI() {
        val btnProceed = findViewById<Button>(R.id.btn_sync_proceed)
        val rg_cloudproviders = findViewById<RadioGroup>(R.id.radiogroup_cloudproviders);
        rg_cloudproviders.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.radio_systempicker -> selected_provider = SyncOption.LocalStorage
                R.id.radio_dropbox -> selected_provider = SyncOption.Dropbox
                R.id.radio_googledrive -> selected_provider = SyncOption.GoogleDrive
                R.id.radio_onedrive -> selected_provider = SyncOption.Onedrive
                R.id.radio_zotero -> selected_provider = SyncOption.ZoteroAPI
                else -> throw Exception("Error, not sure what Radiobox was pressed")
            };
            btnProceed.isEnabled = true
        }

        btnProceed.setOnClickListener {
            if (selected_provider != SyncOption.Unset) {
                presenter.selectSyncSetup(selected_provider)
            }
        }

    }

    private lateinit var presenter: SyncSetupPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync_setup)
//        setSupportActionBar(toolbar)

        presenter = SyncSetupPresenter(this)
    }

}