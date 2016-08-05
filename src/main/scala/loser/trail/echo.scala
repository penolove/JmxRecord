package loser.trail

import java.util.Date
import java.text.SimpleDateFormat
import javax.management._
import javax.management.remote._
import joptsimple.OptionParser
import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.math._



object echo {
  def main(args: Array[String]){
        val url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://invpm30:9999/jmxrmi")
        val jmxc = JMXConnectorFactory.connect(url, null)
        val mbsc = jmxc.getMBeanServerConnection()
        println(mbsc.getAttribute(new ObjectName("kafka.server:type=BrokerTopicMetrics,name=MessagesInPerSec,topic=16party"), "Count").asInstanceOf[Long]);

  }
}