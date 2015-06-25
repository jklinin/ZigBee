package org.project.mw.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import java.util.Map;

import org.project.mw.util.Util;

/**
 * Class of centre model in the editor window default matrix (gridbag) 5x5
 * dafault size 50px
 * 
 * @author Yuri Kalinin
 *
 */
public class PaneModelCentre implements ActionListener  {
	   GridBagLayout gb ;
	    GridBagConstraints gc;
	    Map <Point,JButton> map ;
	    final int SIZE = 64;
	    private JPanel container;
	   
	   PaneModelCentre(){
	    	System.out.println("Created PanelModelCentre");
	        gb = new GridBagLayout();
	        gc = new GridBagConstraints();
	        gc.fill = GridBagConstraints.BOTH;
	        map = new LinkedHashMap<Point,JButton>();
	      //  Container container = getContentPane();
	      container=new JPanel();
	        container.setLayout(gb);
	        int x =0 , y = -1 ;
	       // ArrayList<JButton> arrayList= new ArrayList
	        JButton[] button = new JButton[SIZE];
	        for (int i = 0 ; i < SIZE ; i++ )
	        {
	            button[i] = new JButton(String.valueOf(i + 1));
	            if (i % 4 == 0)
	            {
	                x = 0 ;
	                y = y +1;
	            }
	            gc.gridx = x++;
	            gc.gridy = y;
	            button[i].setMinimumSize(new Dimension(100,100));
	            button[i].setPreferredSize(new Dimension(100,100));
	            gb.setConstraints(button[i],gc);
	            container.add(button[i]);
	            map.put(new Point(x,y),button[i]);
	            button[i].setActionCommand(x+","+y);
	            button[i].addActionListener(this);
	        }
	      
	    }
	    @Override
	    public void actionPerformed(ActionEvent evt)
	    {
	     //   resetAll();
	        JButton source = (JButton)evt.getSource();
	        String command = source.getActionCommand();
	        System.out.println(command);
	        String[] arr = command.split(",");
	        int x = Integer.parseInt(arr[0]);
	        int y = Integer.parseInt(arr[1]);
	        JButton button = map.get(new Point(x,y));
	      //  for ( int iy = y - 1; iy <= y + 1; iy++)
	       // {
	          //  for (int ix = x -1 ; ix <= x + 1 ; ix++)
	          //  {
	               // JButton button = map.get(new Point(ix,iy));
	              
	                if (button != null)
	                {
	                	  button.setBackground(Color.WHITE);
	                   // button.setForeground(Color.red);
	                }
	          //  }
	      //  }
	        source.setForeground(Color.blue);
	    }
	 
	    public JPanel getContainer(){
	    	return container;
	    }
}
