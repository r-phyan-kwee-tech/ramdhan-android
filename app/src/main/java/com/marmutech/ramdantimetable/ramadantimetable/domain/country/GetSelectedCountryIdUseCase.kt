package com.marmutech.ramdantimetable.ramadantimetable.domain.country

import com.marmutech.ramdantimetable.ramadantimetable.domain.CoroutineUseCase
import com.marmutech.ramdantimetable.ramadantimetable.repository.UserSettingRepository
import javax.inject.Inject

class GetSelectedCountryIdUseCase @Inject constructor(private val userSettingRepository: UserSettingRepository) :
    CoroutineUseCase<Unit, String?> {
    override suspend fun execute(param: Unit) =
        userSettingRepository.getSelectedCountryId()
}
