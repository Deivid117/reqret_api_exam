package com.dwh.regres_evaluation.domain.use_cases

import com.dwh.regres_evaluation.domain.model.response.UserDeletedData
import com.dwh.regres_evaluation.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: Int): Flow<UserDeletedData> {
        return userRepository.deleteUser(id)
    }
}