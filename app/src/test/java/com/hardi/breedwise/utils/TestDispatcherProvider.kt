package com.hardi.breedwise.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider : DispatcherProvider{
    private val defaultDispatcherProvider = UnconfinedTestDispatcher()

    override val io : CoroutineDispatcher
    get() = defaultDispatcherProvider
    override val main: CoroutineDispatcher
        get() = defaultDispatcherProvider
    override val default: CoroutineDispatcher
        get() = defaultDispatcherProvider
}