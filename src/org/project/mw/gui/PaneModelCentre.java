package org.project.mw.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.logging.Logger;



import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import org.project.mw.util.Util;

/**
 * Class of centre model in the editor window default matrix (gridbag) 5x5
 * dafault size 50px
 * 
 * @author Yuri Kalinin
 *
 */
public class PaneModelCentre extends JPanel {
	private  static PaneModelCentre paneModelCentreInstanze=null;
	private MouseListener listener = new DragMouseAdapter();
	private ArrayList<JButton> labelCompArrayTemp = new ArrayList();
	private Logger log = Logger.getLogger(PaneModelCentre.class.getName());
	protected int i = 0;

	public PaneModelCentre() {
		log.info("PaneModelCentre is created");
		GridBagLayout gb = new GridBagLayout();
		setLayout(gb);
		GridBagConstraints gbc = new GridBagConstraints();
		
		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 5; col++) {
				gbc.gridx = col;
				gbc.gridy = row;

				CellPane cellPane = new CellPane(50, 50, row, col, i);// new
																		// cell
																		// with
																		// index
				Border border = null;
				if (row < 4) {
					if (col < 4) {
						border = new MatteBorder(1, 1, 0, 0, Color.GRAY);

					} else {
						border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
					}
				} else {
					if (col < 4) {
						border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
					} else {
						border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
					}
				}

				cellPane.setBorder(border);
				JButton label1 = new JButton();
				label1.setMinimumSize(new Dimension(50, 50));
				label1.setPreferredSize(new Dimension(50, 50));
				label1.setMaximumSize(new Dimension(50, 50));
				label1.addMouseListener(listener);
				label1.setTransferHandler(new TransferHandler("icon"));
				labelCompArrayTemp.add(label1);
				Element element = new Element();
				element.setPosition(row, col); // rotation is 0
				Util.getInstance().getElementsArray().add(element);
				cellPane.add(label1, gbc);
				add(cellPane, gbc);
				i++;
			}
		}

	}

	public static PaneModelCentre getPaneModelCentreInstanze(){
		if(paneModelCentreInstanze==null){
			paneModelCentreInstanze=new PaneModelCentre();
		}
		return paneModelCentreInstanze;
	}
	
	// method for the changing of size
	// x, y are the dimension of label and dridbag componets
	public void update(int scalFactor, int xDemension, int yDemension, int compIndex)  {
		log.info("PaneModelCentre is updated");
		labelCompArrayTemp = new ArrayList<JButton>();
		GridBagLayout gb = new GridBagLayout();
		setLayout(gb);
		removeAll();

		GridBagConstraints gbc = new GridBagConstraints();
		int i = 0;
		for (int row = 0; row < 5 * scalFactor; row++) {
			for (int col = 0; col < 5 * scalFactor; col++) {
				gbc.gridx = col;
				gbc.gridy = row;
				// creating new cell pane with the index
				CellPane cellPane = new CellPane(xDemension, yDemension, row, col, i);
				Border border = null;
				if (row < 4) {
					if (col < 4) {
						border = new MatteBorder(1, 1, 0, 0, Color.GRAY);

					} else {
						border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
					}
				} else {
					if (col < 4) {
						border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
					} else {
						border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
					}
				}

				cellPane.setBorder(border);
				
				JButton labelComp = new JButton();
			
					if (i == compIndex | Util.getInstance().getElementsArray().get(i).getRotation() != 0) {
						int j = i;
						labelComp = new JButton() {
							protected void paintComponent(Graphics g) {
								Graphics2D g2 = (Graphics2D) g;
								g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
								AffineTransform aT = g2.getTransform();
								Shape oldshape = g2.getClip();
								double x = getWidth() / 2.0;
							double y = getHeight() / 2.0;
							int rotation = Util.getInstance().getElementsArray().get(j).getRotation();

							if (rotation > 360) {
								System.out.println("Update->" + 0);
								aT.rotate(Math.toRadians(0), x, y);
								Util.getInstance().getElementsArray().get(compIndex).setRotation(0);
								g2.transform(aT);
								g2.setTransform(aT);
								g2.setClip(oldshape);
							} else {
								if (compIndex > -1) {
									System.out.println("Update->" + rotation + 90);
									Util.getInstance().getElementsArray().get(compIndex).setRotation(rotation + 90);
									aT.rotate(Math.toRadians(rotation + 90), x, y);// FIXME
									g2.transform(aT);
									g2.setTransform(aT);
									g2.setClip(oldshape);
								}
							}
							

						/*	g2.transform(aT);
							g2.setTransform(aT);
							g2.setClip(oldshape);*/
							
								super.paintComponent(g);

							}
						};

					}
				
				
				// scalation restart
				
					System.out.println("Rotation from array->"+Util.getInstance().getElementsArray().get(i).getRotation());	
					String icon=Util.getInstance().getElementsArray().get(i).getFileIconName();
					labelComp.setIcon(Util.getInstance().getScaledImage(icon, xDemension, yDemension));
				
			
				labelComp.setMinimumSize(new Dimension(xDemension, yDemension));
				labelComp.setPreferredSize(new Dimension(xDemension, yDemension));
				labelComp.setMaximumSize(new Dimension(xDemension, yDemension));
				labelComp.addMouseListener(listener);
				labelComp.setTransferHandler(new TransferHandler("icon"));
				
				labelCompArrayTemp.add(labelComp);
				Element element = new Element();
				element.setPosition(row, col);
				
				Util.getInstance().getElementsArray().add(element);
				cellPane.add(labelComp, gbc);
				add(cellPane, gbc);
				 i++;

			}
		}
		
	}

	public ArrayList<JButton> getLabelArray() {
		return labelCompArrayTemp;
	}

}
