package mhel.itu.moapd.bikeshare.model.entity

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.sql.Time
import java.time.LocalDateTime

@RealmClass
open class Ride (
    @PrimaryKey
    var id:                 Long?      = null,
    var userId:             Long?      = null,
    var bikeId:             Long?      = null,
    var name:               String?    = null,
    var type:               String?    = null,
    var rate:               Float?     = null,
    var price:              Float?     = null,
    var time:               Long?      = null,
    var startTime:          Long ?      = null,
    var endTime:            Long ?      = null,
    var startLocation:      String?      = null,
    var endLocation:        String?      = null,
    var image:              ByteArray?  = ByteArray(0)
) : RealmModel