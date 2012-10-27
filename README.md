SeeVar
======

See the variable of the object in the java heap.
It depends on SA(Serviceability Agent) which provides a set of APIs for the Java programming language which model the state of Sun Microsystems Java HotSpot Virtual Machine. So it is convenient for Java programmer to retrieve the information of the vm internal with it. Many java diagnose tools build upon it, such as jstack, jmap and so on.

Usage
-----

javac -cp .:$JAVA_HOME/lib/sa-jdi.jar *.java
java -cp .:$JAVA_HOME/lib/sa-jdi.jar FetchObj $classname $attribute $type $verbose $pid

Parameter
---------

classname : full name of the class
attribute : attribute name of the class
type : type of the attribute
verbose : wheather need output the detail info or not (true or false)
pid : the process id which need to be attached

Reference
---------

http://static.usenix.org/event/jvm01/full_papers/russell/russell_html/
http://hllvm.group.iteye.com/group/topic/34278

