package org.xbmc.eventclient;

public class PacketHELO
  extends Packet
{
  public PacketHELO(String devicename)
  {
    super((short)1);
    appendPayload(devicename);
    appendPayload((byte)0);
    appendPayload((short)0);
    appendPayload(0);
    appendPayload(0);
  }
  
  public PacketHELO(String devicename, byte iconType, byte[] iconData)
  {
    super((short)1);
    appendPayload(devicename);
    appendPayload(iconType);
    appendPayload((short)0);
    appendPayload(0);
    appendPayload(0);
    appendPayload(iconData);
  }
}


/* Location:           C:\TEMP\RemoteClient.jar
 * Qualified Name:     org.xbmc.eventclient.PacketHELO
 * JD-Core Version:    0.7.0.1
 */