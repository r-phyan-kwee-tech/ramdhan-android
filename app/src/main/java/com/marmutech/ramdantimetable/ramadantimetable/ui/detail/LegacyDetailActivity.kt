package com.marmutech.ramdantimetable.ramadantimetable.ui.detail

@Deprecated("use DetailViewActivity")
class LegacyDetailActivity /*: CoreActivity()*/ {

    /*@Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var prefUtil: UserPrefUtil
    private lateinit var legacyDetailViewModel: LegacyDetailViewModel
    private var dayId: String? = null

    var tabLayout: TabLayout? = null
    var pageAdapter: ViewPagerAdapter? = null
    var viewPager: androidx.viewpager.widget.ViewPager? = null
    var toolbar: Toolbar? = null
    var collapsingToolbar: CollapsingToolbarLayout? = null

    var binding: FragmentDetailViewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = FragmentDetailViewBinding.inflate(layoutInflater)


        toolbar = binding?.toolbar
        toolbar?.setTitleTextColor(resources.getColor(R.color.colorAccent))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        collapsingToolbar = binding?.collapsingToolbar
        tabLayout = binding?.tabs
        viewPager = binding?.viewpager

        collapsingToolbar?.setExpandedTitleColor(resources.getColor(android.R.color.transparent))


        //Fetching data


        //ViewModel Class Declaration
        legacyDetailViewModel =
            ViewModelProvider(this, viewModelFactory).get(LegacyDetailViewModel::class.java)

        if (intent.data != null) {
            var data: Uri = intent.data
            dayId = data.getQueryParameter("dayId")
            legacyDetailViewModel.loadDay(dayId)
        }

        binding?.isUnicode = prefUtil.getFont()
        legacyDetailViewModel.timeTableDay.observe(this, Observer { dayResource ->
            //TODO bind data from repo here
            if (dayResource?.data != null) {
                binding?.timetable = dayResource.data
                supportActionBar?.title =
                    String.format(resources.getString(R.string.str_day), dayResource.data.day)
                pageAdapter = ViewPagerAdapter(
                    supportFragmentManager,
                    arrayOf(this.resources.getString(R.string.dua_mm_uni), "EN", "دُعَاء\u200E"),
                    dayResource.data
                )
                viewPager?.adapter = pageAdapter
                tabLayout?.setupWithViewPager(viewPager)

            }
        })
    }*/
}
