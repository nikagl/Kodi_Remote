package org.xbmc.eventclient;

public class PacketNOTIFICATION
  extends Packet
{
  public PacketNOTIFICATION(String title, String message, byte iconType, byte[] iconData)
  {
    super((short)7);
    appendPayload(title, message, iconType, iconData);
  }
  
  public PacketNOTIFICATION(String title, String message)
  {
    super((short)7);
    appendPayload(title, message, (byte)0, null);
  }
  
  private void appendPayload(String title, String message, byte iconType, byte[] iconData)
  {
    appendPayload(title);
    appendPayload(message);
    appendPayload(iconType);
    appendPayload(0);
    if (iconData != null) {
      appendPayload(iconData);
    }
  }
}


/* Location:           C:\TEMP\RemoteClient.jar
 * Qualified Name:     org.xbmc.eventclient.PacketNOTIFICATION
 * JD-Core Version:    0.7.0.1
 */