package com.example.appkotlin.tools.recyclerView

import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SimpleRecyclerAdapter<D>(dataList: List<D>, layoutRes: Int, onBindItem: (itemView: View, data: D) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private var mDataList = dataList
    private val mLayoutRes = layoutRes
    private val mOnBindItem = onBindItem

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(mLayoutRes, viewGroup, false)
        return object : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {}
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, i: Int) {
        mOnBindItem.invoke(viewHolder.itemView, mDataList[i])
    }
}

/**
 * 设置RecyclerView的Adapter
 *
 * 仅支持单个数据类型
 *
 * @param dataList 数据源
 * @param layoutRes itemView控件 layout资源文件
 * @param onBindItem itemView控件与数据 绑定回调
 */
fun <D> androidx.recyclerview.widget.RecyclerView.setSimpleAdapter(@NonNull dataList: List<D>,
                                                                                      @NonNull @LayoutRes layoutRes: Int,
                                                                                      @NonNull onBindItem: (itemView: View, data: D) -> Unit) {
    this.adapter = SimpleRecyclerAdapter<D>(dataList, layoutRes, onBindItem)
}
