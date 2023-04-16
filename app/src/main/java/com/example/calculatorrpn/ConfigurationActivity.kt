package com.example.calculatorrpn

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calculatorrpn.databinding.ActivityConfigurationBinding
import com.example.calculatorrpn.databinding.ActivityMainBinding


class ConfigurationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfigurationBinding
    private lateinit var mainBinding: ActivityMainBinding

    val data = Intent()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigurationBinding.inflate(layoutInflater)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.precision.maxValue = 15
        binding.precision.minValue = 0
        binding.precision.value = 5

        binding.rbtnColorBlue.setOnClickListener(){
            data.putExtra("color","blue")
        }
        binding.rbtnColorGreen.setOnClickListener(){
            data.putExtra("color","green")
        }
        binding.rbtnColorWhite.setOnClickListener(){
            data.putExtra("color","white")
        }
        binding.rbtnColorRed.setOnClickListener(){
            data.putExtra("color","red")
        }
        binding.rbtnColorGrey.setOnClickListener(){
            data.putExtra("color","grey")
        }
        binding.rbtnColorYellow.setOnClickListener(){
            data.putExtra("color","yellow")
        }


        binding.rbtnColorBlueK.setOnClickListener(){
            data.putExtra("keyb","blue")
        }
        binding.rbtnColorGreenK.setOnClickListener(){
            data.putExtra("keyb","green")
        }
        binding.rbtnColorWhiteK.setOnClickListener(){
            data.putExtra("keyb","white")
        }
        binding.rbtnColorRedK.setOnClickListener(){
            data.putExtra("keyb","red")
        }
        binding.rbtnColorGreyK.setOnClickListener(){
            data.putExtra("keyb","grey")
        }
        binding.rbtnColorYellowK.setOnClickListener(){
            data.putExtra("keyb","yellow")
        }
        binding.precision.setOnValueChangedListener { picker, oldVal, newVal ->
            val prec = binding.precision.value.toString()
            data.putExtra("precision",prec)
        }

        binding.button.setOnClickListener(){
            setResult(Activity.RESULT_OK, data)
            this.finish()
        }

    }

}