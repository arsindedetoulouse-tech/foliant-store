package com.arsinde.foliantstore.domain.home

interface HomeRepository {
    suspend fun load(): HomeModel?
}