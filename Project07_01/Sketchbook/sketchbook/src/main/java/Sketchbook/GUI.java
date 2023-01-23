package Sketchbook;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class GUI extends JFrame implements ActionListener{
	JMenuBar menuBar;
	JMenu fileMenu,graphicMenu, helpMenu, importSubMenu;
	JMenuItem newMenuItem, openMenuItem, textSubMenuItem, imageSubMenuItem, saveMenuItem, exitMenuItem, 
	          cutMenuItem, copyMenuItem, pasteMenuItem, selectAllMenuItem, checkboxMenuItemShow, 
	          checkboxMenuItemHide, radioButtonEngMenuItem, radioButtonDeMenuItem, colorChooserMenuItem, 
	          gisToolMenuItem, userRegistrationMenuItem, userLoginMenuItem;
	JPopupMenu popupMenu;
	JToolBar toolbar;
	JButton pointButton, lineButton, rectangleButton, selectButton, moveButton, deleteButton;
	JTextArea ta;
	JLabel imageLabel;
    private DrawingPanel drawingPanel;
    public String clickedButton = "";
    public Integer[] selectedFeatures;
    public ArrayList<DrawnShape> selectedList; //list which should save the selected features
    public ArrayList<DrawnShape> unselectedList;
    
    // Instantiate the database
    Database db = new Database();


	public GUI(){	
		
		db.createTable();
		
		selectedList = new ArrayList<>();
		
		setTitle("Sketchbook DEMO!");
		menuBar = new JMenuBar();

		ta = new JTextArea();
		ta.setBounds(10, 300, 500, 600);

		imageLabel = new JLabel("");
		imageLabel.setBounds(10, 10, 300, 200);
		imageLabel.setBackground(getForeground().DARK_GRAY);

		//File Menu 
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		newMenuItem = new JMenuItem("New", new ImageIcon(getClass().getResource("new.png")));
		newMenuItem.setMnemonic(KeyEvent.VK_N);
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newMenuItem.addActionListener(this);

		openMenuItem = new JMenuItem("Open", new ImageIcon(getClass().getResource("open.png")));
		openMenuItem.setMnemonic(KeyEvent.VK_O);
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openMenuItem.addActionListener(this);

		saveMenuItem = new JMenuItem("Save", new ImageIcon(getClass().getResource("save.png")));
		saveMenuItem.setMnemonic(KeyEvent.VK_S);
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveMenuItem.addActionListener(this);

		exitMenuItem = new JMenuItem("Exit", new ImageIcon(getClass().getResource("exit.png")));
		exitMenuItem.setMnemonic(KeyEvent.VK_E);
		exitMenuItem.setToolTipText("Exit application");
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
		exitMenuItem.addActionListener((event) -> System.exit(0));

		importSubMenu = new JMenu("Import");
		textSubMenuItem = new JMenuItem("Text");
		textSubMenuItem.addActionListener(this);
		
		imageSubMenuItem = new JMenuItem("Image");
		imageSubMenuItem.addActionListener(this);
		importSubMenu.add(textSubMenuItem);
		importSubMenu.add(imageSubMenuItem);

		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(importSubMenu);
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		//End of File Menu

		
		
		
		//Java Graphic
		graphicMenu = new JMenu("Graphic");
		graphicMenu.setMnemonic(KeyEvent.VK_G);
		//###add MenuListener
		graphicMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuCanceled(MenuEvent arg0) {
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
			}

			@Override
			public void menuSelected(MenuEvent e) {
				if(e.getSource()==graphicMenu){
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								GraphicDrawing2 line= new GraphicDrawing2();
								JFrame f= new JFrame();
								f.setTitle("Geometry Drawing Panel");
								f.setBounds(450, 190, 500, 500);
								f.getContentPane().add(line);
								f.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
	
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		//###add MenuListener
		helpMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuCanceled(MenuEvent arg0) {
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
			}

			@Override
			public void menuSelected(MenuEvent arg0) {
				JOptionPane.showMessageDialog(getContentPane(),
						"This is a complete example of Java Swing Menu, MenuItem, \n"+
				"Dialogs, Db Functionality, GeoTool Lib Use, Graphic");
			}
		});
		
		menuBar.add(fileMenu);
		menuBar.add(graphicMenu);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);
		
		//Toolbar
        toolbar = new JToolBar();
		toolbar.setBackground(new Color(250,250,250,150));
        
		pointButton = new JButton(new ImageIcon(getClass().getResource("point.png")));
		pointButton.addActionListener(this);
		
		lineButton = new JButton(new ImageIcon(getClass().getResource("line_not.png")));
		lineButton.addActionListener(this);
		
		rectangleButton = new JButton(new ImageIcon(getClass().getResource("vector.png")));
		rectangleButton.addActionListener(this);
		
		selectButton = new JButton(new ImageIcon(getClass().getResource("selection.png")));
		selectButton.addActionListener(this);

		moveButton = new JButton(new ImageIcon(getClass().getResource("move.png")));
		moveButton.addActionListener(this);

		deleteButton = new JButton(new ImageIcon(getClass().getResource("delete.png")));
		deleteButton.addActionListener(this);

		toolbar.add(pointButton);
		toolbar.add(lineButton);
		toolbar.add(rectangleButton);
		toolbar.add(selectButton); 
		toolbar.add(moveButton); 
		toolbar.add(deleteButton); 



		//End of Toolbar

		drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);


		
		
		//Popup Menu
		popupMenu = new JPopupMenu();
		
		JMenuItem maximizeMenuItem = new JMenuItem("Maximize");
		//addActionListener
		maximizeMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(getExtendedState()!= JFrame.MAXIMIZED_BOTH){
					setExtendedState(JFrame.MAXIMIZED_BOTH);
					maximizeMenuItem.setEnabled(false);
				}
			}
			
		});
		JMenuItem minimizeMenuItem = new JMenuItem("Minimize");
		//addActionListener
		minimizeMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(getExtendedState()!= JFrame.NORMAL){
					setExtendedState(JFrame.NORMAL);
					minimizeMenuItem.setEnabled(false);
				}
			}
			
		});

		JMenuItem quitMenuItem = new JMenuItem("Quit");
		quitMenuItem.addActionListener((e) -> System.exit(0));

		popupMenu.add(maximizeMenuItem);
		popupMenu.add(minimizeMenuItem);
		popupMenu.add(quitMenuItem);
		
		//Add MouseListener with MouseAdapter to open the popup menu
		//by right mouse click
		addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if(getExtendedState()!= JFrame.MAXIMIZED_BOTH){
					maximizeMenuItem.setEnabled(true);
					minimizeMenuItem.setEnabled(false);
				}else if(getExtendedState()!= JFrame.NORMAL){
					minimizeMenuItem.setEnabled(true);
					maximizeMenuItem.setEnabled(false);	
				}
				
				if(e.getButton()== MouseEvent.BUTTON3){
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			
		});
		

		//End of Popup Menu
		
		add(toolbar, BorderLayout.NORTH);
		add(imageLabel, BorderLayout.CENTER);
		add(ta, BorderLayout.SOUTH);
		add(drawingPanel, BorderLayout.CENTER);
		setSize(600, 600);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==newMenuItem ){
			ta.setText("Add the new content here!");
		}
		
		if(e.getSource()==openMenuItem ||e.getSource()==textSubMenuItem){
			JFileChooser fc = new JFileChooser();
			fc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter fileExtension = new FileNameExtensionFilter("text file", "txt");
			fc.addChoosableFileFilter(fileExtension);
			int i = fc.showOpenDialog(this);
			if(i==JFileChooser.APPROVE_OPTION){
				File f = fc.getSelectedFile();
				String path = f.getPath();
				try {
					BufferedReader reader = new BufferedReader(new FileReader(path));
					String s1 = "", s2="";
					while((s1=reader.readLine())!=null){
					
						s2+= s1+"\n";
					}
					ta.setText(s2);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		
		if (e.getSource() == imageSubMenuItem) {
			JFileChooser fc = new JFileChooser();
			fc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter extFilter = new FileNameExtensionFilter("*.Images", "jpg", "jpeg", "gif", "png");
			fc.addChoosableFileFilter(extFilter);
			int i = fc.showOpenDialog(this);
			if (i == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				String path = file.getAbsolutePath();
				ImageIcon iconPath = new ImageIcon(path);
				Image image = iconPath.getImage();
				Image resizedImg = image.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(),
						image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(resizedImg);
				imageLabel.setIcon(icon);
				add(imageLabel, BorderLayout.CENTER);
			}
		}
		
		if(e.getSource() == saveMenuItem ) {
			JFileChooser fc = new JFileChooser();
			fc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter extFilter = new FileNameExtensionFilter("CSV files", "csv");
			fc.addChoosableFileFilter(extFilter);
			int i = fc.showSaveDialog(this);
			if (i == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				System.out.println("Saving at " + f.getAbsolutePath());
				CSVOperations.exportCSV(f, db.getAllFromDB());
			}
		}
		
		if(e.getSource()==cutMenuItem){
			ta.cut();
		}
		if(e.getSource()==copyMenuItem){	
			ta.copy();
		}
		if(e.getSource()==pasteMenuItem){	
			ta.paste();
		}
		if(e.getSource()==selectAllMenuItem){	
			ta.selectAll();
		}
		
		if(e.getSource()==radioButtonDeMenuItem){	
			if(ta!= null){
				String text = ta.getText();
				String transText = "Hallo";
				ta.setText(transText);	
			}	
		}
		
		if(e.getSource()==pointButton) {
			clickedButton = "point";
		}
		if(e.getSource()==lineButton) {
			clickedButton = "line";
		}
		if(e.getSource()==rectangleButton) {
			clickedButton = "rectangle";
		}
		
		if(e.getSource()==selectButton) {
			clickedButton = "select";
		}
		
		if(e.getSource()==moveButton) {
			clickedButton = "move";
		}
		
		if(e.getSource()==deleteButton) {
			clickedButton = "delete";
			selectedList.clear();
		}
		
	}
	
	public class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener {

	    private ArrayList<Dot> pointsList;
	    private ArrayList<Line> lineList; //i want to make this for lines
	    private ArrayList<Rect> rectList; //note there is an existing java calss Rectangle, but I'm making my own because of the parameters i want
	    private ArrayList<DrawnShape> shapeList; //i'm thinking of making an additional super class
	    
	    private boolean drawing;
	    
	    //initializaing variables for use in drawing stuff
	    int selectX1, selectX2, selectY1, selectY2, selectClickX, selectClickY, selectRelX, selectRelY;
	    int moveX, moveY, moveDistX, moveDistY;
	    //points stuff
	    int px, py;
	    int xp, yp;
	    //rectangles stuff
	    int recX1, recX2, recY1, recY2;
	    int widthRec, heightRec;
	    int rx1, ry1, rw, rh;
	    //line stuff
	    int lineX1, lineX2, lineY1, lineY2;
	    int x1, x2, y1, y2;
	    double slope;
	    
	    public DrawingPanel() {
	        pointsList = new ArrayList<>();
	        lineList = new ArrayList<>();
	        rectList = new ArrayList<>();
	        shapeList = new ArrayList<>();
//	        selectedList = new ArrayList<>();
	        unselectedList = new ArrayList<>();
	        addMouseListener(this);
	        addMouseMotionListener(this);
	    }

	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	    

//	        	// This for block draws the points every time they get changed
//	        	for (int i = 0; i < pointsList.size(); i++) {
//					//draw a point
//					Point p = pointsList.get(i);
//					g.fillOval(p.x, p.y, 5, 5);
//					//System.out.println(p);
//				}
	        	
	        	if(clickedButton.equals("point")) {
	        		Dot d = new Dot(px, py);
	        		pointsList.add(d);
	        		shapeList.add(d);
	        		db.insertShapes(d.geometry(), "'Point'");
	        		
	        		
	        	}
	        	
	        	// TODO: Make it so that the rectangle is transparent no matter what direction it is drawn
	        	if(clickedButton.equals("select")) {
	        		
	        		if(selectClickX < selectRelX) {
	        			selectX1 = selectClickX;
	        			selectX2 = selectRelX;
	        		} else {
	        			selectX2 = selectClickX;
	        			selectX1 = selectRelX;
	        		}
	        		if(selectClickY < selectRelY) {
	        			selectY1 = selectClickY;
	        			selectY2 = selectRelY;
	        		} else {
	        			selectY2 = selectClickY;
	        			selectY1 = selectRelY;
	        		}
	        		
	        		int widthSelect = selectX2 - selectX1;
	        		int heightSelect = selectY2 - selectY1;
	        		g.setColor(new Color(77, 158, 220, 50));
	        		g.fillRect(selectX1, selectY1, widthSelect, heightSelect);
	        		g.setColor(new Color(77, 158, 220));
	        		g.drawRect(selectX1, selectY1, widthSelect, heightSelect);
	        		
	        		//current issue: when deleting we have to delete the object also from shapeList --> all shapes are redrawn when we select something
	        		
	        		Iterator<DrawnShape> itr = shapeList.iterator();
	        		while(itr.hasNext()) {
	        			DrawnShape shape = itr.next();
	        			if(shape.type == "Point") {
	        				Dot dotSelect = (Dot) shape;
	        				if(dotSelect.px <= selectX2 && dotSelect.px >= selectX1 && dotSelect.py <= selectY2 && dotSelect.py >= selectY1) {
	        					selectedList.add(shape);
	        					itr.remove();
	            			} else {
	            				unselectedList.add(shape);
	            			}
	        			} else if (shape.type == "Line") {
	        				Line lineSelect = (Line) shape;
	        				if(lineSelect.x1 <= selectX2 && lineSelect.x1 >= selectX1 && lineSelect.x2 <= selectX2 && lineSelect.x2 >= selectX1 && lineSelect.y1 <= selectY2 && lineSelect.y1 >= selectY1 && lineSelect.y2 <= selectY2 && lineSelect.y2 >= selectY1) {
	        					selectedList.add(shape);
	        					itr.remove();
	        				}else {
	            				unselectedList.add(shape);
	            			}
	        			} else if (shape.type == "Rectangle") {
	        				Rect rectSelect = (Rect) shape;
	        				if(rectSelect.recX1 <= selectX2 && rectSelect.recX1 >= selectX1 && rectSelect.recX2 <= selectX2 && rectSelect.recX2 >= selectX1 && rectSelect.recY1 <= selectY2 && rectSelect.recY1 >= selectY1 && rectSelect.recY2 <= selectY2 && rectSelect.recY2 >= selectY1) {
	        					selectedList.add(shape);
	        					//shapeList.remove(s);
	        					System.out.println(rectSelect);
	        				}else {
	            				unselectedList.add(shape);
	            			}
	        			}
	        		}
	        		
//	        		for(DrawnShape s : shapeList) {
//	        			if(s.type == "Point") {
//	        				Dot dotSelect = (Dot) s;
//	        				if(dotSelect.px <= selectX2 && dotSelect.px >= selectX1 && dotSelect.py <= selectY2 && dotSelect.py >= selectY1) {
//	        					selectedList.add(s);
////	        					int index = shapeList.indexOf(s);
//	        					//delete object from shapeList
//	        					System.out.println(dotSelect);
//	            			} else {
//	            				unselectedList.add(s);
//	            			}
//	        			} else if(s.type == "Line") {
//	        				Line lineSelect = (Line) s;
//	        				if(lineSelect.x1 <= selectX2 && lineSelect.x1 >= selectX1 && lineSelect.x2 <= selectX2 && lineSelect.x2 >= selectX1 && lineSelect.y1 <= selectY2 && lineSelect.y1 >= selectY1 && lineSelect.y2 <= selectY2 && lineSelect.y2 >= selectY1) {
//	        					selectedList.add(s);
//	        					//shapeList.remove(s);
//	        					System.out.println(lineSelect);
//	        				}else {
//	            				unselectedList.add(s);
//	            			}
//	        			} else if(s.type == "Rectangle") {
//	        				Rect rectSelect = (Rect) s;
//	        				if(rectSelect.recX1 <= selectX2 && rectSelect.recX1 >= selectX1 && rectSelect.recX2 <= selectX2 && rectSelect.recX2 >= selectX1 && rectSelect.recY1 <= selectY2 && rectSelect.recY1 >= selectY1 && rectSelect.recY2 <= selectY2 && rectSelect.recY2 >= selectY1) {
//	        					selectedList.add(s);
//	        					//shapeList.remove(s);
//	        					System.out.println(rectSelect);
//	        				}else {
//	            				unselectedList.add(s);
//	            			}
//	        			}
//	        		}


	        	}

	        	// Write all user drawn lines to an Array List to be drawn later in code
	        	if (clickedButton.equals("line")) {
//	        		 g.setColor(Color.BLACK);
	        		 Line lin = new Line(lineX1, lineY1, lineX2, lineY2); //takes user input and adds it to "lin" of class Line
	        		 lineList.add(lin); //append new lin to ArrayList "lineList"
	        		 shapeList.add(lin);
	        		 // Send new line to the Database
	        		 db.insertShapes(lin.geometry(), "'Line'");
	        		 
	        
	        	}
            
	        	// Write all user drawn rectangles to an Array List to be drawn later in code
	        	if(clickedButton.equals("rectangle")) {
	        		g.setColor(Color.BLACK);
	        		Rect rec = new Rect(recX1, recY1 , recX2, recY2); 
	        		rectList.add(rec);//append new rec to list
	        		shapeList.add(rec);

	        		g.setColor(Color.BLACK); //not sure if there was a purpose to this being here twice... this was not me
//	        		g.drawRect(recX1, recY1, widthRec, heightRec);
	        		// Send new rectangle to the Database
	        		db.insertShapes(rec.geometry(), "'Rectangle'");
	        		
	        		
	        	}
	    
			
	        	g.setColor(Color.BLACK);
	        	
	        	//this implementation has been replaced by the DrawnShape version below - CAN BE DELETED NOW
	        	//the following two for loops need to be outside of the clickedButton areas so that they remain drawn
	        	//iterate through list of lines and draw all the user drawn lines
//	        	for (int i=0; i < lineList.size(); i++){
//	        		g.setColor(Color.black);
//	       			 Line lin2 = lineList.get(i); //loads next item from list into lin2 so that we can draw this out
//	       			 x1 = lin2.x1(); //these just retrieve the x and y elements of the coordanites to use in drawLine below
//	       			 x2 = lin2.x2();
//	       			 y1 = lin2.y1();
//	       			 y2 = lin2.y2();
//	       			 
//	       			 g.drawLine(x1, x2, y1, y2); //draw the line
//                }
//	        	
//	        	//iterate through list of rectanles and draw all the user drawn rectangles
//	        	for (int i=0; i < rectList.size(); i++) {
//        			 Rect rec2 = rectList.get(i); //loads next item from list into rec2 so that we can draw this out
//        			 rx1 = rec2.recX1(); //brings in required drawing parameters from Rect methods for the loaded rectangle rec2
//        			 ry1 = rec2.recY1();
//        			 rw = rec2.widthRec();
//        			 rh = rec2.heightRec();
//
//        			 
// 	        		 g.drawRect(rx1, ry1, rw, rh);
//                }
//	        	
//	        	for (int i=0; i < pointsList.size(); i++) {
//	        		Dot d2 = pointsList.get(i);
//	        		xp = d2.px();
//	        		yp = d2.py();
//	        		
//	        		g.fillOval(xp, yp, 5, 5);
//	        		
//	        	}
	        	
	        	//NEW DRAWING IMPLEMENTATION USING DrawnShape CLASS
	        	
	        	if(selectedList.size() == 0) {
	        		for (DrawnShape shp : shapeList) {
		        		
		        		//points
		        		if (shp.type == "Point") {
		        			Dot d3 = (Dot) shp;
		        			
		        			xp = d3.px();
			        		yp = d3.py();
			        		
			        		g.fillOval(xp, yp, 5, 5);
		        		}
		        		
		        		//lines
		        		if (shp.type == "Line") {
		        			Line lin3 = (Line) shp; //loads next item from list into lin2 so that we can draw this out
			       			
		        			x1 = lin3.x1(); //these just retrieve the x and y elements of the coordanites to use in drawLine below
			       			x2 = lin3.x2();
			       			y1 = lin3.y1();
			       			y2 = lin3.y2();
			       			 
			       			g.drawLine(x1, y1, x2, y2); //draw the line
		        		}
		        		
		        		//rectangles
		        		if (shp.type == "Rectangle") {
		        			Rect rec3 = (Rect) shp; //loads next item from list into rec2 so that we can draw this out
		        			
		        			rx1 = rec3.recX1(); //brings in required drawing parameters from Rect methods for the loaded rectangle rec2
		        			ry1 = rec3.recY1();
		        			rw = rec3.widthRec();
		        			rh = rec3.heightRec();
	 
		 	        		g.drawRect(rx1, ry1, rw, rh);
		        		}	
		        	}	
	        	} else {
	        		for (DrawnShape shp : unselectedList) {
		        		
		        		//points
		        		if (shp.type == "Point") {
		        			Dot d3 = (Dot) shp;
		        			
		        			xp = d3.px();
			        		yp = d3.py();
			        		
			        		g.fillOval(xp, yp, 5, 5);
		        		}
		        		
		        		//lines
		        		if (shp.type == "Line") {
		        			Line lin3 = (Line) shp; //loads next item from list into lin2 so that we can draw this out
			       			
		        			x1 = lin3.x1(); //these just retrieve the x and y elements of the coordanites to use in drawLine below
			       			x2 = lin3.x2();
			       			y1 = lin3.y1();
			       			y2 = lin3.y2();
			       			 
			       			g.drawLine(x1, y1, x2, y2); //draw the line
		        		}
		        		
		        		//rectangles
		        		if (shp.type == "Rectangle") {
		        			Rect rec3 = (Rect) shp; //loads next item from list into rec2 so that we can draw this out
		        			
		        			rx1 = rec3.recX1(); //brings in required drawing parameters from Rect methods for the loaded rectangle rec2
		        			ry1 = rec3.recY1();
		        			rw = rec3.widthRec();
		        			rh = rec3.heightRec();
	 
		 	        		g.drawRect(rx1, ry1, rw, rh);
		        		}
		        		
		        	}
		        	
		        	g.setColor(new Color(77, 158, 220));
		        	//draw selectedList
		        	for (DrawnShape shp : selectedList) {
		        		
		        		//points
		        		if (shp.type == "Point") {
		        			Dot d3 = (Dot) shp;
		        			
		        			xp = d3.px();
			        		yp = d3.py();
			        		
			        		g.fillOval(xp, yp, 5, 5);
		        		}
		        		
		        		//lines
		        		if (shp.type == "Line") {
		        			Line lin3 = (Line) shp; //loads next item from list into lin2 so that we can draw this out
			       			
		        			x1 = lin3.x1(); //these just retrieve the x and y elements of the coordanites to use in drawLine below
			       			x2 = lin3.x2();
			       			y1 = lin3.y1();
			       			y2 = lin3.y2();
			       			 
			       			g.drawLine(x1, y1, x2, y2); //draw the line
		        		}
		        		
		        		//rectangles
		        		if (shp.type == "Rectangle") {
		        			Rect rec3 = (Rect) shp; //loads next item from list into rec2 so that we can draw this out
		        			
		        			rx1 = rec3.recX1(); //brings in required drawing parameters from Rect methods for the loaded rectangle rec2
		        			ry1 = rec3.recY1();
		        			rw = rec3.widthRec();
		        			rh = rec3.heightRec();
	 
		 	        		g.drawRect(rx1, ry1, rw, rh);
		        		}
		        		
		        	}
	        		
	        	}
	        	
	        	

	    }

	    // MouseListener methods
	    public void mousePressed(MouseEvent e) {

	    	
	    	if(clickedButton.equals("point")) {
	    		//add selectedList to shapeList
	    		for(DrawnShape s : selectedList) {
	    			shapeList.add(s);
	    		}
	    		//empty selectedList
	    		selectedList.clear();
//	    		points.add(e.getPoint());
//	    		db.insertShapes(e.getPoint().toString(), "'Point'");
		        drawing = true;
		        
		        px = e.getX();
	    		py = e.getY();
	    	}
	    	if(clickedButton.equals("select")) {
	    		//add selectedList to shapeList
	    		for(DrawnShape s : selectedList) {
	    			shapeList.add(s);
	    		}
	    		//empty selectedList
	    		selectedList.clear();
	    		selectClickX = e.getX();
	    		selectClickY = e.getY();
	    	}
	    	if(clickedButton.equals("rectangle")) {
	    		//add selectedList to shapeList
	    		for(DrawnShape s : selectedList) {
	    			shapeList.add(s);
	    		}
	    		//empty selectedList
	    		selectedList.clear();
	    		recX1 = e.getX();
	    		recY1 = e.getY();
	    	}
	        
	    	if(clickedButton.equals("line")) {
	    		//add selectedList to shapeList
	    		for(DrawnShape s : selectedList) {
	    			shapeList.add(s);
	    		}
	    		//empty selectedList
	    		selectedList.clear();
	    		lineX1 = e.getX();
	    		lineY1 = e.getY();
	    	}
	    	
	    	if(clickedButton.equals("move")) {
	    		moveX = e.getX();
	    		moveY = e.getY();
	    	}
	        
	    }

	    public void mouseReleased(MouseEvent e) {
	    	if(clickedButton.equals("select")) {
	    		selectRelX = e.getX();
	    		selectRelY = e.getY();
	    	}
	        drawing = false;
	        repaint();

	    	if(clickedButton.equals("rectangle")) {
	    		recX2 = e.getX();
	    		recY2 = e.getY();
	    	}
	        drawing = false;
	        repaint();

	    	if(clickedButton.equals("line")) {
	    		lineX2 = e.getX();
	    		lineY2 = e.getY();
	    	}
	        drawing = false;
	        repaint();
	        
	        if(clickedButton.equals("move")) {
	    		moveDistX = e.getX() - moveX;
	    		moveDistY = e.getY() - moveY;
	    		
	    		for(DrawnShape p : selectedList) {
	    			if(p.type == "Point") {
	    				Dot moveDot = (Dot) p;
	    				moveDot.px += moveDistX;
	    				moveDot.py += moveDistY;
	    			} else if (p.type == "Line") {
	    				Line moveLine = (Line) p;
	    				moveLine.x1 += moveDistX;
	    				moveLine.x2 += moveDistX;
	    				moveLine.y1 += moveDistY;
	    				moveLine.y2 += moveDistY;
	    			} else if (p.type == "Rectangle") {
	    				Rect moveRect = (Rect) p;
	    				moveRect.recX1 += moveDistX;
	    				moveRect.recX2 += moveDistX;
	    				moveRect.recY1 += moveDistY;
	    				moveRect.recY2 += moveDistY;
	    			}
	    		}
	    	}
	        drawing = false;
	        repaint();
	    }


	    
	    // Other MouseListener methods
	    public void mouseClicked(MouseEvent e) {
	        if (e.getClickCount() == 2) {
	            drawing = false;
	            repaint();
	        }
	    }
	    
	    
	    // Other MouseListener methods
	    public void mouseEntered(MouseEvent e) {}
	    public void mouseExited(MouseEvent e) {}

	    // MouseMotionListener methods
		
	    public void mouseDragged(MouseEvent e) {
	    	//points.add(e.getPoint());
	    	//repaint();
		}
		 

	    public void mouseMoved(MouseEvent e) {}
	}
	
}