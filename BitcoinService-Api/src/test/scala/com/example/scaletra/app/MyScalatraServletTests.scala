package com.example.scaletra.app

import com.bitcoin.beans.{BitcoinData, Price}
import com.bitcoin.service.BitcoinServlet
import com.bitcoin.util.BitcoinUtils
import org.scalatra.test.scalatest._
import org.scalatest.FunSuite

class MyScalatraServletTests extends FunSuite {


  //To Test Maxprice in BitCoin data in each bucket/chunk
   test("Test MaxPrice in Bitcoins Data") {

    val bitcoin = new BitcoinData
    val prices = new Array[Price](31)

    for( i <- 0 to 30 ){
      val date = BitcoinUtils.df.parse(s"2018-11-${i+1}")
      val price = Price(s"${ i * 10 + i }", s"2018-11-${i+1}",date.getTime)
      prices(i) = price
    }

    bitcoin.data.prices = prices
    bitcoin.updatePriceFields()

    val result = BitcoinUtils.getMaxPriceInEachBucket(bitcoin,"2018-11-01","2018-11-30",7)

    result.data.prices.foreach( println )

    assert(result.errorMessage.trim.length == 0)
  }

}
