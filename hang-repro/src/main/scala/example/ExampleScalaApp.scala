package example

import org.apache.log4j.Logger
import org.apache.log4j.LogManager

import com.microsoft.applicationinsights.TelemetryClient;

//
// Min viable repro for intermittent thread deadlock on exit
// Run me with 'sbt run -d' on JRE 8 (twice, if you see it exiting normally).
// It exits without a long lock ~25% of the time.
//

object ExampleScalaApp extends App {
  // Logs to console, per log4j config
  lazy val logger = Logger.getLogger("ExampleScalaApp")
  logger.warn("execution telemetry client")

  // This line causes thread locks on exit - I've seen it take anywhere from
  // 80-700+ seconds to resolve the locks and exit. Commenting out the
  // TelemetryClient immediately resolves the issue. It only happens when log4j
  // is configured AND TelemetryClient() is constructed.

  // I ran into this because I tried to configure the L4J v1.2 App Insights
  // appender and as soon as I enable it in the log4j config under
  // src/main/resources/ (see commented lines), I experienced the same hangs on
  // exit as repro'd here.

  val foo = new TelemetryClient();

  logger.warn("main exiting")
}
