package com.hugopolog.domain.usecase

import com.hugopolog.domain.repository.MainRepository
import javax.inject.Inject

class ExampleUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke() = repository.doSomething()
    }
