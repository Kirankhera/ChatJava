
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;



public class FrameDemo extends JFrame implements Runnable
{
        
    //LoginPanel lp;
    ChatPanel cp;
    DrawPanel dp;
    ControlPanel conp;

    public static JPanel container = new JPanel();
    
    JList online;
    JScrollPane SP_ONLINE=new JScrollPane();
        
    boolean connected;
    Socket s;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public FrameDemo()
    {}

    public FrameDemo(String s)
    {
        super(s);
        online = new JList();
        online.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent evt) 
            {
                JList online = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = online.getSelectedIndex();
                    JOptionPane.showMessageDialog(null,"You Clicked Twice!"+index);
                } else if (evt.getClickCount() == 3) {   
                    int index = online.locationToIndex(evt.getPoint());
                    JOptionPane.showMessageDialog(null,"You Clicked Thrice!"+index);
                }
            }
        });
        online.setForeground(new java.awt.Color(0,0,255));

        SP_ONLINE.setHorizontalScrollBarPolicy(
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_ONLINE.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        SP_ONLINE.setViewportView(online);
 
        SP_ONLINE.setBounds(350,90,130,180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cp = new ChatPanel(this);
        dp = new DrawPanel(this);
        conp = new ControlPanel(this,cp,dp);

        container.setLayout(new BorderLayout());
        container.add(cp, BorderLayout.WEST);
        container.add(dp, BorderLayout.EAST);
        container.add(SP_ONLINE,BorderLayout.CENTER);

        getContentPane().add(container, BorderLayout.CENTER);
        getContentPane().add(conp, BorderLayout.SOUTH);

        //Display the window.
        cp.setVisible(false);
        dp.setVisible(false);
        setSize(1000,500);
        setLocationRelativeTo(null);
        conp.b2.setVisible(false);
        conp.b3.setVisible(false);
        conp.b4.setVisible(false);
        conp.b5.setVisible(false);
        conp.b6.setVisible(false);
        conp.b7.setVisible(false);
        conp.b8.setVisible(false);
        conp.b9.setVisible(false);
        conp.b10.setVisible(false);
        dp.setVisible(false);
        SP_ONLINE.setVisible(false);
        setVisible(true);
        conp.b1.setForeground(Color.blue);
        conp.b2.setForeground(Color.blue);
        conp.b3.setForeground(Color.blue);
        conp.b4.setForeground(Color.blue);
        conp.b5.setForeground(Color.blue);
        conp.b6.setForeground(Color.blue);
        conp.b7.setForeground(Color.blue);
        conp.b8.setForeground(Color.blue);
        conp.b9.setForeground(Color.blue);
        conp.b10.setForeground(Color.blue);
        SP_ONLINE.setForeground(Color.blue);
        cp.ta.setForeground(Color.blue);
        dp.setForeground(Color.black);
    }

    ///---------------------------------
    public void setConnected(boolean c)
    {
        connected = c;
    }

    public boolean isConnected()
    {
        return connected;
    }

    public void connect()
    {
        setConnected(true);
        System.out.println("Should connect now . . . ");
        try
        {
            //s = new Socket("afs3.njit.edu",9623);
            s = new Socket("localhost",6969);
            oos = new ObjectOutputStream(s.getOutputStream());
            new Thread(this).start();
            System.out.println("Connected . . . ");
            conp.b3.setVisible(true);
            conp.b1.setVisible(false);
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void close()
    {
        setConnected(false);//should send last message so others can update user list
        // and remove self from shared arralist of handlers
        try
        {
            oos.writeObject("remove");
            oos.flush();
            oos.close();
            s.close();
            JOptionPane.showMessageDialog(null,"You disconnected!");
            System.out.println("Disconnected . . . ");
            System.exit(0);
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void run()
    {
        try
        {
            ois = new ObjectInputStream(s.getInputStream());
            for (;;) {
                Object o = receiveMessage();
                if (o != null) {
                    if (o.toString().contains("#?!")) {
                        String TEMP1=o.toString().substring(3);
                        TEMP1=TEMP1.replace("[","");
                        TEMP1=TEMP1.replace("]","");
                        String[] CurrentUsers=TEMP1.split(",");
                        online.setListData(CurrentUsers);
                    } else if(o instanceof String) {
                        cp.appendMessage((String)o);
                    } else if(o instanceof StringMessage)
                    {
                        StringMessage sm = (StringMessage)o;
                        String s = (String)sm.getMessage();
                        System.out.println(s);
                        cp.ta.append(s+" has joined"+"\n");
                    } else if(o instanceof ArrayList) {
                        ArrayList<Line> message;
                        message = (ArrayList)o;
                        dp.linelist = message;
                        dp.repaint();
                    } else if(o instanceof UserMessage) {
                    }
                } else {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
        finally
        {
            try
            {
                ois.close();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void ccolor()
    {
        conp.b1.setForeground(Color.darkGray);
        conp.b2.setForeground(Color.GREEN);
        conp.b3.setForeground(Color.yellow);
        conp.b4.setForeground(Color.RED);
        conp.b5.setForeground(Color.blue);
        conp.b6.setForeground(Color.MAGENTA);
        conp.b7.setForeground(Color.cyan);
        conp.b8.setForeground(Color.red);
        conp.b9.setForeground(Color.orange);
        conp.b10.setForeground(Color.blue);
        SP_ONLINE.setForeground(Color.pink);
        cp.ta.setForeground(Color.yellow);
        dp.setForeground(Color.BLUE);

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        cp.ta.setBorder(BorderFactory.createCompoundBorder(border, 
        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }

    public void dcolor()
    {
        String cc;
        cc = JOptionPane.showInputDialog("Enter message:");

        switch (cc)
        {
            case "red":
                dp.setForeground(Color.red);
                break;
            case "blue":
                dp.setForeground(Color.blue);
                break;
            case "green":
                dp.setForeground(Color.green);
                break;
            case "yellow":
                dp.setForeground(Color.yellow);
                break;
            case "pink":
                dp.setForeground(Color.pink);
                break;
            case "orange":
                dp.setForeground(Color.orange);
                break;
            default:
                dp.setForeground(Color.black);
                break;
        }
    }
    public void rcolor()
    {
        conp.b1.setForeground(Color.blue);
        conp.b2.setForeground(Color.blue);
        conp.b3.setForeground(Color.blue);
        conp.b4.setForeground(Color.blue);
        conp.b5.setForeground(Color.blue);
        conp.b6.setForeground(Color.blue);
        conp.b7.setForeground(Color.blue);
        conp.b8.setForeground(Color.blue);
        conp.b9.setForeground(Color.blue);
        conp.b10.setForeground(Color.blue);
        SP_ONLINE.setForeground(Color.blue);
        cp.ta.setForeground(Color.blue);
        dp.setForeground(Color.red);
        cp.ta.setBorder(BorderFactory.createEtchedBorder());
    }

    public void savechat()
    {
        ObjectOutputStream oout = null;
        try
        {
            PrintWriter textout = new PrintWriter("Log.txt");
            FileOutputStream out = new FileOutputStream("DrawLog.txt");
            oout = new ObjectOutputStream(out);
            textout.print(cp.ta.getText());
            oout.writeObject(dp.linelist);
            textout.close();
            oout.flush();
        } catch(IOException e)
        {
            System.out.println(e);
            if (oout != null) {
                try
                {
                    oout.close();
                } catch (IOException ioe){}
            }
        }
    }

    public void loadchat()
    {
        BufferedReader br = null;
        Object myObject;
        try
        {
            br = new BufferedReader(new FileReader("Log.txt"));
            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream("DrawLog.txt"));
            
            myObject = (Object)ois.readObject();

            ArrayList<Line> message;
            message = (ArrayList)myObject;
            dp.linelist = message;
            dp.repaint();

            String line = null;
            
            cp.ta.setText("");
           
            while ((line = br.readLine()) != null) 
            {
                cp.ta.append(line);
            }
        } catch(IOException e)
        {
            System.out.println(e.getMessage());
            if (br != null) {
                try
                {
                    br.close();
                } catch (IOException ioe){}
            }
        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(FrameDemo.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    final public void sendMessage(Object o)
    {
        if (isConnected())
        {
            try
            {
                if (o instanceof LineMessage) 
                {
                    System.out.println("LineMessage written to stream");
                    dp.setVisible(true);
                    oos.writeObject(o);
                    oos.flush();
                } else {
                    oos.writeObject(o);
                    oos.flush();
                }
            } catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public Object receiveMessage()
    {
        Object obj = null;
        try
        {
            obj = ois.readObject();
        } catch(IOException e)
        {
            System.out.println("End of stream.");
        } catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public void clear()
    {
        dp.linelist = new ArrayList<Line>();
        dp.repaint();
        cp.ta.setText("");
    }

    private static void createAndShowGUI() 
    {
        FrameDemo frame = new FrameDemo("Chat System with Shared Whiteboard");   
    }

    public static void main(String[] args) 
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                createAndShowGUI();
            }
        });
    }
}
