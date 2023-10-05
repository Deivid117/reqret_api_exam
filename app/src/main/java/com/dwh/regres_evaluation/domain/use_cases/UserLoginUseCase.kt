package com.dwh.regres_evaluation.domain.use_cases

import com.dwh.regres_evaluation.domain.model.request.UserLogin
import com.dwh.regres_evaluation.domain.model.response.UserToken
import com.dwh.regres_evaluation.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userLogin: UserLogin): Flow<UserToken> {
        return userRepository.userLogin(userLogin)
    }
}