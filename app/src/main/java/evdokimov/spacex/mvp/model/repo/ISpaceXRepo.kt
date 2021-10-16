package evdokimov.spacex.mvp.model.repo

import evdokimov.spacex.mvp.model.entity.Launch
import io.reactivex.rxjava3.core.Single

interface ISpaceXRepo {
    fun getLaunches(): Single<List<Launch>>
}