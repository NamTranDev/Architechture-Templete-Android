/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package tran.nam.core.view.mvvm

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import tran.nam.common.AutoClearedValue
import tran.nam.core.view.BaseParentFragment
import tran.nam.core.viewmodel.BaseFragmentViewModel
import tran.nam.core.viewmodel.IViewModel
import javax.inject.Inject

abstract class BaseParentFragmentMVVM<V : ViewDataBinding, VM : BaseFragmentViewModel> : BaseParentFragment(), IViewModel {

    /**
     * MVVM ViewModel ViewModelProvider.Factory
     */
    var mViewModelFactory: ViewModelProvider.Factory? = null
        @Inject set

    protected var mViewModel: VM? = null

    protected lateinit var mViewDataBinding: V

    protected lateinit var binding: AutoClearedValue<V>

    abstract fun initViewModel(factory: ViewModelProvider.Factory?)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        initViewModel(mViewModelFactory)
        if (mViewModel != null) {
            mViewModel?.onAttach(this)
        }
    }

    override fun initialized() {
        super.initialized()
        if (mViewModel != null)
            mViewModel?.onInitialized()
    }

    override fun initLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        binding = AutoClearedValue(this)
        return mViewDataBinding.root
    }

    override fun onDestroy() {
        this.mViewDataBinding.unbind()
        super.onDestroy()
    }

    override fun showDialogLoading() {
        showLoadingDialog()
    }

    override fun hideDialogLoading() {
        hideLoadingDialog()
    }

    override fun onShowDialogError(message: String?) {
        Toast.makeText(activity(), "Error Dialog", Toast.LENGTH_SHORT).show()
    }
}