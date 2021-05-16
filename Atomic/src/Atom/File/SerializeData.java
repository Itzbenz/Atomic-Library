package Atom.File;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//use json please
public class SerializeData {
	
	
	public static void dataOut(Object ish, File file) throws IOException {
		File temp = FileUtility.temp();
		FileOutputStream fos = new FileOutputStream(temp);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(ish);
		oos.close();
		FileUtility.makeFile(file);
		FileUtility.copy(temp, file);
	}
	
	//wtf is this
	@Deprecated
	public static void dataOut(DataArray<Object> ish, File file) throws IOException {
		dataOut((Object) ish, file);
	}
	
	
	public static <T> T dataIn(File file) throws IOException, ClassNotFoundException {
		FileInputStream fin = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fin);
		T iHandler = (T) ois.readObject();
		ois.close();
		return iHandler;
	}
	
	
	public static <T> DataArray<T> dataArrayIn(File file) throws IOException, ClassNotFoundException {
		FileInputStream fin = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fin);
		DataArray<T> iHandler;
		iHandler = (DataArray<T>) ois.readObject();
		ois.close();
		return iHandler;
	}
	
	@Deprecated
	public static class DataArray<E> implements Serializable {
		public List<E> arrayList;
		
		public DataArray(E[] arr) {
			arrayList = new ArrayList<>(Arrays.asList(arr));
		}
		
		public DataArray(List<E> arr) {
			arrayList = arr;
		}
		
		public <T> ArrayList<T> get() {
			ArrayList<T> ar = new ArrayList<>();
			for (E e : arrayList) {
				try {
					ar.add((T) e);
				}catch (Throwable ignored) {
				}
			}
			return ar;
		}
	}

}
