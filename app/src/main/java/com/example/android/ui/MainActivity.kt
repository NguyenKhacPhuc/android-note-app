package com.example.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android.databinding.MainActivityBinding
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {
    private lateinit var binding: MainActivityBinding
    private var loadingFragment: LoadingFragment? = null
    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun loading() {
        if (loadingFragment?.isDetached == false) return
        if (loadingFragment == null && !this.isFinishing) {
            loadingFragment = LoadingFragment.newInstance()
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(loadingFragment!!, LoadingFragment.TAG)
        transaction.commitAllowingStateLoss()
    }

    fun dismiss() {
        loadingFragment?.dismiss()
        loadingFragment = null
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}