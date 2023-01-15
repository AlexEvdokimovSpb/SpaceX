package evdokimov.spacex.details.presentation

import android.os.Bundle
import evdokimov.spacex.App
import evdokimov.spacex.BackClickListener
import evdokimov.spacex.base.BasicFragment
import evdokimov.spacex.databinding.FragmentDetailsBinding
import evdokimov.spacex.image.ImageLoader
import evdokimov.spacex.news.domain.entity.Launch
import moxy.ktx.moxyPresenter

class DetailsFragment : BasicFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate), DetailsView,
    BackClickListener {

    private val presenter: DetailsPresenter by moxyPresenter {
        val launch = arguments?.getParcelable<Launch>(LAUNCH_ARG) as Launch
        DetailsPresenter(launch).apply {
            App.instance.appComponent.inject(this)
        }
    }

    val imageLoader = ImageLoader()

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
        binding.tvName.text = name
    }

    override fun loadImage(url: String) {
        binding.ivImage.let { imageLoader.load(url, it) }
    }

    override fun setDate(date: String) {
        binding.tvDate.text = date
    }

    override fun setDetails(details: String) {
        binding.tvDetails.text = details
    }
}
