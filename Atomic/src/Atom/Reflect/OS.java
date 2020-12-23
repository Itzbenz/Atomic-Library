package Atom.Reflect;

public class OS {
	public static boolean isWindows = getProperty("os.name").contains("Windows");
	public static boolean isLinux = getProperty("os.name").contains("Linux");
	public static boolean isMac = getProperty("os.name").contains("Mac");
	public static boolean isIos = false;
	public static boolean isAndroid = false;
	public static boolean isARM = getProperty("os.arch").startsWith("arm") || getProperty("os.arch").startsWith("aarch64");
	public static boolean is64Bit = getProperty("os.arch").contains("64") || getProperty("os.arch").startsWith("armv8");
	
	static {
		if (getProperty("java.runtime.name").contains("Android Runtime") || getProperty("java.vm.vendor").contains("The Android Project") || getProperty("java.vendor").contains("The Android Project")) {
			isAndroid = true;
			isWindows = false;
			isLinux = false;
			isMac = false;
			is64Bit = false;
		}
		
		if (getProperty("moe.platform.name").equals("iOS") || !isAndroid && !isWindows && !isLinux && !isMac) {
			isIos = true;
			isAndroid = false;
			isWindows = false;
			isLinux = false;
			isMac = false;
			is64Bit = false;
		}
		
	}
	
	public static String getProperty(String s) {
		return System.getProperty(s, "null");
	}
	
}
