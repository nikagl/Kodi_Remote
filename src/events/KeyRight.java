package events;

import java.io.IOException;
import org.xbmc.eventclient.XBMCClient;

public class KeyRight
  implements AbstractEvent
{
  public void execute(XBMCClient oXBMCClient)
    throws IOException
  {
    oXBMCClient.sendButton("KB", "right", false, true, false, (short)0, (byte)0);
  }
}


/* Location:           C:\TEMP\RemoteClient.jar
 * Qualified Name:     events.KeyRight
 * JD-Core Version:    0.7.0.1
 */