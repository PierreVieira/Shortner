package com.pierre.shortner.feature.links.content.domain.usecase

import com.pierre.shortner.feature.links.content.domain.model.LinkDomainModel
import kotlinx.coroutines.flow.Flow

fun interface GetAllLinks {
    operator fun invoke(): Flow<List<LinkDomainModel>>
}
