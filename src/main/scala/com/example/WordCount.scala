package com.example

import org.apache.spark.sql.SparkSession

object WordCount {
  def main(args: Array[String]): Unit = {
    val parser = new scopt.OptionParser[Config]("WordCount") {
      head("WordCount", "0.1.0")
      opt[String]("input").required().action((x, c) => c.copy(input = x))
        .text("Path to input text file")
      opt[Int]("top").optional().action((x, c) => c.copy(top = x))
        .withFallback(() => 10)
        .text("Top N words to show (default 10)")
    }

    parser.parse(args, Config()) match {
      case Some(cfg) =>
        val spark = SparkSession.builder()
          .appName("WordCount")
          .master("local[*]")
          .getOrCreate()

        import spark.implicits._

        val stopwords = Set("the","a","an","and","or","to","of","in","is","it","for","on","with","as","at","by")
        val lines = spark.read.textFile(cfg.input)

        val topWords = lines
          .flatMap(_.toLowerCase.replaceAll("""[^a-z0-9\s]""", " ").split("""\s+"""))
          .filter(w => w.nonEmpty && !stopwords.contains(w))
          .groupByKey(identity)
          .count()
          .orderBy($"count(1)".desc, $"value".asc)
          .limit(cfg.top)

        topWords.show(false)

        spark.stop()

      case None =>
        // arguments are bad; error message already printed
    }
  }

  case class Config(input: String = "", top: Int = 10)
}
