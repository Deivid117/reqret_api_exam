package com.dwh.regres_evaluation.domain.use_cases

import com.dwh.regres_evaluation.domain.model.request.UserRegister
import com.dwh.regres_evaluation.domain.model.response.UserData
import com.dwh.regres_evaluation.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRegistrationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userRegister: UserRegister): Flow<UserData> {
        return userRepository.userRegister(userRegister)
    }
}