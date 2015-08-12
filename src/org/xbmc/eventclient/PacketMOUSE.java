package org.xbmc.eventclient;

public class PacketMOUSE
  extends Packet
{
  protected static final byte MS_ABSOLUTE = 1;
  
  public PacketMOUSE(int x, int y)
  {
    super((short)4);
    byte flags = 0;
    flags = (byte)(flags | 0x1);
    appendPayload(flags);
    appendPayload((short)x);
    appendPayload((short)y);
  }
}


/* Location:           C:\TEMP\RemoteClient.jar
 * Qualified Name:     org.xbmc.eventclient.PacketMOUSE
 * JD-Core Version:    0.7.0.1
 */