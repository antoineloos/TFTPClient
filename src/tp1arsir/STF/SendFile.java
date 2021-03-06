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
import java.util.Arrays;
import Model.ACK;
import Utils.RequestFactory;
import tp1arsir.TFTPFunction;
import Utils.UnsignedHelper;
import Views.View;
import javafx.scene.control.Label;

/**
 *
 * @author Epulapp
 */
public final class SendFile extends TFTPFunction {

    public static int SendFile(File fileToSend, Label lbError) throws SocketException, IOException {
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(10000);
        byte[] wrqrequest = RequestFactory.createRQRequest(RequestFactory.OP_WRQ, fileToSend.getName(), "octet");
        int length = wrqrequest.length;
        socket.send(CreateDP(wrqrequest, length));
        try {
            DatagramPacket rcDp = CreateDP(new byte[516], 516);
            socket.receive(rcDp);
            if (ACK.getAck(rcDp).getAckNr() == 0) {
                int ancienport = port;
                port = rcDp.getPort();
                int numpacket = 1;
                byte[] buffer = new byte[512];
                FileInputStream in = new FileInputStream(fileToSend.getAbsolutePath());
                int rc = in.read(buffer);
                while (rc != -1) {
                    if (rc < 512) {
                        byte[] bufferOLD = buffer;
                        buffer = new byte[rc];
                        for (int i = 0; i < rc; i++) {
                            buffer[i] = bufferOLD[i];
                        }
                    }
                    byte[] request = RequestFactory.createDataRequest(numpacket, buffer);
                    int tentatives = 0;
                    boolean timeout = false;
                    do {
                        try {
                            socket.send(CreateDP(request, request.length));

                            rcDp = CreateDP(new byte[516], 516);
                            socket.receive(rcDp);

                        } catch (SocketTimeoutException e) {
                            timeout = true;
                            tentatives++;

                            if (tentatives >= SendFile.NB_TENTATIVES) {
                                throw e;
                            }
                        } catch (Exception e) {
                            throw e;
                        } finally {
                            if (timeout == false) {
                                break;
                            }
                        }
                    } while (timeout == true);
                    try {
                        ACK ack = ACK.getAck(rcDp);
                        if (ack.getAckNr() == numpacket) {

                            rc = in.read(buffer);
                            numpacket++;
                        }
                    } catch (IllegalStateException ex) {
                        lbError.setText(ex.getMessage());
                    }

                }
                port = ancienport;
                in.close();
            }
        } catch (IllegalStateException ex) {
            lbError.setText(ex.getMessage());
            return 1;
        }
        return 0;

    }

}
