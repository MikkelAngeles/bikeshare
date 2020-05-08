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

    fun find(id : Long?) : Ride? {
        return realm().where<Ride>()
            .equalTo("id", id)
            .findFirst();
    }

    fun findByUserId(userId : Long?) : Ride? {
        return realm().where<Ride>()
            .equalTo("userId", userId)
            .findFirst();
    }

    fun add(ride : Ride) : Ride? {
        val index = realm().where<Ride>().count();
        realm().executeTransaction {
            val tmp = it.createObject<Ride>(index)
            tmp.userId          = ride.userId
            tmp.bikeId          = ride.bikeId
            tmp.rate            = ride.rate
            tmp.name            = ride.name
            tmp.type            = ride.type
            tmp.price           = ride.price
            tmp.time            = ride.time
            tmp.startLocation   = ride.startLocation
            tmp.endLocation     = ride.endLocation
            tmp.startTime       = ride.startTime
            tmp.endTime         = ride.endTime
            tmp.image           = ride.image
        }
        ride.id = index;
        return ride;
    }

    fun update(ride: Ride) : Boolean{
        val rs = find(ride.id)?:return false
        realm().executeTransaction {
            rs.userId          = ride.userId
            rs.name            = ride.name
            rs.type            = ride.type
            rs.price           = ride.price
            rs.time            = ride.time
            rs.bikeId          = ride.bikeId
            rs.rate            = ride.rate
            rs.startLocation   = ride.startLocation
            rs.endLocation     = ride.endLocation
            rs.startTime       = ride.startTime
            rs.endTime         = ride.endTime
            rs.image           = ride.image
        }
        return true;
    }

    fun endRide(id : Long?, endLocation : String?, endTime : Long?, price : Float?, time: Long?) : Boolean{
        val rs = find(id)?:return false
        realm().executeTransaction {
            rs.endLocation     = endLocation
            rs.endTime         = endTime
            rs.price           = price
            rs.time            = time
        }
        return true;
    }

    fun readByUserId(userId : Long?) : List<Ride> {
        return realm().where<Ride>()
            .equalTo("userId", userId)
            .findAll()
            .toList()
    }

    fun read() : List<Ride> {
        return realm().where<Ride>()
            .findAll()
            .toList()
    }


    fun remove(ride: Ride) {
        val rs = find(ride.id)?:return
        realm().executeTransaction {
            rs.deleteFromRealm();
        }
    }
}