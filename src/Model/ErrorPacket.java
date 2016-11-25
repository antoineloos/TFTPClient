/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.net.DatagramPacket;
import tp1arsir.UnsignedHelper;

/**
 *
 * @author Epulapp
 */
public class ErrorPacket {
     private byte[] opcode = {0, 5};
    private byte[] codeEr;
    private String messageStr;
    private byte[] message;
    private int codeError;
    
    
    public ErrorPacket(byte b, byte b1, byte[] msg)
    {
        codeEr = new byte[]{b, b1};
        message = msg;
        messageStr = msg.toString();
        codeError  = UnsignedHelper.twoBytesToInt(codeEr);
    }
    
    public int getCodeError()
    {
        return codeError;
    }
    
    public static ErrorPacket getErrorPacket(DatagramPacket recvDatagramPacket) throws IllegalStateException
    {
        byte[] data = recvDatagramPacket.getData();

        if (data[0] != 0 && data[1] != 5)
        {
          
            throw new IllegalStateException("");
        }
        byte[] datarcv = new byte[data.length-4];
        for(int i = 0 ; i<data.length-4;i++){ datarcv[i] = data[i+4];}
        return new ErrorPacket(data[2], data[3],datarcv );
    }
    
    public String getMessage()
    {
        return messageStr ;
    }
}
