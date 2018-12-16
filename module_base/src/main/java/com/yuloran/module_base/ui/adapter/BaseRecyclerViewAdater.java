package com.yuloran.module_base.ui.adapter;

import android.view.ViewGroup;

import com.yuloran.module_base.ui.adapter.viewholder.BindingViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * [Description]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/16 18:06
 *
 * @since 1.0.0
 */
public class BaseRecyclerViewAdater extends RecyclerView.Adapter<BindingViewHolder>
{
    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return 0;
    }
}
