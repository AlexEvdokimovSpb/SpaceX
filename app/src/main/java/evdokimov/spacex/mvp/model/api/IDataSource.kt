package evdokimov.spacex.mvp.model.api

import evdokimov.spacex.mvp.model.entity.Launch
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface IDataSource {
    @GET("v4/launches")
    fun getLaunches(): Single<List<Launch>>
}