package com.mtm.easyfloat.example.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.mtm.easyfloat.EasyFloat
import com.mtm.easyfloat.enums.ShowPattern
import com.mtm.easyfloat.enums.SidePattern
import com.mtm.easyfloat.example.R
import com.mtm.easyfloat.example.databinding.ActivityMainBinding
import com.mtm.easyfloat.example.logger
import com.mtm.easyfloat.example.startActivity
import com.mtm.easyfloat.example.widget.*
import com.mtm.easyfloat.interfaces.OnPermissionResult
import com.mtm.easyfloat.interfaces.OnTouchRangeListener
import com.mtm.easyfloat.permission.PermissionUtils
import com.mtm.easyfloat.utils.DragUtils
import com.mtm.easyfloat.widget.BaseSwitchView
import kotlin.math.max


class MainActivity : BaseActivity(), View.OnClickListener {

    companion object {
        private const val TAG_1 = "TAG_1"
        private const val TAG_2 = "TAG_2"
        private const val TAG_3 = "TAG_3"
        private const val TAG_4 = "TAG_4"
    }
    private lateinit var binding : ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.open1.setOnClickListener(this)
        binding.open2.setOnClickListener(this)
        binding.open3.setOnClickListener(this)
        binding.open4.setOnClickListener(this)

        binding.hide1.setOnClickListener(this)
        binding.hide2.setOnClickListener(this)
        binding. hide3.setOnClickListener(this)
        binding. hide4.setOnClickListener(this)

        binding.show1.setOnClickListener(this)
        binding.show2.setOnClickListener(this)
        binding.show3.setOnClickListener(this)
        binding.show4.setOnClickListener(this)

        binding.dismiss1.setOnClickListener(this)
        binding. dismiss2.setOnClickListener(this)
        binding.dismiss3.setOnClickListener(this)
        binding.dismiss4.setOnClickListener(this)

        binding.openSecond.setOnClickListener(this)
        binding.openSwipeTest.setOnClickListener(this)
        binding.openBorderTest.setOnClickListener(this)

        // ??????activity???onCreate???????????????
//        showActivityFloat(TAG_1)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.open1 -> showActivityFloat(TAG_1)
            binding.hide1 -> EasyFloat.hide(TAG_1)
            binding.show1 -> EasyFloat.show(TAG_1)
            binding.dismiss1 -> EasyFloat.dismiss(TAG_1)

            binding.open2 -> showActivity2(TAG_2)
            binding.hide2 -> EasyFloat.hide(TAG_2)
            binding.show2 -> EasyFloat.show(TAG_2)
            binding.dismiss2 -> EasyFloat.dismiss(TAG_2)

            // ????????????????????????????????????????????????????????????????????????
            binding.open3 -> checkPermission()
            binding.hide3 -> EasyFloat.hide()
            binding.show3 -> EasyFloat.show()
            binding.dismiss3 -> EasyFloat.dismiss()

            binding. open4 -> checkPermission(TAG_4)
            binding.hide4 -> EasyFloat.hide(TAG_4)
            binding.show4 -> EasyFloat.show(TAG_4)
            binding.dismiss4 -> EasyFloat.dismiss(TAG_4)

            binding.openSecond -> startActivity<SecondActivity>(this)
            binding.openSwipeTest -> startActivity<SwipeTestActivity>(this)
            binding.openBorderTest -> startActivity<BorderTestActivity>(this)

            else -> return
        }
    }

    /**
     * ??????Callback??????
     */
    @SuppressLint("SetTextI18n")
    private fun showActivityFloat(tag: String) {
        EasyFloat.with(this)
            .setSidePattern(SidePattern.RESULT_HORIZONTAL)
            .setImmersionStatusBar(true)
            .setGravity(Gravity.END, 0, 10)
            // ??????View????????????????????????????????????MyCustomView(this)???R.layout.float_custom
            .setLayout(R.layout.float_img)
            .setTag(TAG_1)
            .registerCallback {
                // ???????????????view?????????????????????setLayout??????view??????
                createResult { isCreated, msg, _ ->
                    toast("isCreated: $isCreated")
                    logger.e("DSL:  $isCreated   $msg")
                }

                show { toast("show") }

                hide { toast("hide") }

                dismiss { toast("dismiss") }

//                touchEvent { view, event ->
//                    if (event.action == MotionEvent.ACTION_DOWN) {
//                        view.findViewById<ImageView>(R.id.image).apply {
//                            text = "???????????????"
//                            setBackgroundResource(R.drawable.corners_green)
//                        }
//                    }
//                }
//
//                drag { view, motionEvent ->
//                    view.findViewById<ImageView>(R.id.image).apply {
//                        text = "????????????..."
//                        setBackgroundResource(R.drawable.corners_red)
//                    }
////                    DragUtils.registerDragClose(motionEvent, object : OnTouchRangeListener {
////                        override fun touchInRange(inRange: Boolean, view: BaseSwitchView) {
////                            setVibrator(inRange)
////                        }
////
////                        override fun touchUpInRange() {
////                            EasyFloat.dismiss(tag, true)
////                        }
////                    })
//                }
//
//                dragEnd {
//                    it.findViewById<TextView>(R.id.textView).apply {
//                        text = "????????????"
//                        val location = IntArray(2)
//                        getLocationOnScreen(location)
//                        setBackgroundResource(if (location[0] > 10) R.drawable.corners_left else R.drawable.corners_right)
//                    }
//                }
            }
            .show()
    }

    private fun showActivity2(tag: String) {
        // ????????????1?????????
        EasyFloat.getFloatView(TAG_1)?.findViewById<TextView>(R.id.textView)?.text = "????????????"

        EasyFloat.with(this)
            .setTag(tag)
            .setGravity(Gravity.CENTER)
            .setLayoutChangedGravity(Gravity.END)
            .setLayout(R.layout.float_seekbar) {
                it.findViewById<ImageView>(R.id.ivClose).setOnClickListener {
                    EasyFloat.dismiss(tag)
                }
                val tvProgress = it.findViewById<TextView>(R.id.tvProgress)
                tvProgress.setOnClickListener { toast(tvProgress.text.toString()) }

                it.findViewById<SeekBar>(R.id.seekBar)
                    .setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(
                            seekBar: SeekBar?, progress: Int, fromUser: Boolean
                        ) {
                            tvProgress.text = progress.toString()
                        }

                        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                    })

                val layoutContent = it.findViewById<View>(R.id.layoutContent)
                it.findViewById<TextView>(R.id.viewOther).setOnClickListener {
                    layoutContent.visibility =
                        if (layoutContent.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                }
            }
            .show()
    }

    private fun showAppFloat() {
        EasyFloat.with(this.applicationContext)
            .setShowPattern(ShowPattern.ALL_TIME)
            .setSidePattern(SidePattern.RESULT_SIDE)
            .setImmersionStatusBar(true)
            .setGravity(Gravity.END, -20, 10)
            .setLayout(R.layout.float_app) {
                it.findViewById<ImageView>(R.id.ivClose).setOnClickListener {
                    EasyFloat.dismiss()
                }
                it.findViewById<TextView>(R.id.tvOpenMain).setOnClickListener {
                    startActivity<MainActivity>(this)
                }
                it.findViewById<CheckBox>(R.id.checkbox)
                    .setOnCheckedChangeListener { _, isChecked -> EasyFloat.dragEnable(isChecked) }

                val progressBar = it.findViewById<RoundProgressBar>(R.id.roundProgressBar).apply {
                    setProgress(66, "66")
                    setOnClickListener { toast(getProgressStr()) }
                }
                it.findViewById<SeekBar>(R.id.seekBar)
                    .setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(
                            seekBar: SeekBar?, progress: Int, fromUser: Boolean
                        ) = progressBar.setProgress(progress, progress.toString())

                        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                    })

