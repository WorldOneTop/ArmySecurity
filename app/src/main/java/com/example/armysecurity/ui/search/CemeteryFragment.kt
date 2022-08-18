package com.example.armysecurity.ui.search

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.armysecurity.R
import com.example.armysecurity.databinding.FragmentCemeteryBinding
import com.example.armysecurity.db.AppDB
import com.example.armysecurity.viewModels.CemeteryVM
import com.example.armysecurity.viewModels.CemeteryVMFactory
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.parceler.Parcels
import java.util.concurrent.TimeUnit


class CemeteryFragment : Fragment() {
    private lateinit var binding:FragmentCemeteryBinding
    private lateinit var filterDialog:AlertDialog
    private lateinit var filterArray:Array<String>
    private lateinit var viewModel: CemeteryVM
    private lateinit var adapter: CemeteryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCemeteryBinding.inflate(layoutInflater)
        filterArray = resources.getStringArray(R.array.cemetery_field_array)
        filterDialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.filter))
            .setItems(resources.getStringArray(R.array.cemetery_field_array)) {
                    _, index ->
                binding.searchField.filterText.setText(filterArray[index])
                getData()
            }
            .create()
        adapter = CemeteryAdapter{
            findNavController().navigate(R.id.action_cemeteryFragment_to_cemeteryDetailFragment,
                bundleOf("type" to "seoul", "data" to Parcels.wrap(it)))
        }
        viewModel = ViewModelProvider(this, CemeteryVMFactory(
            AppDB.getInstance(requireContext()).dbDao()
        ))[CemeteryVM::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initListener()

        binding.rvCemetery.adapter = adapter

        lifecycleScope.launch {
            viewModel.data.collectLatest { adapter.submitData(it) }
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
                binding.pbCemeteryLayout.visibility = View.VISIBLE
            else if(it.source.refresh is LoadState.NotLoading) {
                binding.pbCemeteryLayout.visibility = View.GONE
                if(adapter.itemCount == 0)
                    binding.emptyData.visibility = View.VISIBLE
                else
                    binding.emptyData.visibility = View.GONE
            }

        }
    }
    private fun getData(){
        if(viewModel.setFilter(binding.searchField.filterText.text.toString(), binding.searchField.editText.text.toString()))
            lifecycleScope.launch {
                viewModel.data.collectLatest { adapter.submitData(it) }
            }
    }

}
