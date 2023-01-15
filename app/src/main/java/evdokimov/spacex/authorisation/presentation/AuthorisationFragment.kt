package evdokimov.spacex.authorisation.presentation

import android.os.Bundle
import evdokimov.spacex.BackClickListener
import evdokimov.spacex.base.BasicFragment
import evdokimov.spacex.databinding.FragmentAuthorisationBinding
import moxy.ktx.moxyPresenter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AuthorisationFragment : BasicFragment<FragmentAuthorisationBinding>(FragmentAuthorisationBinding::inflate), AuthorisationView,
    BackClickListener {

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserFragment.
         */ // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = AuthorisationFragment().apply {
            arguments = Bundle().apply { //                putString(ARG_PARAM1, param1)
                //                putString(ARG_PARAM2, param2)
            }
        }
    }

    private val presenter: AuthorisationPresenter by moxyPresenter {
        AuthorisationPresenter().apply {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { //            param1 = it.getString(ARG_PARAM1)
            //            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun backPressed() = presenter.backClick()
}