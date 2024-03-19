package alexProject.activity

import alexProject.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import alexProject.databinding.ActivitySplashBinding
import android.content.Intent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import kotlin.random.Random

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupSplash()
        supportActionBar?.hide()
    }

    private fun setupSplash() = with(binding) {
        handleAleatorySplash()
        imageSplash.animate().setDuration(3000).alpha(1f).withEndAction {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_in)
            finish()
        }
    }

    private fun handleAleatorySplash() = with(binding) {
        val arraySplash = arrayListOf(
            R.drawable.splash_pantera,
            R.drawable.splash_groot,
            R.drawable.splash_spider,
            R.drawable.splash_thanos,
            R.drawable.spash_ironman,
            R.drawable.splash_deadpool,
            R.drawable.splash_thor,
            R.drawable.splash_avanged,
            R.drawable.splash_star_lord
        )
        imageSplash.setImageResource(arraySplash[Random.nextInt(0, arraySplash.size)])
    }


}