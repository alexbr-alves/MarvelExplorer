package alexProject.activity

import alexProject.R
import alexProject.databinding.ActivityTvShowBinding
import alexProject.util.setGone
import alexProject.util.setVisible
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvShowBinding
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowBinding.inflate(layoutInflater)
        initViews(binding)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupListener()
    }

    private fun initViews(binding: ActivityTvShowBinding) {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view_tv_show) as NavHostFragment
    }

    private fun setupListener() {
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.tvShowFragment ->
                    setupToolbar(
                        true, getString(R.string.title_fragment_tv_show)
                    )
            }
        }
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