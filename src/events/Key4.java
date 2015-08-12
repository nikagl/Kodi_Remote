package events;

import java.io.IOException;
import org.xbmc.eventclient.XBMCClient;

public class Key4
  implements AbstractEvent
{
  public void execute(XBMCClient oXBMCClient)
    throws IOException
  {
    oXBMCClient.sendButton("KB", "4", false, true, false, (short)0, (byte)0);
  }
}


/* Location:           C:\TEMP\RemoteClient.jar
 * Qualified Name:     events.Key4
 * JD-Core Version:    0.7.0.1
 */