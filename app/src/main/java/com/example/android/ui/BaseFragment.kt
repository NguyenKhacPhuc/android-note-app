package com.example.android.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.android.viewmodel.BaseViewModel
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel, BD : ViewBinding> : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract val modelClass: Class<VM>

    open val isViewModelProvideByActivity: Boolean = false

    protected val viewModel by lazy {
        ViewModelProvider(
            if (isViewModelProvideByActivity) {
                requireActivity()
            } else {
                this
            }, viewModelFactory
        )[modelClass]
    }

    lateinit var binding: BD
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BD
    protected val navController by lazy {
        findNavController()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
    // region setup view and event
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }
    // endregion

    // region setup data
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateViewWithData()
        processLogicAfterViewCreated()
    }

    /**
     * Update all the data to view if need when we make sure the view is ready to add the value
     * Eg. Set text to view with arguments
     */
    open fun updateViewWithData() = Unit

    /**
     * Process all the business logic after view created to make sure all the fragment is ready to
     * handle data and listener
     */
    open fun processLogicAfterViewCreated() = Unit
    // endregion

    open fun onHandleBackPressed() {
        popBackStack()
    }

    fun NavController.navigateSafe(@IdRes resId: Int, args: Bundle? = null) {
        val destinationId = currentDestination?.getAction(resId)?.destinationId.orEmpty()
        currentDestination?.let { node ->
            val currentNode = when (node) {
                is NavGraph -> node
                else -> node.parent
            }
            if (destinationId != 0) {
                currentNode?.findNode(destinationId)?.let { navigate(resId, args) }
            }
        }
    }

    private fun Int?.orEmpty(default: Int = 0): Int {
        return this ?: default
    }


    open fun popBackStack(applyActivityPopBack: Boolean = false): Boolean {
        val check = navController.popBackStack()

        if (!check && applyActivityPopBack) {
            requireActivity().onBackPressed()
            return false
        }

        return check
    }
}
