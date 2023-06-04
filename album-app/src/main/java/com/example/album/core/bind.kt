package com.example.album.core

import androidx.annotation.IdRes
import androidx.databinding.Bindable
import kotlin.reflect.KProperty

/**
 * Creates a new instance of a [BindableFieldsDelegate] that is initialized with the specified
 * [default value][default] and [fieldIds].
 *
 * The getters for the properties that change should be marked with @[Bindable] to generate a
 * field in `BR` to be used as `fieldIds`.
 *
 * @param default the initial value of the property
 * @param onlyDistinct whether it should compare values and notify listeners only if the property
 * value has changed (defaults to `true`)
 * @param fieldIds the field ids, as generated in `BR`, of the bindable properties whose
 * listeners should be notified when a new property is set on this property
 * @param T the property type
 */
fun <T> bind(default: T, @IdRes vararg fieldIds: Int, onlyDistinct: Boolean = true) =
    BindableFieldsDelegate<CoreViewModel, T>(default, onlyDistinct, fieldIds)

/**
 * Creates a new instance of a [BindableFieldDelegate] that is initialized with the specified
 * [default value][default] and [fieldId].
 *
 * The getter for the property that changes should be marked with @[Bindable] to generate a field
 * in `BR` to be used as `fieldId`.
 *
 * @param default the initial value of the property
 * @param onlyDistinct whether it should compare values and notify listeners only if the property
 * value has changed (defaults to `true`)
 * @param fieldId the field id, as generated in `BR`, of the bindable property whose
 * listeners should be notified when a new property is set on this property
 * @param T the property type
 */
fun <T> bind(default: T, @IdRes fieldId: Int, onlyDistinct: Boolean = true) =
    BindableFieldDelegate<CoreViewModel, T>(default, onlyDistinct, fieldId)

/**
 * Represents a value that, upon being set, notifies listeners that multiple bindable properties
 * have changed by calling [CoreViewModel.notifyPropertyChanged] for each.
 *
 * The getters for the properties that change should be marked with @[Bindable] to generate a
 * field in `BR` to be used as `fieldId`.
 *
 * To create an instance, use the [bind] function.
 *
 * @param defaultValue the initial value of the property
 * @param onlyDistinct whether it should compare values and notify listeners only if the property
 * value has changed
 * @param fieldIds the field ids, as generated in `BR`, of the bindable properties whose listeners
 * should be notified when a new property is set on this property
 * @param T the property type
 */
class BindableFieldsDelegate<in R : CoreViewModel, T>(
    defaultValue: T,
    private val onlyDistinct: Boolean,
    @IdRes private val fieldIds: IntArray
) {
    private var value: T = defaultValue

    operator fun getValue(thisRef: R, property: KProperty<*>): T = value

    operator fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        if (!onlyDistinct || this.value != value) {
            this.value = value
            fieldIds.forEach { thisRef.notifyPropertyChanged(it) }
        }
    }
}

/**
 * Represents a value that, upon being set, notifies listeners that a single bindable property has
 * changed by calling [CoreViewModel.notifyPropertyChanged].
 *
 * The getter for the property that changes should be marked with @[Bindable] to generate a field
 * in `BR` to be used as `fieldId`.
 *
 * To create an instance, use the [bind] function.
 *
 * @param defaultValue the initial value of the property
 * @param onlyDistinct whether it should compare values and notify listeners only if the property
 * value has changed
 * @param fieldId the field id, as generated in `BR`, of the bindable property whose listeners should
 * be notified when a new property is set on this property
 * @param T the property type
 */
class BindableFieldDelegate<in R : CoreViewModel, T>(
    defaultValue: T,
    private val onlyDistinct: Boolean,
    @IdRes private val fieldId: Int
) {
    private var value: T = defaultValue

    operator fun getValue(thisRef: R, property: KProperty<*>): T = value

    operator fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        if (!onlyDistinct || this.value != value) {
            this.value = value
            thisRef.notifyPropertyChanged(fieldId)
        }
    }
}
