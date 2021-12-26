package com.marmutech.ramdantimetable.ramadantimetable.domain.country

import com.marmutech.ramdantimetable.ramadantimetable.domain.CoroutineUseCase
import com.marmutech.ramdantimetable.ramadantimetable.repository.UserSettingRepository
import javax.inject.Inject

class SaveSelectedCountryIdUseCase @Inject constructor(private val userSettingRepository: UserSettingRepository) :
    CoroutineUseCase<String, Unit> {
    override suspend fun execute(param: String): Unit = userSettingRepository.saveSelectedCountryId(
        param
    )

}
