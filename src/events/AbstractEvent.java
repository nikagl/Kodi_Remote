package events;

import java.io.IOException;
import org.xbmc.eventclient.XBMCClient;

public abstract interface AbstractEvent
{
  public abstract void execute(XBMCClient paramXBMCClient)
    throws IOException;
}


/* Location:           C:\TEMP\RemoteClient.jar
 * Qualified Name:     events.AbstractEvent
 * JD-Core Version:    0.7.0.1
 */