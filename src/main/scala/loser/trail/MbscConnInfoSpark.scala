package loser.trail


import java.util.Date
import java.text.SimpleDateFormat
import javax.management._
import javax.management.remote._
import joptsimple.OptionParser
import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.math._


class MbscConnInfoSpark(var urx:String,var objname:String="",var Attr:String="",var Topics:String="") {
        val url = new JMXServiceURL(urx)
        val jmxc = JMXConnectorFactory.connect(url, null)
        val mbsc = jmxc.getMBeanServerConnection()
        var topics=Topics.split(",")
        val SparkObject=new ObjectName(objname+",topic="+topics(0))
        val InfoObject =new ObjectName(objname+",topic="+topics(1))
        
        
        def getflow:Long={
          var tempvariable :Long = 0  
          //add from spark
          try{
            tempvariable+=mbsc.getAttribute(SparkObject,Attr).asInstanceOf[Long]
          }catch{
            case _ => Unit
          }
          //add from Infosphere
          try{
            tempvariable+=mbsc.getAttribute(SparkObject,Attr).asInstanceOf[Long]
           }catch{
            case _ => Unit
          }
          
           tempvariable
        }
  
}