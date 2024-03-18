package ru.aroize.core.coroutines

import ru.aroize.core.di.ComponentHolder
import ru.aroize.core.di.DIComponent

interface CoroutineDispatchersComponent : DIComponent {
    val dispatchers: CoroutineDispatchers
}

object CoroutineDispatchersComponentHolder : ComponentHolder<CoroutineDispatchersComponent>() {
    override fun build(): CoroutineDispatchersComponent {
        return object : CoroutineDispatchersComponent {
            override val dispatchers: CoroutineDispatchers = CoroutineDispatchersImpl()
        }
    }
}