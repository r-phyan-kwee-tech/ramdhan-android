package com.marmutech.ramdantimetable.ramadantimetable.domain.state

import com.marmutech.ramdantimetable.ramadantimetable.domain.CoroutineUseCase
import com.marmutech.ramdantimetable.ramadantimetable.repository.UserSettingRepository
import javax.inject.Inject

class GetSelectedStateIdUseCase @Inject constructor(private val userSettingRepository: UserSettingRepository) :
    CoroutineUseCase<Unit, String?> {
    override suspend fun execute(param: Unit): String? = userSettingRepository.getSelectedStateId()
}
