package evdokimov.spacex.details.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import evdokimov.spacex.*
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

    override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
                view,
                savedInstanceState
        )

        binding.favoriteImage.setOnClickListener {
            presenter.onFavoriteIconClick()
        }
    }

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

    override fun setFavoriteImage(isFavorite: Boolean) {
        if (isFavorite) {
            binding.favoriteImage.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.favoriteImage.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    override fun showNeedLogIn() = showMessage(R.string.log_in)

    override fun backPressed() = presenter.backClick()
}
