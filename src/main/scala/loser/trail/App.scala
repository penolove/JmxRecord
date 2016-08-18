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
    if(option.has("help")) {
      getParser().printHelpOn(System.out);
      System.exit(0)
    }
  }
  
  def main(args: Array[String]) {
    //val qq=list.toArray(args.toList.asJava)
    val parser = getParser();
    val options = parser.parse(args : _*)
    validatePatser(options)

    var x11= options.valueOf("123").asInstanceOf[Int];
    println(x11)
    		//validatePatser(options);
    //validatePatser
    
  }

}
