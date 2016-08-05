package loser.trail


import java.util.Date
import java.text.SimpleDateFormat
import javax.management._
import javax.management.remote._
import joptsimple.OptionParser
import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.math._


//this class is too much codes and fucnitons that need to check


class MbscConn(var urx:String, var whiteList:String="",var objname:String=""){
      
    val url = new JMXServiceURL(urx)
    val attributesWhitelistExists = whiteList!=""
    val attributesWhitelist = if(attributesWhitelistExists) Some(whiteList.split(",")) else None
    val jmxc = JMXConnectorFactory.connect(url, null)
    val mbsc = jmxc.getMBeanServerConnection()
    
    val queries: Iterable[ObjectName] =
      if(objname!="")
         List(new ObjectName(objname))
       else
         List(null)
    
    val names =(queries.map((name: ObjectName) => mbsc.queryNames(name, null))).toSet.flatten
    
    
    val allAttributes: Iterable[(ObjectName, Array[String])] =
      names.map((name: ObjectName) => (name, mbsc.getMBeanInfo(name).getAttributes().map(_.getName)))

      
    val numExpectedAttributes: Map[ObjectName, Int] =
       attributesWhitelistExists match {
        case true => queries.map((_, attributesWhitelist.get.size)).toMap
        case false => names.map((name: ObjectName) =>
          (name, mbsc.getMBeanInfo(name).getAttributes().map(_.getName).size)).toMap
      }
    
    val keys = List("time") ++ queryAttributes(mbsc, names, attributesWhitelist).keys.toArray.sorted
    if(keys.size == numExpectedAttributes.map(_._2).foldLeft(0)(_ + _) + 1)
      println(keys.map("\"" + _ + "\"").mkString(","))
      
    def callone(attr:String):Long={
        val attributes = queryAttributes(mbsc, names, attributesWhitelist)
       // attributes("time") = System.currentTimeMillis().toString
       //println(keys.map(attributes(_)).mkString(","))
       //println(attributes(attr))
       return attributes(attr).asInstanceOf[Long]
    }
        
   def queryAttributes(mbsc: MBeanServerConnection, names: Iterable[ObjectName], attributesWhitelist: Option[Array[String]]) = {
      var attributes = new mutable.HashMap[String, Any]()
      for(name <- names) {
        val mbean = mbsc.getMBeanInfo(name)
        for(attrObj <- mbsc.getAttributes(name, mbean.getAttributes.map(_.getName))) {
          val attr = attrObj.asInstanceOf[Attribute]
          attributesWhitelist match {
            case Some(allowedAttributes) =>
              if(allowedAttributes.contains(attr.getName))
                attributes(attr.getName) = attr.getValue
            case None => attributes(name + ":" + attr.getName) = attr.getValue
          }
        }
      }
      attributes
    }
}