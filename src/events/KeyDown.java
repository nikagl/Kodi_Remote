package events;

import java.io.IOException;
import org.xbmc.eventclient.XBMCClient;

public class KeyDown
  implements AbstractEvent
{
  public void execute(XBMCClient oXBMCClient)
    throws IOException
  {
    oXBMCClient.sendButton("KB", "down", false, true, false, (short)0, (byte)0);
  }
}


/* Location:           C:\TEMP\RemoteClient.jar
 * Qualified Name:     events.KeyDown
 * JD-Core Version:    0.7.0.1
 */