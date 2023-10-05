package com.dwh.regres_evaluation.domain.use_cases

import com.dwh.regres_evaluation.domain.model.response.UserLogged
import com.dwh.regres_evaluation.domain.repository.LocalUserRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val userRepository: LocalUserRepository
) {
    suspend operator fun invoke(user: UserLogged) {
        userRepository.insertUser(user)
    }
}