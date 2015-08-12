package org.xbmc.eventclient;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;

public class XBMCClient
{
  private boolean hasIcon = false;
  private String deviceName;
  private PingThread oPingThread;
  private byte iconType = 2;
  private byte[] iconData;
  private InetAddress hostAddress;
  private int hostPort;
  
  public XBMCClient(InetAddress hostAddress, int hostPort, String deviceName, String iconFile)
    throws IOException
  {
    byte iconType = 2;
    if (iconFile.toLowerCase().endsWith(".jpeg")) {
      iconType = 1;
    }
    if (iconFile.toLowerCase().endsWith(".jpg")) {
      iconType = 1;
    }
    if (iconFile.toLowerCase().endsWith(".gif")) {
      iconType = 3;
    }
    FileInputStream iconFileStream = new FileInputStream(iconFile);
    byte[] iconData = new byte[iconFileStream.available()];
    iconFileStream.read(iconData);
    
    hasIcon = true;
    

    startClient(hostAddress, hostPort, deviceName, iconType, iconData);
  }
  
  public XBMCClient(InetAddress hostAddress, int hostPort, String deviceName, byte iconType, byte[] iconData)
    throws IOException
  {
    hasIcon = true;
    startClient(hostAddress, hostPort, deviceName, iconType, iconData);
  }
  
  public XBMCClient(InetAddress hostAddress, int hostPort, String deviceName)
    throws IOException
  {
    hasIcon = false;
    byte iconType = 0;
    byte[] iconData = (byte[])null;
    startClient(hostAddress, hostPort, deviceName, iconType, iconData);
  }
  
  private void startClient(InetAddress hostAddress, int hostPort, String deviceName, byte iconType, byte[] iconData)
    throws IOException
  {
    this.hostAddress = hostAddress;
    this.hostPort = hostPort;
    this.deviceName = deviceName;
    
    this.iconType = iconType;
    this.iconData = iconData;
    PacketHELO p;
    if (hasIcon) {
      p = new PacketHELO(deviceName, iconType, iconData);
    } else {
      p = new PacketHELO(deviceName);
    }
    p.send(hostAddress, hostPort);
    

    oPingThread = new PingThread(hostAddress, hostPort, 20000);
    oPingThread.start();
  }
  
  public void stopClient()
    throws IOException
  {
    oPingThread.giveup();
    oPingThread.interrupt();
    
    PacketBYE p = new PacketBYE();
    p.send(hostAddress, hostPort);
  }
  
  public void sendNotification(String title, String message)
    throws IOException
  {
    PacketNOTIFICATION p;
    if (hasIcon) {
      p = new PacketNOTIFICATION(title, message, iconType, iconData);
    } else {
      p = new PacketNOTIFICATION(title, message);
    }
    p.send(hostAddress, hostPort);
  }
  
  public void sendButton(short code, boolean repeat, boolean down, boolean queue, short amount, byte axis)
    throws IOException
  {
    PacketBUTTON p = new PacketBUTTON(code, repeat, down, queue, amount, axis);
    p.send(hostAddress, hostPort);
  }
  
  public void sendButton(String map_name, String button_name, boolean repeat, boolean down, boolean queue, short amount, byte axis)
    throws IOException
  {
    PacketBUTTON p = new PacketBUTTON(map_name, button_name, repeat, down, queue, amount, axis);
    p.send(hostAddress, hostPort);
  }
  
  public void sendMouse(int x, int y)
    throws IOException
  {
    PacketMOUSE p = new PacketMOUSE(x, y);
    p.send(hostAddress, hostPort);
  }
  
  public void ping()
    throws IOException
  {
    PacketPING p = new PacketPING();
    p.send(hostAddress, hostPort);
  }
  
  public void sendLog(byte loglevel, String logmessage)
    throws IOException
  {
    PacketLOG p = new PacketLOG(loglevel, logmessage);
    p.send(hostAddress, hostPort);
  }
  
  public void sendAction(String actionmessage)
    throws IOException
  {
    PacketACTION p = new PacketACTION(actionmessage);
    p.send(hostAddress, hostPort);
  }
  
  class PingThread
    extends Thread
  {
    private InetAddress hostAddress;
    private int hostPort;
    private int sleepTime;
    private boolean giveup = false;
    
    public PingThread(InetAddress hostAddress, int hostPort, int sleepTime)
    {
      super();
      this.hostAddress = hostAddress;
      this.hostPort = hostPort;
      this.sleepTime = sleepTime;
    }
    
    public void giveup()
    {
      giveup = true;
    }
    
    public void run()
    {
      while (!giveup)
      {
        try
        {
          PacketPING p = new PacketPING();
          p.send(hostAddress, hostPort);
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
        try
        {
          Thread.sleep(sleepTime);
        }
        catch (InterruptedException localInterruptedException) {}
      }
    }
  }
}



/* Location:           C:\TEMP\RemoteClient.jar

 * Qualified Name:     org.xbmc.eventclient.XBMCClient

 * JD-Core Version:    0.7.0.1

 */