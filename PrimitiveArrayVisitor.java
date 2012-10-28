import sun.jvm.hotspot.oops.*;
import sun.jvm.hotspot.runtime.*;
import sun.jvm.hotspot.utilities.*;

public class PrimitiveArrayVisitor implements Runnable,Constants
{
	private String className;
	private String fieldName;
	private String type;
	private boolean verbose;
	
	public PrimitiveArrayVisitor(String className,String fieldName,String type,boolean verbose)
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
		final OopField field=getField(klass);
		
		heap.iterateObjectsOfKlass(new DefaultHeapVisitor(){
			public boolean doObj(Oop obj/*className instance*/)
			{
				countHolder[0]++;
				System.out.println(klass.getName().asString()+" @ "+ obj.getHandle()+" (object size = "+obj.getObjectSize()+")");
				if(verbose)
                        	{
                                	obj.print();
                        	}
				else
				{
					System.out.print(fieldName+":[");
				}
				
				//TypeArray
				final TypeArray arrayInstance=(TypeArray)field.getValue(obj);
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
							System.out.print(v+" ");
								
						}
					}
					
					public void doByte(ByteField field, boolean isVMField)
					{
						byte v=field.getValue(arrayInstance);
						if(verbose)
						{
							System.out.println(field.getID().getName()+":	{"+field.getOffset()+"}	:"+v);
						}
						else
						{
							System.out.print(v+" ");
						}
					}

					public void doBoolean(BooleanField field, boolean isVMField)
					{
						boolean v=field.getValue(arrayInstance);
						if(verbose)
						{
							System.out.println(field.getID().getName()+":	{"+field.getOffset()+"}	:"+v);
						}
						else
						{
							System.out.print(v+" ");
						}
					}
					public void doShort(ShortField field, boolean isVMField)
					{
						short v=field.getValue(arrayInstance);
						if(verbose)
						{
							System.out.println(field.getID().getName()+":	{"+field.getOffset()+"}	:"+v);
						}
						else
						{
							System.out.print(v+" ");
						}
					}

					public void doInt(IntField field, boolean isVMField)
					{
						int v=field.getValue(arrayInstance);
						if(verbose)
						{
							System.out.println(field.getID().getName()+":	{"+field.getOffset()+"}	:"+v);
						}
						else
						{
							System.out.print(v+" ");
						}
					}
					
					public void doLong(LongField field, boolean isVMField)
					{
						long v=field.getValue(arrayInstance);
						if(verbose)
						{
							System.out.println(field.getID().getName()+":	{"+field.getOffset()+"}	:"+v);
						}
						else
						{
							System.out.print(v+" ");
						}
					}
					
					public void doFloat(FloatField field, boolean isVMField)
					{
						float v=field.getValue(arrayInstance);
						if(verbose)
						{
							System.out.println(field.getID().getName()+":	{"+field.getOffset()+"}	:"+v);
						}
						else
						{
							System.out.print(v+" ");
						}
					}
					
					public void doDouble(DoubleField field, boolean isVMField)
					{
						double v=field.getValue(arrayInstance);
						if(verbose)
						{
							System.out.println(field.getID().getName()+":	{"+field.getOffset()+"}	:"+v);
						}
						else
						{
							System.out.print(v+" ");
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
				
				if(!verbose)
                                {
                                        System.out.print("]");
                                }
				System.out.println();
				return false;
			}
		},klass,false);
		System.out.println("total "+className+" instance count:"+countHolder[0]);
	}
	
	public OopField getField(InstanceKlass klass) throws IllegalArgumentException
	{
		String signiture="";
		if(BYTE_ARRAY.equalsIgnoreCase(this.type))
		{
			signiture=BYTE_ARRAY_VM;
		}
		else if(CHAR_ARRAY.equalsIgnoreCase(this.type))
		{
			signiture=CHAR_ARRAY_VM;
		}
		else if(DOUBLE_ARRAY.equalsIgnoreCase(this.type))
		{
			signiture=DOUBLE_ARRAY_VM;
		}
		else if(FLOAT_ARRAY.equalsIgnoreCase(this.type))
		{
			signiture=FLOAT_ARRAY_VM;
		}
		else if(INT_ARRAY.equalsIgnoreCase(this.type))
		{
			signiture=INT_ARRAY_VM;
		}
		else if(LONG_ARRAY.equalsIgnoreCase(this.type))
		{
			signiture=LONG_ARRAY_VM;
		}
		else if(SHORT_ARRAY.equalsIgnoreCase(this.type))
		{
			signiture=SHORT_ARRAY_VM;
		}
		else if(BOOLEAN_ARRAY.equalsIgnoreCase(this.type))
		{
			signiture=BOOLEAN_ARRAY_VM;
		}
		else
		{
			throw new IllegalArgumentException("illegal type");
		}
		
		return (OopField)klass.findField(this.fieldName,signiture);
	}
}
