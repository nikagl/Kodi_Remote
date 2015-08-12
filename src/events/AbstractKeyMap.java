package events;

import java.util.HashMap;

public class AbstractKeyMap
{
  private static HashMap<Integer, AbstractEvent> eventMap = new HashMap();
  
  static
  {
    eventMap.put(Integer.valueOf(92), new KeyBackslash());
    eventMap.put(Integer.valueOf(8), new KeyBackspace());
    eventMap.put(Integer.valueOf(35), new KeyEnd());
    eventMap.put(Integer.valueOf(10), new KeyEnter());
    eventMap.put(Integer.valueOf(27), new KeyEsc());
    eventMap.put(Integer.valueOf(36), new KeyHome());
    eventMap.put(Integer.valueOf(45), new KeyMinus());
    eventMap.put(Integer.valueOf(46), new KeyPeriod());
    eventMap.put(Integer.valueOf(34), new KeyPgDn());
    eventMap.put(Integer.valueOf(33), new KeyPgUp());
    eventMap.put(Integer.valueOf(107), new KeyPlus());
    eventMap.put(Integer.valueOf(32), new KeySpace());
    eventMap.put(Integer.valueOf(9), new KeyTab());
    eventMap.put(Integer.valueOf(61), new KeyEquals());
    eventMap.put(Integer.valueOf(525), new KeyC());
    

    eventMap.put(Integer.valueOf(38), new KeyUp());
    eventMap.put(Integer.valueOf(40), new KeyDown());
    eventMap.put(Integer.valueOf(37), new KeyLeft());
    eventMap.put(Integer.valueOf(39), new KeyRight());
    

    eventMap.put(Integer.valueOf(65), new KeyA());
    eventMap.put(Integer.valueOf(66), new KeyB());
    eventMap.put(Integer.valueOf(67), new KeyC());
    eventMap.put(Integer.valueOf(68), new KeyD());
    eventMap.put(Integer.valueOf(69), new KeyE());
    eventMap.put(Integer.valueOf(70), new KeyF());
    eventMap.put(Integer.valueOf(71), new KeyG());
    eventMap.put(Integer.valueOf(72), new KeyH());
    eventMap.put(Integer.valueOf(73), new KeyI());
    eventMap.put(Integer.valueOf(74), new KeyJ());
    eventMap.put(Integer.valueOf(75), new KeyK());
    eventMap.put(Integer.valueOf(76), new KeyL());
    eventMap.put(Integer.valueOf(77), new KeyM());
    eventMap.put(Integer.valueOf(78), new KeyN());
    eventMap.put(Integer.valueOf(79), new KeyO());
    eventMap.put(Integer.valueOf(80), new KeyP());
    eventMap.put(Integer.valueOf(81), new KeyQ());
    eventMap.put(Integer.valueOf(82), new KeyR());
    eventMap.put(Integer.valueOf(83), new KeyS());
    eventMap.put(Integer.valueOf(84), new KeyT());
    eventMap.put(Integer.valueOf(85), new KeyU());
    eventMap.put(Integer.valueOf(86), new KeyV());
    eventMap.put(Integer.valueOf(87), new KeyW());
    eventMap.put(Integer.valueOf(88), new KeyX());
    eventMap.put(Integer.valueOf(89), new KeyY());
    eventMap.put(Integer.valueOf(90), new KeyZ());
    

    eventMap.put(Integer.valueOf(48), new Key0());
    eventMap.put(Integer.valueOf(49), new Key1());
    eventMap.put(Integer.valueOf(50), new Key2());
    eventMap.put(Integer.valueOf(51), new Key3());
    eventMap.put(Integer.valueOf(52), new Key4());
    eventMap.put(Integer.valueOf(53), new Key5());
    eventMap.put(Integer.valueOf(54), new Key6());
    eventMap.put(Integer.valueOf(55), new Key7());
    eventMap.put(Integer.valueOf(56), new Key8());
    eventMap.put(Integer.valueOf(57), new Key9());
  }
  
  public static AbstractEvent getEvent(int i)
  {
    return (AbstractEvent)eventMap.get(Integer.valueOf(i));
  }
}


/* Location:           C:\TEMP\RemoteClient.jar
 * Qualified Name:     events.AbstractKeyMap
 * JD-Core Version:    0.7.0.1
 */