package com.bitcoin.helper

import com.bitcoin.beans.BitcoinData
import com.bitcoin.util.BitcoinUtils

class BitcoinServiceImpl extends BitcoinService {

  val bitcoinData = BitcoinUtils.getBitcoinData()

  override def getProceMovement(date: String): BitcoinData = {
    BitcoinUtils.getBitcoinPrices(bitcoinData,date)
  }

  override def getAveragePrice(fromDate: String, toDate: String): BitcoinData = {
    BitcoinUtils.getBitcoinAveragePrice(bitcoinData,fromDate,toDate)
  }

  override def getTradingDecision(numDays: String): BitcoinData = {
    BitcoinUtils.getBitcoinTradingDecision(bitcoinData,numDays)
  }

  override def getMaxPriceInEachBucket(startDate: String, endDate: String, bucketSize: Int): BitcoinData = {
    BitcoinUtils.getMaxPriceInEachBucket(bitcoinData,startDate,endDate,bucketSize)
  }
}
