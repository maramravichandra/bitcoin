package com.bitcoin.util

import java.text.SimpleDateFormat
import java.util.Date

import com.bitcoin.beans.{BitcoinData, Data, Price}
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.google.gson.Gson
import org.apache.commons.lang3.math.NumberUtils
import org.json.JSONObject

import scala.io.Source

object BitcoinUtils {

  val WEEK  = "WEEK"
  val MONTH = "MONTH"
  val YEAR  = "YEAR"
  var DATE_FORMAT = "yyyy-MM-dd";
  val df = new SimpleDateFormat(BitcoinUtils.DATE_FORMAT)
  private var bitcoinResult:Option[BitcoinData] = None


  def getBitcoinData():BitcoinData= {

    if (!bitcoinResult.isDefined) {
      val url = "https://www.coinbase.com/api/v2/prices/BTC-USD/historic?period=all"
      val json = Source.fromURL(url).mkString
      val gson = new Gson();
      val bitcoinData = gson.fromJson(s"""$json""".stripMargin, classOf[BitcoinData]);
      bitcoinData.updatePriceFields()
      println("bitcoinData", bitcoinData.data.prices.length)
      bitcoinResult = Some(bitcoinData)
    }

    bitcoinResult.get
  }


  def getBitcoinPrices(bitcoinData:BitcoinData,priceTime:String) ={

    val priceTmp = bitcoinData.data.prices(bitcoinData.data.prices.length - 1)
    println( "priceTmp",priceTmp.toString)
    val bitcoinDataTmp = new BitcoinData()

    priceTime match{
      case WEEK =>
        bitcoinDataTmp.data = Data(bitcoinData.data.base,bitcoinData.data.currency,
          bitcoinData.data.prices.filter( price => price.year == priceTmp.year && price.week == priceTmp.week ))
        bitcoinDataTmp

      case MONTH =>
        bitcoinDataTmp.data = Data(bitcoinData.data.base, bitcoinData.data.currency,
          bitcoinData.data.prices.filter( price => price.year == priceTmp.year && price.month == priceTmp.month ))
        bitcoinDataTmp

      case YEAR =>
        bitcoinDataTmp.data = Data(bitcoinData.data.base,bitcoinData.data.currency,
          bitcoinData.data.prices.filter( price => price.year == priceTmp.year ))
        bitcoinDataTmp

      case _ =>
        try{

          val inputDate = df.parse(priceTime);
          bitcoinDataTmp.data = Data(bitcoinData.data.base, bitcoinData.data.currency,
            bitcoinData.data.prices.filter( price => price.timestamp == inputDate.getTime))
          bitcoinDataTmp
        }catch{
          case exp:Exception =>
            bitcoinDataTmp.errorMessage = exp.getMessage
            bitcoinDataTmp
        }
    }
  }

  def getBitcoinAveragePrice( bitcoinData:BitcoinData, fromDate:String,toDate:String ) ={

    val bitcoinAverage = new BitcoinData()
    try{
      val fromDt = df.parse(fromDate).getTime
      val toDt   = df.parse(toDate).getTime
      val prices = bitcoinData.data.prices.filter( price => fromDt <= price.timestamp && price.timestamp < toDt)
      val price = prices.map( _.price.toFloat)

      bitcoinAverage.avgPrice = (price.sum/price.length).toFloat
      bitcoinAverage.data = Data(bitcoinData.data.base,bitcoinData.data.currency,prices)
      bitcoinAverage
    }catch{
      case exp:Exception =>
        bitcoinAverage.errorMessage = exp.getMessage
        bitcoinAverage
    }
  }

  def getBitcoinTradingDecision( bitcoinData:BitcoinData, numDaysTmp:String ) ={

    val numDays = NumberUtils.toInt(numDaysTmp)
    val bitcoinDataDes = new BitcoinData()

    if( numDays <= 0 ){
      bitcoinDataDes.errorMessage = "Please give correct number of days to get Bitcoin trading Decision"
    }else if(numDays < 2){
      bitcoinDataDes.errorMessage = "Please give number of days > 1"
    }else{
      val lastXDaysPrices = if( numDays < bitcoinData.data.prices.length ) bitcoinData.data.prices.take(numDays) else bitcoinData.data.prices
      val prices = lastXDaysPrices.map( _.price.toFloat)
      val minPrice = prices.min
      val maxPrice = prices.max
      val avgPrice = prices.sum.toFloat/prices.length

      val latestPrice = if( bitcoinData.data.prices.length > 0 ) bitcoinData.data.prices(0) else Price("0","",0,new Date(),0,0,0)
      val decision = if( latestPrice.price.toFloat < avgPrice ) "BUY"
      else if( latestPrice.price.toFloat > avgPrice && latestPrice.price.toFloat < maxPrice ) "HOLD"
      else if( latestPrice.price.toFloat > avgPrice && latestPrice.price.toFloat == maxPrice ) "SELL"
      else "HOLD"

      bitcoinDataDes.decision = decision
    }

    bitcoinDataDes
  }

  def getMaxPriceInEachBucket( bitcoinDataExisted: BitcoinData, startDate:String,endDate:String,bucketSize:Int): BitcoinData ={

    val bitcoinData = new BitcoinData

    if( bucketSize <= 0 ){
      bitcoinData.errorMessage = "Bucket size must be greater then 0"
    }
    else{

      try{
        val stDt = df.parse(startDate).getTime
        val endDt = df.parse(endDate).getTime

        val filteredPrices = bitcoinDataExisted.data.prices.filter( price => stDt <= price.timestamp && price.timestamp < endDt)
        val bucketedData = filteredPrices.grouped(bucketSize).map(_.maxBy( x => x.price.toFloat))
        bitcoinData.data.prices = bucketedData.toArray

      }catch{
        case exp:Exception =>
          bitcoinData.errorMessage = s"Start Date/ End Date format is incorrect. Please make format is $DATE_FORMAT"
      }

    }

    bitcoinData
  }



  private def getUpAndDownOfBitcoin( bitcoinData:BitcoinData, avgPrice:Float ):(Int,Int)={

    val downs = bitcoinData.data.prices.filter( _.price.toFloat < avgPrice ).length
    val ups   = bitcoinData.data.prices.filter( _.price.toFloat >= avgPrice ).length
    (downs,ups)
  }

  def getJSON(bitcoinData:BitcoinData):String={
    val gson = new Gson()
    gson.toJson(bitcoinData)
  }

}
