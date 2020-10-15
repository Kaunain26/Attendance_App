package com.knesarcreation.attendanceapp.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
data class AttendanceSheet(
    @PrimaryKey val id: Int,
    val subName: String,
    val classYear: String,
    val profName: String,
    val totalStud: Int
) {
    var subCode: String = ""
}

@Entity
data class StudentListClass(
    @PrimaryKey val stdId: Int,
    var isChecked: Boolean = false,
    val stdName: String,
    val stdSrNo: Int,
    val stdUsn: String
)

data class AttendanceSheetAndStudentList(
    @Embedded val attendanceSheet: AttendanceSheet,
    @Relation(
        parentColumn = "id",
        entityColumn = "stdSrNo"
    )
    val studentListClass: MutableList<StudentListClass>
)

@Entity
data class AttendanceHistory(
    @PrimaryKey val hisID: Int,
    val subName: String,
    val classYear: String,
    val profName: String,
    val totalStud: Int
) {
    //    val classYear: String = ""
//    var hisID = 0

    //    val id: Int = 0
    var subCode: String = ""

}

@Entity
data class AttendanceDateTimes(
    @PrimaryKey val id: Int,
    val dateTime: String,
    var attendHisId: Int
)

data class AttendanceHistoryAndAttendanceDatesTime(
    @Embedded val attendanceHistory: AttendanceHistory,
    @Relation(
        parentColumn = "hisID",
        entityColumn = "attendHisId"
    )
    val attendanceDateTimes: MutableList<AttendanceDateTimes>
)

@Entity
data class StudentPastAttendance(
    @PrimaryKey val id: Int,
    val stdHistId: Int,
    var isChecked: Boolean = false,
//    var stdHis: Int = 0,
    val stdName: String,
    var stdUsn: String? = null,
    val stdId: Int,
)

data class AttendanceDatesTimeAndStudentAttendanceHistory(
    @Embedded val attendanceDateTimes: AttendanceDateTimes,
    @Relation(
        parentColumn = "id",
        entityColumn = "stdHistId"
    )
    val studentPastAttendance: MutableList<StudentPastAttendance>
)

@Entity
data class StudentDetails(
    @PrimaryKey val id: Int,
    val sheetNo: Int,
    val studUsn: String,
    val studName: String,
    val studAddress: String,
    val studContact: String,
    val studCourse: String,
    val absent: Int = 0,
    var attendancePercentage: Float,
    val present: Int = 0
)

@Entity
data class TempStudentDetails(
    @PrimaryKey val position: Int,
    val absent: Int,
    val present: Int
)