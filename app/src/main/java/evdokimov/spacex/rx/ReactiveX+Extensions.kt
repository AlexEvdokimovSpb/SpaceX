package evdokimov.spacex.rx

import io.reactivex.rxjava3.annotations.*
import io.reactivex.rxjava3.core.Flowable

/**
 * Shares the latest item emitted by that Publisher
 * [Flowable] -> [ConnectableFlowable] like a [BehaviorSubject]
 */
@CheckReturnValue
@BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
@SchedulerSupport(SchedulerSupport.NONE)
fun <T> Flowable<T>.toBehaviorFlowable() = replay(1).refCount()