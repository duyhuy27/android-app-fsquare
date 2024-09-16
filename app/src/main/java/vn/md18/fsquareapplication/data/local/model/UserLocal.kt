package vn.md18.fsquareapplication.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


const val TABLE_NAME = "user_table"

@Entity(tableName = TABLE_NAME)
data class UserLocal(
    @ColumnInfo(name = "active")
    var active: Int?, // 1

    @ColumnInfo(name = "address")
    var address: String?, // An Giang

    @ColumnInfo(name = "area")
    var area: String?, // An Giang

    @ColumnInfo(name = "areaId")
    var areaId: Int?, // 3

    @ColumnInfo(name = "areaIds")
    var areaIds: List<Any>?,

    @ColumnInfo(name = "areas")
    var areas: List<Any>?,

    @ColumnInfo(name = "authorities")
    var authorities: List<Any>?,

    @ColumnInfo(name = "birthday")
    var birthday: Int?, // 0

    @ColumnInfo(name = "code")
    var code: String?, // 84339995609

    @ColumnInfo(name = "created")
    var created: Long?, // 1610449945969

    @ColumnInfo(name = "createdBy")
    var createdBy: String?, // 84339995609

    @ColumnInfo(name = "currentHomeId")
    var currentHomeId: Int?, // 1954

    @ColumnInfo(name = "email")
    var email: String?, // namlx@vnpt-technolog.vn

    @ColumnInfo(name = "fullName")
    var fullName: String?, // Namlx

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    var id: Int?, // 1050

    @ColumnInfo(name = "langKey")
    var langKey: String?, // vn

    @ColumnInfo(name = "numberOfDevices")
    var numberOfDevices: Int?, // 1

    @ColumnInfo(name = "phone")
    var phone: String?, // 84339995609

    @ColumnInfo(name = "roles")
    var roles: List<Any>?,

    @ColumnInfo(name = "transactionId")
    var transactionId: String?, // 862586530340499456

    @ColumnInfo(name = "type")
    var type: Int?, // 1

    @ColumnInfo(name = "updated")
    var updated: Long?, // 1633602993244

    @ColumnInfo(name = "updatedBy")
    var updatedBy: String?, // 84339995609

    @ColumnInfo(name = "userAvatar")
    var userAvatar: String?, // /attachment/images/avatar_EXonV3foSstUPz5F_1633602993068_.jpg

    @ColumnInfo(name = "username")
    var username: String? // Hh7B6aVwRFpS4yxkHExr4A==
)