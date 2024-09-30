package vn.md18.fsquareapplication.utils.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.databinding.LayoutCustomToolbarBinding
import vn.md18.fsquareapplication.utils.extensions.dpToPx
import vn.md18.fsquareapplication.utils.extensions.enable
import vn.md18.fsquareapplication.utils.extensions.getColorStateListCompat
import vn.md18.fsquareapplication.utils.extensions.getDrawableCompat
import vn.md18.fsquareapplication.utils.extensions.hide
import vn.md18.fsquareapplication.utils.extensions.safeClickWithRx
import vn.md18.fsquareapplication.utils.extensions.setColorTextView
import vn.md18.fsquareapplication.utils.extensions.setEnableView
import vn.md18.fsquareapplication.utils.extensions.setOnSafeClickListener
import vn.md18.fsquareapplication.utils.extensions.setWidthView
import vn.md18.fsquareapplication.utils.extensions.show

@SuppressLint("CustomViewStyleable")
class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {

    lateinit var onClickBackPress: () -> Unit
    lateinit var onClickFirstPress: () -> Unit
    lateinit var onClickSecondPress: () -> Unit
    lateinit var onClickTextRight: () -> Unit
    lateinit var onClickCancelIcon: () -> Unit
    lateinit var onClickTextLeft: () -> Unit
    lateinit var onChangeTuyaPairMode: () -> Unit

    private var enableBackButton: Boolean? = null
    private var iconFirstRight: Int? = null
    private var iconSecondRight: Int? = null
    private var titleToolbar: String? = null
    private var colorIcon: Int? = null
    private var stringRight: String? = null
    private var textLeft: String? = null
    private var titleGravity = Gravity.CENTER or Gravity.START

