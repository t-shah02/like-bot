import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Serializer 
{

	//note that if you replace ArrayList<MyClass> with your own type, you will need to have this type implement java.io.Serializable
	// e.g 	
	//		public class MyClass implements java.io.Serializable {...}
	// and
	//		public void serialize(ArrayList<MyClass> list, String fileName) {...}
	//
	public void serialize(ArrayList<String> list, String fileName) 
	{
		try {
			ObjectOutputStream ouputStream = new ObjectOutputStream(new FileOutputStream(fileName));
			ouputStream.writeObject(list);
			ouputStream.close();
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			//TO DO - handle exception
		}

	}
	
	public void serialize(Object o, String fileName){
		try {
			ObjectOutputStream ouputStream = new ObjectOutputStream(new FileOutputStream(fileName));
			ouputStream.writeObject(o);
			ouputStream.close();
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			//TO DO - handle exception
		}		
	}
		
	public ArrayList<String> deserialize(String fileName) 	
	{	
		ArrayList<String> list = null;
		
		try{
			 FileInputStream inputStream = new FileInputStream(fileName);
			 ObjectInputStream reader = new ObjectInputStream(inputStream);

			 list = (ArrayList<String>)reader.readObject();
			 
			 }
		catch (IOException e)
		{
			//TO DO - handle exception
		}
		catch (ClassNotFoundException e)
		{
			//TO DO - handle exception
		}
		
		return list;
	}

	public Object deserializeToObject(String fileName) 	
	{	
		Object o = null;
		
		try{
			 FileInputStream inputStream = new FileInputStream(fileName);
			 ObjectInputStream reader = new ObjectInputStream(inputStream);

			 o = reader.readObject();
			 
			 }
		catch (IOException e)
		{
			//TO DO - handle exception
		}
		catch (ClassNotFoundException e)
		{
			//TO DO - handle exception
		}
		
		return o;
	}
	
	

}
