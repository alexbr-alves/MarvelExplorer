package alexProject.activity

import BottomSheetError
import alexProject.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import alexProject.databinding.ActivityMainBinding
import alexProject.util.goToActivity
import alexProject.util.setGone
import alexProject.util.setStatusBarColor
import alexProject.util.setVisible
import android.graphics.PorterDuff
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private var currentSearchListener: SearchListener? = null
    val searchQueryLiveData = MutableLiveData<String?>()
    val closedSearchLiveData = MutableLiveData<Boolean>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews(binding)
        setupListener()
        supportActionBar?.hide()
        binding.toolbar.back.setOnClickListener { onBackPressed() }
        setupSearch()
    }

    fun showError(errorMessage: String?) {
        val bottomSheetView = BottomSheetError(
            message = errorMessage ?: getString(R.string.bottom_sheet_error_message),
            onRetryClick = { onBackPressed() }
        )
        bottomSheetView.show(supportFragmentManager, BottomSheetError.TAG)
    }

    private fun setupSearch(hilt: String = "", visible: Boolean = true) =
        binding.toolbar.searchView.apply {
            this.setVisible(visible)
            queryHint = hilt

            val searchIcon = this.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
            searchIcon.setColorFilter(
                ContextCompat.getColor(context, R.color.white),
                PorterDuff.Mode.SRC_ATOP
            )

            val searchEditText =
                this.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            searchEditText.setTextColor(ContextCompat.getColor(context, R.color.black))
            searchEditText.setHintTextColor(ContextCompat.getColor(context, R.color.gray_crayola))
            searchEditText.background =
                ContextCompat.getDrawable(context, R.drawable.background_search_edit)
            searchEditText.textSize = 15f

            val closeEditText =
                this.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
            closeEditText.setColorFilter(
                ContextCompat.getColor(context, R.color.white),
                PorterDuff.Mode.SRC_ATOP
            )

            setOnSearchClickListener {
                binding.toolbar.run {
                    text.setGone()
                    back.setGone()
                }
            }

            setOnCloseListener {
                binding.toolbar.run {
                    text.setVisible()
                    back.setVisible()
                }
                currentSearchListener?.onSearchQueryChanged(null)
                closedSearchLiveData.value = true
                false
            }

            setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchQueryLiveData.value = query
                    clearFocus()
                    return true
                }


                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

    fun setupToolbar(visible: Boolean, title: String = "") =
        with(binding) {
            toolbar.back.setOnClickListener {
                onBackPressed()
            }
            val toolbarInclude: View = findViewById(R.id.toolbar)
            if (visible) {
                toolbarInclude.setVisible()
                toolbar.text.setVisible()
                toolbar.text.text = title
                toolbar.back.setVisible()
            } else {
                toolbarInclude.setGone()
            }
        }

    fun setSearchListener(listener: SearchListener?) {
        currentSearchListener = listener
    }


    private fun setupListener() {
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.setVisible()
            setStatusBarColor(R.color.blue_yankees)
            setupSearch(visible = false)
            when (destination.id) {
                R.id.homeFragment -> {
                    setupToolbar(false)
                    binding.toolbar.back.setGone()
                    setStatusBarColor(R.color.black)
                }

                R.id.comicsFragment -> {
                    setupToolbar(
                        true,
                        getString(R.string.title_fragment_comic)
                    )
                    setupSearch(getString(R.string.comics_fragment_hilt_search), true)
                }

                R.id.favoriteCharacterFragment -> setupToolbar(
                    true,
                    getString(R.string.title_fragment_favorites)
                )

                R.id.timelineFragment -> setupToolbar(
                    true,
                    getString(R.string.feed)
                )

                R.id.eventFragment -> setupToolbar(
                    true,
                    getString(R.string.title_fragment_event)
                )

                R.id.detailsFragment -> {
                    setupToolbar(true, getString(R.string.title_fragment_characters))
                }

                R.id.CharacterFragment -> {
                    setupToolbar(
                        true,
                        getString(R.string.title_fragment_characters)
                    )
                    setupSearch(getString(R.string.character_fragment_hilt_search), true)
                }

                R.id.groupFragment -> setupToolbar(
                    true,
                    getString(R.string.title_fragment_teams_and_groups)
                )

                R.id.groupWikiFragment -> setupToolbar(
                    true,
                    getString(R.string.title_fragment_teams_and_groups)
                )

                R.id.storyFragment -> {
                    binding.toolbar.mainConstraint.setGone()
                    binding.bottomNavigation.setGone()
                }
            }
        }
    }

    fun goToGroupActivity() {
        goToActivity(GroupsActivity::class.java)
    }

    fun goToMoviesActivity() {
        goToActivity(MoviesActivity::class.java)
    }

    fun goToTvShow() {
        goToActivity(TvShowActivity::class.java)
    }

    fun goToGamesActivity() {
        goToActivity(GamesActivity::class.java)
    }

    interface SearchListener {
        fun onSearchQueryChanged(query: String?)
    }


    private fun initViews(binding: ActivityMainBinding) {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.apply {
            setupWithNavController(navController)
        }
    }
}