package com.now.desafio.android.ui.navigations.mainFlow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.now.desafio.android.R
import com.now.desafio.android.ui.adapter.ArtistListAdapter
import com.now.desafio.android.util.ext.navigateWithAnimation
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainListFragment : Fragment() {
    val viewModel: MainViewModel by viewModel()

    private val adapter: ArtistListAdapter by lazy {
        ArtistListAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navArgs by navArgs<MainListFragmentArgs>()
        navArgs.user.let {
            viewModel.setUser(it)
            viewModel.listAllArtists()
        }
        rv_users.adapter = adapter
        subscribe()
        setupListeners()
    }

    private fun subscribe() {
        viewModel.loadUsesrObserver.observe(viewLifecycleOwner, Observer {
            swipe_refresh.isRefreshing = it
        })

        viewModel.userObserver.observe(viewLifecycleOwner, Observer { user ->
            tv_name.text = user.name
            tv_name.setOnClickListener {
                val navOptions = MainListFragmentDirections.actionMainListFragment2ToRegisterOrEditNameFragment(user)
                findNavController().navigateWithAnimation(navOptions)
            }
        })

        viewModel.alertOfflineObserver.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, getString(it), Toast.LENGTH_SHORT).show()
        })

        viewModel.errorObserver.observe(viewLifecycleOwner, Observer {
            tv_error.visibility = View.VISIBLE
            rv_users.visibility = View.GONE
            tv_error.text = it
        })

        viewModel.resultUsersObserver.observe(viewLifecycleOwner, Observer {
            tv_error.visibility = View.GONE
            rv_users.visibility = View.VISIBLE
            adapter.users = it
            adapter.notifyDataSetChanged()
        })

        viewModel.resultUsersWithFilterObserver.observe(viewLifecycleOwner, Observer {
            adapter.users = it
        })
    }

    private fun setupListeners() {
        tv_exit.setOnClickListener {
            findNavController().navigate(R.id.action_mainListFragment2_to_loginFragment2)
        }
        card_search.setOnClickListener {
            search.requestFocusFromTouch()
            search.requestFocus()
            search.onActionViewExpanded()
        }
        swipe_refresh.setOnRefreshListener {
            viewModel.listAllArtists()
        }
        adapter.eventClick = {
            val action = MainListFragmentDirections.actionMainListFragment2ToMainUserDetailedFragment2(it)
            findNavController().navigateWithAnimation(action)
        }

        adapter.eventLongClick = {
            val messageDialog = if (!it.favorited) "Deseja adicionar ${it.name} aos favoritos?" else "Deseja remover ${it.name} dos favoritos?"
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.favoritos))
                .setMessage(messageDialog)
                .setNegativeButton(getString(R.string.nao)) { dialog, _ -> dialog.dismiss() }
                .setPositiveButton(getString(R.string.sim)) { dialog, _ ->
                    viewModel.changeFavorite(it)
                    dialog.dismiss()
                }.show()
        }
        search.setOnCloseListener {
            viewModel.applyFilter("")
            return@setOnCloseListener true
        }
        search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                text?.let { viewModel.applyFilter(it) }
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                text?.let { viewModel.applyFilter(it) }
                return true
            }
        })
    }
}