package com.marmutech.ramdantimetable.ramadantimetable.ui.detail.duapager


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marmutech.ramdantimetable.ramadantimetable.R
import com.marmutech.ramdantimetable.ramadantimetable.databinding.FragmentDuaInfoBinding
import com.marmutech.ramdantimetable.ramadantimetable.ui.CoreFragment
import com.marmutech.ramdantimetable.ramadantimetable.ui.detail.LanguageCode
import timber.log.Timber

class DuaInfoFragment private constructor() : CoreFragment() {

    private var _binding: FragmentDuaInfoBinding? = null
    private val binding: FragmentDuaInfoBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDuaInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun bindData() {
        arguments?.run {
            val languageCode = getParcelable<LanguageCode>(KEY_LANG)
            val translatedDua = getString(KEY_DUA).orEmpty()

            bindUI(languageCode, translatedDua)
        }
    }

    private fun bindUI(languageCode: LanguageCode?, translatedDua: String) {
        requireNotNull(languageCode)
        binding.duaTitle.text = getDuaTitleByLanguage(languageCode)
        binding.duaDetail.text = translatedDua
    }

    private fun getDuaTitleByLanguage(languageCode: LanguageCode) = when (languageCode) {
        LanguageCode.EN -> getString(R.string.dua_title_en)
        LanguageCode.MM -> getString(R.string.dua_title_mm_uni)
        LanguageCode.AR -> getString(R.string.dua_title_ar)
    }


    companion object {
        private const val KEY_LANG = "lang"
        private const val KEY_DUA = "dua"

        fun newInstance(languageCode: LanguageCode, translatedDua: String): DuaInfoFragment {
            Timber.d("languageCode $languageCode")
            val duaInfoFragment = DuaInfoFragment()
            Bundle().apply {
                putString(KEY_DUA, translatedDua)
                putParcelable(KEY_LANG, languageCode)
            }.also {
                duaInfoFragment.arguments = it
            }
            return duaInfoFragment
        }
    }
}
