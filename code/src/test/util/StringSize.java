package util;

import java.io.UnsupportedEncodingException;

public class StringSize {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
	      String s = "Hello";
	      int byteCountUTF16 = s.getBytes("UTF-16").length;
	      int byteCountUTF8  = s.getBytes("UTF-8").length;
	      
	      System.out.println("byteCountUTF16: "+byteCountUTF16);
	      System.out.println("byteCountUTF8: "+byteCountUTF8);
	      
	      String str = "\uD834\uDD2A"; //U+1D12A
	      int length1 = str.length(); //2
	      int length2 = str.codePointCount(0, str.length()); 
	      
	      System.out.println("length1: "+length1);
	      System.out.println("length2: "+length2);
	      
	      int charLen = s.length();
	      int characterLen = s.codePointCount(0, charLen);
	      System.out.println("Length of file : "+charLen);
	      System.out.println("Size of file : "+characterLen);

	}

}
