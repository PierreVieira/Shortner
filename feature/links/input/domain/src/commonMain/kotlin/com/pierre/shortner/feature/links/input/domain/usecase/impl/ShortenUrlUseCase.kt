package com.pierre.shortner.feature.links.input.domain.usecase.impl

import com.pierre.shortner.feature.links.input.domain.model.ShortenUrlStep
import com.pierre.shortner.feature.links.input.domain.repository.LinkInputRepository
import com.pierre.shortner.feature.links.input.domain.usecase.ShortenUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ShortenUrlUseCase(
    private val validateUrl: ValidateUrlUseCase,
    private val repository: LinkInputRepository,
) : ShortenUrl {
    override fun invoke(url: String): Flow<ShortenUrlStep> = flow {
        val safeUrl = url.trim()
        validateUrl(safeUrl)
            .onSuccess {
                emit(ShortenUrlStep.InProgress)
                emit(getStepFromPost(safeUrl))
            }.onFailure {
                emit(ShortenUrlStep.Error(it))
            }
    }

    private suspend fun getStepFromPost(url: String): ShortenUrlStep =
        repository.postUrl(url)
            .fold(
                onSuccess = {
                    ShortenUrlStep.Success
                },
                onFailure = {
                    ShortenUrlStep.Error(it)
                }
            )

}
