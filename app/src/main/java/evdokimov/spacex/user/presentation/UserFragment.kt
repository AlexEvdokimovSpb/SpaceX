package evdokimov.spacex.user.presentation

import android.os.Bundle
import android.view.View
import evdokimov.spacex.App
import evdokimov.spacex.BackClickListener
import evdokimov.spacex.base.BasicFragment
import evdokimov.spacex.databinding.FragmentUserBinding
import moxy.ktx.moxyPresenter

class UserFragment : BasicFragment<FragmentUserBinding>(FragmentUserBinding::inflate), UserView, BackClickListener {

    companion object {

        fun newInstance() = UserFragment()
    }

    private val presenter: UserPresenter by moxyPresenter {
        UserPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logOut.setOnClickListener {
            presenter.logOutClick()
        }
    }

    override fun backPressed(): Boolean = presenter.backClick()
}