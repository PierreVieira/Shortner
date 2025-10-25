package com.pierre.shortner.feature.links.content.data.repository

import com.pierre.shortner.core.room_provider.dao.LinkDao
import com.pierre.shortner.feature.links.content.data.mapper.LinkEntityMapper
import com.pierre.shortner.feature.links.content.domain.model.LinkDomainModel
import com.pierre.shortner.feature.links.content.domain.repository.LinksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LinksRepositoryImpl(
    private val linkDao: LinkDao,
    private val mapper: LinkEntityMapper,
) : LinksRepository {

    override fun getAllLinks(): Flow<List<LinkDomainModel>> =
        linkDao.getAllAsFlow().map { entities ->
            entities.map(mapper::map)
        }
}
