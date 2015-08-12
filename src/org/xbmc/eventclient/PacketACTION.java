package org.xbmc.eventclient;

public class PacketACTION
  extends Packet
{
  public static final byte ACTION_EXECBUILTIN = 1;
  public static final byte ACTION_BUTTON = 2;
  
  public PacketACTION(String actionmessage)
  {
    super((short)10);
    byte actiontype = 1;
    appendPayload(actionmessage, actiontype);
  }
  
  public PacketACTION(String actionmessage, byte actiontype)
  {
    super((short)10);
    appendPayload(actionmessage, actiontype);
  }
  
  private void appendPayload(String actionmessage, byte actiontype)
  {
    appendPayload(actiontype);
    appendPayload(actionmessage);
  }
}


/* Location:           C:\TEMP\RemoteClient.jar
 * Qualified Name:     org.xbmc.eventclient.PacketACTION
 * JD-Core Version:    0.7.0.1
 */