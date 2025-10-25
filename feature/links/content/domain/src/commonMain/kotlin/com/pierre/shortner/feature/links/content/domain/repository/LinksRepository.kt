package com.pierre.shortner.feature.links.content.domain.repository

import com.pierre.shortner.feature.links.content.domain.model.LinkDomainModel
import kotlinx.coroutines.flow.Flow

interface LinksRepository {
    fun getAllLinks(): Flow<List<LinkDomainModel>>
}
