package org.flyjaky.HAHAProxy.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.log4j.Logger;


public class ByteUtils {
	
	
	private static Logger logger = Logger.getLogger(ByteUtils.class);
	
	
	/**
	 * 将int转换底字节在前，高字节在后的byte数组
	 */
	public static byte[] toLH(int n) {
		byte[] b = new byte[4];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		b[2] = (byte) (n >> 16 & 0xff);
		b[3] = (byte) (n >> 24 & 0xff);
		return b;
	}

	public static byte[] intToByteArray(int s) {
		byte[] targets = new byte[2];
		for (int i = 0; i < 4; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >>> offset) & 0xff);
		}
		return targets;
	}


	/**
	 * 从地位到高位
	 * @param s
	 * @param y
     * @return
     */
	public static byte[] int2byteArray(int s, int y) {
		byte[] targets = new byte[y];
		for (int i = 0; i < y; i++) {
			if (i == 0) {
				targets[0] = (byte) (s & 0xff);
			} else {
				targets[i] = (byte) (s >> (i * 8) & 0xff);
			}
		}
		return targets;
	}

	/**
	 * 从地位到高位
	 * @param value
	 * @return
     */
	public static byte[] intToBytes(int value)
	{
		byte[] src = new byte[4];
		src[0] = (byte) ((value>>24) & 0xFF);
		src[1] = (byte) ((value>>16)& 0xFF);
		src[2] = (byte) ((value>>8)&0xFF);
		src[3] = (byte) (value & 0xFF);
		return src;
	}



	public static byte[] shortToByteArray(short s) {
		byte[] targets = new byte[2];
		for (int i = 0; i < 2; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >>> offset) & 0xff);
		}
		return targets;
	}
	
	
	/**
	 * 高位优先
	 * **/
	public static byte[] shortToByteArrayToBigEndian(short s) {
		byte[] targets = new byte[2];
		for (int i = 0; i < 2; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >>> offset) & 0xff);
		}
		byte temp=targets[0];
		targets[0]=targets[1];
		targets[1]=temp;
		return targets;
	}
	
	
//	public static byte[] shortToBigEndianByteArray(short sh){
//		byte[] shortArray=new byte[2];
//		shortAr
		
