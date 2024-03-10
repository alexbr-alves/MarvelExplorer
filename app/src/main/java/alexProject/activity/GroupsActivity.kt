package alexProject.activity

import alexProject.R
import alexProject.databinding.ActivityGroupBinding
import alexProject.util.setGone
import alexProject.util.setVisible
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupBinding
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews(binding)
        //setupToolbar()
        supportActionBar?.hide()
        binding.toolbar.back.setOnClickListener { onBackPressed() }

    }

    private fun initViews(binding : ActivityGroupBinding) {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view_group) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.apply {
            setupWithNavController(navController)
        }
    }

    fun setupToolbar(textVisible : Boolean, title : String = "") =
        with(binding) {
            toolbar.back.setOnClickListener {
                onBackPressed()
            }
            val toolbarInclude : View = findViewById(R.id.toolbar)
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