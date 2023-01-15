package evdokimov.spacex.news.presentation

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import evdokimov.spacex.*
import evdokimov.spacex.base.BasicFragment
import evdokimov.spacex.databinding.FragmentNewsBinding
import evdokimov.spacex.news.presentation.list.NewsAdapter
import moxy.ktx.moxyPresenter

class NewsFragment : BasicFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate), NewsView, BackClickListener {

    private val presenter by moxyPresenter {
        NewsPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }
    private var adapter: NewsAdapter? = null
    private var isSorted = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun init() {
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvList.addItemDecoration(
            DividerItemDecoration(
                requireContext(), LinearLayoutManager.VERTICAL
            )
        )
        adapter = NewsAdapter(presenter.launchesListPresenter)
        binding.rvList.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backClick()

    companion object {

        fun newInstance() = NewsFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort -> {
                if (!isSorted) {
                    adapter?.sortAscendingDate()
                    isSorted = true
                    Toast.makeText(
                        requireContext(), getString(R.string.new_dates_first), Toast.LENGTH_LONG
                    ).show()
                } else {
                    adapter?.sortDescendingDate()
                    isSorted = false
                    Toast.makeText(
                        requireContext(), getString(R.string.old_dates_first), Toast.LENGTH_LONG
                    ).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
