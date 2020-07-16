package com.library.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : DaggerFragment() {

    private var baseActivity: BaseActivity<*,*>? = null
    private var mRootView: View? = null
    protected var viewDataBinding: T? = null
    private var mViewModel: V? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun bindingVariable(): Int

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun layoutId(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun viewModel(): V

    override fun onCreate(savedInstanceState: Bundle?) {
        //performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = viewModel()
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        mRootView = viewDataBinding!!.root
        return mRootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(bindingVariable(), mViewModel)
        viewDataBinding!!.lifecycleOwner = this
        viewDataBinding!!.executePendingBindings()

    }

//    fun hideKeyboard() {
//        if (baseActivity != null) {
//            baseActivity!!.hideKeyboard()
//        }
//    }

//    fun openActivityOnTokenExpire() {
//        if (baseActivity != null) {
//            baseActivity!!.openActivityOnTokenExpire()
//        }
//    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }
}