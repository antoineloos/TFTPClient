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
public class DataPacket {
    
    private byte[] opcode = {0, 4};
    private byte[] blockNr;
    private int blockNrInt;
    private byte[] data;
    
    public DataPacket(byte b, byte b1, byte[] dataArray)
    {
        blockNr = new byte[]{b, b1};
        data = dataArray;
        blockNrInt = UnsignedHelper.twoBytesToInt(blockNr);
    }
    
    public int getPacketNr()
    {
        return blockNrInt;
    }
    
    public static DataPacket getDataPacket(DatagramPacket recvDatagramPacket) throws IllegalStateException
    {
        byte[] data = recvDatagramPacket.getData();

        if (data[0] != 0 && data[1] != 3)
        {
          
            throw new IllegalStateException("");
        }
        byte[] datarcv = new byte[data.length-4];
        for(int i = 0 ; i<data.length-4;i++){ datarcv[i] = data[i+4];}
        return new DataPacket(data[2], data[3],datarcv );
    }
    
    public byte[] getData()
    {
        return data;
    }
}