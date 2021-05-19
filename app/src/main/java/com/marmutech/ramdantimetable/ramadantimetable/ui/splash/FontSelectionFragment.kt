package com.marmutech.ramdantimetable.ramadantimetable.ui.splash


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentFontSelectionBinding
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment
import com.marmutech.ramdantimetable.ramadantimetable.util.CommonUtil
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import timber.log.Timber
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class FontSelectionFragment : CoreFragment() {

    @Inject
    lateinit var prefUtil: UserPrefUtil

    @Inject
    lateinit var commonUtil: CommonUtil

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var splashViewModel: SplashViewModel


    private var binding: FragmentFontSelectionBinding? = null
    private var fontSwitch: SwitchCompat? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFontSelectionBinding.inflate(inflater, container, false)
        fontSwitch = binding?.swChangeType
        fontSwitch?.isChecked = prefUtil.getFont()
        fontSwitch?.setOnCheckedChangeListener { _, isChecked -> prefUtil.setFont(isChecked) }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashViewModel = ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)
        if (commonUtil.isNetworkConnected()) {
            splashViewModel.loadAvaliableCountries(50, 1)
            observeData()
        } else {
            commonUtil.getConnectionDialog(prefUtil.getFont(), requireContext()).show()
        }
    }

    private fun observeData() {
        splashViewModel.countryList.observe(
            viewLifecycleOwner,
            Observer<Resource<List<Country>>> { t ->
                Timber.d("dayList obersve " + t?.data)
                if (t?.data != null && t.data.isNotEmpty()) {

                    splashViewModel.loadAvailableStates(t.data.first().objectId, 1000, 1)
                }

            })
        splashViewModel.stateList.observe(viewLifecycleOwner, Observer<Resource<List<State>>> { t ->
            if (t?.data != null) {
                // TODO prefetch data complete
            }
        })
    }
}
