package com.example.armysecurity.ui.event

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.armysecurity.R
import com.example.armysecurity.databinding.FragmentSaleBinding
import com.example.armysecurity.databinding.PageWarManBinding
import com.example.armysecurity.db.AppDB
import com.example.armysecurity.ui.event.adapter.SaleAdapter
import com.example.armysecurity.ui.event.adapter.WarManAdapter
import com.example.armysecurity.viewModels.SaleVM
import com.example.armysecurity.viewModels.WarVM
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.parceler.Parcels
import java.util.concurrent.TimeUnit


class SaleFragment : Fragment() {
    private lateinit var binding:FragmentSaleBinding
    private lateinit var adapter: SaleAdapter
    private lateinit var filterDialog: AlertDialog
    private val viewModel: SaleVM by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                SaleVM(AppDB.getInstance(requireContext()).dbDao()) as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSaleBinding.inflate(layoutInflater)
        adapter = SaleAdapter{
            findNavController().navigate(R.id.action_saleFragment_to_warDetailFragment,
                bundleOf("type" to "Sale", "data" to Parcels.wrap(it))
            )
        }
        filterDialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.filter))
            .setItems(resources.getStringArray(R.array.sale_field_array)) {
                    _, index ->
                binding.searchField.filterText.setText(resources.getStringArray(R.array.sale_field_array)[index])
                binding.searchField.editText.setText("")
            }
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initListener()

        binding.rv.adapter = adapter
        binding.fieldLayout.name.text = getString(R.string.sale_field_inst)
        binding.fieldLayout.sub.text = getString(R.string.sale_field_dcnt)

        lifecycleScope.launch {
            viewModel.getData("","").collectLatest { adapter.submitData(it) }
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
                binding.pbLayout.visibility = View.VISIBLE
            else if(it.source.refresh is LoadState.NotLoading) {
                binding.pbLayout.visibility = View.GONE
                if(adapter.itemCount == 0)
                    binding.emptyData.visibility = View.VISIBLE
                else
                    binding.emptyData.visibility = View.GONE
            }
        }
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.getData(
                binding.searchField.filterText.text.toString(),binding.searchField.editText.text.toString()
            ).collectLatest { adapter.submitData(it) }
        }
    }
}