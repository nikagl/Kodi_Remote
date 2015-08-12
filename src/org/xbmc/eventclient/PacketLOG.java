package org.xbmc.eventclient;

public class PacketLOG
  extends Packet
{
  public PacketLOG(byte loglevel, String logmessage)
  {
    super((short)9);
    appendPayload(loglevel);
    appendPayload(logmessage);
  }
}


/* Location:           C:\TEMP\RemoteClient.jar
 * Qualified Name:     org.xbmc.eventclient.PacketLOG
 * JD-Core Version:    0.7.0.1
 */