package tam.pa.moviecatalogue.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import tam.pa.moviecatalogue.databinding.ActivitySplashScreenBinding
import tam.pa.moviecatalogue.ui.home.HomeActivity
import tam.pa.moviecatalogue.utils.EspressoIdlingResource

class SplashScreenActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySplashScreenBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        EspressoIdlingResource.increment()
        Handler(mainLooper).postDelayed({
            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow){
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
                EspressoIdlingResource.decrement()
            }
        }, 3000)
    }
}