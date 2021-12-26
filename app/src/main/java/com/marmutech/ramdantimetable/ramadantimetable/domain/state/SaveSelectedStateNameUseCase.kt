package com.marmutech.ramdantimetable.ramadantimetable.domain.state

import com.marmutech.ramdantimetable.ramadantimetable.domain.CoroutineUseCase
import com.marmutech.ramdantimetable.ramadantimetable.repository.UserSettingRepository
import javax.inject.Inject

class SaveSelectedStateNameUseCase @Inject constructor(private val userSettingRepository: UserSettingRepository) :
    CoroutineUseCase<String, Unit> {
    override suspend fun execute(param: String) {
        userSettingRepository.saveSelectedStateName(param)
    }
}
