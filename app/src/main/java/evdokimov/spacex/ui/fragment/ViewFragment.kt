package evdokimov.spacex.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import evdokimov.spacex.App
import evdokimov.spacex.databinding.FragmentViewBinding
import evdokimov.spacex.mvp.model.entity.Launch
import evdokimov.spacex.mvp.presenter.ViewPresenter
import evdokimov.spacex.mvp.view.IViewFragmentView
import evdokimov.spacex.ui.BackClickListener
import evdokimov.spacex.ui.ImageLoader
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ViewFragment : MvpAppCompatFragment(), IViewFragmentView, BackClickListener {

    private val presenter: ViewPresenter by moxyPresenter {
        val launch = arguments?.getParcelable<Launch>(LAUNCH_ARG) as Launch
        ViewPresenter(launch).apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var vb: FragmentViewBinding? = null
    val imageLoader = ImageLoader()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentViewBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun backPressed() = presenter.backClick()

    companion object {
        private const val LAUNCH_ARG = "launch"

        fun newInstance(launch: Launch) = ViewFragment().apply {
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
