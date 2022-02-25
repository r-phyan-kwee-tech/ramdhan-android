package com.marmutech.ramdantimetable.ramadantimetable.domain

import com.marmutech.ramdantimetable.ramadantimetable.repository.UserSettingRepository
import javax.inject.Inject

class SaveIsOnBoardingFinishUseCase @Inject constructor(private val userSettingRepository: UserSettingRepository) :
    CoroutineUseCase<Boolean, Unit> {
    override suspend fun execute(param: Boolean) = userSettingRepository.saveIsOnBoardingFinish(param)
}
