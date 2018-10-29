# bitcoin

This project is implemented using scala and sbt. To run this project sbt should be installed in your local system.
In this project 3 service were implemented
1.Allowing users to see the bitcoin price movement for last week, last month, last year or any
  custom date. url("http://localhost:8080/bitcoin/getpricemovement/:date) .
  date values can be(WEEK,MONTH,YEAR or any custom date in yyyy-MM-dd format)
2. Allowing users to see the X days rolling / moving average bitcoin prices between two custom
  dates.url("http://localhost:8080/bitcoin/avgprice/:fromdate/:todate")
  fromdate is any cusotm date in yyyy-MM-dd format and todate is also a any custom date in yyyy-MM-dd format.
3. Allowing users to get bitcoin trading decision on whether to BUY, SELL or HOLD based on the
   price movement for the last X days. url("http://localhost:8080/bitcoin/getpredicteddecision/:numdays").
   numdays is an integer and number days that you want to use to take the trading decision
   
