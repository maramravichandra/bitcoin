package com.bitcoin.helper

import com.bitcoin.beans.BitcoinData
import com.bitcoin.util.BitcoinUtils

class BitcoinServiceImpl extends BitcoinService {

  override def getProceMovement(date: String): BitcoinData = {
    BitcoinUtils.getBitcoinPrices(BitcoinUtils.getBitcoinData,date)
  }

  override def getAveragePrice(fromDate: String, toDate: String): BitcoinData = {
    BitcoinUtils.getBitcoinAveragePrice(BitcoinUtils.getBitcoinData,fromDate,toDate)
  }

  override def getTradingDecision(numDays: String): BitcoinData = {
    BitcoinUtils.getBitcoinTradingDecision(BitcoinUtils.getBitcoinData,numDays)
  }

  override def getMaxPriceInEachBucket(startDate: String, endDate: String, bucketSize: Int): BitcoinData = {
    BitcoinUtils.getMaxPriceInEachBucket(BitcoinUtils.getBitcoinData,startDate,endDate,bucketSize)
  }
}
