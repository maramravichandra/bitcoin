package com.bitcoin.service

import com.bitcoin.helper.BitcoinService
import com.bitcoin.util.BitcoinUtils
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import org.scalatra.ScalatraServlet

class BitcoinServlet(bitcoinService:BitcoinService) extends ScalatraServlet  {

  get("/getpricemovement/:date") {
    val result = bitcoinService.getProceMovement(params("date"))
    BitcoinUtils.getJSON(result)
  }

  get("/avgprice/:fromdate/:todate") {
    val result = bitcoinService.getAveragePrice(params("fromdate"),params("todate"))
    BitcoinUtils.getJSON(result)
  }

  get("/getpredicteddecision/:numdays") {
    val result = bitcoinService.getTradingDecision(params("numdays"))
    BitcoinUtils.getJSON(result)
  }

  get("/getmaxpriceinbucket/:startDt/:endDt/:bucketSize") {
    val result = bitcoinService.getMaxPriceInEachBucket(params("startDt"),params("endDt"),params("bucketSize").toInt)
    BitcoinUtils.getJSON(result)
  }


}
