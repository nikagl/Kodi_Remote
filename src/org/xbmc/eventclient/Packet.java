package org.xbmc.eventclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public abstract class Packet
{
  private byte[] sig;
  private byte[] payload = new byte[0];
  private byte minver;
  private byte majver;
  private short packettype;
  private static final short MAX_PACKET_SIZE = 1024;
  private static final short HEADER_SIZE = 32;
  private static final short MAX_PAYLOAD_SIZE = 992;
  protected static final byte PT_HELO = 1;
  protected static final byte PT_BYE = 2;
  protected static final byte PT_BUTTON = 3;
  protected static final byte PT_MOUSE = 4;
  protected static final byte PT_PING = 5;
  protected static final byte PT_BROADCAST = 6;
  protected static final byte PT_NOTIFICATION = 7;
  protected static final byte PT_BLOB = 8;
  protected static final byte PT_LOG = 9;
  protected static final byte PT_ACTION = 10;
  protected static final byte PT_DEBUG = -1;
  public static final byte ICON_NONE = 0;
  public static final byte ICON_JPEG = 1;
  public static final byte ICON_PNG = 2;
  public static final byte ICON_GIF = 3;
  private static int uid = (int)(Math.random() * 2147483647.0D);
  
  protected Packet(short packettype)
  {
    sig = new byte[] { 88, 66, 77, 67 };
    minver = 0;
    majver = 2;
    this.packettype = packettype;
  }
  
  protected void appendPayload(String payload)
  {
    byte[] payloadarr = payload.getBytes();
    int oldpayloadsize = this.payload.length;
    byte[] oldpayload = this.payload;
    this.payload = new byte[oldpayloadsize + payloadarr.length + 1];
    System.arraycopy(oldpayload, 0, this.payload, 0, oldpayloadsize);
    System.arraycopy(payloadarr, 0, this.payload, oldpayloadsize, payloadarr.length);
  }
  
  protected void appendPayload(byte payload)
  {
    appendPayload(new byte[] { payload });
  }
  
  protected void appendPayload(byte[] payloadarr)
  {
    int oldpayloadsize = payload.length;
    byte[] oldpayload = payload;
    payload = new byte[oldpayloadsize + payloadarr.length];
    System.arraycopy(oldpayload, 0, payload, 0, oldpayloadsize);
    System.arraycopy(payloadarr, 0, payload, oldpayloadsize, payloadarr.length);
  }
  
  protected void appendPayload(int i)
  {
    appendPayload(intToByteArray(i));
  }
  
  protected void appendPayload(short s)
  {
    appendPayload(shortToByteArray(s));
  }
  
  public int getNumPackets()
  {
    return (payload.length + 991) / 992;
  }
  
  private byte[] getHeader(int seq, int maxseq, short actpayloadsize)
  {
    byte[] header = new byte[32];
    System.arraycopy(sig, 0, header, 0, 4);
    header[4] = majver;
    header[5] = minver;
    byte[] packettypearr = shortToByteArray(packettype);
    System.arraycopy(packettypearr, 0, header, 6, 2);
    byte[] seqarr = intToByteArray(seq);
    System.arraycopy(seqarr, 0, header, 8, 4);
    byte[] maxseqarr = intToByteArray(maxseq);
    System.arraycopy(maxseqarr, 0, header, 12, 4);
    byte[] payloadsize = shortToByteArray(actpayloadsize);
    System.arraycopy(payloadsize, 0, header, 16, 2);
    byte[] uid = intToByteArray(Packet.uid);
    System.arraycopy(uid, 0, header, 18, 4);
    byte[] reserved = new byte[10];
    System.arraycopy(reserved, 0, header, 22, 10);
    
    return header;
  }
  
  private byte[] getUDPMessage(int seq)
  {
    int maxseq = (payload.length + 991) / 992;
    if (seq > maxseq) {
      return null;
    }
    short actpayloadsize;
    if (seq == maxseq) {
      actpayloadsize = (short)(payload.length % 992);
    } else {
      actpayloadsize = 992;
    }
    byte[] pack = new byte[32 + actpayloadsize];
    
    System.arraycopy(getHeader(seq, maxseq, actpayloadsize), 0, pack, 0, 32);
    System.arraycopy(payload, (seq - 1) * 992, pack, 32, actpayloadsize);
    
    return pack;
  }
  
  public void send(InetAddress adr, int port)
    throws IOException
  {
    int maxseq = getNumPackets();
    DatagramSocket s = new DatagramSocket();
    for (int seq = 1; seq <= maxseq; seq++)
    {
      byte[] pack = getUDPMessage(seq);
      DatagramPacket p = new DatagramPacket(pack, pack.length);
      p.setAddress(adr);
      p.setPort(port);
      s.send(p);
    }
  }
  
  private static final byte[] intToByteArray(int value)
  {
    return new byte[] {
      (byte)(value >>> 24), 
      (byte)(value >>> 16), 
      (byte)(value >>> 8), 
      (byte)value };
  }
  
  private static final byte[] shortToByteArray(short value)
  {
    return new byte[] {
      (byte)(value >>> 8), 
      (byte)value };
  }
}



/* Location:           C:\TEMP\RemoteClient.jar

 * Qualified Name:     org.xbmc.eventclient.Packet

 * JD-Core Version:    0.7.0.1

 */