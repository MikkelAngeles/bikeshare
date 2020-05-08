package mhel.itu.moapd.bikeshare.model.repository

import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.deleteFromRealm
import io.realm.kotlin.where
import mhel.itu.moapd.bikeshare.model.entity.Ride

object RideRepository {
    private fun realm() : Realm {
        return Realm.getDefaultInstance()
    }

    fun findAsync(id : Long?) : Ride {
        return realm().where<Ride>()
            .equalTo("id", id)
            .findFirstAsync();
    }

    fun add(ride : Ride) : Ride? {
        val index = realm().where<Ride>().count();
        realm().executeTransaction {
            it.createObject<Ride>(index)
        }
        ride.id = index;
        return ride;
    }

    fun read() : List<Ride> {
        return realm().where<Ride>()
            .findAll()
            .toList()
    }


    fun remove(ride: Ride) {
        val rs = findAsync(ride.id);
        realm().executeTransaction {
            rs.deleteFromRealm();
        }
    }
}