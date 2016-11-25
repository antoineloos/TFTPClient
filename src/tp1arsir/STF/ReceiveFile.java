/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1arsir.STF;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import Model.DataPacket;
import Utils.RequestFactory;
import tp1arsir.TFTPFunction;

/**
 *
 * @author Epulapp
 */
public class ReceiveFile extends TFTPFunction {
    public static int ReceiveFile(String fileToReceive) throws SocketException, IOException 
    {
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(10000);
        byte[] rrqrequest = RequestFactory.createRQRequest(RequestFactory.OP_RRQ, fileToReceive, "octet");
        int length = rrqrequest.length;
        socket.send(CreateDP(rrqrequest, length));

        DatagramPacket rcDp = CreateDP(new byte[516]);
        socket.receive(rcDp);
        DataPacket responsedata = DataPacket.getDataPacket(rcDp);
        if (responsedata.getPacketNr() == 1) {
            int ancienport = port;
            port = rcDp.getPort();
            int numpacket = 0;
            byte[] buffer = responsedata.getData();
            int taillepacket = rcDp.getLength() - 4;
            FileOutputStream out = new FileOutputStream(fileToReceive);
            out.write(buffer);
            numpacket ++;
            while (taillepacket >= 512) {
                int tentatives = 0;
                boolean timeout = false;
                do {
                    try {
                        socket.send(CreateDP(RequestFactory.createAckRequest(numpacket),4));
                        
                        rcDp = CreateDP(new byte[516], 516);
                        socket.receive(rcDp);
                        
                    } catch (SocketTimeoutException e) {
                        timeout = true;
                        tentatives ++;
                        
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
                DataPacket dtpk = DataPacket.getDataPacket(rcDp);
                
                if (dtpk.getPacketNr() == numpacket +1 ) {
                    numpacket ++;
                    buffer = dtpk.getData();
                    taillepacket = rcDp.getLength() - 4;
                    out.write(buffer);
                } 
            }
            out.write(buffer);
            socket.send(CreateDP(RequestFactory.createAckRequest(numpacket),4));
            port = ancienport;
            out.close();
        }
        
        return 0;

    }
}
