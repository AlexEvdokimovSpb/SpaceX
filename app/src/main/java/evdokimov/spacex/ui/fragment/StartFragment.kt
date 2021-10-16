package evdokimov.spacex.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import evdokimov.spacex.App
import evdokimov.spacex.R
import evdokimov.spacex.databinding.FragmentStartBinding
import evdokimov.spacex.mvp.presenter.StartPresenter
import evdokimov.spacex.mvp.view.IStartFragmentView
import evdokimov.spacex.ui.BackClickListener
import evdokimov.spacex.ui.RVAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import android.view.MenuInflater

class StartFragment : MvpAppCompatFragment(), IStartFragmentView, BackClickListener {

    private val presenter by moxyPresenter {
        StartPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }
    private var vb: FragmentStartBinding? = null
    private var adapter: RVAdapter? = null
    private var isSorted = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentStartBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun init() {
        vb?.rvList?.layoutManager = LinearLayoutManager(requireContext())
        vb?.rvList?.addItemDecoration(DividerItemDecoration(requireContext(),
            LinearLayoutManager.VERTICAL))
        adapter = RVAdapter(presenter.launchesListPresenter)
        vb?.rvList?.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backClick()

    companion object {
        fun newInstance() = StartFragment()
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
                    Toast.makeText(requireContext(),
                        getString(R.string.new_dates_first),
                        Toast.LENGTH_LONG).show()
                } else {
                    adapter?.sortDescendingDate()
                    isSorted = false
                    Toast.makeText(requireContext(),
                        getString(R.string.old_dates_first),
                        Toast.LENGTH_LONG).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
