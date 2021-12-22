package com.marmutech.ramdantimetable.ramadantimetable.util

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.exceptionHandler(block: (Throwable) -> Unit) =
    CoroutineExceptionHandler { _, throwable ->
        this.launch { block.invoke(throwable) }
    }
