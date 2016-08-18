package loser.trail


import joptsimple.OptionParser;
import joptsimple.OptionSet;
import scala.collection.JavaConverters._



/**
 * @author ${user.name}
 */
object App {
  
  def getParser():OptionParser={
     var parser = new OptionParser();
     parser.acceptsAll(List("123").asJava, "insert thread.sleep(time2sleep) in function.")
           .withRequiredArg()
           .ofType(classOf[Int])
           .defaultsTo(0)
     parser.acceptsAll(List("help").asJava, "Print usage information.");
     parser
  }

  def validatePatser(option:OptionSet ):Unit={
    val exitStatus = 0;
    if(option.has("help")) {
      getParser().printHelpOn(System.out);
      System.exit(1)
    }
  }
  
  def main(args: Array[String]) {
    //val qq=list.toArray(args.toList.asJava)
    val parser = getParser();
    val options = parser.parse(args : _*)
    
    if(options.has("help")) {
      parser.printHelpOn(System.out)
      System.exit(0)
    }
    		//validatePatser(options);
    //validatePatser
    
  }

}
