package com.dwh.regres_evaluation.domain.use_cases

import com.dwh.regres_evaluation.domain.model.request.UpdateUser
import com.dwh.regres_evaluation.domain.model.response.UserUpdated
import com.dwh.regres_evaluation.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(id: Int, user: UpdateUser): Flow<UserUpdated> {
        return userRepository.updateUser(id, user)
    }

}