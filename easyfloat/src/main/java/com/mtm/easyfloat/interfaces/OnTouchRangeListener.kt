package com.mtm.easyfloat.interfaces

import com.mtm.easyfloat.widget.BaseSwitchView

/**
 * @author: liuzhenfeng
 * @date: 2020/10/25  20:25
 * @Package: com.mtm.easyfloat.interfaces
 * @Description: 区域触摸事件回调
 */
interface OnTouchRangeListener {

    /**
     * 手指触摸到指定区域
     */
    fun touchInRange(inRange: Boolean, view: BaseSwitchView)

    /**
     * 在指定区域抬起手指
     */
    fun touchUpInRange()

}