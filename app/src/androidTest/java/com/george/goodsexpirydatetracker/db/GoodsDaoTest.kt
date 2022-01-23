package com.george.goodsexpirydatetracker.db

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.george.goodsexpirydatetracker.getOrAwaitValue
import com.george.goodsexpirydatetracker.models.Commodity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.properties.Delegates

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class GoodsDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: GoodsDatabase
    private lateinit var dao: GoodsDao
    private var currentDate by Delegates.notNull<Long>()


    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GoodsDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getDao()
        currentDate = Date().time
    }

    @After
    fun teardown() {
        database.close()
    }

    /*@Test
    fun upsertCommodityWithASC() = runBlockingTest {
        val currentDate = Date().time
        val commodity= Commodity(id = 1, name = "n", category = "c", expiryDate = currentDate)
        dao.upsertCommodity(commodity)

        val allGoodsSortedByDateASC = dao.getGoodsSortedByExpiryDateDescending().getOrAwaitValue()

        assertThat(allGoodsSortedByDateASC).contains(commodity)
    }*/

    @Test
    fun upsertCommodityWithDESC() = runBlockingTest {
        val commodity= Commodity(id = 1, name = "n", category = "c", expiryDate = currentDate)
        dao.upsertCommodity(commodity)

        val allGoodsSortedByDateDESC = dao.getGoodsSortedByExpiryDateDescending().getOrAwaitValue()

        assertThat(allGoodsSortedByDateDESC).contains(commodity)
    }

    /*@Test
    fun deleteCommodityWithASC() = runBlockingTest {
        val currentDate = Date().time
        val commodity= Commodity(id = 1, name = "n", category = "c", expiryDate = currentDate)
        dao.apply {
            upsertCommodity(commodity)
            deleteCommodity(commodity)
        }

        val allGoodsSortedByDateASC = dao.getGoodsSortedByDateASC().getOrAwaitValue()
        assertThat(allGoodsSortedByDateASC).doesNotContain(commodity)

    }*/

    @Test
    fun deleteCommodityWithDESC() = runBlockingTest {
        val commodity= Commodity(id = 1, name = "n", category = "c", expiryDate = currentDate)
        dao.apply {
            upsertCommodity(commodity)
            deleteCommodity(commodity)
        }

        val allGoodsSortedByDateDESC = dao.getGoodsSortedByExpiryDateDescending().getOrAwaitValue()
        assertThat(allGoodsSortedByDateDESC).doesNotContain(commodity)

    }

    @Test
    fun getGoodsSortedByDateDESC() = runBlockingTest {
        val commodity1 = Commodity(id = 1, name = "n", category = "c", expiryDate = currentDate)
        val commodity2 = Commodity(id = 2, name = "n", category = "c", expiryDate = currentDate+1000)
        val commodity3 = Commodity(id = 3, name = "n", category = "c", expiryDate = currentDate+2000)
        val commodity4 = Commodity(id = 4, name = "n", category = "c", expiryDate = currentDate+6000)
        val commodity5 = Commodity(id = 5, name = "n", category = "c", expiryDate = currentDate+4000)
        val commodity6 = Commodity(id = 6, name = "n", category = "c", expiryDate = currentDate+3000)

        dao.upsertCommodity(commodity1)
        dao.upsertCommodity(commodity2)
        dao.upsertCommodity(commodity3)
        dao.upsertCommodity(commodity4)
        dao.upsertCommodity(commodity5)
        dao.upsertCommodity(commodity6)

        // get data descending
        val allGoodsSortedByDateDESC = dao.getGoodsSortedByExpiryDateDescending().getOrAwaitValue()

        // big item must come first
        val firstItm = allGoodsSortedByDateDESC.first()
        // small item must come latest
        val lastItem = allGoodsSortedByDateDESC.last()

        val result = firstItm.expiryDate!! > lastItem.expiryDate!!

        assertThat(result).isTrue()

    }

    /*@Test
    fun getGoodsSortedByDateASE() = runBlockingTest {
        val currentDate = Date().time
        val commodity1 = Commodity(id = 1, name = "n", category = "c", expiryDate = currentDate)
        val commodity2 = Commodity(id = 2, name = "n", category = "c", expiryDate = currentDate+1000)
        val commodity3 = Commodity(id = 3, name = "n", category = "c", expiryDate = currentDate+2000)
        val commodity4 = Commodity(id = 4, name = "n", category = "c", expiryDate = currentDate+6000)
        val commodity5 = Commodity(id = 5, name = "n", category = "c", expiryDate = currentDate+4000)
        val commodity6 = Commodity(id = 6, name = "n", category = "c", expiryDate = currentDate+3000)

        dao.upsertCommodity(commodity1)
        dao.upsertCommodity(commodity2)
        dao.upsertCommodity(commodity3)
        dao.upsertCommodity(commodity4)
        dao.upsertCommodity(commodity5)
        dao.upsertCommodity(commodity6)

        // get data descending
        val allGoodsSortedByDateDESC = dao.getGoodsSortedByDateASC().getOrAwaitValue()

        // small item must come first
        val firstItm = allGoodsSortedByDateDESC.first()
        // big item must come latest
        val lastItem = allGoodsSortedByDateDESC.last()

        val result = firstItm.expiryDate!! < lastItem.expiryDate!!

        assertThat(result).isTrue()

    }*/

    @Test
    fun getAllValidGoodsSortedByExpiryDateDescending() = runBlockingTest {
        val commodity1 = Commodity(id = 1, name = "n", category = "c", expiryDate = currentDate)
        val commodity2 = Commodity(id = 2, name = "n", category = "c", expiryDate = currentDate+1000)
        val commodity3 = Commodity(id = 3, name = "n", category = "c", expiryDate = currentDate+2000)
        val commodity4 = Commodity(id = 4, name = "n", category = "c", expiryDate = currentDate-6000)
        val commodity5 = Commodity(id = 5, name = "n", category = "c", expiryDate = currentDate+4000)
        val commodity6 = Commodity(id = 6, name = "n", category = "c", expiryDate = currentDate-3000)

        dao.upsertCommodity(commodity1)
        dao.upsertCommodity(commodity2)
        dao.upsertCommodity(commodity3)
        dao.upsertCommodity(commodity4)
        dao.upsertCommodity(commodity5)
        dao.upsertCommodity(commodity6)

        // get data descending
        val allValidGoods = dao.getAllValidGoodsSortedByExpiryDateDescending(currentDate).getOrAwaitValue()
        Log.d("GEORGE", "$allValidGoods")

        val expiredGoods = allValidGoods.filter { it.expiryDate!! < currentDate }
        val isGreater = expiredGoods.isEmpty()

        // big item must come first
        val firstItm = allValidGoods.first().expiryDate!!
        // small item must come latest
        val lastItem = allValidGoods.last().expiryDate!!

        val isArranged = firstItm > lastItem

        assertThat(isArranged && isGreater).isTrue()
    }

    @Test
    fun getAllExpiredGoodsSortedByExpiryDateDescending() = runBlockingTest {
        val commodity1 = Commodity(id = 1, name = "n", category = "c", expiryDate = currentDate)
        val commodity2 = Commodity(id = 2, name = "n", category = "c", expiryDate = currentDate+1000)
        val commodity3 = Commodity(id = 3, name = "n", category = "c", expiryDate = currentDate-2000)
        val commodity4 = Commodity(id = 4, name = "n", category = "c", expiryDate = currentDate-6000)
        val commodity5 = Commodity(id = 5, name = "n", category = "c", expiryDate = currentDate+4000)
        val commodity6 = Commodity(id = 6, name = "n", category = "c", expiryDate = currentDate-3000)

        dao.upsertCommodity(commodity1)
        dao.upsertCommodity(commodity2)
        dao.upsertCommodity(commodity3)
        dao.upsertCommodity(commodity4)
        dao.upsertCommodity(commodity5)
        dao.upsertCommodity(commodity6)

        // get data descending
        val allExpiredGoods = dao.getAllExpiredGoodsSortedByExpiryDateDescending(currentDate).getOrAwaitValue()

        val validGoods =  allExpiredGoods.filter { it.expiryDate!! > currentDate }
        val isSmaller = validGoods.isEmpty().also { Log.d("GEORGE",": $it") }

        // big item must come first
        Log.d("GEORGE", "$allExpiredGoods")
        val firstItm = allExpiredGoods.first().expiryDate!!
        // small item must come latest
        Log.d("GEORGE", "$allExpiredGoods")
        val lastItem = allExpiredGoods.last().expiryDate!!

        val isArranged = (firstItm > lastItem).also { Log.d("GEORGE",": $it") }

        assertThat(isArranged && isSmaller).isTrue()
    }


}