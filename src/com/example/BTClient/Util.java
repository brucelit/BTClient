package com.example.BTClient;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

public class Util {
	// int转byte数组
		public static byte[] intToBytes(int value) {
			byte[] src = new byte[2];
			for (int i = 0; i < 2; i++) {
				src[1-i] = (byte) ((value >>8*i) & 0xFF);
			}
			return src;
		} 

		// int转byte
		public static byte intToByte(int value) {
			byte src = (byte) (value & 0xFF);
			return src;
		}
		
		public byte[] packageToSend(int send_data,int send_flat) {
			byte[] c = new byte[7];
			byte[] d = intToBytes(send_data);
			switch (send_flat) {
			//驱动轮电机
			case 1:
				c[2] = (byte) (0x08);
				c[4] =  d[0];
				c[5] =  d[1];
				
				Log.e("电机一目标值","true");
				break;
			case 2:
				c[2] = (byte) (0x09);
				c[4] =  d[0];
				c[5] =  d[1];
				
				Log.e("电机一占空比","true");
				break;	
			case 3:
				c[2] = (byte) (0x10);
				c[4] =  d[0];
				c[5] =  d[1];
				
				Log.e("电机一P","true");
				break;	
			case 4:
				c[2] = (byte) (0x11);
				c[4] =  d[0];
				c[5] =  d[1];
				
				Log.e("电机一I","true");
				break;	
			case 5:
				c[2] = (byte) (0x12);
				c[4] =  d[0];
				c[5] =  d[1];
				
				Log.e("电机一D","true");
				break;	
			case 6:
				c[2] = (byte) (0x13);
				c[4] =  (byte) (0x00);
				c[5] =  (byte) (0x00);
			
				Log.e("电机一目标值置零","true");
				break;	
			case 7:
				c[2] = (byte) (0x14);
				c[4] =  d[0];
				c[5] =  d[1];
				Log.e("横滚电机占空比","true");
				break;
			case 8:
				c[2] = (byte) (0x15);
				c[4] =  d[0];
				c[5] =  d[1];
				Log.e("航向角电机占空比","true");
				break;	
			case 9:
				c[2] = (byte) (0x02);
				c[4] =  d[0];
				c[5] =  d[1];
				Log.e("俯仰陀螺仪角度","true");
				break;	
			case 10:
				c[2] = (byte) (0x03);
				c[4] =  d[0];
				c[5] =  d[1];
				
				Log.e("俯仰陀螺仪角速度","true");
				break;
			case 11:
				c[2] = (byte) (0x04);
				c[4] =  d[0];
				c[5] =  d[1];
				Log.e("横滚陀螺仪角度","true");
				break;	
			case 12:
				c[2] = (byte) (0x05);
				c[4] =  d[0];
				c[5] =  d[1];
				Log.e("横滚陀螺仪角速度","true");
				break;	
			case 13:
				c[2] = (byte) (0x07);
				c[4] =  d[0];
				c[5] =  d[1];
				Log.e("航向陀螺仪角度","true");
				break;	
			case 14:
				c[2] = (byte) (0x07);
				c[4] =  d[0];
				c[5] =  d[1];
				Log.e("航向陀螺仪角速度","true");
				break;	
			case 15:
				c[2] = (byte) (0x020);
				c[4] =  d[0];
				c[5] =  d[1];
				Log.e("PC端未开启控制","true");
				break;	
			case 16:
				c[2] = (byte) (0x021);
				c[4] =  d[0];
				c[5] =  d[1];
				Log.e("PC端已开启控制","true");
				break;	
			default:
				break;
			}
			c[0] = (byte) (0x5A);
			c[1] = (byte) (0x5A);
			c[3] = (byte) (0x02);
			int [] a=new int[10];
			a[0]=ByteToInt(c[0]);
			a[1]=ByteToInt(c[1]);
			a[2]=ByteToInt(c[2]);
			a[3]=ByteToInt(c[3]);
			a[4]=ByteToInt(c[4]);
			a[5]=ByteToInt(c[5]);
			a[6]=a[0]+a[1]+a[2]+a[3]+a[4]+a[5];
			c[6] =  intToByte(a[6]);
			return c;
	}
		

		// 接收时将帧解码
		public static int[] DecodeToInt(byte[] data) {
			int i = 0;
			//若读到帧头则开始处理，否则
			while (!(data[i] == (byte) 0xAA && data[i + 1] == (byte) 0xBB)) {
				i++;
			}
			int[] dataInt = new int[2];
			if ((data[i + 2] ^ data[i + 3] ^ data[i + 4] ^ data[i + 5] ^ data[i + 6]) == data[i + 7]) {
				if (data[i + 2] == 0x02) {
					dataInt[0] = Util.ByteToInt(data[i + 3]);
					dataInt[1] = 100 * Util.ByteToInt(data[i + 4]) + 10
							* Util.ByteToInt(data[i + 5])
							+ Util.ByteToInt(data[i + 6]);
				}
			}
			return dataInt;
		}

