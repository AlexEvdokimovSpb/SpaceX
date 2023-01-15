package evdokimov.spacex.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import evdokimov.spacex.App
import evdokimov.spacex.databinding.FragmentDetailsBinding
import evdokimov.spacex.image.ImageLoader
import evdokimov.spacex.main.BackClickListener
import evdokimov.spacex.news.domain.entity.Launch
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class DetailsFragment : MvpAppCompatFragment(), DetailsView, BackClickListener {

    private val presenter: DetailsPresenter by moxyPresenter {
        val launch = arguments?.getParcelable<Launch>(LAUNCH_ARG) as Launch
        DetailsPresenter(launch).apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var vb: FragmentDetailsBinding? = null
    val imageLoader = ImageLoader()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentDetailsBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun backPressed() = presenter.backClick()

    companion object {

        private const val LAUNCH_ARG = "launch"

        fun newInstance(launch: Launch) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(LAUNCH_ARG, launch)
            }
        }
    }

    override fun setName(name: String) {
        vb?.tvName?.text = name
    }

    override fun loadImage(url: String) {
        vb?.ivImage?.let { imageLoader.load(url, it) }
    }

    override fun setDate(date: String) {
        vb?.tvDate?.text = date
    }

    override fun setDetails(details: String) {
        vb?.tvDetails?.text = details
    }
}
