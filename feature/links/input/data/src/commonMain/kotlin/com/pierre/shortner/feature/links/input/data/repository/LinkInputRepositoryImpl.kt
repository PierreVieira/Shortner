package com.pierre.shortner.feature.links.input.data.repository

import com.pierre.shortner.core.room_provider.dao.LinkDao
import com.pierre.shortner.feature.links.input.data.dto.ShortenUrlDto
import com.pierre.shortner.feature.links.input.data.mapper.ShortenUrlDtoMapper
import com.pierre.shortner.feature.links.input.data.repository.datasource.LinkInputRemoteDataSource
import com.pierre.shortner.feature.links.input.domain.repository.LinkInputRepository

class LinkInputRepositoryImpl(
    private val remoteDataSource: LinkInputRemoteDataSource,
    private val linkDao: LinkDao,
    private val shortenUrlDtoMapper: ShortenUrlDtoMapper
) : LinkInputRepository {

    override suspend fun postUrl(url: String): Result<Unit> = remoteDataSource
        .postUrl(url)
        .map { response: ShortenUrlDto ->
            linkDao.insert(shortenUrlDtoMapper.toEntity(response))
        }

    override suspend fun getAllOriginalLinks(): List<String> =
        linkDao.getAll().map { it.originalLink }
}
