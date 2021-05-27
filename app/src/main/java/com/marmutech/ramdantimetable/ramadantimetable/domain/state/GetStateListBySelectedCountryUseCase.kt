package com.marmutech.ramdantimetable.ramadantimetable.domain.state

import com.marmutech.ramdantimetable.ramadantimetable.domain.FlowUseCase
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import com.marmutech.ramdantimetable.ramadantimetable.repository.TimeTableRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetStateListBySelectedCountryUseCase @Inject constructor(private val timeTableRepo: TimeTableRepo) :
    FlowUseCase<String, List<State>> {
    override suspend fun execute(param: String): Flow<List<State>> = timeTableRepo.loadStateList(
        param
    ).flowOn(Dispatchers.IO)
}
