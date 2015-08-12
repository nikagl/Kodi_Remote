package ui;

import events.AbstractEvent;
import events.AbstractKeyMap;
import org.xbmc.eventclient.XBMCClient;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyboardReader
        extends JFrame {
    private static final Pattern pat = Pattern.compile("\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");
    private static final long serialVersionUID = 1L;
    private static XBMCClient oXBMCClient;
    private final String CLIENT_NAME = "Kodi Remote Client";
    private JPanel connectionPanel;
    private JFormattedTextField ipField;
    private JButton connectButton;
    private JButton disconnectButton;
    private JTextArea textArea;
    private JLabel statusLabel;

    public KeyboardReader(String ipaddress)
            throws IOException {
        super("Kodi Remote Controller");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.gif")));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                KeyboardReader.this.stopClient();
                super.windowClosing(e);
            }
        });
        connectionPanel = new JPanel();
        connectionPanel.add(new JLabel("IP Address:"));
        connectionPanel.add(getIpText(ipaddress));
        connectionPanel.add(getConnectButton());
        connectionPanel.add(getDisconnectButton());

        textArea = new JTextArea(10, 35);
        textArea.addKeyListener(new ComponentKeyListener());

        statusLabel = new JLabel("Ready");

        setLayout(new BorderLayout());
        getContentPane().add(connectionPanel, "North");
        getContentPane().add(textArea, "Center");
        getContentPane().add(statusLabel, "South");

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public static void main(String argv[])
            throws IOException {
        try {
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception localException) {
        }

        if (argv.length > 0) {
            KeyboardReader reader = new KeyboardReader(argv[0]);
        } else {
            KeyboardReader reader = new KeyboardReader("10.0.0.1");
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    private void startXBMCClient() {
        if (oXBMCClient != null) {
            stopClient();
            oXBMCClient = null;
        }
        try {
            statusLabel.setText("Starting Kodi Remote Client...");
            StringTokenizer token = new StringTokenizer(ipField.getText(), ".");


            InetAddress host = Inet4Address.getByAddress(new byte[]{(byte) Integer.parseInt(token.nextToken()), (byte) Integer.parseInt(token.nextToken()),
                    (byte) Integer.parseInt(token.nextToken()), (byte) Integer.parseInt(token.nextToken())});

            oXBMCClient = new XBMCClient(host, 9777, "Kodi Remote Client");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        statusLabel.setText("Kodi Remote Client started.");
    }

    private void stopClient() {
        if (oXBMCClient != null) {
            statusLabel.setText("Stopping Kodi Remote Client...");
            System.out.println("Stopping Kodi Remote Client...");
            try {
                oXBMCClient.sendLog((byte) 0, "Kodi Remote Client disconnecting....");
                oXBMCClient.sendNotification("Kodi Remote Client", "Kodi Remote Client will disconnect");
                oXBMCClient.stopClient();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            oXBMCClient = null;
            statusLabel.setText("Kodi Remote Client stopped.");
        }
    }

    private JButton getConnectButton() {
        if (connectButton == null) {
            connectButton = new JButton("Connect");
            connectButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    KeyboardReader.this.startXBMCClient();
                    textArea.requestFocus();
                }
            });
        }
        return connectButton;
    }

    private JButton getDisconnectButton() {
        if (disconnectButton == null) {
            disconnectButton = new JButton("Disconnect");
            disconnectButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    KeyboardReader.this.stopClient();
                }
            });
        }
        return disconnectButton;
    }

    private JFormattedTextField getIpText(java.lang.String ipaddress) {
        if (ipField == null) {
            ipField = new JFormattedTextField();
            ipField.setColumns(10);
            ipField.setText(ipaddress);
            ipField.getDocument().addDocumentListener(new DocumentListener() {
                void checkDocument(DocumentEvent e) {
                    try {
                        String text = e.getDocument().getText(0,
                                e.getDocument().getLength());
                        connectButton.setEnabled(KeyboardReader.this.checkString(text));
                    } catch (BadLocationException localBadLocationException) {
                    }
                }

                public void insertUpdate(DocumentEvent e) {
                    checkDocument(e);
                }

                public void removeUpdate(DocumentEvent e) {
                    checkDocument(e);
                }

                public void changedUpdate(DocumentEvent e) {
                    checkDocument(e);
                }
            });
        }
        return ipField;
    }

    private boolean checkString(String s) {
        Matcher m = pat.matcher(s);
        return m.matches();
    }

    class ComponentKeyListener
            implements KeyListener {
        ComponentKeyListener() {
        }

        public void keyPressed(KeyEvent e) {
            if (KeyboardReader.oXBMCClient == null) {
                statusLabel.setText("Kodi Remote Client not connected");
                JTextArea text = (JTextArea) e.getSource();
                text.setText("");
                return;
            }
            AbstractEvent event = AbstractKeyMap.getEvent(e.getKeyCode());
            if (event != null) {
                try {
                    event.execute(KeyboardReader.oXBMCClient);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                statusLabel.setText("Key Not defined - " + KeyEvent.getKeyText(e.getKeyCode()));
            }
            JTextArea text = (JTextArea) e.getSource();
            text.setText("");
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }
    }
}


/* Location:           C:\TEMP\RemoteClient.jar
 * Qualified Name:     ui.KeyboardReader
 * JD-Core Version:    0.7.0.1
 */