@file:Suppress("UNCHECKED_CAST")

package ru.aroize.core.recycler

fun<T : ListItem> AdapterDelegate<*>.castAdapterDelegate(): AdapterDelegate<T>
        = this as AdapterDelegate<T>