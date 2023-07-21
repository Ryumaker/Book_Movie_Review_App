package com.toyproject.bookandmoviereview

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.toyproject.bookandmoviereview.databinding.ActivityHomeBinding
import com.toyproject.bookandmoviereview.models.bottomAppBarHeight

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // 앱바에 타이틀이 나오지 않음

        val navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)

        drawerLayout = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottomHome -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.bottomDiscussion -> {
                    replaceFragment(DiscussionBoardFragment())
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
            bottomNavigationView.post {
                val pixels = bottomNavigationView.measuredHeight.toFloat()
                // val pixels = bottomNavigationView.measuredHeight.toFloat()
                // val dp = convertPixelsToDp(this@HomeActivity, pixels)
                bottomAppBarHeight = pixels
                // Toast.makeText(this, bottomAppBarHeight.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun convertPixelsToDp(context: Context, pixels: Float): Float {
        val screenPixelDensity = context.resources.displayMetrics.density
        return pixels / screenPixelDensity
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            Toast.makeText(this, "Search Icon", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }

    // Drawer에 있는 메뉴 선택
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navMyReview -> replaceFragment(MyReviewFragment())
            R.id.navParticipatedInDiscussionBoard -> replaceFragment(ParticipatedInDiscussionFragment())
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}