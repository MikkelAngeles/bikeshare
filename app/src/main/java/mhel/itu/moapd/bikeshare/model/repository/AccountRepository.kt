package mhel.itu.moapd.bikeshare.model.repository

import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.deleteFromRealm
import io.realm.kotlin.where
import mhel.itu.moapd.bikeshare.model.entity.Account

object AccountRepository {
    private fun realm() : Realm {
        return Realm.getDefaultInstance()
    }

    fun find(id : Long?) : Account? {
        return realm().where<Account>()
            .equalTo("id", id)
            .findFirst();
    }

    fun add(account : Account) : Account? {
        val index = realm().where<Account>().count();
        realm().executeTransaction {
            val tmp = it.createObject<Account>(index)
            tmp.userName        = account.userName
            tmp.rides           = account.rides
            tmp.balance         = account.balance
        }
        account.id = index;
        return account;
    }

    fun addBalance (id : Long, amount : Float?) : Float? {
        val user = find(id) ?: return null;
        val newAmount = (amount?: 0.0f)
        val curBalance = (user.balance?: 0.0f)

        val newBalance = newAmount.plus(curBalance);
        realm().executeTransaction {
            user.balance = newBalance;
        }
        return user.balance;
    }

    fun read() : List<Account> {
        return realm().where<Account>()
            .findAll()
            .toList()
    }

    fun remove(id: Long) {
        val rs = find(id) ?:return
        realm().executeTransaction {
            rs.deleteFromRealm();
        }
    }

    fun update(account : Account) : Boolean {
        val rs = find(account.id) ?: return false
        realm().executeTransaction {
            rs.userName        = account.userName
            rs.rides           = account.rides
            rs.balance         = account.balance
            rs.activeRide      = account.activeRide
        }
        return true;
    }

    fun startRide(id : Long?, rideId : Long?) : Boolean {
        val rs = find(id) ?: return false
        realm().executeTransaction {
            rs.activeRide      = rideId
            rs.rides           = rs.rides + 1
        }
        return true;
    }

    fun endRide(id : Long?, price : Float?) : Account? {
        val rs = find(id) ?: return null
        realm().executeTransaction {
            rs.balance         = rs.balance!!.minus(price!!)
            rs.activeRide      = null
        }
        return rs;
    }



}