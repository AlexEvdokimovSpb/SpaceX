package evdokimov.spacex.news.presentation

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import evdokimov.spacex.*
import evdokimov.spacex.base.BasicFragment
import evdokimov.spacex.databinding.FragmentNewsBinding
import evdokimov.spacex.news.domain.entity.Launch
import evdokimov.spacex.news.presentation.list.NewsAdapter
import moxy.ktx.moxyPresenter

class NewsFragment : BasicFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate), NewsView, BackClickListener {

    companion object {

        fun newInstance() = NewsFragment()
    }

    private val presenter by moxyPresenter {
        NewsPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNewsAdapter()
        presenter.update()
    }

    private fun initNewsAdapter() {
        newsAdapter = NewsAdapter()
        newsAdapter.apply {
            onClickListener = presenter::onNewsSelect
        }
        with(binding.newsList) {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), LinearLayoutManager.VERTICAL
                )
            )
            adapter = newsAdapter
        }
    }

    override fun updateView(launches: List<Launch>) = newsAdapter.setData(launches)

    override fun backPressed() = presenter.backClick()
}
