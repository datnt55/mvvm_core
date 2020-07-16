package com.library.core.view

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.library.core.di.viewmodel.ViewModelProviderFactory
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : DaggerAppCompatActivity() {

    @set:Inject
    lateinit var factory: ViewModelProviderFactory

    protected lateinit var mViewDataBinding: T

    private lateinit var mViewModel: V

    abstract fun getBindingVariable(): Int

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        //performDependencyInjection().
        super.onCreate(savedInstanceState)
        performDataBinding()

    }

    open fun getViewDataBinding(): T {
        return mViewDataBinding
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = getViewModel()
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
    }

    private fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}