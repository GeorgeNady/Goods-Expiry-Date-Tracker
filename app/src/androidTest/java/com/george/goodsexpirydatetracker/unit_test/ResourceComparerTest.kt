package com.george.goodsexpirydatetracker.unit_test

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.george.goodsexpirydatetracker.R
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResourceComparerTest {

    private lateinit var resourceComparer : ResourceComparer

    /**
     * # this block of code compiled every time before calling each test case
     * */
    @Before
    fun setup() {
        resourceComparer = ResourceComparer()
    }

    /**
     * # this block of code compiled every time after calling each test case
     * and actually we use it to destroy our object's instances
     * */
    @After
    fun teardown() {

    }

    @Test
    fun stringResourceSameAsGivenString_ReturnsTrue() {
        resourceComparer = ResourceComparer()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context,R.string.app_name,"Goods Expiry Date Tracker")
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceDifferentAsGivenString_ReturnsFalse() {
        resourceComparer = ResourceComparer()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context,R.string.app_name,"hello")
        assertThat(result).isFalse()
    }

}