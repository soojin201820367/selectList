package com.aa.selectlist.adapten.item

import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aa.selectlist.R
import java.lang.ref.WeakReference

class itemViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    private var view: WeakReference<View> = WeakReference(itemView)
    private var textView: TextView? =null
    private var imageButtonSelection: ImageButton? = null

    lateinit var data:ItemData

    var isSelectionMode = false

    var onSelection: (() -> Unit?)? =null

    init{
        findView()
        setListener()
    }
    private fun findView(){
        textView = view.get()?.findViewById(R.id.textView)
        imageButtonSelection = view.get()?.findViewById(R.id.imageButtonSelection)
    }
    private fun setListener() {
        view.get()?.setOnClickListener{

            if(isSelectionMode){

                data.isSelected = !data.isSelected
                updateSelection()

                onSelection?.let{
                    it()
                }

            }
            else{
                Toast.makeText(it.context, data.content, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateView() {
        textView?.text = "${data.content}\n\n${data.id}"
        imageButtonSelection?.visibility = if(isSelectionMode){
            View.VISIBLE
        }
        else{
            View.GONE
        }

        if (!isSelectionMode) {

            data.isSelected = false
        }
        updateSelection()
    }

    private fun updateSelection() {
        imageButtonSelection?.setImageResource(if(data.isSelected) {
            R.mipmap.ti_check
        }
        else{
            R.mipmap.ti_un_check
        })
    }

}