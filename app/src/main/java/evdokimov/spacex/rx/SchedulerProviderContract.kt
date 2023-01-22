package evdokimov.spacex.rx

import io.reactivex.rxjava3.core.Scheduler

interface SchedulerProviderContract {
    fun ui(): Scheduler
    fun io(): Scheduler
    fun computation(): Scheduler
}
