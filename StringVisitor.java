import sun.jvm.hotspot.oops.*;
import sun.jvm.hotspot.runtime.*;
import sun.jvm.hotspot.utilities.*;

public class StringVisitor implements Runnable,Constants
{
	private String className;
	private String fieldName;
	private String type;
	private boolean verbose;
	
	public StringVisitor(String className,String fieldName,String type,boolean verbose)
	{
		this.className=className;
		this.fieldName=fieldName;
		this.type=type;
		this.verbose=verbose;
	}

	public void run()
	{
		final int[] countHolder=new int[1];
		
		ObjectHeap heap=VM.getVM().getObjectHeap();
		final InstanceKlass klass=SystemDictionaryHelper.findInstanceKlass(className);
		String signiture="Ljava/lang/String;";
		final OopField field=(OopField)klass.findField(this.fieldName,signiture);
		
		heap.iterateObjectsOfKlass(new DefaultHeapVisitor(){
			public boolean doObj(Oop obj/*className instance*/)
			{
				countHolder[0]++;
				System.out.println(klass.getName().asString()+" @ "+ obj.getHandle()+" (object size = "+obj.getObjectSize()+")");
				if(!verbose)
				{
					System.out.print(fieldName+":");
				}
				
				//string instance
				final Oop oop=field.getValue(obj);
				InstanceKlass k=(InstanceKlass)oop.getKlass();
				OopField valueField = (OopField)k.findField("value", "[C");
				//char[] instance
				final TypeArray arrayInstance=(TypeArray)valueField.getValue(oop);
				
				arrayInstance.iterate(new DefaultOopVisitor(){
					public void doChar(CharField field, boolean isVMField)
					{
						char v=field.getValue(arrayInstance);
						if(verbose)
						{
							System.out.println(field.getID().getName()+":	{"+field.getOffset()+"}	:"+v);
						}
						else
						{
							System.out.print(v);
						}
					}

					public void doCInt(CIntField field, boolean isVMField)
					{
						if(verbose)
						{
							long v=field.getValue(arrayInstance);
							System.out.println(field.getID().getName()+":	{"+field.getOffset()+"}	:"+v);
						}
					}
					
					public void doOop(OopField field, boolean isVMField)
					{
						if(verbose)
						{
							TypeArrayKlass pp=(TypeArrayKlass)field.getValue(arrayInstance);
							System.out.println(field.getID().getName()+":	{"+field.getOffset()+"}	:"
								+pp.getClass().getSimpleName()+" for "+pp.signature()+" @ "
								+pp.getHandle());
						}
					}
				},true);
				System.out.println();
				return false;
			}
		},klass,false);
		System.out.println("total "+className+" instance count:"+countHolder[0]);
	}
}