//                // ?????? ListView ??????????????????
//                it.findViewById<ListView>(R.id.lv_test).apply {
//                    adapter = MyAdapter(
//                        this@MainActivity,
//                        arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "...")
//                    )
//
//                    // ?????? ListView ??????????????????????????????????????????????????????????????????????????????
//                    setOnTouchListener { _, event ->
//                        logger.e("listView: ${event.action}")
//                        EasyFloat.appFloatDragEnable(event?.action == MotionEvent.ACTION_UP)
//                        false
//                    }
//                }
            }
            .registerCallback {
                drag { _, motionEvent ->
                    DragUtils.registerDragClose(motionEvent, object : OnTouchRangeListener {
                        override fun touchInRange(inRange: Boolean, view: BaseSwitchView) {
                            setVibrator(inRange)
                            view.findViewById<TextView>(com.mtm.easyfloat.R.id.tv_delete).text =
                                if (inRange) "????????????" else "????????????"

                            view.findViewById<ImageView>(com.mtm.easyfloat.R.id.iv_delete)
                                .setImageResource(
                                    if (inRange) com.mtm.easyfloat.R.drawable.icon_delete_selected
                                    else com.mtm.easyfloat.R.drawable.icon_delete_normal
                                )
                        }

                        override fun touchUpInRange() {
                            EasyFloat.dismiss()
                        }
                    }, showPattern = ShowPattern.ALL_TIME)
                }
            }
            .show()
    }

    private fun showAppFloat2(tag: String) {
        EasyFloat.with(this.applicationContext)
            .setTag(tag)
            .setShowPattern(ShowPattern.FOREGROUND)
            .setLocation(100, 100)
            .setAnimator(null)
            .setFilter(SecondActivity::class.java)
            .setLayout(R.layout.float_app_scale) {
                val content = it.findViewById<RelativeLayout>(R.id.rlContent)
                val params = content.layoutParams as FrameLayout.LayoutParams
                it.findViewById<ScaleImage>(R.id.ivScale).onScaledListener =
                    object : ScaleImage.OnScaledListener {
                        override fun onScaled(x: Float, y: Float, event: MotionEvent) {
                            params.width = max(params.width + x.toInt(), 400)
                            params.height = max(params.height + y.toInt(), 300)
                            // ??????xml??????????????????
//                            content.layoutParams = params
                            // ??????????????????????????????????????????????????????????????????????????????
                            EasyFloat.updateFloat(tag, width = params.width, height = params.height)
                        }
                    }

                it.findViewById<ImageView>(R.id.ivClose).setOnClickListener {
                    EasyFloat.dismiss(tag)
                }
            }
            .show()
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????????EasyFloat???????????????
     */
    private fun checkPermission(tag: String? = null) {
        if (PermissionUtils.checkPermission(this)) {
            if (tag == null) showAppFloat() else showAppFloat2(tag)
        } else {
            AlertDialog.Builder(this)
                .setMessage("??????????????????????????????????????????????????????")
                .setPositiveButton("?????????") { _, _ ->
                    if (tag == null) showAppFloat() else showAppFloat2(tag)
                }
                .setNegativeButton("??????") { _, _ -> }
                .show()
        }
    }

    /**
     * ????????????????????????
     */
    private fun requestPermission() {
        PermissionUtils.requestPermission(this, object : OnPermissionResult {
            override fun permissionResult(isOpen: Boolean) {
                logger.i(isOpen)
            }
        })
    }

    private fun toast(string: String = "onClick") =
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()

}
