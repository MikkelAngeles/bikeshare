package mhel.itu.moapd.bikeshare.model.entity

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Bike (
    @PrimaryKey
    var id:             Long?       = null,
    var name:           String?     = null,
    var type:           String?     = null,
    var image:          ByteArray?  = ByteArray(0),
    var isAvailable:    Boolean     = true,
    var location:       String?     = "",
    var rate:           Float?      = null
) : RealmModel