package com.knesarcreation.attendanceapp.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.knesarcreation.attendanceapp.CreateAttendanceSheet
import com.knesarcreation.attendanceapp.R
import com.knesarcreation.attendanceapp.adapter.AdapterAttendanceSheet
import com.knesarcreation.attendanceapp.database.AttendanceHistory
import com.knesarcreation.attendanceapp.database.AttendanceSheet
import com.knesarcreation.attendanceapp.database.Database
import com.knesarcreation.attendanceapp.database.DatabaseInstance
import kotlinx.android.synthetic.main.attendance_sheet.view.*


class AttendanceSheetFragment : Fragment() {

    var id1 = 0
    var isActive = true
    val clickedOn = true
    lateinit var mAdapter: AdapterAttendanceSheet
    var mAttendanceList = mutableListOf<AttendanceSheet>()
    var mDatabase: Database? = null

    companion object {
        const val EDIT_REQUEST_CODE = 2
        const val REQUEST_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.attendance_sheet, container, false)

        view.txtTitleNameAttendSheet.text = "Attendance Sheets"
        isActive = true

        /*Setting up database*/
        setDB()

        callCreateAttendanceSheet(view)

        view.mRecyclerView.setHasFixedSize(true)
        view.mRecyclerView.layoutManager = LinearLayoutManager(activity as Context)

        gettingDataFromDatabase(view)

        buildRecyclerView(view)

        /*Help button*/
        view.btnHelp.setOnClickListener {
            val builder = AlertDialog.Builder(activity as Context)
            val dialogLayout: View = layoutInflater
                .inflate(R.layout.help_dialog, null)
            val imgView: ImageView =
                dialogLayout.findViewById<View>(R.id.imgSwipe) as ImageView
            val txtView = dialogLayout.findViewById<View>(R.id.txtRightSwipe) as TextView
            val btnNext: Button = dialogLayout.findViewById<View>(R.id.btnNext) as Button
            val btnGotIt: Button =
                dialogLayout.findViewById<View>(R.id.btnGotIt) as Button

            btnNext.visibility = View.VISIBLE
            builder.setView(dialogLayout)
            val dialog: AlertDialog = builder.create()

            btnNext.setOnClickListener {

                btnNext.visibility = View.INVISIBLE
                btnGotIt.visibility = View.VISIBLE
                imgView.setImageResource(R.drawable.swipe_left)
                txtView.text = "Do Right Swipe To ' Edit ' Any Item"
            }
            btnGotIt.setOnClickListener(View.OnClickListener { dialog.dismiss() })
            dialog.setCancelable(false)
            dialog.show()
        }
        /*  ItemTouchHelper(
              `AttendanceSheetFragment$onCreateView$2`(
                  this,
                  view,
                  0,
                  12
              )
          ).attachToRecyclerView(mRecyclerView)
  */
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == -1) {

            val profName = data!!.getStringExtra(CreateAttendanceSheet.EXTRA_PROF_NAME)
            val subName = data.getStringExtra(CreateAttendanceSheet.EXTRA_SUB_NAME)
            val subCode = data.getStringExtra(CreateAttendanceSheet.EXTRA_SUB_CODE)
            val chooseYear = data.getStringExtra(CreateAttendanceSheet.EXTRA_CLASS)
            val id = System.currentTimeMillis().toInt()

            val currentStdAttendanceSheet = AttendanceSheet(
                id, subName!!, chooseYear!!,
                "by $profName", 0
            )

            if (!subCode!!.isBlank()) {
                currentStdAttendanceSheet.subCode = ("($subCode)")
            }
            mAttendanceList.add(currentStdAttendanceSheet)
            mDatabase!!.mDao().insertAttendanceSheet(currentStdAttendanceSheet)

            val currentAttendanceHistory = AttendanceHistory(
                id,
                subName,
                chooseYear,
                "by $profName",
                0,
            )
            if (subCode.isNotBlank()) {
                currentAttendanceHistory.subCode = "($subCode)"
            }
            mDatabase!!.mDao().insertAttendanceHistory(currentAttendanceHistory)

            view?.let { gettingDataFromDatabase(it) }

            view?.let { buildRecyclerView(it) }

            val adapterAttendanceSheet: AdapterAttendanceSheet? = mAdapter

            adapterAttendanceSheet?.notifyDataSetChanged()
        } /*else if (requestCode == 2 && resultCode == -1) {

            val profName2 = data!!.getStringExtra(CreateAttendanceSheet.EXTRA_PROF_NAME)
            val subName2 = data.getStringExtra(CreateAttendanceSheet.EXTRA_SUB_NAME)
            val subCode2 = data.getStringExtra(CreateAttendanceSheet.EXTRA_SUB_CODE)
            val chooseYear2 = data.getStringExtra(CreateAttendanceSheet.EXTRA_CLASS)
            val database3: Database? = mDatabase

            val mAttendanceDao: AttendanceDao = database3!!.mDao()

            val num = id1

            mAttendanceDao.editAttendanceSheet(profName2, subName2, subCode2, chooseYear2, num)
            val database4: Database? = mDatabase

            val mAttendanceDao2: AttendanceDao = database4!!.mDao()
            val num2 = id1

            mAttendanceDao2.editAttendanceHistory(profName2, subName2, subCode2, chooseYear2, num2)

            view?.let { gettingDataFromDatabase(it) }

            view?.let { buildRecyclerView(it) }

            mAdapter?.notifyDataSetChanged()
        }*/
    }

    private fun buildRecyclerView(view: View) {
        mAdapter = AdapterAttendanceSheet(
            activity as Context,
            isActive,
            clickedOn,
            mAttendanceList
        )
        mAdapter.notifyDataSetChanged()
        view.mRecyclerView.adapter = mAdapter
    }

    private fun gettingDataFromDatabase(view: View) {
        mAttendanceList = mDatabase?.mDao()?.getAllAttendanceSheets()!!
        if (mAttendanceList.isNotEmpty()) {
            view.llNoAttendanceSheets.visibility = View.INVISIBLE
        }
    }

    private fun callCreateAttendanceSheet(view: View) {
        view.btnCreateSheet.setOnClickListener {
            startActivityForResult(
                Intent(activity as Context, CreateAttendanceSheet::class.java),
                REQUEST_CODE
            )
        }
    }

    override fun onResume() {
        super.onResume()

        view?.let { gettingDataFromDatabase(it) }

        view?.let { buildRecyclerView(it) }

        val adapterAttendanceSheet: AdapterAttendanceSheet? = mAdapter

        adapterAttendanceSheet?.notifyDataSetChanged()
        isActive = true
    }

    private fun setDB() {
        mDatabase = DatabaseInstance().newInstance(activity as Context)
    }
}
