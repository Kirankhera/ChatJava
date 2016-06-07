import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChatPanel extends JPanel
{
    // Otherclass objects
        JTextField tf;
	JTextArea ta;
	JLabel um;
        FrameDemo container;
	
	
	public ChatPanel()
	{
	}
        
	public ChatPanel(final FrameDemo container)
	{
            // Variable Declarations
            
			this.container = container;
			setLayout(new BorderLayout());
			
			
			ta = new JTextArea();
                        ta.setEditable(false);
                        JScrollPane tasp = new JScrollPane(ta);
                        
			um = new JLabel("Your conversation will appear below:");
			um.setForeground(new java.awt.Color(0,0,255));
			um.setToolTipText("Type your message in the given field below and press enter to send");

                        tf = new JTextField();
                        tf.setToolTipText("Type your message and press enter");
			tf.addActionListener(new ActionListener()
					   {
				public void actionPerformed(ActionEvent ae)
						{
							String text = tf.getText();
							tf.setText("");
							final StringMessage sm = new StringMessage();
							sm.setMessage(text);
							container.sendMessage(sm);
                                                        tf.setToolTipText("");
						}
				
					   });
                        
			add(tf, BorderLayout.SOUTH);
			add(um, BorderLayout.NORTH);
			add(tasp, BorderLayout.CENTER);
	
	}

// Functions and Dimensions

	public void appendMessage(String m)
		{
		ta.append(m+"\n");
		}
	public Dimension getPreferredSize()
		{
		return new Dimension(450,100);
		}
	public Dimension getMinimumSize()
		{
		return new Dimension(450,100);
		}
}