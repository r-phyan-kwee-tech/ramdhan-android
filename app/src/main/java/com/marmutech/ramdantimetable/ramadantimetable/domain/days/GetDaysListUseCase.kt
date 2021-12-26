package com.marmutech.ramdantimetable.ramadantimetable.domain.days

import com.marmutech.ramdantimetable.ramadantimetable.di.IOCoroutineDispatcher
import com.marmutech.ramdantimetable.ramadantimetable.domain.FlowUseCase
import com.marmutech.ramdantimetable.ramadantimetable.model.TimeTableDay
import com.marmutech.ramdantimetable.ramadantimetable.repository.TimeTableRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetDaysListUseCase @Inject constructor(
    private val timeTableRepo: TimeTableRepo,
    @IOCoroutineDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, List<TimeTableDay>> {
    override suspend fun execute(param: Unit): Flow<List<TimeTableDay>> {
        return timeTableRepo.loadDays().flowOn(dispatcher)
    }
}
