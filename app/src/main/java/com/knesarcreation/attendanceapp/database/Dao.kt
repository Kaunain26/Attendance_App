package com.knesarcreation.attendanceapp.database

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Insert
    fun insertAttendanceSheet(attendanceSheet: AttendanceSheet?): Long

    @Insert
    fun insertStudentDetails(studentDetails: StudentDetails?)

    @Insert
    fun insertStudentList(studentListClass: StudentListClass?)

    @Insert
    fun insertAttendanceHistory(attendanceHistory: AttendanceHistory?): Long

    @Insert
    fun insertAttendanceDatesTime(attendanceDateTimes: AttendanceDateTimes?): Long

    @Insert
    fun insertStudentHistory(studentPastAttendance: StudentPastAttendance?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTempStudentDetails(tempStudentDetails: TempStudentDetails)

    @Delete
    fun deleteTempStudentDetails(tempStudentDetails: TempStudentDetails)

    /* fun deleteAttendanceHistory(i: Int)
     fun deleteAttendanceSheet(i: Int)
     fun deleteHistoryAttendanceDateTimes(i: Int)
     fun deleteStdDetailsWRTSheetNo(i: Int)
     fun deleteStudentFromStdAttendHist(str: String?)
     fun deleteStudentFromStdDetails(str: String?)
     fun deleteStudentFromStdList(str: String?)*/

    /*  fun editAttendanceHistory(str: String?, str2: String?, str3: String?, str4: String?, i: Int)
      fun editAttendanceSheet(str: String?, str2: String?, str3: String?, str4: String?, i: Int)*/

    @Query("SELECT * FROM AttendanceSheet")
    fun getAllAttendanceSheets(): MutableList<AttendanceSheet>

    @Query("SELECT * FROM AttendanceSheet WHERE id =:id")
    fun getAllStudents(id: Int): List<AttendanceSheetAndStudentList>

    @Query("SELECT * FROM StudentDetails WHERE studUsn =:stdUsn")
    fun getStudentDetails(stdUsn: String): List<StudentDetails>

    @Query("SELECT * FROM AttendanceHistory")
    fun getAllAttendanceHistory(): MutableList<AttendanceHistory>

    @Query("SELECT * FROM AttendanceHistory WHERE hisID = :id")
    fun getHistoryAndDatesTimes(id: Int): List<AttendanceHistoryAndAttendanceDatesTime>

    @Query("SELECT * FROM AttendanceDateTimes WHERE id = :id")
    fun getDatesTimesAndStudentHist(id: Int): List<AttendanceDatesTimeAndStudentAttendanceHistory>


    /* Update student details*/
    @Query("UPDATE StudentDetails SET studAddress =:address WHERE id = :id")
    fun updateAddressStudentDetails(id: Int, address: String)

    @Query("UPDATE StudentDetails SET studContact =:contact WHERE id = :id")
    fun updateContactStudentDetails(id: Int, contact: String)

    @Query("UPDATE StudentDetails SET studCourse =:course WHERE id = :id")
    fun updateCourseStudentDetails(id: Int, course: String)

    @Query("UPDATE StudentDetails SET studName =:stdName WHERE id = :id")
    fun updateNameStudentDetails(id: Int, stdName: String)

    @Query("UPDATE StudentDetails SET studUsn =:stdUsn WHERE id = :id")
    fun updateUSNStudentDetails(id: Int, stdUsn: String)

    @Query("UPDATE StudentDetails SET absent =:abstStatus WHERE studUsn = :stdUsn")
    fun updateStudentAbsentStatus(abstStatus: Int, stdUsn: String)

    @Query("UPDATE StudentDetails SET present =:prsetStatus WHERE  studUsn = :stdUsn")
    fun updateStudentPresentStatus(prsetStatus: Int, stdUsn: String)

    @Query("UPDATE StudentDetails SET attendancePercentage =:percentage WHERE studUsn = :stdUsn")
    fun updateStudentPercentageStatus(percentage: Double, stdUsn: String)


    @Query("UPDATE AttendanceHistory SET  totalStud =:totalStudent WHERE hisID = :id")
    fun updateTotalStudInAttendanceHistory(id: Int, totalStudent: String?)

    @Query("UPDATE AttendanceSheet SET totalStud =:totalStudent WHERE id = :id")
    fun updateTotalStudInAttendanceSheet(id: Int, totalStudent: String?)


    @Query("UPDATE StudentListClass SET isChecked =:isChecked WHERE stdUsn = :usn")
    fun updateStudentList(isChecked: Boolean, usn: String)

    @Query("UPDATE StudentPastAttendance SET isChecked =:isChecked WHERE id= :id")
    fun updateStudentPastAttendance(isChecked: Boolean, id: Int)


    @Query("UPDATE StudentPastAttendance SET stdName =:stdName WHERE id = :id")
    fun updateNameStudentHistory(id: Int, stdName: String?)

    @Query("UPDATE StudentPastAttendance SET stdUsn =:stdUsn WHERE stdId = :id")
    fun updateUSNStudentAttendanceHistory(id: Int, stdUsn: String?)

    @Query("UPDATE StudentListClass SET stdName =:stdName WHERE stdId =:id")
    fun updateNameStudentLists(id: Int, stdName: String?)

    @Query("UPDATE StudentListClass SET stdUsn =:stdUsn WHERE stdId =:id")
    fun updateUSNStudentListsFromID(id: Int, stdUsn: String?)

}