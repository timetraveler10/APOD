package com.hussein.apod.data.repo

import coil.network.HttpException
import com.hussein.apod.data.local.ApodDatabase
import com.hussein.apod.data.mappers.toApod
import com.hussein.apod.data.mappers.toApodEntity
import com.hussein.apod.data.remote.KtorClientFactory
import com.hussein.apod.data.remote.dto.ApodDto
import com.hussein.apod.domain.model.Apod
import com.hussein.apod.domain.repo.ApodRepo
import com.hussein.apod.util.Constants.BASE_URL
import com.hussein.apod.util.DateUtils
import com.hussein.apod.util.Resource
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApodRepoImpl @Inject constructor(
    db: ApodDatabase,
    private val ktorClientFactory: KtorClientFactory
) : ApodRepo {


    private val dao = db.dao

    override fun getAllData(): Flow<Resource<List<Apod>>> {

        return flow {

            val allData = dao.getAllAPods()

            if (allData.isNotEmpty()) {

                if (DateUtils.compareDates(dao.getLastRecordDate()!!) && DateUtils.isAfter6Am()) {

                    emit(Resource.Loading())

                    val response = try {
                        ktorClientFactory.build().get<ApodDto?> {
                            url(BASE_URL)
                        }
                    } catch (e: HttpException) {
                        emit(Resource.Error(message = e.message))
                        null
                    }

                    response?.let { ApodDto ->
                        dao.insertApod(ApodDto.toApodEntity())
                        emit(Resource.Success(data = dao.getAllAPods().map { it.toApod() }))
                        return@flow
                    }
                }

                emit(Resource.Success(data = dao.getAllAPods().map { it.toApod() }))

            } else {

                emit(Resource.Loading())

                val response = try {
                    ktorClientFactory.build().get<ApodDto?> {
                        url(BASE_URL)
                    }

                } catch (e: HttpException) {
                    emit(Resource.Error(message = e.message))
                    null
                }
                response?.let {
                    dao.insertApod(it.toApodEntity())
                    emit(Resource.Success(data = dao.getAllAPods().map { it.toApod() }))
                }
            }

        }
    }

    override suspend fun getApodFromDb(id: Int): Apod {
        return withContext(Dispatchers.Main) {
            dao.getApod(id).toApod()
        }
    }

    override suspend fun deleteAllApods() {
        withContext(Dispatchers.Main) {
            dao.deleteAllApods()
        }
    }

    override suspend fun deleteApodFromDb(id: Int) {
        withContext(Dispatchers.Main) {
            dao.deleteApod(id = id)
        }
    }





    override fun getRandomApodsFromApi(): Flow<Resource<List<Apod>>> {
        return flow {
            emit(Resource.Loading())

            try {

                val factory = ktorClientFactory.build()
                val response = factory.get<List<ApodDto>> {
                    url(BASE_URL)
                    parameter(key = "count", value = "15")
                }
                emit(Resource.Success(data = response.map { it.toApod() }))

            } catch (e: Exception) {
                emit(Resource.Error(message = e.message))
            }
        }
    }
}