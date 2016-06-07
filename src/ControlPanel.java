import java.awt.event.*;
import javax.swing.*;

public class ControlPanel extends JPanel
{
    //Variable Declarations
	JButton b1, b2, b3, b4, b5,b6,b7,b8,b9,b10;
	FrameDemo container;
   	ChatPanel cp;
	DrawPanel dp;
	
	public ControlPanel(FrameDemo fd, ChatPanel cp1, DrawPanel dp1)
		{
		container = fd;
		cp=cp1;
                dp = dp1;
		b1 = new JButton("Connect");
		b2 = new JButton("Disconnect");
                b3 = new JButton("Login");
		b4 = new JButton("Clear");
		b5 = new JButton("Send Drawing");
                b6 = new JButton("Save-Chat");
		b7 = new JButton("Load-Chat");
                b8 = new JButton("Color");
                b9 = new JButton("Reset Color");
                b10 = new JButton("Draw Color");

		b1.addActionListener(new ActionListener()
				   {
						public void actionPerformed(ActionEvent ae)
				   	{
						container.connect();
					}
				   });
		b2.addActionListener(new ActionListener()
				   {
						public void actionPerformed(ActionEvent ae)
					{
						container.close();
					}
				   });
		
		b3.addActionListener(new ActionListener()
				   {
	      					public void actionPerformed(ActionEvent ae)
					{
                                            final StringMessage sm = new StringMessage();
							
						String urname;
                                             urname= JOptionPane.showInputDialog("Enter username:");
                                             if(urname!=null)
                                             {
                                             sm.setMessage(urname);
                                             container.sendMessage(sm);
                                             b3.setVisible(false);
                                             b2.setVisible(true);
                                             b4.setVisible(true);
                                             b5.setVisible(true);
                                             b6.setVisible(true);
                                             b7.setVisible(true);
                                             b8.setVisible(true);
                                             b9.setVisible(true);
                                             b10.setVisible(true);
                                             cp.setVisible(true);
                                             dp.setVisible(true);
                                             container.SP_ONLINE.setVisible(true);
                                             container.online.setVisible(true);
                                             }
					}
				   });

				
		b4.addActionListener(new ActionListener()
				   {
						public void actionPerformed(ActionEvent ae)
					{
						container.clear();
					}
				   });
		b5.addActionListener(new ActionListener()
				   {
						public void actionPerformed(ActionEvent ae)
					{
						LineMessage lm = new LineMessage();
						lm.setLineMessage(container.dp.linelist);
						container.sendMessage(lm);
                                             
					}
				   });
		b6.addActionListener(new ActionListener()
				   {
						public void actionPerformed(ActionEvent ae)
					{
						container.savechat();
					}
				   });
		b7.addActionListener(new ActionListener()
				   {
						public void actionPerformed(ActionEvent ae)
					{
                                            
						container.loadchat();
					}
				   });
                b8.addActionListener(new ActionListener()
				   {
						public void actionPerformed(ActionEvent ae)
				   	{
						container.ccolor();
					}
				   });
                b9.addActionListener(new ActionListener()
				   {
						public void actionPerformed(ActionEvent ae)
				   	{
						container.rcolor();
					}
				   });
                 b10.addActionListener(new ActionListener()
				   {
						public void actionPerformed(ActionEvent ae)
				   	{
						container.dcolor();
					}
				   });
		
		add(b1);
		add(b2);
                add(b3);
		add(b4);
		add(b5);
		add(b6);
		add(b7);
                add(b8);
                add(b9);
                add(b10);
		}
}

