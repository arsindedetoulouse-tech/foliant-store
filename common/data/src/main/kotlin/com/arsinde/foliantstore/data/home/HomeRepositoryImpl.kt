package com.arsinde.foliantstore.data.home

import com.arsinde.foliantstore.domain.home.HomeModel
import com.arsinde.foliantstore.domain.home.HomeRepository
import kotlinx.coroutines.CancellationException

class HomeRepositoryImpl(
    private val dataSource: HomeDataSource,
) : HomeRepository {
    override suspend fun load(): HomeModel? {
        val remote = try {
            dataSource.loadRemote()
        } catch (e: Throwable) {
            if (e is CancellationException) throw e
            dataSource.loadLocal() ?: throw e
        }

        try {
            dataSource.saveLocal(remote)
        } catch (e: Throwable) {
            if (e is CancellationException) throw e
        }

        return remote
    }
}