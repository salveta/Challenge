package com.salvaperez.challenge

import com.salvaperez.challenge.presentation.extensions.formatCredit
import org.assertj.core.api.Assertions
import org.junit.Test

class CurrencyTest {

    @Test
    fun `correct remove currency zero decimal`() {
        val price: String =  "2.0"
        val result = price.formatCredit()
        Assertions.assertThat(result).isEqualToIgnoringCase("2")
    }

    @Test
    fun `correct currency price formatting with decimal`() {
        val price: String =  "2.5"
        val result = price.formatCredit()
        Assertions.assertThat(result).isEqualToIgnoringCase("2.5")
    }
}