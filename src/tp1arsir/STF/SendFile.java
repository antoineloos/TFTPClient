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
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import tp1arsir.ACK;
import tp1arsir.RequestFactory;
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
    private static String courantIP = "";

    private final static int PACKET_SIZE = 516;

    private static int SendFile(File fileToSend) throws SocketException, IOException {
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(10000);

        socket.send(CreateDP(RequestFactory.createRQRequest(RequestFactory.OP_WRQ, fileToSend.getName(), "octet")));

        DatagramPacket rcDp = CreateDP(new byte[4]);
        socket.receive(rcDp);
        if (ACK.getAck(rcDp).getAckNr() == 0) {
            int numpacket = 1;
            byte[] buffer = new byte[512];
            FileInputStream in = new FileInputStream(fileToSend.getAbsolutePath());
            int rc = in.read(buffer);
            while (rc != -1) {
                byte[] request = RequestFactory.createDataRequest(numpacket, buffer);
                socket.send(CreateDP(request));
                int tentatives = 0;
                do {
                    boolean timeout = false;
                    try {
                        socket.receive(rcDp);
                    } catch (SocketTimeoutException e) {
                        timeout = true;
                    } catch (Exception e) {
                        throw e;
                    } finally {
                        if (timeout == true) {
                            tentatives ++;
                        }
                    }

                } while (rcDp == null);

                rc = in.read(buffer);
            }
        };

        return 0;

    }

    private static DatagramPacket CreateDP(byte[] buf) throws UnknownHostException {
        return new DatagramPacket(buf, packetLenght, InetAddress.getByName(courantIP), port);
    }

    private static boolean isLastPacket(DatagramPacket datagramPacket) {
        if (datagramPacket.getLength() < 512) {
            return true;
        } else {
            return false;
        }
    }

}
