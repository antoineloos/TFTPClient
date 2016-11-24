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
public class TFTPFunction {
    protected static DatagramPacket CreateDP(byte[] buf) throws UnknownHostException
    {
        return new DatagramPacket(buf, packetLenght , InetAddress.getByName(courantIP), port);
    }
    
    protected static boolean isLastPacket(DatagramPacket datagramPacket) {
		if (datagramPacket.getLength() < 512)
			return true;
		else
			return false;
	}
    
    public static void setCourantIP(String aCourantIP) {
        courantIP = aCourantIP;
    }
    
    protected static final int port = 69;
    protected static final int packetLenght = 512;
    protected static String courantIP ="";
    protected final static int PACKET_SIZE = 516;
}
