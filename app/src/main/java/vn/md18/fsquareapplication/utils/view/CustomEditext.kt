package vn.md18.fsquareapplication.utils.view

import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.utils.extensions.getColorCompat
import vn.md18.fsquareapplication.utils.extensions.getColorStateListCompat
import vn.md18.fsquareapplication.utils.extensions.getDrawableCompat


import android.annotation.SuppressLint
import android.app.ProgressDialog.show
import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import vn.md18.fsquareapplication.databinding.LayoutCustomEditextBinding
import vn.md18.fsquareapplication.utils.extensions.dpToPx
import vn.md18.fsquareapplication.utils.extensions.hide
import vn.md18.fsquareapplication.utils.extensions.setEnableView
import vn.md18.fsquareapplication.utils.extensions.setFocusableView
import vn.md18.fsquareapplication.utils.extensions.setOnSafeClickListener
import vn.md18.fsquareapplication.utils.extensions.show


@SuppressLint("CustomViewStyleable")
class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {
    private var binding: LayoutCustomEditextBinding =
        LayoutCustomEditextBinding.inflate(LayoutInflater.from(context), this, true)

    val editText: AppCompatEditText = binding.edtInput
    lateinit var onClickTextRight: () -> Unit

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEdittext)
        val hintText = typedArray.getString(R.styleable.CustomEdittext_hintText)
        val imeOption = typedArray.getInt(R.styleable.CustomEdittext_actionIme, 0)
        val togglePassword =
            typedArray.getBoolean(R.styleable.CustomEdittext_showIconTogglePassword, false)
        val inputType = typedArray.getInt(R.styleable.CustomEdittext_inputType, 0)
        val iconStart = typedArray.getResourceId(R.styleable.CustomEdittext_iconStart, 0)
        val iconEnd = typedArray.getResourceId(R.styleable.CustomEdittext_iconEnd, 0)
        val colorIcon = typedArray.getResourceId(R.styleable.CustomEdittext_colorIconEnd, R.color.dark_blue)
        val enable = typedArray.getBoolean(R.styleable.CustomEdittext_enable, true)
        val textStart = typedArray.getString(R.styleable.CustomEdittext_textStart)
        setEnable(enable)
        setHintText(hintText)
        setImeOptionEditText(imeOption)
        setTogglePassword(togglePassword)
        setInputType(inputType)
        setFrameStart(iconStart, textStart)
        setUpIconStart(iconStart)
        setUpIconEnd(iconEnd, togglePassword, colorIcon)
        setTextStart(textStart)
        setFrameStart(iconStart, textStart)
        typedArray.recycle()

    }

    private fun setFrameStart(iconStart: Int, textStart: String?) {
        if (iconStart != 0 || !textStart.isNullOrEmpty()) {
            binding.frameLayout.visibility = View.VISIBLE
        } else {
            binding.frameLayout.visibility = View.GONE
        }
    }

    fun setOnClickListenerEdt(listener: OnClickListener) {
        binding.edtInput.setOnClickListener(listener)
    }

    fun setOnClickButtonEnd(listener: OnClickListener) {
        binding.iconSecond.setOnClickListener(listener)
    }

    private fun setTextStart(textStart: String?) {
        if (textStart.isNullOrEmpty()) {
            binding.tvStart.visibility = View.GONE
        } else {
            binding.tvStart.visibility = View.VISIBLE
            binding.tvStart.text = textStart
        }
    }

    private fun setEnable(enable: Boolean) {
        binding.edtInput.inputType = InputType.TYPE_NULL
        binding.edtInput.isClickable = false
        binding.edtInput.setHintTextColor(
            if (!enable) context.getColorCompat(R.color.dark_blue) else context.getColorCompat(
                R.color.grey_light_text
            )
        )
    }

    fun setHint(hint: String) {
        binding.edtInput.hint = hint
    }

    fun getText(): String {
        return binding.edtInput.text.toString()
    }

    fun setText(inputString: String) {
        binding.edtInput.setText(inputString)
    }

    fun addTextChangedListener(textWatcher: TextWatcher) {
        binding.edtInput.addTextChangedListener(textWatcher)
    }

    private fun setUpIconStart(resourceId: Int) {
        if (resourceId == 0) {
            binding.imgStart.visibility = View.GONE
        } else {
            binding.imgStart.apply {
                visibility = View.VISIBLE
                setImageDrawable(context.getDrawableCompat(resourceId))
            }
        }
    }

    private fun setUpIconEnd(resourceId: Int, togglePassword: Boolean, color: Int) {
        if (!togglePassword && resourceId != 0) {
            binding.edtInput.isEnabled = false
            binding.iconSecond.apply {
                visibility = View.VISIBLE
                setPadding(dpToPx(18F))
                setImageDrawable(context.getDrawableCompat(resourceId))
                imageTintList = context.getColorStateListCompat(color)
            }
        }
    }


    private fun setTogglePassword(togglePassword: Boolean) {
        binding.iconSecond.apply {
            setPadding(dpToPx(14F))
            visibility = if (togglePassword) View.VISIBLE else View.GONE
            setOnClickListener {
                checkedToggleView(button = binding.iconSecond, binding.edtInput)
            }
        }
    }

    fun setError(message: String) {
        binding.ctlEdt.background = ContextCompat.getDrawable(context, R.drawable.bg_edt_corner_red)
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = message
    }

    fun setNormal() {
        binding.ctlEdt.background =
            ContextCompat.getDrawable(context, R.drawable.bg_edt_corner_grey)
        binding.tvError.visibility = View.GONE
    }

    private fun setImeOptionEditText(imeOption: Int) {
        binding.edtInput.imeOptions = when (imeOption) {
            1 -> EditorInfo.IME_ACTION_NONE
            2 -> EditorInfo.IME_ACTION_GO
            3 -> EditorInfo.IME_ACTION_SEARCH
            4 -> EditorInfo.IME_ACTION_SEND
            5 -> EditorInfo.IME_ACTION_DONE
            6 -> EditorInfo.IME_ACTION_NEXT
            7 -> EditorInfo.IME_ACTION_PREVIOUS
            else -> 0
        }
    }

    private fun setInputType(inputType: Int) {
        when (inputType) {
            0 -> binding.edtInput.inputType = InputType.TYPE_CLASS_TEXT
            1 -> binding.edtInput.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            2 -> {
                binding.edtInput.apply {
                    this.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    binding.iconSecond.isSelected = false
                    this.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }

            3 -> {
                binding.edtInput.inputType = InputType.TYPE_CLASS_PHONE
            }

            4 -> {
                binding.edtInput.inputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            }

            else -> EditorInfo.TYPE_CLASS_TEXT
        }
    }

    private fun setHintText(hintText: String?) {
        binding.edtInput.hint = hintText

    }

    fun setNormalFixed() {
        binding.ctlEdt.background =
            ContextCompat.getDrawable(context, R.drawable.bg_edt_corner_grey)
        binding.tvError.visibility = View.INVISIBLE
    }

    private fun checkedToggleView(button: View, editText: EditText) {
        button.isSelected = !button.isSelected
        editText.transformationMethod =
            if (!button.isSelected) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
        editText.text?.length?.let { it1 ->
            editText.setSelection(it1)
        }
    }

    fun setMaxLength(length: Int) {
        editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(length))
    }

    fun setGravityTextError(gravityValue: Int) {
        binding.tvError.gravity = gravityValue
    }

    fun addOnFocusChangeListener(onFocusChangeListener: OnFocusChangeListener) {
        binding.edtInput.onFocusChangeListener = onFocusChangeListener
    }

    fun onChangeBorderColorWhenFocus(hasFocus: Boolean) {
        binding.ctlEdt.background = ContextCompat.getDrawable(
            context,
            if (hasFocus) {
                R.drawable.bg_edt_corner_neutral_70
            } else {
                R.drawable.bg_edt_corner_neutral_50
            }
        )
    }

    fun setFocusableCustomView(isFocus: Boolean) {
        binding.edtInput.setFocusableView(isFocus)
    }

    fun setRequestFocusableCustomView() {
        binding.edtInput.requestFocus()
    }

    fun edtIsFocused(): Boolean = binding.edtInput.isFocused

    fun setFocusChangeListener(onFocusChangeListener: OnFocusChangeListener) {
        editText.onFocusChangeListener = onFocusChangeListener
    }

    fun setTextRight(stringRight: String?) {
        if (!stringRight.isNullOrEmpty()) {
            binding.iconSecond.hide()
            binding.tvRight.apply {
                show()
                text = stringRight
                setEnableView(true)
                setOnSafeClickListener {
                    if (this@CustomEditText::onClickTextRight.isInitialized) {
                        onClickTextRight.invoke()
                    }
                }
            }
        } else {
            binding.tvRight.hide()
        }
    }

    fun setStateRightActionText(enable: Boolean) {
        binding.tvRight.setEnableView(enable)
    }
}