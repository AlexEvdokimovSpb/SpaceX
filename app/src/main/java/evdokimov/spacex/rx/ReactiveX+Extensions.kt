package evdokimov.spacex.rx

import io.reactivex.rxjava3.annotations.*
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.functions.BiFunction
import org.reactivestreams.Publisher

/**
 * Shares the latest item emitted by that Publisher
 * [Flowable] -> [ConnectableFlowable] like a [BehaviorSubject]
 */
@CheckReturnValue
@BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
@SchedulerSupport(SchedulerSupport.NONE)
fun <T> Flowable<T>.toBehaviorFlowable() = replay(1).refCount()

@CheckReturnValue
@BackpressureSupport(BackpressureKind.PASS_THROUGH)
@SchedulerSupport(SchedulerSupport.NONE)
fun <T : Any, U : Any> Flowable<T>.withLatestFrom(other: Publisher<U>): Flowable<Pair<T, U>> =
        withLatestFrom(other, BiFunction { t, u -> Pair(t, u) })