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
    public static final int NB_TENTATIVES = 3;
    
    protected static DatagramPacket CreateDP(byte[] buf) throws UnknownHostException
    {
        return new DatagramPacket(buf, PACKET_SIZE , InetAddress.getByName(courantIP), port);
    }
    
    protected static DatagramPacket CreateDP(byte[] buf, int packetSize) throws UnknownHostException
    {
        return new DatagramPacket(buf, packetSize , InetAddress.getByName(courantIP), port);
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
    
    protected static int port = 69;
    protected static final int packetLenght = 512;
    protected static String courantIP ="";
    protected final static int PACKET_SIZE = 516;
}
