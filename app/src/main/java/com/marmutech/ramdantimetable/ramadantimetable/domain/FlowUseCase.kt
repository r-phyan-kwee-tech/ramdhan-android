package com.marmutech.ramdantimetable.ramadantimetable.domain

import kotlinx.coroutines.flow.Flow


interface FlowUseCase<Param, Result> {
    suspend fun execute(param: Param): Flow<Result>
}
