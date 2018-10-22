package ${packageName}.${funtionName}

import android.os.Bundle

import ${applicationPackage}.R
<#if hasInject>
import tran.nam.core.view.BaseActivityInjection
<#else>
import tran.nam.core.view.BaseActivity
</#if>

<#if hasBiding>
import android.databinding.DataBindingUtil
import ${applicationPackage}.databinding.Activity${Name}Binding
</#if>

class ${activityName} : <#if hasInject>BaseActivityInjection<#else>BaseActivity</#if>(){

    <#if hasBiding>
    private lateinit var mViewDataBinding : Activity${underscoreToCamelCase(classToResource(activityName))}Binding
    </#if>

    override fun layoutId(): Int {
            return R.layout.${layoutName}
        }

    override fun initView(savedInstanceState: Bundle?) {
        <#if hasBiding>
        mViewDataBinding = DataBindingUtil.setContentView(this, layoutId())
        mViewDataBinding.view = this
        </#if>
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    <#if hasStatusBar>
    override fun setStatusBar() {

    }
    </#if>
}