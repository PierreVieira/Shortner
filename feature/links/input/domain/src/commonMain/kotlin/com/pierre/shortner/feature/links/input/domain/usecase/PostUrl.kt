package com.pierre.shortner.feature.links.input.domain.usecase

fun interface PostUrl {
   suspend operator fun invoke(url: String): Result<Unit>
}
