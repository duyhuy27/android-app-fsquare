package vn.md18.fsquareapplication.core.base


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.model.KeyPath
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.databinding.DialogLoadingViewBinding
import vn.md18.fsquareapplication.di.component.datamanager.DataManager
import vn.md18.fsquareapplication.di.component.resource.ResourcesService
import vn.md18.fsquareapplication.di.component.scheduler.SchedulerProvider
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.getColorCompat
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import java.lang.reflect.ParameterizedType
import java.util.*
import javax.inject.Inject

abstract class BaseActivity<viewBinding : ViewBinding, VM : BaseViewModel> : FragmentActivity(), BaseBehavior {

    /**
     * Inject Field
     */
    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var resourcesService: ResourcesService

    @Inject
    lateinit var dataManager: DataManager

    /**
     * ViewBinding Type
     */
    open lateinit var binding: viewBinding

    /**
     * Override ViewModel Type
     */
    abstract val viewModel: VM

    /**
     * Inflate ViewBinding
     */
    protected abstract fun inflateLayout(layoutInflater: LayoutInflater): viewBinding


    private var isUserCloseApp: Boolean = false

    /**
     * Callback From Fragment
     */
    private var activityInterface: ActivityInterface? = null

    /**
     * Variable with launch activity for result new
     * StartActivityForResult Deprecated()
     */
    open var launchSomeActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        onDataReceiverActivityForResult(result)
    }

    /**
     * Receive Data for Activity For Result
     */
    open fun onDataReceiverActivityForResult(activityResult: ActivityResult) {}


    /**
     * Set activity Interface from fragment
     */
    fun setActivityInterface(activityInterface: ActivityInterface?) {
        this.activityInterface = activityInterface
    }

    /**
     * Dialog LoadingView
     */
    private val dialogLoading: Dialog by lazy {
        Dialog(this, R.style.AppTheme_FullScreen_LightStatusBar).apply {
            window?.setBackgroundDrawableResource(R.color.translucent_white)
        }
    }

    private lateinit var dialogLoadingViewBinding: DialogLoadingViewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateLayout(layoutInflater)
        setContentView(binding.root)


        createLoadingDialog()
        setUpViewModel()
        onViewLoaded()
        addViewListener()
        addDataObserver()
    }


    /**
     * Add observer view -> ViewModel
     */
    override fun addDataObserver() {
        viewModel.errorState.observe(this) {
            onError(it)
        }
        viewModel.loadingState.observe(this) {
            onLoading(it)
        }
        viewModel.errorMessage.observe(this@BaseActivity) {
            it?.let {
                onErrorMessage(it)
            }
        }
    }


    /**
     * Create loading dialog by
     */
    private fun createLoadingDialog() {
        dialogLoading.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogLoadingViewBinding =
                DialogLoadingViewBinding.inflate(LayoutInflater.from(this@BaseActivity))
            setContentView(dialogLoadingViewBinding.root)
        }
    }

    private fun onErrorMessage(errorId: Int?) {
        errorId?.let {
            showCustomToast(getString(it), Constant.ToastStatus.FAILURE)
        }
    }

    fun setConfigLoadingDialog(
        loadingEnable: Boolean,
        colorAnimation: Int? = null,
        colorBackground: Int? = null,
    ) {

        if (colorAnimation != null) {
            dialogLoadingViewBinding.loadingAnimation.addValueCallback(
                KeyPath("**"),
                LottieProperty.COLOR_FILTER
            ) {
                PorterDuffColorFilter(
                    getColorCompat(colorAnimation),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        }

        if (colorBackground != null) {
            dialogLoading.window?.setBackgroundDrawableResource(colorBackground)
        }

        dialogLoading.setContentView(dialogLoadingViewBinding.root)
        onLoading(loadingEnable)
        //onLoadingWithTimeout(loadingEnable)
    }

    /**
     * Initialized ViewModel
     */
    private fun setUpViewModel() {
        viewModel.init(schedulerProvider = schedulerProvider, resourcesService = resourcesService, dataManager = dataManager)
        viewModel.onDidBindViewModel()
    }


    /**
     * Open Activity
     */
    fun openActivity(cla: Class<*>, vararg flags: Int) {
        val intent = Intent(this, cla)
        for (flag in flags) {
            intent.addFlags(flag)
        }
        startActivity(intent)
    }

    /**
     * Open Activity for result
     */
    fun openActivityForResult(cla: Class<*>, vararg flags: Int) {
        val intent = Intent(this, cla)
        for (flag in flags) {
            intent.addFlags(flag)
        }
        launchSomeActivity.launch(intent)
    }

    /**
     * Hide keyboard android Os
     */
    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * DispatchTouchEvent -> Clear focus with edittext
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            if (currentFocus is EditText) {
                val outRect = Rect()
                (currentFocus as EditText).getGlobalVisibleRect(outRect)
                ev?.let {
                    if (outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                        return super.dispatchTouchEvent(ev)
                    }
                }
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                (currentFocus as EditText).clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * Exit app
     * Touch twice to exit app
     */
    private fun existApp() {
        if (isUserCloseApp) {
            finish()
        } else {
            Toast.makeText(this, resourcesService.getString(R.string.application_touch_twice_to_exits), Toast.LENGTH_LONG).show()
            isUserCloseApp = true
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    isUserCloseApp = false
                }
            }, 3000)
        }
    }

    /**
     * OnBackPressed
     * if activity from fragment null or back stack empty -> super.onBackPressed()
     */
    override fun onBackPressed() {
        if (activityInterface != null) {
            activityInterface!!.onBackPressed()
            return
        }
        if (supportFragmentManager.backStackEntryCount > 0) {
            popFragment()
            return
        }
        super.onBackPressed()
    }

    fun addFragment(fragment: BaseFragment<*, *>) {
        addFragment(fragment, animate = true, clearStack = false, tag = fragment.getTagFragment())
    }

    fun addFragment(fragment: BaseFragment<*, *>, tag: String) {
        addFragment(fragment, animate = true, clearStack = false, tag = tag)
    }

    private fun addFragment(
        fragment: BaseFragment<*, *>,
        animate: Boolean = false,
        clearStack: Boolean = false,
        tag: String? = null
    ) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (clearStack) {
            while (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStackImmediate()
            }
        } else {
            fragmentTransaction.addToBackStack(tag ?: fragment.getTagFragment())
        }
        if (supportFragmentManager.findFragmentByTag(tag ?: fragment.getTagFragment()) != null) {
            return
        }
        if (!animate) {
            fragmentTransaction.setCustomAnimations(0, 0)
        } else {
            fragmentTransaction.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
        }
        hideKeyboard()
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.add(android.R.id.content, fragment, tag ?: fragment.getTagFragment())
        fragmentTransaction.commitAllowingStateLoss()
    }


    /**
     * Fragment onBecome to Activity
     */
    private fun setFragmentOnBecomeVisible() {
        val fragmentList = supportFragmentManager.fragments.filterIsInstance<BaseFragment<*, *>>()
        if (!fragmentList.isNullOrEmpty()) {
            val fragmentVisible = fragmentList.last()
            fragmentVisible.onBecomeVisible()
        }
    }


    /**
     * Pop and remove fragment
     */
    private fun popFragment(tag: String? = null) {
        hideKeyboard()
        if (supportFragmentManager.backStackEntryCount > 0) {
            if (tag == null) {
                supportFragmentManager.popBackStackImmediate()
            } else {
                supportFragmentManager.popBackStackImmediate(
                    tag,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
            setFragmentOnBecomeVisible()
        } else {
            finish()
        }
    }

    override fun onBecomeInvisible() {
    }

    override fun onBecomeVisible() {

    }

    /**
     * Base Error Exception
     */
    override fun onError(error: Any) {
        onLoading(false)
        showCustomToast(error.toString(), Constant.ToastStatus.FAILURE)
    }

    /**
     * On Key board showing
     */
    override fun onKeyboardShowing(isShowing: Boolean) {
        val fragments = supportFragmentManager.fragments
        fragments.filterIsInstance<BaseFragment<*, *>>()
        if (fragments.isNotEmpty()) {
            (fragments.last() as BaseFragment<*, *>).onKeyboardShowing(isShowing)
        }
    }

    /**
     * Show and Hide Loading
     */
    override fun onLoading(isLoading: Boolean) {
        if (isLoading) dialogLoading.show()
        else dialogLoading.dismiss()
    }

    /**
     * Destroy activity
     * Unregister Launch some activity
     * Dispose all Event Rx Stream of ViewModel
     */
    override fun onDestroy() {
        launchSomeActivity.unregister()
        viewModel.compositeDisposable.dispose()
        super.onDestroy()
    }

    @Suppress("UNCHECKED_CAST")
    fun getViewModelClass(): Class<VM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<VM>
    }
}