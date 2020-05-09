package mhel.itu.moapd.bikeshare

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import junit.framework.Assert.assertNotNull
import mhel.itu.moapd.bikeshare.model.entity.Account
import mhel.itu.moapd.bikeshare.model.repository.AccountRepository

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {
    @Mock
    private lateinit var mockContext: Context

    @Test
    fun addAccountGivenValidInputReturnsAccount() {
        //`when`(mockContext.os))

        Realm.init(mockContext)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build());

        val acc = Account(
            userName    = "Mikkel Helmersen",
            rides       = 0,
            balance     = 1337f
        )
        val rs = AccountRepository.add(acc)
        assertNotNull(rs);
    }
}
