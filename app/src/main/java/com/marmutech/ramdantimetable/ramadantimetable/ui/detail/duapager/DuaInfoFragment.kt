package com.marmutech.ramdantimetable.ramadantimetable.ui.detail.duapager


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.R.string.*
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentDuaInfoBinding
import com.marmutech.ramdantimetable.ramadantimetable.di.Injectable
import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import org.rabbitconverter.rabbit.Rabbit
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val LANG = "lang"
private const val DUA = "dua"

/**
 * A simple [Fragment] subclass.
 *
 */
class DuaInfoFragment : Fragment(), Injectable {
    private var lang: String? = null
    private var dua: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lang = it.getString(LANG)

            dua = it.getString(DUA)
        }
    }

    var binding: FragmentDuaInfoBinding? = null

    @Inject
    lateinit var prefUtil: UserPrefUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dua_info, container, false)

        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        binding?.duaDetail = getDuabyLang(lang!!, dua!!)
        binding?.duaTitle = getDuaTitleByLang(lang!!)
    }

    fun getDuabyLang(lang: String, duaDetail: String): String {
        var result: String = ""
        when (lang) {
            "mm" -> if (prefUtil.getFont()) {
                result = duaDetail
            } else result = Rabbit.uni2zg(duaDetail)
            "en", "ar" -> result = duaDetail
        }
        return result
    }

    fun getDuaTitleByLang(lang: String): String {
        var result: String = ""
        when (lang) {
            "mm" -> if (prefUtil.getFont()) {
                result = this.resources.getString(dua_title_mm_uni)
            } else result = this.resources.getString(dua_title_mm_zawgyi)
            "en" -> result = this.resources.getString(dua_title_en)
            "ar" -> result = this.resources.getString(dua_title_ar)
        }
        return result
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param issueId Parameter 1.
         * @param issueNumber Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(language: String, issueNumber: String) =
                DuaInfoFragment().apply {
                    arguments = Bundle().apply {
                        putString(LANG, language)

                        putString(DUA, issueNumber)
                    }
                }
    }


}
