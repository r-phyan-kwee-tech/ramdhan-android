package com.marmutech.ramdantimetable.ramadantimetable.domain

import kotlinx.coroutines.flow.Flow


interface FlowUseCase<T, R> {
    suspend fun execute(t: T): Flow<R>
}
