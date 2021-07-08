package com.example.influxbeta.utils

import junit.framework.Assert.assertEquals
import org.junit.Test

class UtilTest
{
    @Test
    fun quantity_price_in_calc_amount_out_testcase()
    {
        val amount = Util.amount_out_quantityNprice_In(5,20.0)
        assertEquals(100.0,amount)
    }
}