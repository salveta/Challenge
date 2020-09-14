package com.salvaperez.challenge

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.salvaperez.challenge.presentation.products.ProductsActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test

class ProductsActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(ProductsActivity::class.java)

    @Test
    fun check_shopping_is_not_visible() {
        onView(withId(R.id.cntShoppingCart)).check(matches(not(isDisplayed())))
    }

    @Test
    fun check_recycler_is_visible_and_not_text() {
        onView(withId(R.id.rvProducts)).check(matches(isDisplayed()))
        onView(withId(R.id.txVoucherUnavailable)).check(matches(not(isDisplayed())))
    }
}
