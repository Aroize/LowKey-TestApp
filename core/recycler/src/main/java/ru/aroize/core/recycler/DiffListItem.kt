package ru.aroize.core.recycler

interface DiffListItem : ListItem {
    fun areItemsSame(other: DiffListItem): Boolean
    fun areContentsSame(other: DiffListItem): Boolean
}