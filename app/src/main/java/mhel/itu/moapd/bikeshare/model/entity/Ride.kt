package mhel.itu.moapd.bikeshare.model.entity

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Ride (
    @PrimaryKey
    var id:             Long?      = null,
    var start:          String?    = null,
    var end:            String?    = null,
    var price:          Float?     = null,
    var userId:         Long?      = null
) : RealmModel