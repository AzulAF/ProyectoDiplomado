package com.azul.mod6prac1.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.azul.mod6prac1.R
import com.azul.mod6prac1.data.network.NetworkUtils
import com.azul.mod6prac1.databinding.ActivityMain2Binding
import com.azul.mod6prac1.ui.fragments.ArtistListFragment
import com.google.android.material.snackbar.Snackbar

//import com.azul.mod6prac1.ui.fragments.ItemListFragment

class ArtistListsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        checkNetworkAndNotify(this)
        //Mostramos el fragment inicial ItemsListFragment
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ArtistListFragment())
                .commit()
        }
    }

    fun checkNetworkAndNotify(context: Context) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            Toast.makeText(context, binding.root.context.getString(R.string.SinConexion), Toast.LENGTH_LONG).show()
        } else if (NetworkUtils.isUsingMobileData(context)) {
            Snackbar.make(
                (context as Activity).findViewById(android.R.id.content),
                binding.root.context.getString(R.string.DatosMoviles),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

}