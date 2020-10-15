package com.knesarcreation.attendanceapp

/*import com.knesarcreation.attendanceapp.fragment.AttendanceHistoryFragment
import com.knesarcreation.attendanceapp.fragment.StudentInformationFragment*/
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener
import androidx.fragment.app.Fragment
import com.knesarcreation.attendanceapp.fragment.AttendanceHistoryFragment
import com.knesarcreation.attendanceapp.fragment.AttendanceSheetFragment
import com.knesarcreation.attendanceapp.fragment.StudentInformationFragment
import kotlinx.android.synthetic.main.activity_main_screen.*


class MainScreenActivity : AppCompatActivity() {
    val END_SCALE = 0.7f
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        fragmentTransaction(AttendanceSheetFragment())

        /* Setting click listener for menu items inside the navigation drawer*/
        navigationItemClickListener()

        imgNavigateBtn.setOnClickListener {
            mDrawerLayout.openDrawer(GravityCompat.START)
        }

        animateNavigationDrawer()
        getSharedPreferences("SHARED_PREFS", 0).edit().putBoolean("Active", true).apply()
    }

    private fun navigationItemClickListener() {
        navMenuAttendance.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.attendanceHistory -> fragmentTransaction(AttendanceHistoryFragment())

                R.id.attendanceSheet -> fragmentTransaction(AttendanceSheetFragment())

                R.id.studentInformation -> fragmentTransaction(StudentInformationFragment())

            }
            mDrawerLayout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
    }

    private fun fragmentTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).commit()
        navMenuAttendance.setCheckedItem(R.id.attendanceSheet)
    }

    private fun animateNavigationDrawer() {
        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        mDrawerLayout.setScrimColor(resources.getColor(R.color.lightBlue))

        mDrawerLayout.addDrawerListener(object : SimpleDrawerListener() {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                // Scale the View based on current slide offset
                val diffScaledOffset: Float = slideOffset * (1 - END_SCALE)
                val offsetScale = 1 - diffScaledOffset
                contentView.scaleX = offsetScale
                contentView.scaleY = offsetScale

                // Translate the View, accounting for the scaled width
                val xOffset: Float = drawerView.width * slideOffset
                val xOffsetDiff = contentView.width * diffScaledOffset / 2
                val xTranslation = xOffset - xOffsetDiff
                contentView.translationX = xTranslation
            }
        })

    }

    override fun onBackPressed() {
        when {
            mDrawerLayout.isDrawerVisible(GravityCompat.START) -> {
                mDrawerLayout.closeDrawer(GravityCompat.START)
            }
            supportFragmentManager.findFragmentById(R.id.fragment_container) !is AttendanceSheetFragment -> {
                fragmentTransaction(AttendanceSheetFragment())
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}