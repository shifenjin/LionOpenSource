package com.example.appkotlin.tools.recyclerView.test

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.view.View
import com.example.appkotlin.R
import com.example.appkotlin.tools.recyclerView.setSimpleAdapter
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.activity_recycler_view_item.view.*
import java.util.*

class RecyclerViewActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        // 数据
        val recyclerViewDataItemList = ArrayList<RecyclerViewDataItem>()
        for (i in 0..19) {
            val recyclerViewDataItem = RecyclerViewDataItem(R.mipmap.zhaoliyin)
            recyclerViewDataItemList.add(recyclerViewDataItem)
        }

        rv_test.layoutManager =
            androidx.recyclerview.widget.GridLayoutManager(this, 3)
        rv_test.setSimpleAdapter(recyclerViewDataItemList,
                R.layout.activity_recycler_view_item) {
            view: View, recyclerViewDataItem: RecyclerViewDataItem ->
                view.iv_icon.setImageResource(recyclerViewDataItem.iconRes)
        }
    }


}
