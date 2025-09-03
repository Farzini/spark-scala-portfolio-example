package com.example

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/** A tiny DataFrame job that groups 'events' by user and counts per day. */
object Sessionize {
  def main(args: Array[String]): Unit = {
    val parser = new scopt.OptionParser[Config]("Sessionize") {
      head("Sessionize", "0.1.0")
      opt[String]("input").required().action((x, c) => c.copy(input = x))
        .text("Path to input JSON with fields: userId, eventType, ts (epoch millis)")
    }

    parser.parse(args, Config()) match {
      case Some(cfg) =>
        val spark = SparkSession.builder()
          .appName("Sessionize")
          .master("local[*]")
          .getOrCreate()

        import spark.implicits._

        val df = spark.read.json(cfg.input)
          .withColumn("ts", ($"ts" / 1000).cast("timestamp"))
          .withColumn("date", to_date($"ts"))

        val daily = df.groupBy($"userId", $"date").agg(
          count(lit(1)).as("events"),
          countDistinct($"eventType").as("eventTypes")
        ).orderBy($"userId", $"date")

        daily.show(false)

        spark.stop()

      case None => // bad args
    }
  }

  case class Config(input: String = "")
}
