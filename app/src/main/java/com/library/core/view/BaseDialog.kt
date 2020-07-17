package com.ordergo.staff.view.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


abstract class BaseDialog<T : ViewDataBinding, V>(private val item : V? = null, context: Context) : Dialog(context) {

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getBindingVariable(): Int

    protected lateinit var mViewDataBinding: T

    abstract fun bindView(viewBinding : T)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        mViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), null, false)

        if(item != null){
            mViewDataBinding.setVariable(getBindingVariable(), item)
            mViewDataBinding.executePendingBindings()
        }

        setContentView(mViewDataBinding.root)

        bindView(mViewDataBinding)
    }





}