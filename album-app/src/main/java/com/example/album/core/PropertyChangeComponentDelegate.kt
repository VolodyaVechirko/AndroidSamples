package com.example.album.core

import android.app.Activity
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.collection.ArrayMap
import androidx.databinding.Observable
import androidx.fragment.app.Fragment

typealias PropertyChangeListener = () -> Unit

/**
 * Delegate that allows property change listeners to be registered on [Observable](Observables)
 * and that automatically handles clean-up and unregistration when the View gets destroyed.
 */
class PropertyChangeComponentDelegate {

    private var registeredPropertyChangeCallbacks =
        mutableMapOf<Observable, Observable.OnPropertyChangedCallback>()

    private var propertyChangeListeners =
        mutableMapOf<Observable, MutableMap<Int, PropertyChangeListener>>()

    /**
     * Register a property change listener on provided the [Observable], which will get called when
     * the property indicated by the binding id changes. The listener is automatically cleaned up
     * and unregistered when the View gets destroyed.
     *
     * @param bindingVariableId the binding id of the property whose changes should be notified
     * @param listener a function that will be called when the property changes
     */
    @MainThread
    fun registerOnPropertyChangeListener(
        observable: Observable,
        @IdRes bindingVariableId: Int,
        listener: () -> Unit
    ) {
        // Get the map of listeners, initializing and saving it first if needed
        val listeners = propertyChangeListeners

        // Get the collection of listeners for an observable, initializing & saving it if needed
        val observableListeners = listeners[observable] ?: ArrayMap<Int, PropertyChangeListener>()
            .also {
                listeners[observable] = it
                // Make sure we also register the callback for property changed on the
                // observable
                setupPropertyChangeListeners(observable)
            }

        // Add the listener
        observableListeners[bindingVariableId] = listener
    }

    @MainThread
    private fun setupPropertyChangeListeners(observable: Observable) {
        // Prepare the callback that triggers the right listener, based on the property id
        val callback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                val listeners = propertyChangeListeners[observable]!!
                listeners.entries.firstOrNull { it.key == propertyId }?.value?.invoke()
            }
        }

        // Persist the registered callback
        registeredPropertyChangeCallbacks[observable] = callback

        // Register the callback on the observable
        observable.addOnPropertyChangedCallback(callback)
    }

    /**
     * Cleans up the registered [PropertyChangeListeners](PropertyChangeListener).
     *
     * Must be called from [Activity.onDestroy] or [Fragment.onDestroy].
     */
    @CallSuper
    @MainThread
    fun onDestroy() {
        registeredPropertyChangeCallbacks.forEach { (observable, callback) ->
            observable.removeOnPropertyChangedCallback(callback)
        }
    }
}
