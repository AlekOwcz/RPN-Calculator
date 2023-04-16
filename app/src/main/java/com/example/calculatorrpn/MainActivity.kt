package com.example.calculatorrpn

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.calculatorrpn.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val stack: Stack<Double> = Stack()
    private var stackPtr = -1
    private var canDecimal = true
    private var idle = true
    private var neg = false
    private var decimalPlace = 5
    val REQUEST_CODE = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateStacks()
        //numbers
        binding.btnOne.setOnClickListener(){
            digitClick(binding.btnOne.text)
        }
        binding.btnTwo.setOnClickListener(){
            digitClick(binding.btnTwo.text)
        }
        binding.btnThree.setOnClickListener(){
            digitClick(binding.btnThree.text)
        }
        binding.btnFour.setOnClickListener(){
            digitClick(binding.btnFour.text)
        }
        binding.btnFive.setOnClickListener(){
            digitClick(binding.btnFive.text)
        }
        binding.btnSix.setOnClickListener(){
            digitClick(binding.btnSix.text)
        }
        binding.btnSeven.setOnClickListener(){
            digitClick(binding.btnSeven.text)
        }
        binding.btnEight.setOnClickListener(){
            digitClick(binding.btnEight.text)
        }
        binding.btnNine.setOnClickListener(){
            digitClick(binding.btnNine.text)
        }
        binding.btnZero.setOnClickListener(){
            digitClick(binding.btnZero.text)
        }
        //CLEAR ALL
        binding.btnAllclear.setOnClickListener(){
            clearAll()
            updateStacks()
        }
        //UNDO
        binding.btnUndo.setOnClickListener(){
            if(!idle) undo()
        }
        //DROP
        binding.btnDrop.setOnClickListener(){
            if(stack.isNotEmpty()) {
                stackDrop()
                stackPtr -= 1
                updateStacks()
            }
        }
        //SWAP
        binding.btnSwap.setOnClickListener(){
            if(stackPtr >=1) {
                stackSwap()
                updateStacks()
            }
        }
        //ROOT
        binding.btnRoot.setOnClickListener(){
            if(stackPtr >= 0 && stack.lastElement() >= 0) {
                stackRoot()
                updateStacks()
            }
        }
        //EXPONENT
        binding.btnExponent.setOnClickListener(){
            if(stackPtr >= 1) {
                stackExponent()
                updateStacks()
            }
        }
        //DIVISION
        binding.btnDivide.setOnClickListener(){
            if(stackPtr >= 1) {
                stackDivide()
                updateStacks()
            }
        }
        //MULTIPLICATION
        binding.btnMultiply.setOnClickListener(){
            if(stackPtr >= 1) {
                stackMultiply()
                updateStacks()
            }
        }
        //ADDITION
        binding.btnPlus.setOnClickListener(){
            if(stackPtr >= 1) {
                stackAdd()
                updateStacks()
            }
        }
        //SUBTRACTION
        binding.btnMinus.setOnClickListener(){
            if(stackPtr >= 1) {
                stackSubtract()
                updateStacks()
            }
        }
        //ENTERING VALUE
        binding.btnEnter.setOnClickListener(){
            if(!idle) {
                enterVal()
            } else {
                val a = stack.pop()
                stack.push(a)
                stack.push(a)
                stackPtr += 1
            }
            updateStacks()
        }
        //DECIMAL POINT
        binding.btnDecimal.setOnClickListener(){
            if(canDecimal) addDecimal()
        }
        //NUMBER SIGN
        binding.btnSign.setOnClickListener(){
            changeSign()
        }
        binding.btnSettings.setOnClickListener(){
            openSettings()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if(data != null){
                if(data.hasExtra("color")){
                    when(data.extras?.getString("color")){
                        "blue" -> {
                            setDisplay(R.color.bluedisp)
                        }
                        "white" -> {
                            setDisplay(R.color.pure_white)
                        }
                        "green" -> {
                            setDisplay(R.color.green)
                        }
                        "grey" -> {
                            setDisplay(R.color.grey)
                        }
                        "red" -> {
                            setDisplay(R.color.reddisp)
                        }
                        "yellow" -> {
                            setDisplay(R.color.yellow)
                        }
                    }
                }
                if(data.hasExtra("keyb")){
                    when(data.extras?.getString("keyb")){
                        "blue" -> {
                           setKeyboard(R.color.bluedisp)
                        }
                        "white" -> {
                            setKeyboard(R.color.white)
                        }
                        "green" -> {
                            setKeyboard(R.color.green)
                        }
                        "grey" -> {
                            setKeyboard(R.color.grey)
                        }
                        "red" -> {
                            setKeyboard(R.color.reddisp)
                        }
                        "yellow" -> {
                            setKeyboard(R.color.yellow)
                        }
                    }

                }
                if(data.hasExtra("precision")){
                    decimalPlace = data.extras?.getString("precision")!!.toInt()
                    updateStacks()
                }
            }
        }
    }

    private fun setKeyboard(i : Int){
        binding.btnOne.setBackgroundResource(i)
        binding.btnTwo.setBackgroundResource(i)
        binding.btnThree.setBackgroundResource(i)
        binding.btnFour.setBackgroundResource(i)
        binding.btnFive.setBackgroundResource(i)
        binding.btnSix.setBackgroundResource(i)
        binding.btnSeven.setBackgroundResource(i)
        binding.btnEight.setBackgroundResource(i)
        binding.btnNine.setBackgroundResource(i)
        binding.btnZero.setBackgroundResource(i)
        binding.btnSign.setBackgroundResource(i)
        binding.btnDecimal.setBackgroundResource(i)
        binding.btnDrop.setBackgroundResource(i)
        binding.btnSwap.setBackgroundResource(i)
        binding.btnUndo.setBackgroundResource(i)
        binding.btnAllclear.setBackgroundResource(i)
        binding.btnSettings.setBackgroundResource(i)
        binding.btnExponent.setBackgroundResource(i)
        binding.btnRoot.setBackgroundResource(i)
        binding.btnMultiply.setBackgroundResource(i)
        binding.btnDivide.setBackgroundResource(i)
        binding.btnPlus.setBackgroundResource(i)
        binding.btnMinus.setBackgroundResource(i)

    }

    private fun setDisplay(i: Int){
        binding.RPNDisplay.setBackgroundResource(i)
        binding.stackOne.setBackgroundResource(i)
        binding.stackTwo.setBackgroundResource(i)
        binding.stackThree.setBackgroundResource(i)
        binding.stackFour.setBackgroundResource(i)
        binding.stackOneNum.setBackgroundResource(i)
        binding.stackTwoNum.setBackgroundResource(i)
        binding.stackThreeNum.setBackgroundResource(i)
        binding.stackFourNum.setBackgroundResource(i)
    }


    fun openSettings() {
        val i = Intent(this, ConfigurationActivity::class.java)
        startActivityForResult(i,REQUEST_CODE)
    }

    private fun stackSwap() {
        val a = stack.pop()
        val b = stack.pop()
        stack.push(a)
        stack.push(b)
    }

    private fun stackRoot() {
        val a = stack.pop()
        stack.push(sqrt(a))
    }

    private fun stackExponent() {
        val a = stack.pop()
        val b = stack.pop()
        stack.push(b.pow(a))
        stackPtr -= 1
    }

    private fun stackDivide() {
        val a = stack.pop()
        val b = stack.pop()
        stack.push(b/a)
        stackPtr -= 1
    }

    private fun stackMultiply() {
        val a = stack.pop()
        val b = stack.pop()
        stack.push(b*a)
        stackPtr -= 1
    }

    private fun stackAdd() {
        val a = stack.pop()
        val b = stack.pop()
        stack.push(b+a)
        stackPtr -= 1
    }

    private fun stackSubtract() {
        val a = stack.pop()
        val b = stack.pop()
        stack.push(b-a)
        stackPtr -= 1
    }

    private fun stackDrop() {
        stack.pop()
    }

    private fun enterVal() { //////////////////////////
        stackPtr += 1
        var num = ""
        for(ch in binding.stackOne.text){
            num += ch
        }
        stack.push(num.toDouble())
        canDecimal = true
        idle = true
    }

    private fun changeSign() {
        val a = binding.stackOne.text
        val len = binding.stackOne.length()
        if(len > 0 && !idle) {
            if (neg) {
                binding.stackOne.text = binding.stackOne.text.subSequence(1, len)
                neg = false
            } else {
                binding.stackOne.text = "-$a"
                neg = true
            }
        } else {
            if(stack.size > 0) {
                var tmp = stack.pop()
                tmp *= -1
                stack.push(tmp)
                updateStacks()
            }
        }
    }

    private fun updateStacks() {
        //stack 4
        if(idle) {
            if (stackPtr >= 3) {
                binding.stackFour.text = stack[stackPtr - 3].round(decimalPlace).toString()
            } else {
                binding.stackFour.text = ""
            }
            binding.stackFourNum.text = "4:"
            //stack 3
            if (stackPtr >= 2) {
                binding.stackThree.text = stack[stackPtr - 2].round(decimalPlace).toString()
            } else {
                binding.stackThree.text = ""
            }
            binding.stackThreeNum.text = "3:"
            //stack 2
            if (stackPtr >= 1) {
                binding.stackTwo.text = stack[stackPtr - 1].round(decimalPlace).toString()
            } else {
                binding.stackTwo.text = ""
            }
            binding.stackTwoNum.text = "2:"
            //stack 1
            if (stackPtr >= 0) {
                //binding.stackOne.text = stack[stackPtr].toString()
                binding.stackOne.text = stack[stackPtr].round(decimalPlace).toString()
            } else {
                binding.stackOne.text = ""
            }
            binding.stackOneNum.text = "1:"
        } else {
            if (stackPtr >= 2) {
                binding.stackFour.text = stack[stackPtr - 2].round(decimalPlace).toString()
            } else {
                binding.stackFour.text = ""
            }
            binding.stackFourNum.text = "3:"
            //stack 3
            if (stackPtr >= 1) {
                binding.stackThree.text = stack[stackPtr - 1].round(decimalPlace).toString()
            } else {
                binding.stackThree.text = ""
            }
            binding.stackThreeNum.text = "2:"
            //stack 2
            if (stackPtr >= 0) {
                binding.stackTwo.text = stack[stackPtr].round(decimalPlace).toString()
            } else {
                binding.stackTwo.text = ""
            }
            binding.stackTwoNum.text = "1:"
            binding.stackOneNum.text = "→"
            neg = false
            canDecimal = true
        }
    }

    private fun digitClick(n:CharSequence){
        if(idle) {
            idle = false
            binding.stackOne.text = ""
            updateStacks()
        }
        binding.stackOne.append(n)
    }

    private fun clearAll() {
        stack.clear()
        stackPtr = -1
        canDecimal = true
        binding.stackOne.text = ""
        idle = true
    }

    private fun undo() {
        val len = binding.stackOne.length()
        if(len > 0){
            if(binding.stackOne.text.endsWith('.')) canDecimal = true
            binding.stackOne.text = binding.stackOne.text.subSequence(0, len-1)
        }
    }

    private fun addDecimal(){
        canDecimal = false
        if(binding.stackOne.length() > 0) binding.stackOne.append(".")
        else binding.stackOne.append("0.")
    }
}