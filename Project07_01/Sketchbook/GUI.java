package Sketchbook;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class GUI extends JFrame implements ActionListener{
	JMenuBar menuBar;
	JMenu fileMenu,graphicMenu, helpMenu, importSubMenu, dataMenu;
	JMenuItem newMenuItem, openMenuItem, textSubMenuItem, imageSubMenuItem, saveMenuItem, exitMenuItem, 
	          cutMenuItem, copyMenuItem, pasteMenuItem, selectAllMenuItem, checkboxMenuItemShow, 
	          checkboxMenuItemHide, radioButtonEngMenuItem, radioButtonDeMenuItem, colorChooserMenuItem, 
	          gisToolMenuItem, userRegistrationMenuItem, userLoginMenuItem, importShpMenuItem, save2DbMenuItem, loadFromDbMenuItem, importCsvMenuItem, exportCsvMenuItem;
	JPopupMenu popupMenu;
	JToolBar toolbar;
	JButton pointButton, lineButton, rectangleButton, selectButton, moveButton, deleteButton, slelectRectButton, slelectPointButton, slelectLinesButton;
	JTextArea ta, taL,taR;
	JLabel imageLabel;
    private DrawingPanel drawingPanel;
    public String clickedButton = "";
    public Integer[] selectedFeatures;
        
    // Instantiate the database
    Database db = new Database();


	public GUI(){	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	
		db.createTable();
		
		Color toolbarColor = new Color(34, 125, 179);
		Color menuColor = new Color(34, 87, 179);
        
		setTitle("Sketchbook DEMO!");
		menuBar = new JMenuBar();
		menuBar.setBackground(menuColor);
		
		ta = new JTextArea();
		ta.setBackground(menuColor);

		taL = new JTextArea();
		taL.setBackground(menuColor);
		
		taR = new JTextArea();
		taR.setBackground(menuColor);

//
//		imageLabel = new JLabel("");
//		imageLabel.setBounds(10, 10, 300, 200);
//		imageLabel.setBackground(getForeground().DARK_GRAY);
//
		//File Menu 
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.setForeground(Color.WHITE);


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

		
		//Data Menu 
		dataMenu = new JMenu("Data");
		dataMenu.setMnemonic(KeyEvent.VK_F);
		dataMenu.setForeground(Color.WHITE);


		
		
		
		importShpMenuItem = new JMenuItem("Import Shapefile", new ImageIcon(getClass().getResource("new.png")));
		importShpMenuItem.setToolTipText("Import Data as Shapefile");
		importShpMenuItem.addActionListener(this);

		save2DbMenuItem = new JMenuItem("Save to Database", new ImageIcon(getClass().getResource("open.png")));
		save2DbMenuItem.setToolTipText("Save Selection to Database. If no Seletion, Saves all Data");
		save2DbMenuItem.addActionListener(this);

		loadFromDbMenuItem = new JMenuItem("Load from Database", new ImageIcon(getClass().getResource("save.png")));
		loadFromDbMenuItem.setToolTipText("Load from Database");
		loadFromDbMenuItem.addActionListener(this);

		importCsvMenuItem = new JMenuItem("Import CSV", new ImageIcon(getClass().getResource("exit.png")));
		importCsvMenuItem.setToolTipText("Import Data from CSV File");
		importCsvMenuItem.addActionListener((event) -> System.exit(0));

		exportCsvMenuItem = new JMenuItem("Export CSV", new ImageIcon(getClass().getResource("exit.png")));
		exportCsvMenuItem.setToolTipText("Export Selection as CSV. If no Seletion, Saves all Data");
		exportCsvMenuItem.addActionListener((event) -> System.exit(0));

		dataMenu.add(importShpMenuItem);
		dataMenu.add(loadFromDbMenuItem);
		dataMenu.add(save2DbMenuItem);
		dataMenu.add(importCsvMenuItem);
		dataMenu.add(exportCsvMenuItem);
		//End of Data Menu

		
		
//		
//		//Java Graphic
//		graphicMenu = new JMenu("Graphic");
//		graphicMenu.setMnemonic(KeyEvent.VK_G);
//		//###add MenuListener
//		graphicMenu.addMenuListener(new MenuListener() {
//			@Override
//			public void menuCanceled(MenuEvent arg0) {
//			}
//
//			@Override
//			public void menuDeselected(MenuEvent arg0) {
//			}
//
//			@Override
//			public void menuSelected(MenuEvent e) {
//				if(e.getSource()==graphicMenu){
//					EventQueue.invokeLater(new Runnable() {
//						public void run() {
//							try {
//								GraphicDrawing2 line= new GraphicDrawing2();
//								JFrame f= new JFrame();
//								f.setTitle("Geometry Drawing Panel");
//								f.setBounds(450, 190, 500, 500);
//								f.getContentPane().add(line);
//								f.setVisible(true);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//					});
//				}
//			}
//		});
	
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.setForeground(Color.WHITE);

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
		menuBar.add(dataMenu);
//		menuBar.add(graphicMenu);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);
		
		
		//Toolbar
		toolbar = new JToolBar();
		toolbar.setLocation(10, 5);
		toolbar.setMargin(new Insets(3, 10, 5, 10));
		toolbar.setFloatable(true);
		


		
		
		//#######################################################Unsure about this

		
		lineButton = new JButton(new ImageIcon(getClass().getResource("line_not.png")));
		lineButton.setText("Line");
		lineButton.setToolTipText("Draw Pines");
		lineButton.setVerticalTextPosition(AbstractButton.CENTER);
		lineButton.setHorizontalTextPosition(AbstractButton.TRAILING); 
		lineButton.addActionListener(this);
		lineButton.setBorder(new RoundedBorder(35));
        lineButton.setForeground(Color.BLACK);
        lineButton.setContentAreaFilled(false);
		
		
		
		pointButton = new JButton(new ImageIcon(getClass().getResource("point.png")));
		pointButton.setText("Point");
		//pointButton.setFocusPainted(false);
		pointButton.setToolTipText("Draw Points");
		pointButton.setVerticalTextPosition(AbstractButton.CENTER);
	    pointButton.setHorizontalTextPosition(AbstractButton.TRAILING); 
	    pointButton.addActionListener(this);
		pointButton.setBorder(new RoundedBorder(35));
        pointButton.setForeground(Color.BLACK);
        pointButton.setContentAreaFilled(false);
	
		
//		lineButton = new JButton(new ImageIcon(getClass().getResource("line_not.png")));
//		lineButton.setText("Line");
//		lineButton.setToolTipText("Draw Pines");
//		lineButton.setVerticalTextPosition(AbstractButton.CENTER);
//		lineButton.setHorizontalTextPosition(AbstractButton.TRAILING); 
//		lineButton.addActionListener(this);
		
		rectangleButton = new JButton(new ImageIcon(getClass().getResource("vector.png")));
		rectangleButton.setText("Rectangle");
		rectangleButton.setToolTipText("Draw Rectangles");
		rectangleButton.setVerticalTextPosition(AbstractButton.CENTER);
		rectangleButton.setHorizontalTextPosition(AbstractButton.TRAILING); 
		rectangleButton.addActionListener(this);
		rectangleButton.setBorder(new RoundedBorder(35));
        rectangleButton.setForeground(Color.BLACK);
        rectangleButton.setContentAreaFilled(false);
	
		
			
		selectButton = new JButton(new ImageIcon(getClass().getResource("selection.png")));
		selectButton.setText("Select All");
		selectButton.setToolTipText("Select Items");
		selectButton.setVerticalTextPosition(AbstractButton.CENTER);
		selectButton.setHorizontalTextPosition(AbstractButton.TRAILING); 
		selectButton.addActionListener(this);
		selectButton.setBorder(new RoundedBorder(35));
        selectButton.setForeground(Color.BLACK);
        selectButton.setContentAreaFilled(false);
	

		moveButton = new JButton(new ImageIcon(getClass().getResource("move.png")));
		moveButton.setText("Move");
		moveButton.setToolTipText("Move Objects");
		moveButton.setVerticalTextPosition(AbstractButton.CENTER);
		moveButton.setHorizontalTextPosition(AbstractButton.TRAILING); 
		moveButton.addActionListener(this);
		moveButton.setBorder(new RoundedBorder(35));
        moveButton.setForeground(Color.BLACK);
        moveButton.setContentAreaFilled(false);
	

		deleteButton = new JButton(new ImageIcon(getClass().getResource("delete.png")));
		deleteButton.setToolTipText("Delete Objects");
		deleteButton.setText("Delete");
		deleteButton.setVerticalTextPosition(AbstractButton.CENTER);
		deleteButton.setHorizontalTextPosition(AbstractButton.TRAILING); 
		deleteButton.addActionListener(this);
		deleteButton.setBorder(new RoundedBorder(35));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setContentAreaFilled(false);
		
		
        slelectLinesButton = new JButton(new ImageIcon(getClass().getResource("delete.png")));
        slelectLinesButton.setToolTipText("Select all Lines");
        slelectLinesButton.setText("Select Lines");
        slelectLinesButton.setVerticalTextPosition(AbstractButton.CENTER);
        slelectLinesButton.setHorizontalTextPosition(AbstractButton.TRAILING); 
        slelectLinesButton.addActionListener(this);
        slelectLinesButton.setBorder(new RoundedBorder(35));
        slelectLinesButton.setForeground(Color.BLACK);
        slelectLinesButton.setContentAreaFilled(false);
		
		
        slelectRectButton = new JButton(new ImageIcon(getClass().getResource("delete.png")));
        slelectRectButton.setToolTipText("Select all Rectangles");
        slelectRectButton.setText("Select Rectangles");
        slelectRectButton.setVerticalTextPosition(AbstractButton.CENTER);
        slelectRectButton.setHorizontalTextPosition(AbstractButton.TRAILING); 
        slelectRectButton.addActionListener(this);
        slelectRectButton.setBorder(new RoundedBorder(35));
        slelectRectButton.setForeground(Color.BLACK);
        slelectRectButton.setContentAreaFilled(false);
		
        slelectPointButton = new JButton(new ImageIcon(getClass().getResource("delete.png")));
		slelectPointButton.setToolTipText("Select all Points");
		slelectPointButton.setText("Selcet Points");
		slelectPointButton.setVerticalTextPosition(AbstractButton.CENTER);
		slelectPointButton.setHorizontalTextPosition(AbstractButton.TRAILING); 
		slelectPointButton.addActionListener(this);
		slelectPointButton.setBorder(new RoundedBorder(35));
		slelectPointButton.setForeground(Color.BLACK);
        slelectPointButton.setContentAreaFilled(false);
		

		 
      GridLayout gridLay = new GridLayout(1,13);
		toolbar.setLayout(gridLay);
		toolbar.add(pointButton);
		toolbar.add(lineButton);
		toolbar.add(rectangleButton);
		toolbar.add(Box.createRigidArea(new Dimension(4, 0)));
		toolbar.add(selectButton); 
		toolbar.add(slelectPointButton); 
		toolbar.add(slelectRectButton); 
		toolbar.add(slelectLinesButton); 
		toolbar.add(Box.createRigidArea(new Dimension(4, 0)));
		toolbar.add(Box.createRigidArea(new Dimension(4, 0)));
		toolbar.add(moveButton); 
		toolbar.add(deleteButton); 
		

		//End of Toolbar

		drawingPanel = new DrawingPanel();
		drawingPanel.setBackground(new Color(190,190,190));
        drawingPanel.setBorder(new LineBorder(menuColor, 15, false));

		
        //drawingPanel.add(taL, BorderLayout.EAST);
        //drawingPanel.add(taR, BorderLayout.WEST);
		
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
		
		add(drawingPanel, BorderLayout.CENTER);
		add(toolbar, BorderLayout.NORTH);
		//add(imageLabel, BorderLayout.CENTER);
		add(ta, BorderLayout.SOUTH);
		setSize(600, 600);
		setVisible(true);
	}

	private void setBorder(Border createEmptyBorder) {
		// TODO Auto-generated method stub
		
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
		
	}
	
	public class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener {

	    private ArrayList<Dot> pointsList;
	    private ArrayList<Line> lineList; //i want to make this for lines
	    private ArrayList<Rect> rectList; //note there is an existing java calss Rectangle, but I'm making my own because of the parameters i want
	    private ArrayList<DrawnShape> shapeList; //i'm thinking of making an additional super class
	    private boolean drawing;
	    
	    //initializaing variables for use in drawing stuff
	    int selectX1, selectX2, selectY1, selectY2;
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
	        		int widthSelect = selectX2 - selectX1;
	        		int heightSelect = selectY2 - selectY1;
	        		g.setColor(new Color(77, 158, 220, 50));
	        		g.fillRect(selectX1, selectY1, widthSelect, heightSelect);
	        		g.setColor(new Color(77, 158, 220));
	        		g.drawRect(selectX1, selectY1, widthSelect, heightSelect);
	        		
	        		db.getAllFromDB();
	        		selectedFeatures = db.selectFeatures(selectX1, selectY1, selectX2, selectY2);

	        	}

	        	// Write all user drawn lines to an Array List to be drawn later in code
	        	if (clickedButton.equals("line")) {
//	        		 g.setColor(Color.BLACK);
	        		 Line lin = new Line(lineX1, lineX2, lineY1, lineY2); //takes user input and adds it to "lin" of class Line
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
	        	
	        	for (int i=0; i < shapeList.size(); i++) {
	        		
	        		DrawnShape shp = shapeList.get(i);
	        		
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
		       			 
		       			g.drawLine(x1, x2, y1, y2); //draw the line
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

	    // MouseListener methods
	    public void mousePressed(MouseEvent e) {
	    	if(clickedButton.equals("point")) {
//	    		points.add(e.getPoint());
//	    		db.insertShapes(e.getPoint().toString(), "'Point'");
		        drawing = true;
		        
		        px = e.getX();
	    		py = e.getY();
	    	}
	    	if(clickedButton.equals("select")) {
	    		selectX1 = e.getX();
	    		selectY1 = e.getY();
	    	}
	    	if(clickedButton.equals("rectangle")) {
	    		recX1 = e.getX();
	    		recY1 = e.getY();
	    	}
	        
	    	if(clickedButton.equals("line")) {
	    		lineX1 = e.getX();
	    		lineY1 = e.getY();
	    	}
	        
	    }

	    public void mouseReleased(MouseEvent e) {
	    	if(clickedButton.equals("select")) {
	    		selectX2 = e.getX();
	    		selectY2 = e.getY();
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
