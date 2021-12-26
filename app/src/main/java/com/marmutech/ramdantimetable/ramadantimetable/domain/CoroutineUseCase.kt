package com.marmutech.ramdantimetable.ramadantimetable.domain

interface CoroutineUseCase<Param, Result> {
    suspend fun execute(param: Param): Result
}
