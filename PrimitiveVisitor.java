import sun.jvm.hotspot.oops.*;
import sun.jvm.hotspot.runtime.*;
import sun.jvm.hotspot.utilities.*;
import sun.jvm.hotspot.debugger.*;

public class PrimitiveVisitor implements Runnable,Constants
{
	private String className;
	private String fieldName;
	private String type;
	private boolean verbose;
	
	public PrimitiveVisitor(String className,String fieldName,String type,boolean verbose)
	{
		this.className=className;
		this.fieldName=fieldName;
		this.type=type;
		this.verbose=verbose;
	}
	
	public void run()
	{
		try
		{
			final int[] countHolder=new int[1];
			
			ObjectHeap heap=VM.getVM().getObjectHeap();
			InstanceKlass klass=SystemDictionaryHelper.findInstanceKlass(className);
			final Field field=getField(klass);
						
			heap.iterateObjectsOfKlass(new DefaultHeapVisitor(){
				public boolean doObj(Oop obj/*className instance*/)
				{
					if(verbose)
                        		{
                                		obj.print();
                        		}
					
					countHolder[0]++;
					Object r=getFieldValue(field,obj);
					System.out.println(className+" @ "+obj.getHandle()+" (object size = "+obj.getObjectSize()+")");
					System.out.print(fieldName+":"+r);
					System.out.println();
					return false;
				}
			},klass,false);
			System.out.println("total "+className+" instance count:"+countHolder[0]);
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (AddressException e) 
		{
			System.err.println("Error accessing address 0x"
				+ Long.toHexString(e.getAddress()));
			e.printStackTrace();
		}
	}
	
	public Object getFieldValue(Field field,Oop obj) throws IllegalArgumentException
	{
		Object ret=null;
		if(BYTE.equalsIgnoreCase(this.type))
		{
			ret=obj.getHandle().getJByteAt(field.getOffset());
		}
		else if(CHAR.equalsIgnoreCase(this.type))
		{
			ret=obj.getHandle().getJCharAt(field.getOffset());
		}
		else if(DOUBLE.equalsIgnoreCase(this.type))
		{
			ret=obj.getHandle().getJDoubleAt(field.getOffset());
		}
		else if(FLOAT.equalsIgnoreCase(this.type))
		{
			ret=obj.getHandle().getJFloatAt(field.getOffset());
		}
		else if(INT.equalsIgnoreCase(this.type))
		{
			ret=obj.getHandle().getJIntAt(field.getOffset());
		}
		else if(LONG.equalsIgnoreCase(this.type))
		{
			ret=obj.getHandle().getJLongAt(field.getOffset());
		}
		else if(SHORT.equalsIgnoreCase(this.type))
		{
			ret=obj.getHandle().getJShortAt(field.getOffset());
		}
		else if(BOOLEAN.equalsIgnoreCase(this.type))
		{
			ret=obj.getHandle().getJBooleanAt(field.getOffset());
		}
		else
		{
			throw new IllegalArgumentException("illegal type");
		}
		
		return ret;
	}
	
	public Field getField(InstanceKlass klass) throws IllegalArgumentException
	{
		String signiture="";
		if(BYTE.equalsIgnoreCase(this.type))
		{
			signiture=BYTE_VM;
		}
		else if(CHAR.equalsIgnoreCase(this.type))
		{
			signiture=CHAR_VM;
		}
		else if(DOUBLE.equalsIgnoreCase(this.type))
		{
			signiture=DOUBLE_VM;
		}
		else if(FLOAT.equalsIgnoreCase(this.type))
		{
			signiture=FLOAT_VM;
		}
		else if(INT.equalsIgnoreCase(this.type))
		{
			signiture=INT_VM;
		}
		else if(LONG.equalsIgnoreCase(this.type))
		{
			signiture=LONG_VM;
		}
		else if(SHORT.equalsIgnoreCase(this.type))
		{
			signiture=SHORT_VM;
		}
		else if(BOOLEAN.equalsIgnoreCase(this.type))
		{
			signiture=BOOLEAN_VM;
		}
		else
		{
			throw new IllegalArgumentException("illegal type");
		}
		
		return klass.findField(this.fieldName,signiture);
	}
}
