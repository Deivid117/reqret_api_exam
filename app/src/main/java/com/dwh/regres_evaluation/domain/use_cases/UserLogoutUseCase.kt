package com.dwh.regres_evaluation.domain.use_cases

import com.dwh.regres_evaluation.domain.repository.LocalUserRepository
import javax.inject.Inject

class UserLogoutUseCase @Inject constructor(
    private val userRepository: LocalUserRepository
) {

    suspend operator fun invoke() {
        userRepository.deleteAll()
    }

}