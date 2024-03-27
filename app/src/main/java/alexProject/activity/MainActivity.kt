package alexProject.activity

import BottomSheetError
import alexProject.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import alexProject.databinding.ActivityMainBinding
import alexProject.util.goToActivity
import alexProject.util.setGone
import alexProject.util.setVisible
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navHostFragment : NavHostFragment


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews(binding)
        setupListener()
        supportActionBar?.hide()
        binding.toolbar.back.setOnClickListener { onBackPressed() }
    }

    fun showError(errorMessage: String?) {
        val bottomSheetView = BottomSheetError(
        message = errorMessage?:getString(R.string.bottom_sheet_error_message),
            onRetryClick = { onBackPressed() }
        )
        bottomSheetView.show(supportFragmentManager, BottomSheetError.TAG)
    }

    fun setupToolbar(visible : Boolean, title : String = "") =
        with(binding) {
            toolbar.back.setOnClickListener {
                onBackPressed()
            }
            val toolbarInclude : View = findViewById(R.id.toolbar)
            if (visible) {
                toolbarInclude.setVisible()
                toolbar.text.setVisible()
                toolbar.text.text = title
                toolbar.back.setVisible()
            } else {
                toolbarInclude.setGone()
            }
        }


    private fun setupListener() {
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.setVisible()
            when (destination.id) {
                R.id.homeFragment -> {
                    setupToolbar(false)
                    binding.toolbar.back.setGone()
                }

                R.id.comicsFragment -> setupToolbar(
                    true,
                    getString(R.string.title_fragment_comic)
                )

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

                R.id.CharacterFragment -> setupToolbar(
                    true,
                    getString(R.string.title_fragment_characters)
                )
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

    private fun initViews(binding : ActivityMainBinding) {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.apply {
            setupWithNavController(navController)
        }
    }
}