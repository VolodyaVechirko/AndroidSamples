package com.example.album.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.album.BR

abstract class CoreFragment<out VM : CoreViewModel, out B : ViewDataBinding> : Fragment() {
    abstract val viewModel: VM
    abstract val layoutId: Int

    private val propertyChangeDelegate = PropertyChangeComponentDelegate()

    private var _binding: B? = null
    val binding: B get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<B>(inflater, layoutId, container, false).apply {
            setVariable(BR.viewModel, viewModel)
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.navController = (activity as? NavHolder)?.navController
    }

    override fun onStop() {
        super.onStop()
        viewModel.navController = null
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        propertyChangeDelegate.onDestroy()
    }

//    inline fun <reified VM : ViewModel> Fragment.viewModel(): VM =
//        ViewModelProvider(this)[VM::class.java]

    /**
     * Register a property change listener on the [Observable], which will get called when the
     * property indicated by the binding id changes. The listener is automatically cleaned up and
     * unregistered when the View gets destroyed.
     *
     * @param varId the binding id of the property whose changes should be notified
     * @param l a function that will be called when the property changes
     * @return the Observable, to simplify method chaining
     */
    fun Observable.onPropertyChange(@IdRes varId: Int, l: PropertyChangeListener): Observable {
        propertyChangeDelegate.registerOnPropertyChangeListener(this, varId, l)
        return this
    }
}
