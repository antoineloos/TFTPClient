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
import tp1arsir.RequestFactory;
import tp1arsir.TFTPFunction;
import tp1arsir.UnsignedHelper;

/**
 *
 * @author Epulapp
 */
public final class SendFile extends TFTPFunction {

    
    
    
    private static int SendFile(File fileToSend) throws SocketException, IOException 
    {
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(10000);
       
     
        socket.send(CreateDP(RequestFactory.createRQRequest(RequestFactory.OP_WRQ,fileToSend.getName(), "octet")));
        
        DatagramPacket rcDp = CreateDP(new byte[4]);
        socket.receive(rcDp);
        if(ACK.getAck(rcDp).getAckNr()==0)
        {
            byte[] buffer = new byte[512];
            FileInputStream in = new FileInputStream(fileToSend.getAbsolutePath());
            int rc = in.read(buffer);
            while(rc != -1)
            {
              
              rc = in.read(buffer);
            }
        };
       
        
        
        return 0;
        
    }
    
     
    
    

	
	
}
