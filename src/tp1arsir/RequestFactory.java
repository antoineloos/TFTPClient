/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1arsir;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Epulapp
 */
public final class RequestFactory {
    
        
        public static final byte OP_RRQ = 1;
        public static final byte OP_WRQ = 2;
	private static final byte OP_DATAPACKET = 3;
	private static final byte OP_ACK = 4;
	private static final byte OP_ERROR = 5;
        
        //for WRQ/RRQ packets
        public static byte[] createRQRequest(final byte opCode, final String fileName, final String mode) {
		byte zeroByte = 0;
		int wrqByteLength = 2 + fileName.length() + 1 + mode.length() + 1;
		byte[] wrqByteArray = new byte[wrqByteLength];

		int position = 0;
		wrqByteArray[position] = zeroByte;
		position++;
		wrqByteArray[position] = opCode;
		position++;
		for (int i = 0; i < fileName.length(); i++) {
			wrqByteArray[position] = (byte) fileName.charAt(i);
			position++;
		}
		wrqByteArray[position] = zeroByte;
		position++;
		for (int i = 0; i < mode.length(); i++) {
			wrqByteArray[position] = (byte) mode.charAt(i);
			position++;
		}
		wrqByteArray[position] = zeroByte;
		return wrqByteArray;
	}
    
        //for data packets
        
        public static byte[] createDataRequest( final int packetnum, final Byte[] data) {
		byte zeroByte = 0;
		int wrqByteLength = 2 + 2 + data.length;
		byte[] wrqByteArray = new byte[wrqByteLength];
                byte[] numpacket  = UnsignedHelper.intTo2UnsignedBytes(packetnum);
		int position = 0;
		wrqByteArray[position] = zeroByte;
		position++;
		wrqByteArray[position] = OP_DATAPACKET;
		position++;
                wrqByteArray[position] = numpacket[0];
                position++;
                wrqByteArray[position] = numpacket[1];
                position++;
		for (int i = 0; i < data.length; i++) {
			wrqByteArray[position] = data[i];
			position++;
		}
		
		return wrqByteArray;
	}
        
        //for ACK packets
        
        public static byte[] createAckRequest( final int packetnum) {
		byte zeroByte = 0;
		int wrqByteLength = 4;
		byte[] wrqByteArray = new byte[wrqByteLength];
                byte[] numpacket  = UnsignedHelper.intTo2UnsignedBytes(packetnum);
		int position = 0;
		wrqByteArray[position] = zeroByte;
		position++;
		wrqByteArray[position] = OP_ACK;
		position++;
                wrqByteArray[position] = numpacket[0];
                position++;
                wrqByteArray[position] = numpacket[1];
                
		return wrqByteArray;
	}
        
        //for Error packets
        
        public static byte[] createErrorRequest(final int packetnum) {
		byte zeroByte = 0;
		int wrqByteLength = 4;
		byte[] wrqByteArray = new byte[wrqByteLength];
                byte[] numpacket  = UnsignedHelper.intTo2UnsignedBytes(packetnum);
		int position = 0;
		wrqByteArray[position] = zeroByte;
		position++;
		wrqByteArray[position] = OP_ERROR;
		position++;
                wrqByteArray[position] = numpacket[0];
                position++;
                wrqByteArray[position] = numpacket[1];
                
		return wrqByteArray;
	}
      
}
