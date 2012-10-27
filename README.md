SeeVar
======

See the attribute of the object in java heap. <br />
It depends on SA(Serviceability Agent) which provides a set of APIs for the Java programming language which model the state of Sun Microsystems Java HotSpot Virtual Machine. So it is convenient for Java programmer to retrieve the information of the vm internal with it. Many java diagnose tools build upon it, such as jstack, jmap and so on.

# Usage

javac -cp .:$JAVA_HOME/lib/sa-jdi.jar *.java <br />
java -cp .:$JAVA_HOME/lib/sa-jdi.jar FetchObj $classname $attribute $type $verbose $pid <br />


# Parameter

classname : full name of the class <br />
attribute : attribute name of the class <br />
type : type of the attribute <br />
verbose : wheather need output the detail info or not (true or false) <br />
pid : the process id which need to be attached <br />

# Output(without verbose)
java -cp .:$JAVA_HOME/lib/sa-jdi.jar FetchObj Run2 foo int true 26899 <br />
Run2 @ 0xe96ec530 (object size = 24) <br />
foo:20 <br />
Run2 @ 0xe96ec548 (object size = 24) <br />
foo:30 <br />
Run2 @ 0xe96ec560 (object size = 24) <br />
foo:40 <br />
Run2 @ 0xe96ec578 (object size = 24) <br />
foo:50 <br />
Run2 @ 0xe96ec590 (object size = 24) <br />
foo:60 <br />
total Run2 instance count:5 <br />
done.cost:1690 <br />

# Reference

http://static.usenix.org/event/jvm01/full_papers/russell/russell_html/ <br />
http://hllvm.group.iteye.com/group/topic/34278 <br />

