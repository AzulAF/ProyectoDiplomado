package com.azul.mod6prac1.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.azul.mod6prac1.R
import com.azul.mod6prac1.application.ItemsDPApp
import com.azul.mod6prac1.data.ItemRepository
import com.azul.mod6prac1.data.db.model.ItemEntity
import com.azul.mod6prac1.data.network.NetworkUtils
import com.azul.mod6prac1.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MarcadoresActivity : AppCompatActivity() {

    private var items: MutableList<ItemEntity> = mutableListOf()
    private lateinit var repository: ItemRepository
    private lateinit var itemsAdapter: ItemAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkNetworkAndNotify(this)
        repository = (application as ItemsDPApp).repository

        itemsAdapter = ItemAdapter{ selectedItem->
            //Registro de un item
            val dialog = ItemDialog(newItem = false, item = selectedItem, updateUI = {
                updateUI()
            }, message = { text ->
                message(text)
            })
            dialog.show(supportFragmentManager, "dialog2")
        }

        //El recyclerview
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(this@MarcadoresActivity)
            adapter = itemsAdapter
        }


        updateUI()
    }

    fun click(view: View) {
        val dialog = ItemDialog(updateUI = {
            updateUI()
        }, message = { text ->
            //Aquí va el mensaje
            message(text)

        })

        dialog.show(supportFragmentManager, "dialog1")

    }

    private fun message(text: String){
        Snackbar.make(
            binding.cl,
            text,
            Snackbar.LENGTH_SHORT
        )
            .setTextColor(getColor(R.color.white))
            .setBackgroundTint(getColor(R.color.snackbar))
            .show()
    }

    private fun updateUI(){
        lifecycleScope.launch {
            items = repository.getAllItems()
            binding.tvSinRegistros.visibility =
                if(items.isNotEmpty()) View.INVISIBLE else View.VISIBLE
            itemsAdapter.updateList(items)
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