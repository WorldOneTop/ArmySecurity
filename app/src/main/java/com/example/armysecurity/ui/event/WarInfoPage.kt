package com.example.armysecurity.ui.event

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.armysecurity.R
import com.example.armysecurity.databinding.PageWarInfoBinding
import com.example.armysecurity.db.AppDB
import com.example.armysecurity.ui.event.adapter.WarAdapter
import com.example.armysecurity.ui.search.CemeteryAdapter
import com.example.armysecurity.viewModels.CemeteryVM
import com.example.armysecurity.viewModels.WarVM
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.parceler.Parcels
import java.util.concurrent.TimeUnit

//전투정보
class WarInfoPage(private val viewModel: WarVM) : Fragment() {
    private lateinit var binding:PageWarInfoBinding
    private lateinit var adapter: WarAdapter
    private lateinit var filterDialog:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PageWarInfoBinding.inflate(layoutInflater)
        adapter = WarAdapter{
            findNavController().navigate(R.id.action_warFragment_to_warDetailFragment,
                bundleOf("type" to "War", "data" to Parcels.wrap(it))
            )
        }
        filterDialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.filter))
            .setItems(resources.getStringArray(R.array.war_field_array)) {
                    _, index ->
                binding.searchField.filterText.setText(resources.getStringArray(R.array.war_field_array)[index])
                binding.searchField.editText.setText("")
            }
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initListener()

        binding.rvWar.adapter = adapter
        binding.fieldLayout.name.text = getString(R.string.war_field_war_name)
        binding.fieldLayout.sub.text = getString(R.string.war_field_place)

        lifecycleScope.launch {
            viewModel.getWar("","").collectLatest { adapter.submitData(it) }
        }

        return binding.root
    }
    private fun initListener(){

        binding.searchField.filterText.setOnClickListener{
            filterDialog.show()
        }

        binding.searchField.editText.textChanges()
            .debounce(700, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .subscribe{
                getData()
            }
        adapter.addLoadStateListener {
            if(it.source.refresh is LoadState.Loading)
                binding.pbWarLayout.visibility = View.VISIBLE
            else if(it.source.refresh is LoadState.NotLoading) {
                binding.pbWarLayout.visibility = View.GONE
                if(adapter.itemCount == 0)
                    binding.emptyData.visibility = View.VISIBLE
                else
                    binding.emptyData.visibility = View.GONE
            }
        }
    }

    private fun getData() {
        lifecycleScope.launch {
                viewModel.getWar(
                    binding.searchField.filterText.text.toString(),binding.searchField.editText.text.toString()
                ).collectLatest { adapter.submitData(it) }
        }
    }
}