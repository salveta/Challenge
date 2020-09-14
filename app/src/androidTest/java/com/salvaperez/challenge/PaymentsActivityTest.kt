package com.salvaperez.challenge

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.rule.ActivityTestRule
import com.salvaperez.challenge.presentation.payment.PaymentActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

class PaymentsActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(PaymentActivity::class.java)

    @Test
    fun check_delete_icon_is_visible_and_payment_buttons() {
        Espresso.onView(ViewMatchers.withId(R.id.cntDeleteProducts))
            .check(matches(Matchers.`is`(isDisplayed())))

        Espresso.onView(ViewMatchers.withId(R.id.btnPaypal))
            .check(matches(Matchers.`is`(isDisplayed())))

        Espresso.onView(ViewMatchers.withId(R.id.btnCreditCard))
            .check(matches(Matchers.`is`(isDisplayed())))
    }
}