//		ByteBuffer lengthbuffer = ByteBuffer.wrap(sh);
//		data = lengthbuffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
//	}


	public static byte[] longToByte(long number) {
		long temp = number;
		byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Long(temp & 0xff).byteValue();// 将最低位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}


	public static String byte2HexStr(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			// if (n<b.length-1) hs=hs+":";
		}
		return hs.toUpperCase();
	}

	/**
	 * ip转换
	 */
	public static String byte2HexStr(byte[] b, String splitStrArg) {
		String hs = "";
		String stmp = "";
		String splitStr = "";
		if (null != splitStrArg) {
			splitStr = splitStrArg;
		}
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp + splitStr;
			} else if (n < b.length - 1) {
				hs = hs + stmp + splitStr;
			} else {
				hs = hs + stmp;
			}
			// if (n<b.length-1) hs=hs+":";
		}
		return hs.toUpperCase();
	}

	/**
	 * 高位排序字节转数字
	 **/
	public static int byteHigthEndian2int(byte[] arg) {
		int data = 0;
		if(null!=arg && arg.length>0){
			if (arg.length == 1) {
				return (int) arg[0];
			}
			ByteBuffer lengthbuffer = ByteBuffer.wrap(arg);
			data = lengthbuffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
		}
		return data;
	}
	
	/**
	 * 高位排序字节转数字
	 **/
	public static float byteHigthEndian2Float(byte[] arg) {
		float data = 0;
		if(null!=arg && arg.length>0){
			if (arg.length == 1) {
				return (float) arg[0];
			}
			ByteBuffer lengthbuffer = ByteBuffer.wrap(arg);
			data = lengthbuffer.order(ByteOrder.LITTLE_ENDIAN).getFloat();
		}
		return data;
	}
	/**
	 * 高位排序字节转数字
	 **/
	public static long byteHigthEndian2Long(byte[] arg) {
		long data = 0;
		if(null!=arg && arg.length>0){
			if (arg.length == 1) {
				return (long) arg[0];
			}
			ByteBuffer lengthbuffer = ByteBuffer.wrap(arg);
			data = lengthbuffer.order(ByteOrder.LITTLE_ENDIAN).getLong();
		}
		return data;
	}


	public static int byte2int(byte[] res) {
		int data = 0;
		if(null!=res && res.length>0){
			int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
					| ((res[2] << 24) >>> 8) | (res[3] << 24);
			data = targets;
		}
		return data;
	}


	/**
	 * 将一个单字节的byte转换成32位的int
	 * @param b
	 * @return
     */
	public static int unsignedByteToInt(byte b) {
		return (int) b & 0xFF;
	}

	public static int HexToInt(String strHex){
		int nResult = 0;
		if ( !IsHex(strHex) )
			return nResult;
		String str = strHex.toUpperCase();
		if ( str.length() > 2 ){
			if ( str.charAt(0) == '0' && str.charAt(1) == 'X' ){
				str = str.substring(2);
			}
		}
		int nLen = str.length();
		for ( int i=0; i<nLen; ++i ){
			char ch = str.charAt(nLen-i-1);
			try {
				nResult += (GetHex(ch)*GetPower(16, i));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.toString());
			}
		}
		return nResult;
	}

	public static boolean IsHex(String strHex){
		int i = 0;
		if ( strHex.length() > 2 ){
			if ( strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x') ){
				i = 2;
			}
		}
		for ( ; i<strHex.length(); ++i ){
			char ch = strHex.charAt(i);
			if ( (ch>='0' && ch<='9') || (ch>='A' && ch<='F') || (ch>='a' && ch<='f') )
				continue;
			return false;
		}
		return true;
	}

	//计算16进制对应的数值
	public static int GetHex(char ch) throws Exception{
		if ( ch>='0' && ch<='9' )
			return (int)(ch-'0');
		if ( ch>='a' && ch<='f' )
			return (int)(ch-'a'+10);
		if ( ch>='A' && ch<='F' )
			return (int)(ch-'A'+10);
		throw new Exception("error param");
	}

	public static int GetPower(int nValue, int nCount) throws Exception{
		if ( nCount <0 )
			throw new Exception("nCount can't small than 1!");
		if ( nCount == 0 )
			return 1;
		int nSum = 1;
		for ( int i=0; i<nCount; ++i ){
			nSum = nSum*nValue;
		}
		return nSum;
	}
	public static void main(String[] args){
		short data=32767;
		byte[] shortArray=ByteUtils.shortToByteArrayToBigEndian(data);
		logger.info(ByteUtils.getShort(shortArray, 0));
//		logger.info(HexToInt("0x29"));
	}
	
	 public static char getChar(byte[] bytes) {
	        return (char) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
	    }
	 
	 public static short getShort(byte[] b, int index) {  
	        return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));  
	    }  
	 
	 public static byte[] charToBytes(char c) {  
	        byte[] b = new byte[8];  
	        b[0] = (byte) (c >>> 8);  
	        b[1] = (byte) c;  
	        return b;  
	    } 
	 /**
	  * byte转double
	  * @param readBuffer
	  * @return
	  */
	 public static double bytesToDouble(byte[] readBuffer)  {  
	      return Double.longBitsToDouble((((long)readBuffer[0] << 56) +  
	                 ((long)(readBuffer[1] & 255) << 48) +  
	                 ((long)(readBuffer[2] & 255) << 40) +  
	                 ((long)(readBuffer[3] & 255) << 32) +  
	                 ((long)(readBuffer[4] & 255) << 24) +  
	                 ((readBuffer[5] & 255) << 16) +  
	                 ((readBuffer[6] & 255) <<  8) +  
	                 ((readBuffer[7] & 255) <<  0))  
	           );  
	 } 
	 /**
	  * double转byte
	  * @param d
	  * @return
	  */
	 public static byte[] doubleToBytes(double d) {  
	     byte writeBuffer[]= new byte[8];  
	      long v = Double.doubleToLongBits(d);  
	         writeBuffer[0] = (byte)(v >>> 56);  
	         writeBuffer[1] = (byte)(v >>> 48);  
	         writeBuffer[2] = (byte)(v >>> 40);  
	         writeBuffer[3] = (byte)(v >>> 32);  
	         writeBuffer[4] = (byte)(v >>> 24);  
	         writeBuffer[5] = (byte)(v >>> 16);  
	         writeBuffer[6] = (byte)(v >>>  8);  
	         writeBuffer[7] = (byte)(v >>>  0);  
	         return writeBuffer;  
	 }  
	 /**
	  * byte数组转long型 
	  * @param b
	  * @return
	  */
	 public static long byteToLong(byte[] b) { 
	        long s = 0; 
	        long s0 = b[0] & 0xff;// 最低位 
	        long s1 = b[1] & 0xff; 
	        long s2 = b[2] & 0xff; 
	        long s3 = b[3] & 0xff; 
	        long s4 = b[4] & 0xff;// 最低位 
	        long s5 = b[5] & 0xff; 
	        long s6 = b[6] & 0xff; 
	        long s7 = b[7] & 0xff; 
	 
	        // s0不变 
	        s1 <<= 8; 
	        s2 <<= 16; 
	        s3 <<= 24; 
	        s4 <<= 8 * 4; 
	        s5 <<= 8 * 5; 
	        s6 <<= 8 * 6; 
	        s7 <<= 8 * 7; 
	        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7; 
	        return s; 
	    } 
}