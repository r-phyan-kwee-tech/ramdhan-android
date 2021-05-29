package com.marmutech.ramdantimetable.ramadantimetable.domain.state

import com.marmutech.ramdantimetable.ramadantimetable.domain.CoroutineUseCase

class GetSelectedStateIdUseCase : CoroutineUseCase<Unit, String> {
    override suspend fun execute(param: Unit): String {
        TODO("Not yet implemented")
    }
}
