package alexProject.activity

import alexProject.R
import alexProject.databinding.ActivityGroupBinding
import alexProject.databinding.ActivityMoviesBinding
import alexProject.util.setGone
import alexProject.util.setVisible
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesBinding
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews(binding)
        supportActionBar?.hide()
        binding.toolbar.back.setOnClickListener { onBackPressed() }
        setupListener()
    }

    private fun setupListener() {
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.groupFragment -> setupToolbar(
                    true, getString(R.string.title_fragment_teams_and_groups)
                )
            }
        }
    }

    private fun initViews(binding: ActivityMoviesBinding) {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view_group) as NavHostFragment
    }

    fun setupToolbar(textVisible: Boolean, title: String = "") =
        with(binding) {
            toolbar.back.setOnClickListener {
                onBackPressed()
            }
            val toolbarInclude: View = findViewById(R.id.toolbar)
            if (textVisible) {
                toolbarInclude.setVisible()
                toolbar.text.setVisible()
                toolbar.text.text = title
                toolbar.back.setVisible()
            } else {
                toolbarInclude.setGone()
            }
        }

}