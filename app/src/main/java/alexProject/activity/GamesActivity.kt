package alexProject.activity

import BottomSheetError
import alexProject.R
import alexProject.databinding.ActivityGamesBinding
import alexProject.util.setGone
import alexProject.util.setVisible
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGamesBinding
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews(binding)
        supportActionBar?.hide()
        binding.toolbar.back.setOnClickListener { onBackPressed() }
        setupListener()
    }

    private fun setupListener() {
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.gamesFragment -> setupToolbar(true, getString(R.string.title_fragment_games))
            }
        }
    }

    private fun initViews(binding: ActivityGamesBinding) {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view_games) as NavHostFragment
    }

    fun showError(errorMessage: String?) {
        val bottomSheetView = BottomSheetError(
            message = errorMessage?:getString(R.string.bottom_sheet_error_message),
            onRetryClick = { onBackPressed() }
        )
        bottomSheetView.show(supportFragmentManager, BottomSheetError.TAG)
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