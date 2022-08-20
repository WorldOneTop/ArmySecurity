package com.example.armysecurity.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.armysecurity.R
import com.example.armysecurity.data.Cemetery
import com.example.armysecurity.data.MyData
import com.example.armysecurity.databinding.DialogBookmarkEditBinding
import com.example.armysecurity.databinding.DialogDaejeonFilterBinding
import com.example.armysecurity.databinding.FragmentMyPageBinding
import com.example.armysecurity.viewModels.MainVM
import org.parceler.Parcels

class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding
    private lateinit var adapter: MyPageAdapter
    private lateinit var settingDialog: Dialog
    private lateinit var settingBinding: DialogBookmarkEditBinding
    private var settingDataIndex = -1

    private val viewModel: MainVM by activityViewModels{
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                MainVM() as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMyPageBinding.inflate(layoutInflater)
        settingDialog = Dialog(requireContext())
        settingBinding = DialogBookmarkEditBinding.inflate(layoutInflater)
        initSettingDialog()
        adapter = MyPageAdapter({
            onItemClickListener(it)
        },{
            settingDataIndex = viewModel.myData.list.indexOf(it)
            if(settingDataIndex != -1) {
                settingBinding.order.setText(settingDataIndex.toString())
                settingBinding.memo.setText(viewModel.myData.list[settingDataIndex].memo)
                settingDialog.show()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.root.adapter = adapter

        viewModel.myLiveData.observe(requireActivity()) {
            (binding.root.adapter as MyPageAdapter).setData(it)
        }

        return binding.root
    }

    private fun onItemClickListener(data:MyData){
        if(data.cemetery != null){
            findNavController().navigate(R.id.action_tab_btm_myPage_to_cemeteryDetailFragment,
                bundleOf("type" to "seoul", "data" to Parcels.wrap(data.cemetery))
            )
        }else if(data.cemeteryDaejeon != null){
            findNavController().navigate(R.id.action_tab_btm_myPage_to_cemeteryDetailFragment,
                bundleOf("type" to "daejeon", "data" to Parcels.wrap(data.cemeteryDaejeon))
            )
        }else{
            findNavController().navigate(R.id.action_tab_btm_myPage_to_warDetailFragment,
                if(data.fly != null)
                    bundleOf("type" to "Fly", "data" to Parcels.wrap(data.fly))
                else if(data.relics != null)
                    bundleOf("type" to "Relics", "data" to Parcels.wrap(data.relics))
                else if(data.sale != null)
                    bundleOf("type" to "Sale", "data" to Parcels.wrap(data.sale))
                else if(data.trip != null)
                    bundleOf("type" to "Trip", "data" to Parcels.wrap(data.trip))
                else if(data.war != null)
                    bundleOf("type" to "War", "data" to Parcels.wrap(data.war))
                else
                    bundleOf("type" to "WarMan", "data" to Parcels.wrap(data.warMan))
            )
        }
    }

    private fun initSettingDialog(){
        settingDialog.setContentView(settingBinding.root)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(settingDialog.window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        settingDialog.window?.attributes = lp

        settingBinding.close.setOnClickListener{settingDialog.dismiss()}

        settingBinding.save.setOnClickListener{
            if(settingBinding.order.text.isDigitsOnly()){
                var changeIndex = settingBinding.order.text.toString().toInt()
                viewModel.myData.list.getOrNull(settingDataIndex)?.memo = settingBinding.memo.text.toString()
                if(changeIndex < 0){
                    changeIndex = 0
                }else if(changeIndex >= viewModel.myData.list.size){
                    changeIndex = viewModel.myData.list.size-1
                }
                val data = viewModel.myData.list.removeAt(settingDataIndex)
                viewModel.myData.list.add(changeIndex, data)

                viewModel.saveData(requireContext())
                settingDialog.dismiss()
            }else{
                Toast.makeText(requireContext(),"순서는 ${getString(R.string.error_only_digit)}",Toast.LENGTH_SHORT).show()
            }
        }
        settingBinding.remove.setOnClickListener{
            viewModel.removeData(requireContext(), viewModel.myData.list[settingDataIndex])
            settingDialog.dismiss()
        }
        settingDialog.create()
    }
}