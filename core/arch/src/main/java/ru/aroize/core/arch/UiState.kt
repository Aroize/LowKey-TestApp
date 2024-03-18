package ru.aroize.core.arch


interface Content
interface Loading
interface Error

object DefaultLoading: Loading
class DefaultError(val message: String): Error

data class UiState<C: Content, L: Loading, E: Error>(
    val content: C,
    val loading: L? = null,
    val error: E? = null
) {

    enum class State { READY, LOADING, FAILED }

    val state: State
        get() {
            if (loading != null) return State.LOADING
            if (error != null) return State.FAILED
            return State.READY
        }
}

