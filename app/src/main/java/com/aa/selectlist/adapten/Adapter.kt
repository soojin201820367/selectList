package com.aa.selectlist.adapten
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.aa.selectlist.R
import com.aa.selectlist.adapten.item.ItemData
import com.aa.selectlist.adapten.item.itemViewHolder
import org.intellij.lang.annotations.JdkConstants

class Adapter: RecyclerView.Adapter<itemViewHolder>(){

    private var list: MutableList<ItemData> = mutableListOf()

    var onLoadMore: (() -> Unit)? =null
    private  var isSelectionMode: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_item,parent, false)
        return itemViewHolder(view)
    }

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {

        holder.data = list[position]
        holder.isSelectionMode = isSelectionMode
        holder.onSelection = {

            for(data in list) {
                if(data.isSelected){
                    Log.d("???", "선택한 데이터는 ${data.content} ${data.id}")
                }

            }
        }
        holder.updateView()

        if(position == list.size -1){
            onLoadMore?. let{
                it()
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun  reload(list : MutableList<ItemData>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list : MutableList<ItemData>){
        this.list.addAll(list)
        notifyItemChanged(this.list.size - list.size+1, list.size)

    }
    fun toggleSelecctionMode (isSelectionMode: Boolean) {
        this.isSelectionMode =isSelectionMode
        notifyDataSetChanged()
    }
}