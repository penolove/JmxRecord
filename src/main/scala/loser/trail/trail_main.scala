package loser.trail


import scala.collection.mutable
import scala.collection.mutable.MutableList
import scala.math._


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;



object trail_main {
  var conf= new Configuration ;

  def main(args: Array[String]){
    
    
    conf = HBaseConfiguration.create();
    conf.set("hbase.zookeeper.quorum", "InvPM30");  //cluster
		conf.set("hbase.zookeeper.property.clientPort", "2181"); //port of zookeeper
		
		var	con = ConnectionFactory.createConnection(conf); // class of connection
		
		var tableName = TableName.valueOf("Rec4Speed");
		var table = con.getTable(tableName); 


		
    val invpm27=new MbscConnInfoSpark("service:jmx:rmi:///jndi/rmi://invpm27:9999/jmxrmi","kafka.server:type=BrokerTopicMetrics,name=MessagesInPerSec","Count","16party,7party1")
    val invpm28=new MbscConnInfoSpark("service:jmx:rmi:///jndi/rmi://invpm28:9999/jmxrmi","kafka.server:type=BrokerTopicMetrics,name=MessagesInPerSec","Count","16party,7party1")
    val invpm29=new MbscConnInfoSpark("service:jmx:rmi:///jndi/rmi://invpm29:9999/jmxrmi","kafka.server:type=BrokerTopicMetrics,name=MessagesInPerSec","Count","16party,7party1")
    val invpm30=new MbscConnInfoSpark("service:jmx:rmi:///jndi/rmi://invpm30:9999/jmxrmi","kafka.server:type=BrokerTopicMetrics,name=MessagesInPerSec","Count","16party,7party1")
    val invpm31=new MbscConnInfoSpark("service:jmx:rmi:///jndi/rmi://invpm31:9999/jmxrmi","kafka.server:type=BrokerTopicMetrics,name=MessagesInPerSec","Count","16party,7party1")
    val invpm33=new MbscConnInfoSpark("service:jmx:rmi:///jndi/rmi://invpm33:9999/jmxrmi","kafka.server:type=BrokerTopicMetrics,name=MessagesInPerSec","Count","16party,7party1")
    
    
    var records = new mutable.HashMap[String, Long]()
    records("invpm27")=invpm27.getflow
    records("invpm28")=invpm28.getflow
    records("invpm29")=invpm29.getflow
    records("invpm30")=invpm30.getflow
    records("invpm31")=invpm31.getflow
    records("invpm33")=invpm33.getflow
    
     
    
    while(true){
      val start = System.currentTimeMillis
      var temp= new mutable.ArrayBuffer[Long]
      temp+=(invpm27.getflow-records("invpm27"))
      records("invpm27")=invpm27.getflow
      temp+=(invpm28.getflow-records("invpm28"))
      records("invpm28")=invpm28.getflow
      temp+=(invpm29.getflow-records("invpm29"))
      records("invpm29")=invpm29.getflow
      temp+=(invpm30.getflow-records("invpm30"))
      records("invpm30")=invpm30.getflow
      temp+=(invpm31.getflow-records("invpm31"))
      records("invpm31")=invpm31.getflow
      temp+=(invpm33.getflow-records("invpm33"))
      records("invpm33")=invpm33.getflow
      val p = new Put(Bytes.toBytes("Rowkey"))
      p.addColumn(Bytes.toBytes("all"),Bytes.toBytes("timeend"),Bytes.toBytes(((temp.sum)/2000.0).toString()))
		  table.put(p)
		  println(temp.sum)
      temp.clear
      val sleep = max(0, 2000 - (System.currentTimeMillis - start))
      println(sleep)
      Thread.sleep(sleep)
    }
    
  }
  
}