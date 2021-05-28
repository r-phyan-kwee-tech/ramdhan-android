package com.marmutech.ramdantimetable.ramadantimetable.domain.country

import com.marmutech.ramdantimetable.ramadantimetable.di.IOCoroutineDispatcher
import com.marmutech.ramdantimetable.ramadantimetable.domain.FlowUseCase
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.repository.TimeTableRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCountryListUseCase @Inject constructor(
    private val timeTableRepository: TimeTableRepo,
    @IOCoroutineDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, List<Country>> {
    override suspend fun execute(param: Unit): Flow<List<Country>> {
        return timeTableRepository.loadCountryList().flowOn(dispatcher)
    }
}
