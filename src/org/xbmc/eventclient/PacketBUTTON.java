package org.xbmc.eventclient;

public class PacketBUTTON
  extends Packet
{
  protected static final byte BT_USE_NAME = 1;
  protected static final byte BT_DOWN = 2;
  protected static final byte BT_UP = 4;
  protected static final byte BT_USE_AMOUNT = 8;
  protected static final byte BT_QUEUE = 16;
  protected static final byte BT_NO_REPEAT = 32;
  protected static final byte BT_VKEY = 64;
  protected static final byte BT_AXIS = -128;
  protected static final byte BT_AXISSINGLE = 0;
  
  public PacketBUTTON(short code, boolean repeat, boolean down, boolean queue, short amount, byte axis)
  {
    super((short)3);
    String map_name = "";
    String button_name = "";
    short flags = 0;
    appendPayload(code, map_name, button_name, repeat, down, queue, amount, axis, flags);
  }
  
  public PacketBUTTON(String map_name, String button_name, boolean repeat, boolean down, boolean queue, short amount, byte axis)
  {
    super((short)3);
    short code = 0;
    short flags = 1;
    appendPayload(code, map_name, button_name, repeat, down, queue, amount, axis, flags);
  }
  
  private void appendPayload(short code, String map_name, String button_name, boolean repeat, boolean down, boolean queue, short amount, byte axis, short flags)
  {
    if (amount > 0) {
      flags = (short)(flags | 0x8);
    } else {
      amount = 0;
    }
    if (down) {
      flags = (short)(flags | 0x2);
    } else {
      flags = (short)(flags | 0x4);
    }
    if (!repeat) {
      flags = (short)(flags | 0x20);
    }
    if (queue) {
      flags = (short)(flags | 0x10);
    }
    if (axis == 1) {
      flags = (short)(flags | 0x0);
    } else if (axis == 2) {
      flags = (short)(flags | 0xFFFFFF80);
    }
    appendPayload(code);
    appendPayload(flags);
    appendPayload(amount);
    appendPayload(map_name);
    appendPayload(button_name);
  }
}


/* Location:           C:\TEMP\RemoteClient.jar
 * Qualified Name:     org.xbmc.eventclient.PacketBUTTON
 * JD-Core Version:    0.7.0.1
 */