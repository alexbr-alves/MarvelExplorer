package alexProject.activity

import alexProject.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import alexProject.databinding.ActivityMainBinding
import alexProject.util.setGone
import alexProject.util.setVisible
import android.content.Intent
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

    private fun setupToolbar(visible : Boolean, title : String = "") =
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
            when (destination.id) {
                R.id.homeFragment -> {
                    setupToolbar(
                        true,
                        getString(R.string.marvel)
                    )
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

                R.id.detailsCharacterFragment -> hideToolbar()

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
                R.id.detailsComicFragment -> hideToolbar()

                R.id.detailsEventFragment -> hideToolbar()
            }
        }
    }

    fun goToGroupActivity() {
        val intent = Intent(this, GroupsActivity::class.java)
        startActivity(intent)
    }

    fun goToMoviesActivity() {
        val intent = Intent(this, MoviesActivity::class.java)
        startActivity(intent)
    }

    private fun hideToolbar() {
        val toolbarInclude : View = findViewById(R.id.toolbar)
        toolbarInclude.setGone()
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