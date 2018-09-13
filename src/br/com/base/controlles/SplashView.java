package br.com.base.controlles;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

public class SplashView extends JWindow implements Runnable{

	private static final long serialVersionUID = 1L;
	private Container container;
	private JPanel header;
	private JPanel footer;
	private ImageIcon imagem;
	private JLabel logo;
	private static JProgressBar progressBar;
	private static Timer timer;
	private static final int DURATION = 30,  percentMax = 100;
	private static int percentMin = 1;
     
	ActionListener actionListener = (ActionEvent arg0) -> {
          
            progressBar.setValue(percentMin);
           
            if (percentMax == percentMin) {
                timer.stop();
               
                SplashView.this.setVisible(false);
                 dispose();
                
                
            } 
           
            percentMin++;
        };

	public SplashView() throws IOException {
		createGUI();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
//		startProgressBar();
                

	}

	private void startProgressBar() {
		SplashView.timer = new Timer(SplashView.DURATION, actionListener);
		SplashView.timer.start();
	}

	private void createGUI() {
		this.container = getContentPane();
		this.header = new JPanel();
		this.footer = new JPanel();
		this.imagem = new ImageIcon(getClass().getResource("/Images/logocast.png")); 
               
		this.logo = new JLabel(imagem);
               
		SplashView.progressBar = new JProgressBar();
                
		this.header.setBorder(new EtchedBorder());
		this.header.add(logo, BorderLayout.CENTER);
              
	

		this.footer.setBorder(new EtchedBorder());
		this.footer.setLayout(new BoxLayout(footer, BoxLayout.LINE_AXIS));
		this.footer.add(progressBar);
            
		this.container.setLayout(new BorderLayout());
		this.container.add(header, BorderLayout.CENTER);
		this.container.add(footer, BorderLayout.SOUTH);
               
                
              
            
          
                
	}

    @Override
    public void run() {
       startProgressBar();
    }
        
      

}
