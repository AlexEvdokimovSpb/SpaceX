package evdokimov.spacex.details.presentation

import androidx.core.os.bundleOf
import evdokimov.spacex.App
import evdokimov.spacex.BackClickListener
import evdokimov.spacex.base.BasicFragment
import evdokimov.spacex.databinding.FragmentDetailsBinding
import evdokimov.spacex.image.ImageLoader
import moxy.ktx.moxyPresenter

class DetailsFragment : BasicFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate),
        DetailsView,
        BackClickListener {

    companion object {

        private const val LAUNCH_ARG = "launchId"

        fun newInstance(id: String) = DetailsFragment().apply {
            arguments = bundleOf(LAUNCH_ARG to id)
        }
    }

    private val presenter: DetailsPresenter by moxyPresenter {
        DetailsPresenter(getLaunchId()).apply {
            App.instance.appComponent.inject(this)
        }
    }

    private fun getLaunchId() = this.arguments?.getString(LAUNCH_ARG)
            .toString()

    val imageLoader = ImageLoader()

    override fun backPressed() = presenter.backClick()

    override fun setName(name: String) {
        binding.launchName.text = name
    }

    override fun loadImage(url: String) {
        binding.launchImage.let {
            imageLoader.load(
                    url,
                    it
            )
        }
    }

    override fun setDate(date: String) {
        binding.launchDate.text = date
    }

    override fun setDetails(details: String) {
        binding.launchDetails.text = details
    }
}
