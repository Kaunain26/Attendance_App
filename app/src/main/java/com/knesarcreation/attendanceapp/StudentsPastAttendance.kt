package com.knesarcreation.attendanceapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.knesarcreation.attendanceapp.adapter.AdapterStdPastAttendance
import com.knesarcreation.attendanceapp.database.Database
import com.knesarcreation.attendanceapp.database.DatabaseInstance
import com.knesarcreation.attendanceapp.database.StudentPastAttendance
import kotlinx.android.synthetic.main.activity_std_past_attendance.*

class StudentsPastAttendance : AppCompatActivity(), AdapterStdPastAttendance.OnItemClickListener {
    private var id: Int? = 0
    private var showAlertDialog: Boolean = false

    /* var incrementedPresent = 0
     var decrementAbsent = 0
     var incrementAbsent = 0
     var decrementPresent = 0*/
    private var mAdapter: AdapterStdPastAttendance? = null
    private var mDatabase: Database? = null
    private var mStdPastList = mutableListOf<StudentPastAttendance>()

    /*For saving position of checkbox which is checked or not*/
    private var positionList = mutableListOf<Int>()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_std_past_attendance)

        setDB()
        id = intent?.getIntExtra("id", 0)
        val subName = intent?.getStringExtra("subName")
        val dateTime = intent?.getStringExtra("dateTime")
        txtDateTimeStdPastAttend.text = dateTime
        txtSubNameFragmentPast.text = subName

        Log.d(
            "studentPastHistory",
            "${mDatabase?.mDao()?.getDatesTimesAndStudentHist(id!!.toInt())!!}"
        )
        for (i in mDatabase?.mDao()?.getDatesTimesAndStudentHist(id!!.toInt())!!) {
            mStdPastList = i.studentPastAttendance
        }

        if (mStdPastList.isNotEmpty()) {
            imgNoRecordsStdPastAttend.visibility = View.INVISIBLE
            hintMessageStdPastAttend.visibility = View.INVISIBLE
            hintSmallMessageStdPastAttend.visibility = View.INVISIBLE
        }

        buildRecyclerView()

        saveAttendanceBtn()

        imgBackBtnStdPastAttend.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun saveAttendanceBtn() {
        imgSaveBtnPastAttend.setOnClickListener {
            /* val builder = AlertDialog.Builder(this)
             builder.setTitle("Save Changes" as CharSequence)
             builder.setMessage("Do you want to save changes to attendance?" as CharSequence)
             builder.setPositiveButton(
                 "Save"
             ) { dialog, _ ->
                 savePastAttendance()
                 finish()
                 dialog.dismiss()
             }
             builder.setNegativeButton(
                 "Don't save"
             ) { dialog, _ ->
                 dialog.dismiss()
                 finish()
             }
             builder.show()*/
            savePastAttendance()
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun buildRecyclerView() {
        mRecyclerViewPastAttd.setHasFixedSize(true)
        mRecyclerViewPastAttd.layoutManager = LinearLayoutManager(this)
        mAdapter = AdapterStdPastAttendance(this, this, mStdPastList)
        mRecyclerViewPastAttd.adapter = mAdapter
    }

    private fun savePastAttendance() {
        for (k in positionList) {
            /* for (tempList in tempStudDetailsList) {
                 if (k == tempList.position) {*/
            /*Saving: The changes made in past attendance with respect to position which was added to positionList*/
            if (mStdPastList[k].isChecked) {
                mDatabase?.mDao()
                    ?.updateStudentPastAttendance(true, mStdPastList[k].id)

            } else {
                mDatabase?.mDao()
                    ?.updateStudentPastAttendance(false, mStdPastList[k].id)
            }
        }
    }

    private fun setDB() {
        mDatabase = DatabaseInstance().newInstance(this)
    }

    override fun onItemClick(
        Position: Int,
        myViewHolder: AdapterStdPastAttendance.MyViewHolder?
    ) {
        showAlertDialog = true
        /*Adding position to positionList when checkbox is checked*/
        positionList.add(Position)
        if (myViewHolder?.checkbox?.isChecked!!) {
            for (i in mDatabase?.mDao()?.getStudentDetails(mStdPastList[Position].stdUsn!!)!!) {
                /* val tempStudentDetails = TempStudentDetails(Position, i.absent - 1, i.present + 1)
                 tempStudDetailsList.add(tempStudentDetails)
                 mDatabase?.mDao()?.insertTempStudentDetails(tempStudentDetails)!!*/
                mDatabase?.mDao()
                    ?.updateStudentPresentStatus(i.present + 1, mStdPastList[Position].stdUsn!!)

                mDatabase?.mDao()
                    ?.updateStudentAbsentStatus(i.absent - 1, mStdPastList[Position].stdUsn!!)
            }
        } else {

            for (i in mDatabase?.mDao()?.getStudentDetails(mStdPastList[Position].stdUsn!!)!!) {
                /*   val tempStudentDetails = TempStudentDetails(Position, i.absent + 1, i.present - 1)
                   tempStudDetailsList.add(
                       TempStudentDetails(
                           Position,
                           i.absent + 1,
                           i.present - 1

                       )
                   )*/
                mDatabase?.mDao()
                    ?.updateStudentAbsentStatus(i.absent + 1, mStdPastList[Position].stdUsn!!)

                mDatabase?.mDao()
                    ?.updateStudentPresentStatus(i.present - 1, mStdPastList[Position].stdUsn!!)
            }
        }
    }

    /*  private fun backPressed() {
          if (showAlertDialog) {
              val builder = AlertDialog.Builder(this)
              builder.setTitle("Alert!!" as CharSequence)
              builder.setMessage("Do you want to save changes to attendance?" as CharSequence)
              builder.setPositiveButton("Save") { dialog, _ ->
                  savePastAttendance()
                  finish()
                  dialog.dismiss()
              }
              builder.setNegativeButton("No") { dialog, _ ->
                  dialog.dismiss()
                  finish()
              }
              builder.show()
          } else {
              super.onBackPressed()
          }
      }*/
}