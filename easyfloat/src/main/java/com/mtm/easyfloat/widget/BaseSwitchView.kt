package com.mtm.easyfloat.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.RelativeLayout
import com.mtm.easyfloat.interfaces.OnTouchRangeListener

/**
 * @author: liuzhenfeng
 * @date: 2020/10/25  11:08
 * @Package: com.mtm.easyfloat.widget
 * @Description:
 */
abstract class BaseSwitchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    abstract fun setTouchRangeListener(event: MotionEvent, listener: OnTouchRangeListener? = null)

}