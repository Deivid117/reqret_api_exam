package com.dwh.regres_evaluation.domain.use_cases

import com.dwh.regres_evaluation.domain.model.response.UserLogged
import com.dwh.regres_evaluation.domain.repository.LocalUserRepository
import javax.inject.Inject

class FindUserLogged @Inject constructor(
    private val userRepository: LocalUserRepository
) {

    suspend operator fun invoke(): UserLogged? {
        return userRepository.findUserLogged()
    }

}