    private val binding: LayoutCustomToolbarBinding =
        LayoutCustomToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar)

        enableBackButton = typedArray.getBoolean(R.styleable.CustomToolbar_enableBackButton, true)
        iconFirstRight = typedArray.getResourceId(R.styleable.CustomToolbar_iconFirstRight, 0)
        iconSecondRight = typedArray.getResourceId(R.styleable.CustomToolbar_iconSecondRight, 0)
        titleToolbar = typedArray.getString(R.styleable.CustomToolbar_titleToolbar)
        colorIcon = typedArray.getResourceId(R.styleable.CustomToolbar_colorIcon, R.color.dark_blue)
        stringRight = typedArray.getString(R.styleable.CustomToolbar_textRight)
        textLeft = typedArray.getString(R.styleable.CustomToolbar_textLeft)

        iconFirstRight = typedArray.getResourceId(R.styleable.CustomToolbar_iconFirstRight, 0)
        iconSecondRight = typedArray.getResourceId(R.styleable.CustomToolbar_iconSecondRight, 0)
        titleToolbar = typedArray.getString(R.styleable.CustomToolbar_titleToolbar)
        colorIcon = typedArray.getResourceId(R.styleable.CustomToolbar_colorIcon, R.color.dark_blue)
        titleGravity =
            typedArray.getResourceId(R.styleable.CustomToolbar_titleGravity, Gravity.CENTER)

        setWidthDefault(
            stringRight = stringRight,
            iconFirstRight = iconFirstRight!!,
            iconSecondRight = iconSecondRight!!
        )

        setUpBackButton(enableBackButton = enableBackButton!!)

        setUpTitle(titleToolbar = titleToolbar)

        setUpTitleGravity(gravity = titleGravity)

        setupIconRight(iconFirstRight = iconFirstRight!!, iconSecondRight = iconSecondRight!!)

        setColorTintToolbar(color = colorIcon!!)

        setTextRight(stringRight = stringRight)

        setTextLeft(textLeft = textLeft)

        typedArray.recycle()
    }

    private fun setWidthDefault(stringRight: String?, iconFirstRight: Int, iconSecondRight: Int) {
        if (stringRight.isNullOrEmpty() && iconFirstRight == 0 && iconSecondRight == 0) {
            setWidthView(binding.frameStart, binding.linearEnd)
            return
        }
        if (stringRight.isNullOrEmpty() && (iconFirstRight != 0 && iconSecondRight != 0)) {
            setWidthView(binding.linearEnd, binding.frameStart)
            return
        }
        if (!stringRight.isNullOrEmpty()) {
            setWidthView(binding.linearEnd, binding.frameStart)
            return
        }
    }


    fun setUpBackButton(enableBackButton: Boolean) {
        binding.icBack.apply {
            visibility = if (enableBackButton) View.VISIBLE else View.GONE
            setOnClickListener {
                if (this@CustomToolbar::onClickBackPress.isInitialized) {
                    onClickBackPress.invoke()
                }
            }
        }

        binding.tvLeft.hide()
    }

    fun setupLeftIcon(iconLeft: Int) {
        if (iconLeft != 0) {
            binding.icBack.apply {
                visibility = View.VISIBLE
                setImageDrawable(context.getDrawableCompat(iconLeft))
                if (iconLeft == R.drawable.ic_back) {
                    setOnClickListener {
                        if (this@CustomToolbar::onClickBackPress.isInitialized) {
                            onClickBackPress.invoke()
                        }
                    }
                } else {
                    setOnClickListener {
                        if (this@CustomToolbar::onClickCancelIcon.isInitialized) {
                            onClickCancelIcon.invoke()
                        }
                    }
                }
            }
        } else {
            binding.icBack.visibility = View.GONE
        }
    }


    fun setImgButtonLeft(icon: Int, enableBackButton: Boolean) {
        binding.icBack.apply {
            visibility = if (enableBackButton) View.VISIBLE else View.GONE
            setImageResource(icon)
            setOnClickListener {
                if (this@CustomToolbar::onClickBackPress.isInitialized) {
                    onClickBackPress.invoke()
                }
            }
        }

    }

    fun setUpTitle(titleToolbar: String?) {
        if (titleToolbar == null) return
        binding.tvTitle.text = titleToolbar
        binding.tvTitle.gravity = titleGravity
    }

    fun setUpTitleGravity(gravity: Int) {
        titleGravity = gravity
        binding.tvTitle.gravity = gravity
    }

    fun setTextRight(stringRight: String?) {
        if (!stringRight.isNullOrEmpty()) {
            binding.icRightSecond.visibility = View.GONE
            binding.icRightFirst.visibility = View.GONE
            binding.tvRight.apply {
                visibility = View.VISIBLE
                text = stringRight
                enable()
                isClickable = true
                setOnSafeClickListener {
                    if (this@CustomToolbar::onClickTextRight.isInitialized) {
                        onClickTextRight.invoke()
                    }
                }

            }
            setWidthDefault(
                stringRight = stringRight,
                iconFirstRight = iconFirstRight!!,
                iconSecondRight = iconSecondRight!!
            )

        } else {
            binding.tvRight.visibility = View.GONE
        }
    }

    fun setTextRightColor(color: Int) {
        binding.tvRight.setColorTextView(color)
    }

    fun enableClick(enable: Boolean) {
        binding.tvRight.isClickable = enable
    }

    fun changeStateTextRight(isEnabled: Boolean) {
        binding.tvRight.isEnabled = isEnabled
    }

    fun setupIconRight(iconFirstRight: Int, iconSecondRight: Int) {
        if (iconSecondRight != 0) {
            binding.icRightSecond.apply {
                visibility = View.VISIBLE
                setImageDrawable(context.getDrawableCompat(iconSecondRight))
                setOnClickListener {
                    if (this@CustomToolbar::onClickSecondPress.isInitialized) {
                        onClickSecondPress.invoke()
                    }
                }
            }
        }
        if (iconFirstRight != 0) {
            binding.icRightFirst.apply {
                visibility = View.VISIBLE
                setImageDrawable(context.getDrawableCompat(iconFirstRight))
                setOnClickListener {
                    if (this@CustomToolbar::onClickFirstPress.isInitialized) {
                        onClickFirstPress.invoke()
                    }
                }
            }
        }
        if (iconFirstRight == 0) binding.icRightFirst.visibility = View.GONE
        if (iconSecondRight == 0) binding.icRightSecond.visibility = View.GONE
    }

    fun setColorTintToolbar(color: Int) {
        binding.icBack.imageTintList = context.getColorStateListCompat(color)
        binding.icRightFirst.imageTintList = context.getColorStateListCompat(color)
        binding.icRightSecond.imageTintList = context.getColorStateListCompat(color)
        binding.tvTitle.setTextColor(context.getColorStateListCompat(color))
    }

    fun setStateRightActionText(enable: Boolean) {
        binding.tvRight.setEnableView(enable)
    }

    fun setStateSecondRightButton(enable: Boolean) {
        binding.icRightSecond.setEnableView(enable)
    }

    fun setBackButtonImage(iconBack: Int) {
        binding.icBack.setImageDrawable(context.getDrawableCompat(iconBack))
    }

    // Set lại constraint cho title để không bị chèn vào các icon xung quanh
    fun setUpConstraintTittle() {
        val constraintSet = ConstraintSet()
        val tvTitleId = binding.tvTitle.id
        constraintSet.clone(binding.clContainer)
        binding.tvTitle.id.apply {
            constraintSet.clear(tvTitleId, ConstraintSet.END)
            constraintSet.clear(tvTitleId, ConstraintSet.START)
            constraintSet.connect(tvTitleId, ConstraintSet.START, binding.frameStart.id, ConstraintSet.END, 0)
            constraintSet.connect(tvTitleId, ConstraintSet.END, binding.linearEnd.id, ConstraintSet.START, 6)
        }
        constraintSet.applyTo(binding.clContainer)
    }

    fun setTextLeft(textLeft: String?) {
        if (!textLeft.isNullOrEmpty()) {
            binding.apply {
                icBack.hide()

                tvLeft.apply {
                    show()
                    text = textLeft
                    safeClickWithRx {
                        if (this@CustomToolbar::onClickTextLeft.isInitialized) {
                            onClickTextLeft.invoke()
                        }
                    }
                }
            }
        } else {
            binding.tvLeft.hide()
        }
    }

    fun setTextTuyaPairMode(textRight: String?) {
        binding.apply {
            textRight?.let {
                llTuyaPairMode.show()
                tvRightSecond.text = it
                icChangeTuyaPairMode.safeClickWithRx {
                    if (this@CustomToolbar::onChangeTuyaPairMode.isInitialized) {
                        onChangeTuyaPairMode.invoke()
                    }
                }
            } ?: llTuyaPairMode.hide()
        }
    }
    
    fun enableIconFirstRight(alpha: Float, isEnable: Boolean) {
        binding.icRightFirst.setEnableView(alpha, isEnable)
    }

    fun enableIconSecondRight(alpha: Float, isEnable: Boolean) {
        binding.icRightSecond.setEnableView(alpha, isEnable)
    }

    fun setPaddingTitle(left: Float, top: Float, right: Float, bottom: Float) {
        binding.tvTitle.setPadding(dpToPx(left), dpToPx(top), dpToPx(right), dpToPx(bottom))
    }

    fun setEnableIconLeft(isEnable: Boolean) {
        binding.icBack.setEnableView(isEnable)
    }
}