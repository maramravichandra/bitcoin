package com.bitcoin.beans

import java.util.{Calendar, Date}

import com.bitcoin.util.BitcoinUtils
import java.text.SimpleDateFormat

import com.google.gson.Gson

class BitcoinData{

  var data:Data = Data("","",Array.empty)
  var errorMessage:String = ""
  var avgPrice:Float = 0
  var decision = ""

  def updatePriceFields(): Unit ={
    val cal = Calendar.getInstance()
    val df = new SimpleDateFormat(BitcoinUtils.DATE_FORMAT)
    data.prices.foreach{ price =>
      val date = df.parse(price.time)
      cal.setTime(date)
      price.timestamp = date.getTime
      price.date  = date
      price.week  = cal.get(Calendar.WEEK_OF_YEAR)
      price.month = cal.get(Calendar.MONTH)
      price.year  = cal.get(Calendar.YEAR)
    }

    data.prices = data.prices.sortBy( price => (price.year+""+price.month+""+price.week).toInt)
  }
}

case class Data(base:String,currency:String, var prices:Array[Price])
case class Price(price:String,time:String, @transient var timestamp:Long,
                 @transient var  date:Date = null,@transient var  week:Int = 0,
                 @transient var  month:Int = 0,@transient var  year:Int = 0)