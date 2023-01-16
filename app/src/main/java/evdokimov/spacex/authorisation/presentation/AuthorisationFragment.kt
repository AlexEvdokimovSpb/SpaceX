package evdokimov.spacex.authorisation.presentation

import evdokimov.spacex.App
import evdokimov.spacex.BackClickListener
import evdokimov.spacex.base.BasicFragment
import evdokimov.spacex.databinding.FragmentAuthorisationBinding
import moxy.ktx.moxyPresenter

class AuthorisationFragment : BasicFragment<FragmentAuthorisationBinding>(FragmentAuthorisationBinding::inflate),
    AuthorisationView, BackClickListener {

    companion object {

        @JvmStatic
        fun newInstance() = AuthorisationFragment()
    }

    private val presenter: AuthorisationPresenter by moxyPresenter {
        AuthorisationPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun backPressed(): Boolean = presenter.backClick()
}