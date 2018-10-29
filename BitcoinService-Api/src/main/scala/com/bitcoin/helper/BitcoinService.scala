package com.bitcoin.helper

import com.bitcoin.beans.BitcoinData

trait BitcoinService {

  /**
    * Allowing users to see the bitcoin price movement for last week, last month, last year or any
    * custom date
    * For last week send WEEK as input
    * For last month send MONTH as input
    * For last yeat send YEAT as input
    * For to get bitcoint movement for specific date send the respective date as input
    * @param date
    * @return BitcoinData
    */
  def getProceMovement(date:String):BitcoinData

  /**
    * Allowing users to see the X days rolling / moving average bitcoin prices between two custom
    * dates.
    * @param fromDate
    * @param toDate
    * @return BitcoinData
    */
  def getAveragePrice(fromDate:String,toDate:String):BitcoinData

  /**
    * Allowing users to get bitcoin trading decision on whether to BUY, SELL or HOLD based on the
    * price movement for the last X days (use any calculation/algorithm which you’d consider optimal to
    * determine the outcome).
    *
    * @param numDays
    * @return BitcoinData
    */
  def getTradingDecision(numDays:String):BitcoinData

}