		// 将byte转换为int型
		public static int ByteToInt(byte src) {
			int value = src & 0xFF;
			return value;
		}
		
		 public static int byte2Int(byte[] b) {
		        int intValue = 0;
		        for (int i = 0; i < b.length; i++) {
		            intValue += (b[i] & 0xFF) << (8 * (3 - i));
		        }
		        return intValue;
		    }

		
		 public static String bytesToHexString(byte[] src) {
				StringBuilder stringBuilder = new StringBuilder("");
				if (src == null || src.length <= 0) {
					return null;
				}
				for (int i = 0; i < 7; i++) {
					int v = src[i] & 0xFF;
					String hv = Integer.toHexString(v);
					if (hv.length() < 2) {
						stringBuilder.append(0);
						System.out.println(i);
					}
					stringBuilder.append(hv);
				}
				return stringBuilder.toString();
			}  
		 
		 
		 public static int bytes2int(byte[] b) {
			//byte[] b=new byte[]{1,2,3,4};
			int mask = 0xff;
			int temp = 0;
			int res = 0;
			for (int i = 0; i < 2; i++) {
			res <<= 8;
			temp = b[i] & mask;
			res |= temp;
			}
			return res;
		 }

		 
		 public static String bytesToHexString2(byte[] c) {
			    int [] a=new int[12];
				a[0]=ByteToInt(c[0]);
				a[1]=ByteToInt(c[1]);
				a[2]=ByteToInt(c[2]);
				a[3]=ByteToInt(c[3]);
				a[4]=ByteToInt(c[4]);
				a[5]=ByteToInt(c[5]);
				a[6]=ByteToInt(c[6]);
				a[7]=ByteToInt(c[7]);
				a[8]=ByteToInt(c[8]);
				a[9]=ByteToInt(c[9]);
			    String hv;
				
			    if (c == null) {
					return null;
				}
				//帧完好
				else if(c[0]==(byte)(0x5A)&&c[1]==(byte)(0x5A))//&&(c[10] == intToByte(a[0]+a[1]+a[2]+a[3]+a[4]+a[5]+a[6]+a[7]+a[8]+a[9])))
				{
				byte []b=new byte[7];
				b[0]=c[4];
				b[1]=c[5];
				b[2]=c[6];
				b[3]=c[7];
				b[4]=c[8];
				b[5]=c[9];
				b[6]=c[2];
				hv = bytesToHexString(b);
				Log.e(hv,"true");
				return hv;
				}
			
				//丢了一个5A
				else if(c[2]==(byte) (0x06))
				{
					byte []b=new byte[7];
					b[0]=c[4];
					b[1]=c[5];
					b[2]=c[6];
					b[3]=c[7];
					b[4]=c[8];
					b[5]=c[9];
					b[6]=c[2];
				hv = bytesToHexString(b);
				Log.e(hv,"true");
				return hv;
				}
			    
				else if(c[1]==(byte) (0x06))
				{
					byte []b=new byte[7];
					b[0]=c[4];
					b[1]=c[5];
					b[2]=c[6];
					b[3]=c[7];
					b[4]=c[8];
					b[5]=c[9];
					b[6]=c[2];
				hv = bytesToHexString(b);
				Log.e(hv,"true");
				return hv;
				}
				//5A5A均被丢
				else if(c[1]==(byte) (0x06))//&&(c[8] == intToByte(a[0]+a[1]+a[2]+a[3]+a[4]+a[5]+a[6]+a[7])))
				{
					byte []b=new byte[7];
					b[0]=c[4];
					b[1]=c[5];
					b[2]=c[6];
					b[3]=c[7];
					b[4]=c[8];
					b[5]=c[9];
					b[6]=c[2];
					hv = bytesToHexString(b);
					Log.e(hv,"true");
				    return hv;
		 }
				else{
					byte []b=new byte[7];
				b[0]=c[4];
				b[1]=c[5];
				b[2]=c[6];
				b[3]=c[7];
				b[4]=c[8];
				b[5]=c[9];
				b[6]=c[2];
			hv = bytesToHexString(b);
			Log.e(hv,"true");
			return hv;
				}
			 
}
			

		 public static final String bytesToHexStringTwo(byte[] bArray, int count) {
				StringBuffer sb = new StringBuffer(bArray.length);
				String sTemp;
				for (int i = 0; i < count; i++) 
				{
					sTemp = Integer.toHexString(0xFF & bArray[i]);
					if (sTemp.length() < 2)
						sb.append(0);
					sb.append(sTemp.toUpperCase());
				}
				return sb.toString();
			 }
			

			public static String Stringspace(String str) {

				String temp = "";
				String temp2 = "";
				for (int i = 0; i < str.length(); i++) {

					if (i % 2 == 0) {
						temp = str.charAt(i) + "";
						temp2 += temp;
						System.out.println(temp);
					} else {
						temp2 += str.charAt(i) + " ";
					}

				}
				return temp2;
			}
		
	}


	