package vn.md18.fsquareapplication.core.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import vn.md18.fsquareapplication.di.component.datamanager.DataManager
import vn.md18.fsquareapplication.di.component.resource.ResourcesService
import vn.md18.fsquareapplication.di.component.scheduler.SchedulerProvider
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding, ViewModel : BaseViewModel> : Fragment(),
    BaseBehavior {

    lateinit var binding: VB
    abstract val viewModel: ViewModel

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var resourcesService: ResourcesService

    @Inject
    lateinit var dataManager: DataManager

    protected abstract fun inflateLayout(layoutInflater: LayoutInflater): VB


    abstract fun getTagFragment(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflateLayout(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewLoaded()
        addViewListener()
        addDataObserver()

    }


    /**
     * Initialized ViewModel
     */
    private fun setUpViewModel() {
        viewModel.init(schedulerProvider = schedulerProvider, resourcesService = resourcesService, dataManager = dataManager)
        viewModel.onDidBindViewModel()
    }

    override fun onBecomeInvisible() {
        if (activity is BaseActivity<*, *>) {
            (activity as BaseActivity<*, *>).onBecomeInvisible()
        }
    }

    override fun onBecomeVisible() {
        if (activity is BaseActivity<*, *>) {
            (activity as BaseActivity<*, *>).onBecomeVisible()
        }
    }

    override fun onKeyboardShowing(isShowing: Boolean) {
        if (activity is BaseActivity<*, *>) {
            (activity as BaseActivity<*, *>).onKeyboardShowing(isShowing)
        }
    }

    fun openActivity(cla: Class<*>, dataSend: HashMap<String, Any>, vararg flags: Int) {
        val intent = Intent(activity, cla)
        for (flag in flags) {
            intent.addFlags(flag)
        }
        startActivity(intent)
    }

    fun openActivity(cla: Class<*>, data: Bundle, vararg flags: Int) {
        val intent = Intent(activity, cla)
        intent.putExtra(Constant.KEY_BUNDLE, data)
        for (flag in flags) {
            intent.addFlags(flag)
        }
        startActivity(intent)
    }

    fun openActivity(cla: Class<*>, vararg flags: Int) {
        val intent = Intent(activity, cla)
        for (flag in flags) {
            intent.addFlags(flag)
        }
        startActivity(intent)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClass(): Class<ViewModel> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]   // index of 0 means first argument of Base class param
        return type as Class<ViewModel>
    }

    /**
     * Calling onLoading In Activity with default
     * Custom -> Override and remove super
     */
    override fun onLoading(isLoading: Boolean) {
        if (activity is BaseActivity<*, *>) {
            (activity as BaseActivity<*, *>).onLoading(isLoading)
        }
    }

    /**
     * Calling onError In Activity with default
     * Custom -> Override and remove super
     */
    override fun onError(error: Any) {
        if (activity is BaseActivity<*, *>) {
            (activity as BaseActivity<*, *>).onError(error)
        }
    }

    /**
     * onDestroyView()
     * Dispose all Event Rx Stream of ViewModel
     */
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.compositeDisposable.dispose()
    }

    override fun addDataObserver() {
        activity?.let { activity ->
            viewModel.apply {
                errorState.observe(activity) {
                    FSLogger.e("huynd: base fragment == $it")
                    onError(it)
                }
                loadingState.observe(activity) {
                    onLoading(it)
                }
            }
        }
    }

}