package com.dwh.regres_evaluation.domain.use_cases

import com.dwh.regres_evaluation.domain.model.request.CreateUser
import com.dwh.regres_evaluation.domain.model.response.UserCreated
import com.dwh.regres_evaluation.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: CreateUser): Flow<UserCreated> {
        return userRepository.createUser(user)
    }
}