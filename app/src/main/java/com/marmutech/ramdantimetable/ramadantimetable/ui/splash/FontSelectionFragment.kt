package com.marmutech.ramdantimetable.ramadantimetable.ui.splash


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SwitchCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentFontSelectionBinding
import com.marmutech.ramdantimetable.ramadantimetable.di.Injectable
import com.marmutech.ramdantimetable.ramadantimetable.model.Country
import com.marmutech.ramdantimetable.ramadantimetable.model.State
import com.marmutech.ramdantimetable.ramadantimetable.util.CommonUtil
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import com.marmutech.ramdantimetable.ramadantimetable.vo.Resource
import timber.log.Timber
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FontSelectionFragment : Fragment(), Injectable {

    //TODO Proper Databindg has to apply

    @Inject
    lateinit var prefUtil: UserPrefUtil
    @Inject
    lateinit var commonUtil: CommonUtil
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    var binding: FragmentFontSelectionBinding? = null
    private var fontSwitch: SwitchCompat? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_font_selection, container, false)
        fontSwitch = binding?.swChangeType
        fontSwitch?.isChecked = prefUtil.getFont()
        fontSwitch?.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked -> prefUtil.setFont(isChecked) })
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var splashViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java)

        if (commonUtil.isNetworkConnected()) {
            splashViewModel.loadAvaliableCountries(50, 1)
            splashViewModel.countryList.observe(this, Observer<Resource<List<Country>>> { t ->
                Timber.d("dayList obersve " + t?.data)
                if (t?.data != null && !t?.data.isEmpty()) {

                    splashViewModel.loadAvailableStates(t?.data.first().objectId, 20, 1)
                    splashViewModel.stateList.observe(this, Observer<Resource<List<State>>> { t ->
                        if (t?.data != null) {
                            // TODO prefetch data complete
                        }
                    })
                }

            })
        } else {
            commonUtil.getConnectionDialog(prefUtil.getFont(), this!!.context!!).show()
        }
    }

    override fun onResume() {
        super.onResume()
        var splashViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java)

        splashViewModel.loadAvaliableCountries(50, 1)
        splashViewModel.countryList.observe(this, Observer<Resource<List<Country>>> { t ->
            Timber.d("dayList obersve " + t?.data)
            if (t?.data != null && !t?.data.isEmpty()) {

                splashViewModel.loadAvailableStates(t?.data.first().objectId, 20, 1)
                splashViewModel.stateList.observe(this, Observer<Resource<List<State>>> { t ->
                    if (t?.data != null) {
                        // TODO prefetch data complete
                    }
                })
            }

        })

    }


}
