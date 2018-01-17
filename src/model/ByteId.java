/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author syntel
 */
public class ByteId {
     private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    /**
     * Converts a byte array to a hexadecimal string
     * 
     * @return Hexadecimal string version of the byte array given.
     */
    public static String bytesToHex(byte[] bytes) 
    {
        if ( bytes == null || bytes.length == 0 )
        {
            return null;
        }
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
