package com.pierre.shortner.feature.links.data.repository

import com.pierre.shortner.core.room_provider.dao.LinkDao
import com.pierre.shortner.feature.links.data.datasource.LinksRemoteDataSource
import com.pierre.shortner.feature.links.data.mapper.toDomain
import com.pierre.shortner.feature.links.data.mapper.toEntity
import com.pierre.shortner.feature.links.domain.model.Link
import com.pierre.shortner.feature.links.domain.repository.LinksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LinksRepositoryImpl(
    private val remoteDataSource: LinksRemoteDataSource,
    private val linkDao: LinkDao,
) : LinksRepository {

    override suspend fun shortenUrl(url: String): Result<Unit> =
        remoteDataSource.shortenUrl(url).mapCatching { response ->
            val link = response.toDomain()
            val entity = link.toEntity()
            val id = linkDao.insert(entity)
            link.copy(id = id)
        }

    override fun getAllLinks(): Flow<List<Link>> {
        return linkDao.getAllAsFlow().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun deleteLink(id: Long) {
        linkDao.deleteById(id)
    }

    override suspend fun deleteAllLinks() {
        linkDao.clear()
    }
}
