package me.glaremasters.vpfms.base

internal interface State {
    fun load()
    fun kill()
}