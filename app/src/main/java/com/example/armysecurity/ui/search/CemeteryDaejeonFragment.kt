package com.example.armysecurity.ui.search

import android.app.Dialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.armysecurity.R
import com.example.armysecurity.data.CemeteryDaejeon
import com.example.armysecurity.databinding.DialogDaejeonFilterBinding
import com.example.armysecurity.databinding.FragmentCemeteryDaejeonBinding
import com.example.armysecurity.viewModels.CemeteryDaejeonVM
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.parceler.Parcels


class CemeteryDaejeonFragment : Fragment() {
    private lateinit var binding:FragmentCemeteryDaejeonBinding
    private lateinit var viewModel: CemeteryDaejeonVM
    private lateinit var adapter: CemeteryDaejeonAdapter
    private lateinit var filterDialog: Dialog
    private lateinit var filterBinding: DialogDaejeonFilterBinding
    private var searchData:CemeteryDaejeon = CemeteryDaejeon(
        "",null,null,null,null,null,null,null,null,null,null,null,null,null
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCemeteryDaejeonBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CemeteryDaejeonVM::class.java]
        adapter = CemeteryDaejeonAdapter{
            findNavController().navigate(R.id.action_cemeteryDaejeonFragment_to_cemeteryDetailFragment,
                bundleOf("type" to "daejeon", "data" to Parcels.wrap(it))
            )
        }
        filterDialog = Dialog(requireContext())
        filterBinding = DialogDaejeonFilterBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.searchField.filterText.setText(getString(R.string.cemetery_field_name))
        binding.rvCemetery.adapter = adapter

        initListener()
        initDialog()
        initFilterListener()

        return binding.root
    }

    private fun initDialog(){
        filterDialog.setContentView(filterBinding.root)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(filterDialog.window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        filterDialog.window?.attributes = lp
    }
    private fun initListener(){
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
            else
                binding.emptyData.visibility = View.VISIBLE
        }
        binding.btnSearch.setOnClickListener{
            searchData.name = binding.searchField.editText.text.toString()
            if(searchData.name.isBlank()){
                Toast.makeText(requireContext(),getString(R.string.input_cemetery_field_name),Toast.LENGTH_SHORT).show()
            }else{
                searchData()
            }
        }
        binding.btnSearchDetail.setOnClickListener{
            searchData.name = binding.searchField.editText.text.toString()
            filterBinding.data = searchData
            filterDialog.show()
        }
    }

    private fun initFilterListener(){

        filterBinding.close.setOnClickListener{
            filterDialog.dismiss()
        }
        filterBinding.reset.setOnClickListener{
            searchData.name = ""
            searchData.pname1 = null
            searchData.deathday = null
            searchData.sn = null
            searchData.qualification = null
            searchData.burrday = null
            searchData.graveno = null
            filterBinding.name.setText("")
            filterBinding.pname.setText("")
            filterBinding.deathday.setText("")
            filterBinding.sn.setText("")
            filterBinding.qualification.setText("")
            filterBinding.burrday.setText("")
            filterBinding.graveno.setText("")
        }
        filterBinding.search.setOnClickListener{
            searchData.name = filterBinding.name.text.toString()
            binding.searchField.editText.setText(filterBinding.name.text.toString())
            if(searchData.name.isBlank()){
                Toast.makeText(requireContext(),getString(R.string.input_cemetery_field_name),Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            searchData.pname1 = convFilterData(filterBinding.pname.text.toString())
            searchData.deathday = convFilterData(filterBinding.deathday.text.toString())
            searchData.sn = convFilterData(filterBinding.sn.text.toString())
            searchData.qualification = convFilterData(filterBinding.qualification.text.toString())
            searchData.burrday = convFilterData(filterBinding.burrday.text.toString())
            searchData.graveno = convFilterData(filterBinding.graveno.text.toString())
            filterDialog.dismiss()
            searchData()
        }
    }

    private fun convFilterData(data:String?):String?{
        return data?.ifBlank { null }
    }

    private fun searchData(){
        val manager: InputMethodManager? =
            requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?

        manager?.hideSoftInputFromWindow(
            activity?.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
        viewModel.setData(searchData)
        lifecycleScope.launch {
            viewModel.data.collectLatest { adapter.submitData(it) }
        }
    }


}