import sun.jvm.hotspot.tools.*;
import java.util.*;

public class FetchObj extends Tool implements Constants
{
	private String className;
	private String fieldName;
	private String type;
	private boolean verbose;
	private Runnable visitor;
	
	public FetchObj(String className,String fieldName,String type,boolean verbose)
	{
		this.className=className;
		this.fieldName=fieldName;
		this.type=type;
		this.verbose=verbose;
		if(STRING.equals(type))
		{
			visitor=new StringVisitor(className,fieldName,type,verbose);
		}
		else if(type.contains("[]"))
		{
			visitor=new PrimitiveArrayVisitor(className,fieldName,type,verbose);
		}
		else
		{
			visitor=new PrimitiveVisitor(className,fieldName,type,verbose);
		}
	}
	
	public void run()
	{
		visitor.run();
	}
		
	public static void main(String[] args)
	{
		long start=System.currentTimeMillis();
		String className=args[0];
		String fieldName=args[1];
		String type=args[2];
		String verbose=args[3];
		//pid args[4]
		
		List<String> list=new ArrayList<String>();
		for(int i=4;i<args.length;i++)
		{
			list.add(args[i]);
		}
		
		FetchObj fo=new FetchObj(className,fieldName,type,"true".equals(verbose)?true:false);
		fo.start(list.toArray(new String[]{}));
		System.out.println("done.cost:"+(System.currentTimeMillis()-start));
		fo.stop();
	}
}