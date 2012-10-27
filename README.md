SeeVar
======

See the variable of the object in the java heap

Usage:

javac -cp .:$JAVA_HOME/lib/sa-jdi.jar *.java

java -cp .:$JAVA_HOME/lib/sa-jdi.jar FetchObj $classname $attribute $type $verbose $pid

Parameter:

classname : full name of the class

attribute : attribute name of the class

type : type of the attribute

verbose : wheather need output the detail info or not (true or false)

pid : the process id which need to be attached

