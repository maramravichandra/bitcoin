package com.example.scaletra.app

import com.bitcoin.service.BitcoinServlet
import org.scalatra.test.scalatest._

class MyScalatraServletTests extends ScalatraFunSuite {

  addServlet(classOf[BitcoinServlet], "/*")

  test("GET / on MyScalatraServlet should return status 200") {
    get("/bitcoin") {
      status should equal (200)
    }
  }

}
