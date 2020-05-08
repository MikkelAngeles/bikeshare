package mhel.itu.moapd.bikeshare.model.repository
import mhel.itu.moapd.bikeshare.model.entity.Bike
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.deleteFromRealm
import io.realm.kotlin.where

object BikeRepository {

    private fun realm() : Realm {
        return Realm.getDefaultInstance()
    }

    fun findAsync(id : Long?) : Bike {
        return realm().where<Bike>()
                      .equalTo("id", id)
                      .findFirstAsync();
    }

    fun add(bike : Bike) : Bike? {
        val index = realm().where<Bike>().count();
        realm().executeTransaction {
            val tmp = it.createObject<Bike>(index)
            tmp.name        = bike.name
            tmp.type        = bike.type
            tmp.location    = bike.location
            tmp.rate        = bike.rate
            tmp.isAvailable = bike.isAvailable
        }
        bike.id = index;
        return bike;
    }

    fun read() : List<Bike> {
        return realm().where<Bike>()
                      .findAll()
                      .toList()
    }

    fun readAvailable() : List<Bike> {
        return realm().where<Bike>()
                      .equalTo("isAvailable", true)
                      .findAll()
    }

    fun remove(bike: Bike) {
        val rs = findAsync(bike.id);
        realm().executeTransaction {
            rs.deleteFromRealm();
        }
    }
}