package com.dwh.regres_evaluation.domain.use_cases

import com.dwh.regres_evaluation.domain.model.response.UserLogged
import com.dwh.regres_evaluation.domain.repository.LocalUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val localUserRepository: LocalUserRepository
) {
    suspend operator fun invoke(): Flow<UserLogged> {
        return localUserRepository.getUserInfo()
    }
}