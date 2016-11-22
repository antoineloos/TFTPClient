/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1arsir.STF;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import tp1arsir.ACK;
import tp1arsir.UnsignedHelper;

/**
 *
 * @author Epulapp
 */
public final class SendFile {

    /**
     * @param aCourantIP the courantIP to set
     */
    public static void setCourantIP(String aCourantIP) {
        courantIP = aCourantIP;
    }
    
    private static final int port = 69;
    private static final int packetLenght = 512;
    private static String courantIP ="";
    
	private static final byte OP_WRQ = 2;
	private static final byte OP_DATAPACKET = 3;
	private static final byte OP_ACK = 4;
	private static final byte OP_ERROR = 5;

	private final static int PACKET_SIZE = 516;
    
    
    private static int SendFile(File fileToSend) throws SocketException, IOException 
    {
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(10000);
       
     
        socket.send(CreateDP(createRequest(OP_WRQ, fileToSend.getName(), "octet")));
        
        DatagramPacket rcDp = CreateDP(new byte[4]);
        socket.receive(rcDp);
        if(getAck(rcDp).getAckNr()==0)
        {
            byte[] buffer = new byte[512];
            FileInputStream in = new FileInputStream(fileToSend.getAbsolutePath());
            int rc = in.read(buffer);
            while(rc != -1)
            {
              
              rc = in.read(buffer);
            }
        };
       
        
        
        return null;
        
    }
    
     public static ACK getAck(DatagramPacket recvDatagramPacket) throws IllegalStateException
    {
        byte[] data = recvDatagramPacket.getData();
//        System.out.println("\n" + data[0] + ", " + data[1] + ", " + data[2] + "," + data[3] + "\n");
        if (data[0] != 0 && data[1] != 4)
        {
            //TODO handle with proper error response
            throw new IllegalStateException("Not ack opcode, received code = " + data[0] + " " + data[1]);
        }
        return new ACK(data[2], data[3]);
    }
    
    private static DatagramPacket CreateDP(byte[] buf) throws UnknownHostException
    {
        return new DatagramPacket(buf, packetLenght , InetAddress.getByName(courantIP), port);
    }
    
    private static boolean isLastPacket(DatagramPacket datagramPacket) {
		if (datagramPacket.getLength() < 512)
			return true;
		else
			return false;
	}

	/*
	 * RRQ / WRQ packet format
	 * 
	 * 2 bytes - Opcode; string - filename; 1 byte - 0; string - mode; 1 byte -
	 * 0;
	 */
	private static byte[] createRequest(final byte opCode, final String fileName,
			final String mode) {
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
        
        private static byte[] createData(final byte opCode, final int packetnum, final Byte[] data) {
		byte zeroByte = 0;
		int wrqByteLength = 2 + 2 + data.length;
		byte[] wrqByteArray = new byte[wrqByteLength];
                byte[] numpacket  = UnsignedHelper.intTo2UnsignedBytes(packetnum);
		int position = 0;
		wrqByteArray[position] = zeroByte;
		position++;
		wrqByteArray[position] = opCode;
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
}
