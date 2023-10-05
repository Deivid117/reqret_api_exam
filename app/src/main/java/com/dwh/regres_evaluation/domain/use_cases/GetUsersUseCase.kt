package com.dwh.regres_evaluation.domain.use_cases

import com.dwh.regres_evaluation.domain.model.response.Users
import com.dwh.regres_evaluation.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<List<Users>> {
        return userRepository.getAllUsers()
    }
}