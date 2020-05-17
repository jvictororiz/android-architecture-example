package com.now.desafio.android.util.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


fun EditText.insertMask(mask: String){
    addTextChangedListener(Mask.mask(mask, this))

}
object Mask {
    fun mask(mask: String, etCpf: EditText): TextWatcher {
        return object : TextWatcher {
            var isUpdating: Boolean = false
            var oldString: String = ""

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = replaceChars(s.toString())
                var cpfWithMask = ""

                if (count == 0) // is deleting
                    isUpdating = true

                if (isUpdating) {
                    oldString = str
                    isUpdating = false
                    return
                }

                var i = 0
                for (m: Char in mask.toCharArray()) {
                    if (m != '#' && str.length > oldString.length) {
                        cpfWithMask += m
                        continue
                    }
                    try {
                        cpfWithMask += str[i]
                    } catch (e: Exception) {
                        break
                    }
                    i++
                }

                isUpdating = true
                etCpf.setText(cpfWithMask)
                etCpf.setSelection(cpfWithMask.length)
            }

            override fun afterTextChanged(editable: Editable) {

            }
        }
    }

    private fun replaceChars(cpfFull: String): String {
        return cpfFull.replace(".", "").replace("-", "")
                .replace("(", "").replace(")", "")
                .replace("/", "").replace(" ", "")
                .replace("*", "")
    }

}
