package maldondo.Danny_Threads;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Animator extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton start, stop;
	public static Thread workerThread;
	JPanel ballPanel;
	Ball ball;
	boolean animate, stopped, restart, starter;
	Timer timer;
	int speed = 100;

	@SuppressWarnings("serial")
	public Animator()
	{
		super("Animation");
		start = new JButton("Start");
		stop = new JButton("Stop");
		Box buttons = new Box(BoxLayout.X_AXIS);
		buttons.add(start);
		buttons.add(stop);
		getContentPane().add(buttons, BorderLayout.NORTH);
		ballPanel = new JPanel()
		{
			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				ball.paint(g);
			}
		};
		ball = new Ball(ballPanel);
		getContentPane().add(ballPanel);

		start.addActionListener(new StartListener());
		stop.addActionListener(new StopListener());
	}

	private class StartListener implements ActionListener
	{
		@SuppressWarnings("static-access")
		@Override
		public void actionPerformed(ActionEvent e)
		{
			timer = new Timer(speed, this);
			timer.start();
			if(stopped)
			{
				animate = false;
			}
			else
			{
				animate = true;
			}
			
			if(animate)
			{
				ball.move();
				repaint();
				try
				{
					workerThread.sleep(100);
				} 
				catch (InterruptedException ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}

	private class StopListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			animate = false;
			stopped = true;
			timer.stop();
			ball.stop();
			repaint();
		}
	}

	public static void main(String[] args)
	{
		JFrame f = new Animator();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(300, 300, 300, 200);
		Animator.workerThread = new Thread();
		workerThread.start();
		f.setVisible(true);
	}	
}