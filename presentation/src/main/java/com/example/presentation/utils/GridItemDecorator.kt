package com.example.presentation.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class GridItemDecorator(
    private val margin: Int,
    private val removeTopMargin: Boolean = false,
    private val removeBottomMargin: Boolean = false
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val gridLayoutManager = parent.layoutManager as GridLayoutManager
        val spanCount = gridLayoutManager.spanCount
        val marginInPx = dpToPx(margin)
        if (gridLayoutManager.spanSizeLookup.getSpanSize(position) != spanCount) {
            when (gridLayoutManager.spanSizeLookup.getSpanIndex(position, spanCount)) {
                0 -> outRect.right = marginInPx / 2
                spanCount.dec() -> outRect.left = marginInPx / 2
                else -> {
                    outRect.right = marginInPx / 2
                    outRect.left = marginInPx / 2
                }
            }
        }

        if (!removeBottomMargin) outRect.bottom = marginInPx
        if (!removeTopMargin) outRect.bottom = marginInPx
    }
}