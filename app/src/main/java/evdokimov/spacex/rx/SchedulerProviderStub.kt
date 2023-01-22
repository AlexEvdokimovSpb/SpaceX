package evdokimov.spacex.rx

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class SchedulerProviderStub : SchedulerProviderContract {
    override fun ui(): Scheduler = Schedulers.trampoline()
    override fun io(): Scheduler = Schedulers.trampoline()
    override fun computation(): Scheduler = Schedulers.trampoline()
}
