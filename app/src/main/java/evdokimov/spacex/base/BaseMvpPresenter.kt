package evdokimov.spacex.base

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter

abstract class BaseMvpPresenter<View : BaseMvpView> : MvpPresenter<View>() {

    protected val compositeDisposable = CompositeDisposable()

    protected fun Disposable.autoDisposable(): Disposable = apply { compositeDisposable.add(this) }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}