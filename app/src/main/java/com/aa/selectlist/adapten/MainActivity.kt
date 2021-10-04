package com.aa.selectlist.adapten

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.aa.selectlist.R
import com.aa.selectlist.adapten.item.ItemData
import com.google.android.material.switchmaterial.SwitchMaterial
import java.util.*
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var switchMaterial: SwitchMaterial
    private lateinit var adapter: Adapter
    private lateinit var layoutmanager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       findView()
        setListener()
        initList()
        reload()

    }
    private fun findView(){
        swipeRefreshLayout =findViewById(R.id.swipeRefreshLayout)
        recyclerView=findViewById(R.id.recyclerView)
        switchMaterial=findViewById(R.id.switchMaterial)
    }
    private fun  setListener() {
       switchMaterial.setOnCheckedChangeListener{_, isChecked ->
        adapter.toggleSelecctionMode(isChecked)

       }
    }

    private fun initList(){
        layoutmanager = LinearLayoutManager(this)
        adapter = Adapter()
        adapter.onLoadMore = {
            loadMore()
        }
        swipeRefreshLayout.setOnRefreshListener {
            reload()
            swipeRefreshLayout.isRefreshing =false
        }
        recyclerView.layoutManager = layoutmanager
        recyclerView.adapter = adapter

    }
    private fun reload(){
        recyclerView.post{
            adapter.reload(createDummyData(0,15))
        }
    }
    private fun  loadMore(){
        recyclerView.post{
            adapter.loadMore(createDummyData(adapter.itemCount,10 ))
        }
    }


    private fun createDummyData(offset: Int, limit: Int): MutableList<ItemData> {
        val list = mutableListOf<ItemData>()

        var data: ItemData
        for (i in offset..(offset + limit)) {
            data = ItemData(
                id = UUID.randomUUID().toString().replace("-", ""),
                content = "content index $i"
            )
            list.add(data)
        }
        return list
    }
}