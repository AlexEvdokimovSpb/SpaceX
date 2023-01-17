package evdokimov.spacex.authorisation.presentation

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import evdokimov.spacex.App
import evdokimov.spacex.BackClickListener
import evdokimov.spacex.base.BasicFragment
import evdokimov.spacex.databinding.FragmentAuthorisationBinding
import moxy.ktx.moxyPresenter

class AuthorisationFragment : BasicFragment<FragmentAuthorisationBinding>(FragmentAuthorisationBinding::inflate),
    AuthorisationView, BackClickListener {

    companion object {

        fun newInstance() = AuthorisationFragment()
    }

    private val presenter: AuthorisationPresenter by moxyPresenter {
        AuthorisationPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.login.doAfterTextChanged { login ->
            presenter.loginChanged(login.toString())
        }
        binding.password.doAfterTextChanged { password ->
            presenter.passwordChanged(password.toString())
        }
        binding.confirmation.setOnClickListener {
            presenter.confirmationClick()
        }
    }

    override fun backPressed(): Boolean = presenter.backClick()
}