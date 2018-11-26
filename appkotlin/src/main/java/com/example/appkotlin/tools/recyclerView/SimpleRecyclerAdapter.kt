package com.example.appkotlin.tools.recyclerView

import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SimpleRecyclerAdapter<D>(dataList: List<D>, layoutRes: Int, onBindItem: (itemView: View, data: D) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mDataList = dataList
    private val mLayoutRes = layoutRes
    private val mOnBindItem = onBindItem

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(mLayoutRes, viewGroup, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
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
fun <D> RecyclerView.setSimpleAdapter(@NonNull dataList: List<D>,
                                      @NonNull @LayoutRes layoutRes: Int,
                                      @NonNull onBindItem: (itemView: View, data: D) -> Unit) {
    this.adapter = SimpleRecyclerAdapter<D>(dataList, layoutRes, onBindItem)
}
