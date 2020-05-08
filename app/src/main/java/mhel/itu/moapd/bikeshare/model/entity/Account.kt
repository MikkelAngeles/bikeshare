package mhel.itu.moapd.bikeshare.model.entity

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Account (
    @PrimaryKey
    var id:         Long?       = null,
    var userName:   String?     = null,
    var rides:      Int         = 0,
    var balance:    Float?      = 0.0f
) : RealmModel