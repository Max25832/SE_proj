package Sketchbook;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class GUI extends JFrame implements ActionListener{
	JMenuBar menuBar;
	JMenu fileMenu,graphicMenu, helpMenu, importSubMenu, dataMenu;
	JMenuItem newMenuItem, openMenuItem, textSubMenuItem, imageSubMenuItem, saveMenuItem, exitMenuItem, 
	          cutMenuItem, copyMenuItem, pasteMenuItem, selectAllMenuItem, checkboxMenuItemShow, 
	          checkboxMenuItemHide, radioButtonEngMenuItem, radioButtonDeMenuItem, colorChooserMenuItem, 
	          gisToolMenuItem, userRegistrationMenuItem, userLoginMenuItem, importShpMenuItem, save2DbMenuItem, 
	          loadFromDbMenuItem, importCsvMenuItem, exportCsvMenuItem, clearDbMenuItem;
	JPopupMenu popupMenu;
	JToolBar toolbar;
	JButton pointButton, lineButton, rectangleButton, selectButton, moveButton, deleteButton, slelectRectButton, slelectPointButton, slelectLinesButton;
	JTextArea ta, taL,taR;
	JLabel imageLabel;
    private DrawingPanel drawingPanel;
    public String clickedButton = "";
    public Integer[] selectedFeatures;
    
   /** 
    * Declaring lists for saving drawn objects and differentiating selected items
    * shapeList - stores all objects
    * selectedList - keeps track of actively selected items
    * unselectedList - keeps track of unselected items when selected items exist
    * @author Ida Hausmann, Caden Wells
    */
    public ArrayList<DrawnShape> shapeList;
    public ArrayList<DrawnShape> selectedList;
    public ArrayList<DrawnShape> unselectedList;
        
    // Instantiate the database
    Database db = new Database();
    


	public GUI(){	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	
		db.createTable();
	    selectedList = new ArrayList<>(); //initialize selected list
    	    
/**
 * defined colors that are going to be used  
 * @author: Max Kirsch 
 */
		Color toolbarColor = new Color(200,200,200);
		Color menuColor = new Color(34, 87, 179);
		Color drawingPanelColor= new Color(250,250,250);
	        
		setTitle("Sketchbook DEMO!");
		menuBar = new JMenuBar();
		menuBar.setBackground(menuColor);
		
		ta = new JTextArea();
		ta.setBackground(menuColor);

		

/** 
 * ***************File Menu*********************
 * creating menu dropwdown list
 * 
 * @author: Max Kirsch 
 */

		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.setForeground(Color.WHITE);

//Items for the dropdown list 
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

		//adding the items to the dropdown list
		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(importSubMenu);
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		//End of File Menu

		
/** 
 * ********************Data Menu******************* 
 * 
 * Dropdown Menu for importing, exporting and saving data
 * @author: Max Kirsch  
 */
		dataMenu = new JMenu("Data");
		dataMenu.setMnemonic(KeyEvent.VK_D);
		dataMenu.setForeground(Color.WHITE);


		
//Data menu items 
 
		
		importShpMenuItem = new JMenuItem("Import Shapefile");
		importShpMenuItem.setToolTipText("Import Data as Shapefile");
		importShpMenuItem.addActionListener(this);

		save2DbMenuItem = new JMenuItem("Save to Database");
		save2DbMenuItem.setToolTipText("Save Selection to Database. If no Seletion, Saves all Data");
		save2DbMenuItem.addActionListener(this);

		loadFromDbMenuItem = new JMenuItem("Load from Database");
		loadFromDbMenuItem.setToolTipText("Load from Database");
		loadFromDbMenuItem.addActionListener(this);

		importCsvMenuItem = new JMenuItem("Import CSV");
		importCsvMenuItem.setToolTipText("Import Data from CSV File");
		importCsvMenuItem.addActionListener(this);

		exportCsvMenuItem = new JMenuItem("Export CSV");
		exportCsvMenuItem.setToolTipText("Export Selection as CSV. If no Seletion, Saves all Data");
		exportCsvMenuItem.addActionListener(this);
		
		clearDbMenuItem = new JMenuItem("Clear Database");
		clearDbMenuItem.setToolTipText("Delete all data from the Database");
		clearDbMenuItem.addActionListener(this);

		//added the Data items to the Data dropdown
		dataMenu.add(importShpMenuItem);
		dataMenu.add(loadFromDbMenuItem);
		dataMenu.add(save2DbMenuItem);
		dataMenu.add(importCsvMenuItem);
		dataMenu.add(exportCsvMenuItem);
		dataMenu.add(clearDbMenuItem);
		//End of Data Menu

		
		

/**
 * **************Help Menu*******************
 * @author: Max Kirsch 
 */

		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.setForeground(Color.WHITE);

		//Help menu MenuListener --> print out a messagfe when pressed 
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
						"This application allwos to: draw, move, export, import and delete shapes." +"The sourcecode can be found at our GitHub Repository: https://github.com/Max25832/SE_proj");
			}
		});
		
		
		//adding the Menus to the benu bar  
		menuBar.add(fileMenu);
		menuBar.add(dataMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
		menuBar.add(Box.createHorizontalGlue());
		
		
		//Toolbar
		toolbar = new JToolBar();
		toolbar.setLocation(10, 5);
		toolbar.setMargin(new Insets(3, 10, 5, 10));
		toolbar.setFloatable(true);
		toolbar.setBackground(toolbarColor);

        /** *****************Buttons*********************
         * All Buttons are JButtons that have the following attributes:
         * an image and a text
         * ToolTip: shows a text, when hovering the curser over a button.
         * action listener -> action is implemented later in code 
         * 
         * @author: Max Kirsch 
         */
		
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
		pointButton.setToolTipText("Draw Points");
		pointButton.setVerticalTextPosition(AbstractButton.CENTER);
	    pointButton.setHorizontalTextPosition(AbstractButton.TRAILING); 
	    pointButton.addActionListener(this);
		pointButton.setBorder(new RoundedBorder(35));
        pointButton.setForeground(Color.BLACK);
        pointButton.setContentAreaFilled(false);

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
		selectButton.setText("Select");
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
				
        slelectLinesButton = new JButton(new ImageIcon(getClass().getResource("selectline.png")));
        slelectLinesButton.setToolTipText("Select all Lines");
        slelectLinesButton.setText("Select Lines");
        slelectLinesButton.setVerticalTextPosition(AbstractButton.CENTER);
        slelectLinesButton.setHorizontalTextPosition(AbstractButton.TRAILING); 
        slelectLinesButton.addActionListener(this);
        slelectLinesButton.setBorder(new RoundedBorder(35));
        slelectLinesButton.setForeground(Color.BLACK);
        slelectLinesButton.setContentAreaFilled(false);
			
        slelectRectButton = new JButton(new ImageIcon(getClass().getResource("selectrect.png")));
        slelectRectButton.setToolTipText("Select all Rectangles");
        slelectRectButton.setText("Select Rectangles");
        slelectRectButton.setVerticalTextPosition(AbstractButton.CENTER);
        slelectRectButton.setHorizontalTextPosition(AbstractButton.TRAILING); 
        slelectRectButton.addActionListener(this);
        slelectRectButton.setBorder(new RoundedBorder(35));
        slelectRectButton.setForeground(Color.BLACK);
        slelectRectButton.setContentAreaFilled(false);
		
        slelectPointButton = new JButton(new ImageIcon(getClass().getResource("slecetpoints.png")));
		slelectPointButton.setToolTipText("Select all Points");
		slelectPointButton.setText("Select Points");
		slelectPointButton.setVerticalTextPosition(AbstractButton.CENTER);
		slelectPointButton.setHorizontalTextPosition(AbstractButton.TRAILING); 
		slelectPointButton.addActionListener(this);
		slelectPointButton.setBorder(new RoundedBorder(35));
		slelectPointButton.setForeground(Color.BLACK);
        slelectPointButton.setContentAreaFilled(false);
		

/** ******************Toolbar***************** 
 *
 * for the Toolbar a gridlayout was created so that the buttons are evenly distributed
 * each button was added to the toolbat and to seperate them rigit areas where created and put between the buttons
 * 
 * @author: Max Kirsch 
 */
        
      GridLayout gridLay = new GridLayout(1,13);
		toolbar.setLayout(gridLay);
		toolbar.add(pointButton);
		toolbar.add(lineButton);
		toolbar.add(rectangleButton);
		toolbar.add(Box.createRigidArea(new Dimension(1, 0)));
		toolbar.add(selectButton); 
		toolbar.add(slelectPointButton); 
		toolbar.add(slelectRectButton); 
		toolbar.add(slelectLinesButton); 
		toolbar.add(Box.createRigidArea(new Dimension(1, 0)));
		toolbar.add(Box.createRigidArea(new Dimension(1, 0)));
		toolbar.add(moveButton); 
		toolbar.add(deleteButton); 

		//End of Toolbar

/** ***************Drawing Panel****************** 
 * 
 * changed color and put an outline around it 
 * 
 * @author: Max Kirsch 
 */
 		drawingPanel = new DrawingPanel();
		drawingPanel.setBackground(drawingPanelColor);
    drawingPanel.setBorder(new LineBorder(menuColor, 15, false));

	
		//Popup Menu

		popupMenu = new JPopupMenu();
		
		JMenuItem maximizeMenuItem = new JMenuItem("Maximize");
		//addActionListener
		maximizeMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
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
		setSize(1600, 900);
		setVisible(true);
	}

	/**
	 * This actionPerformed governs mouseclicks for all menu items.
	 * Many are as provided in the Swing UI example provided by Naznin Akter.
	 * Those with substantial changes are listed as follows:
	 * 
	 * saveMenuItem - saves all selected shapes using CSVOperations.exportCSV()
	 * importShpMenuItem - Allows the user to select and visualize an Esri shapefile
	 * 		(.shp) using the geotools java library.
	 * save2DbMenuItem - Saves all selected shapes to an SQL database, 
	 * 		if no selected shapes, will save all shapes.
	 * loadFromDbMenuItem - Loads data as strings from an SQL database using Database.getAllFromDB1()
	 * 		then creates a DrawnShape object based on its type parameter.
	 * importCsvMenuItem - Loads data as strings from CSV using CSVOperations.importCSV()
	 * 		then creates a DrawnShape object based on its type parameter.
	 * exportCsvMenuItem - Saves all selected shapes to a CSV file, 
	 * 		if no selected shapes, will save all shapes.
	 * clearDbMenuItem - Deletes all data in the current SQL database.
	 * 
	 * @author Felipe Vasquez
	 */
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
					reader.close();
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
		
		// Save all shapes to csv
		if(e.getSource() == saveMenuItem ) {
			JFileChooser fc = new JFileChooser();
			fc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter extFilter = new FileNameExtensionFilter("CSV files", "csv");
			fc.addChoosableFileFilter(extFilter);
			int i = fc.showSaveDialog(this);
			File f = fc.getSelectedFile();
			System.out.println("Saving at " + f.getAbsolutePath());
			ArrayList<String> csvLine = new ArrayList<String>();
			for (DrawnShape shp : shapeList) {
				csvLine.add(shp.geometry + ", '" + shp.type + "'");
			}
			CSVOperations.exportCSV(f, csvLine);
		}
		
		// TODO Make it so that closing the window doesn't terminate the app.
		if(e.getSource() == importShpMenuItem ) {
			File file = JFileDataStoreChooser.showOpenFile("shp", null);
			if (file == null) {
			    return;
			}
					
			FileDataStore store;
			try {
				store = FileDataStoreFinder.getDataStore(file);
				SimpleFeatureSource featureSource = store.getFeatureSource();
				
				// Create a map content and add our shapefile to it
				MapContent map = new MapContent();
				map.setTitle("Quickstart");
				
				Style style = SLD.createSimpleStyle(featureSource.getSchema());
				Layer layer = new FeatureLayer(featureSource, style);
				map.addLayer(layer);
	
				// Now display the map
				JMapFrame.showMap(map);
			} catch (IOException e1) {
	
				e1.printStackTrace();
			}

		}
		
		// TODO Low priority: Check if items in db already exist.
		if(e.getSource() == save2DbMenuItem ) {
			if (selectedList.size() == 0) {
				for (DrawnShape shp : shapeList) {
					db.insertShapes(shp.geometry, "'" + shp.type + "'");
				}
			} else {
				for (DrawnShape shp : selectedList) {
					db.insertShapes(shp.geometry, "'" + shp.type + "'");
				}
			}
		}
		
		// TODO Handle DB Logic from database. FileChooser not needed
		if(e.getSource() == loadFromDbMenuItem ) {
			String[] fromDB = db.getAllFromDB();
			for (String entry: fromDB) {
				System.out.println(entry);
				String[] split = entry.split(";");
				String type = split[0];
				String[] subString = split[1].split(", ");
				if (type.equals("Point")) {
					System.out.println("Printing Point");
					shapeList.add(new Dot(subString[0], subString[1]));
				} else if (type.equals("Line")) {
					shapeList.add(new Line(subString[0], subString[1], subString[2], subString[3]));
				} else if (type.equals("Rectangle")) {
					shapeList.add(new Rect(subString[0], subString[1], subString[2], subString[3]));
				}
			}
		}
		
		// Import all lines from a CSV
		if(e.getSource() == importCsvMenuItem ) {
			JFileChooser fc = new JFileChooser();
			fc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter extFilter = new FileNameExtensionFilter("CSV files", "csv");
			fc.addChoosableFileFilter(extFilter);
			int i = fc.showOpenDialog(this);
			if (i == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				System.out.println("Loading from " + f.getAbsolutePath());
				String[] csvLines = CSVOperations.importCSV(f);
				System.out.println(shapeList.size());
				for (String string : csvLines) {
					String[] subString = string.split(", ", 0);
					String type = subString[subString.length - 1].replace("'", "");
					if (type.equals("Point")) {
						System.out.println("Printing Point");
						shapeList.add(new Dot(subString[0], subString[1]));
					} else if (type.equals("Line")) {
						shapeList.add(new Line(subString[0], subString[1], subString[2], subString[3]));
					} else if (type.equals("Rectangle")) {
						shapeList.add(new Rect(subString[0], subString[1], subString[2], subString[3]));
					}
				}
				System.out.println("Click on the drawing panel to load shapes");
			}
		}
		
		// Save selection to CSV, otherwise save all shapes.
		if(e.getSource() == exportCsvMenuItem ) {
			JFileChooser fc = new JFileChooser();
			fc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter extFilter = new FileNameExtensionFilter("CSV files", "csv");
			fc.addChoosableFileFilter(extFilter);
			int i = fc.showSaveDialog(this);
			if (i == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				System.out.println("Saving at " + f.getAbsolutePath());
				ArrayList<String> csvLine = new ArrayList<String>();
				if (selectedList.size() == 0) {
					for (DrawnShape shp : shapeList) {
						csvLine.add(shp.geometry + ", '" + shp.type + "'");
					}
				} else {
					for (DrawnShape shp : selectedList) {
						csvLine.add(shp.geometry + ", '" + shp.type + "'");
					}
				}
				CSVOperations.exportCSV(f, csvLine);
			}
		}
		
		// TODO add delete from database
		if(e.getSource()==clearDbMenuItem){
			try {
				db.deleteTable();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
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
		
		/**
		 * Check which button is clicked and set clickedButton variable accordingly
		 * @author Ida Hausmann
		 */
		
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
			selectedList.clear(); //delete all objects stored in selected list (when "delete" is clicked)
		}
		if(e.getSource()== slelectRectButton) {
			clickedButton = "selectRect";
		}
		
		if(e.getSource()==slelectPointButton) {
			clickedButton = "selectPoint";
			
		}
		if(e.getSource()==slelectLinesButton) {
			clickedButton = "selectLine";
		}
			

		
	}
	
	
	/**
	 * Drawing Panel for drawing shapes - contains everything pertaining to that
	 * @author Ida Hausmann, Caden Wells 
	 */
	public class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener {

	    private boolean drawing;
	    
	    //initializing variables needed for selecting and moving objects
	    int selectX1, selectX2, selectY1, selectY2, selectClickX, selectClickY, selectRelX, selectRelY;
	    int moveX, moveY, moveDistX, moveDistY;
	    //for saving point coordinates
	    int px, py;
	    int xp, yp;
	    //for saving rectangle parameters
	    int recX1, recX2, recY1, recY2;
	    int widthRec, heightRec;
	    int rx1, ry1, rw, rh;
	    //for saving line coordinates
	    int lineX1, lineX2, lineY1, lineY2;
	    int x1, x2, y1, y2;
	    double slope;
	    
	    //constructor method for DrawingPanel
	    //creates needed lists and implements mouse listeners
	    public DrawingPanel() {

	        shapeList = new ArrayList<>();
	        unselectedList = new ArrayList<>();
	        addMouseListener(this);
	        addMouseMotionListener(this);
	    }
	    
	    //method for drawing in the drawing panel
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g); //inherit from super class (JPanel)
	        	
	        	//adding new point (d) to shapeList when "point" button is active
	        	if(clickedButton.equals("point")) {
	        		Dot d = new Dot(px, py);
	        		shapeList.add(d);	        		
	        		
	        	}
	        	
	        	//adding new line (lin) to shapeList when "line" button is active
	        	if (clickedButton.equals("line")) {
	        		 Line lin = new Line(lineX1, lineY1, lineX2, lineY2); //takes user input and adds it to "lin" of class Line
	        		 shapeList.add(lin);
	        	}
            
	        	//adding new rectangle (rec) to shapeList when "rectangle" button is active
	        	if(clickedButton.equals("rectangle")) {
	        		Rect rec = new Rect(recX1, recY1 , recX2, recY2); 
	        		shapeList.add(rec);
	        	}
	        	
	        	//when selectPoint button is active, select all points
	        	//iterate through shape list, add all type points to selectedList, delete from shape list,
	        	//add all other objects (lines and rectangles) to unselected list
	        	if(clickedButton.equals("selectPoint")) {
	        		Iterator<DrawnShape> itr = shapeList.iterator();
	        		while(itr.hasNext()) {
	        			DrawnShape shape = itr.next();
	        			if(shape.type=="Point") {
	        				selectedList.add(shape);
	        				itr.remove();
	        			}else {
	        				unselectedList.add(shape);
	        			}
	        		}
	        	}
	        	
	        	//when selectLine button is active, select all lines
	        	//iterate through shape list, add all type lines to selectedList, delete from shape list,
	        	//add all other objects (points and rectangles) to unselected list
	        	if(clickedButton.equals("selectLine")) {
	        		Iterator<DrawnShape> itr = shapeList.iterator();
	        		while(itr.hasNext()) {
	        			DrawnShape shape = itr.next();
	        			if(shape.type=="Line") {
	        				selectedList.add(shape);
	        				itr.remove();
	        			}else {
	        				unselectedList.add(shape);
	        			}
	        		}
	        	}
	        	
	        	//when selectRect button is active, select all rectangles
	        	//iterate through shape list, add all type rectangle to selectedList, delete from shape list,
	        	//add all other objects (lines and points) to unselected list
	        	if(clickedButton.equals("selectRect")) {
	        		Iterator<DrawnShape> itr = shapeList.iterator();
	        		while(itr.hasNext()) {
	        			DrawnShape shape = itr.next();
	        			if(shape.type=="Rectangle") {
	        				selectedList.add(shape);
	        				itr.remove();
	        			}else {
	        				unselectedList.add(shape);
	        			}
	        		}
	        	}
	        	
	        	//when select button is active, select all items within bounds of defined rectangle 
	        	if(clickedButton.equals("select")) {
	        		//check the direction in which the user has drawn the rectangle
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
	        		
	        		//calculating and defining the select area
	        		int widthSelect = selectX2 - selectX1;
	        		int heightSelect = selectY2 - selectY1;
	        		//draw the select rectangle (in blue, with border)
	        		g.setColor(new Color(77, 158, 220, 50));
	        		g.fillRect(selectX1, selectY1, widthSelect, heightSelect);
	        		g.setColor(new Color(77, 158, 220));
	        		g.drawRect(selectX1, selectY1, widthSelect, heightSelect);
	        		
	        		//Iterate through the shape list to select shapes within the rectangle
	        		Iterator<DrawnShape> itr = shapeList.iterator();
	        		while(itr.hasNext()) {
	        			DrawnShape shape = itr.next();
	        			
	        			//if shape is of type point, select based on these parameters 
	        			//add to selectedList accordingly, remove from shape list and add other points to unselectedList
	        			if(shape.type == "Point") {
	        				Dot dotSelect = (Dot) shape;
	        				if(dotSelect.px <= selectX2 && dotSelect.px >= selectX1 && dotSelect.py <= selectY2 && dotSelect.py >= selectY1) {
	        					selectedList.add(shape);
	        					itr.remove();
	            			} else {
	            				unselectedList.add(shape);
	            			}
	        				
	        			//if shape is of type line, select based on these parameters 
		        		//add to selectedList accordingly, remove from shape list and add other points to unselectedList	
	        			} else if (shape.type == "Line") {
	        				Line lineSelect = (Line) shape;
	        				if(lineSelect.x1 <= selectX2 && lineSelect.x1 >= selectX1 && lineSelect.x2 <= selectX2 && lineSelect.x2 >= selectX1 && lineSelect.y1 <= selectY2 && lineSelect.y1 >= selectY1 && lineSelect.y2 <= selectY2 && lineSelect.y2 >= selectY1) {
	        					selectedList.add(shape);
	        					itr.remove();
	        				}else {
	            				unselectedList.add(shape);
	            			}
	        				
	        			//if shape is of type rectangle, select based on these parameters 
		        		//add to selectedList accordingly, remove from shape list and add other points to unselectedList
	        			} else if (shape.type == "Rectangle") {
	        				Rect rectSelect = (Rect) shape;
	        				if(rectSelect.recX1 <= selectX2 && rectSelect.recX1 >= selectX1 && rectSelect.recX2 <= selectX2 && rectSelect.recX2 >= selectX1 && rectSelect.recY1 <= selectY2 && rectSelect.recY1 >= selectY1 && rectSelect.recY2 <= selectY2 && rectSelect.recY2 >= selectY1) {
	        					selectedList.add(shape);
	        					//shapeList.remove(s);
	        					itr.remove();
	        					System.out.println(rectSelect);
	        				}else {
	            				unselectedList.add(shape);
	            			}
	        			}
	        		}
	        	  		
	        		
	        	}

	        	
	    
	        	//setting the color to draw the objects 
	        	g.setColor(Color.BLACK);

	        	
	        	//checking if items are selected
	        	//if no elements are selected: draw the objects of the shapeList
	        	//if elements are selected: draw the objects of the selected and the unselected List
	        	if(selectedList.size() == 0) {
	        		for (DrawnShape shp : shapeList) {
		        		
		        		//if the type of the current object is point, draw an oval with the size of 5x5 and the coordinates as parameters
		        		if (shp.type == "Point") {
		        			Dot d3 = (Dot) shp;
		        			
		        			xp = d3.px();
			        		yp = d3.py();
			        		
			        		g.fillOval(xp, yp, 5, 5);
		        		}
		        		
		        		//if the type of the current object is line, draw a line
		        		if (shp.type == "Line") {
		        			Line lin3 = (Line) shp;
			       			
		        			x1 = lin3.x1();
			       			x2 = lin3.x2();
			       			y1 = lin3.y1();
			       			y2 = lin3.y2();
			       			 
			       			g.drawLine(x1, y1, x2, y2); //draw the line
		        		}
		        		
		        		//if the type of the current object is rectangle, draw a rectangle
		        		if (shp.type == "Rectangle") {
		        			Rect rec3 = (Rect) shp;
		        			
		        			rx1 = rec3.recX1();
		        			ry1 = rec3.recY1();
		        			rw = rec3.widthRec();
		        			rh = rec3.heightRec();
	 
		 	        		g.drawRect(rx1, ry1, rw, rh);
		        		}	
		        	}	
	        	} else {
	        		//start drawing the unselected List the same way the shapeList is drawn 
	        		for (DrawnShape shp : unselectedList) {
		        		
	        			//if the type of the current object is point, draw an oval with the size of 5x5 and the coordinates as parameters
		        		if (shp.type == "Point") {
		        			Dot d3 = (Dot) shp;
		        			
		        			xp = d3.px();
			        		yp = d3.py();
			        		
			        		g.fillOval(xp, yp, 5, 5);
		        		}
		        		
		        		//if the type of the current object is line, draw a line
		        		if (shp.type == "Line") {
		        			Line lin3 = (Line) shp; 
			       			
		        			x1 = lin3.x1(); 
			       			x2 = lin3.x2();
			       			y1 = lin3.y1();
			       			y2 = lin3.y2();
			       			 
			       			g.drawLine(x1, y1, x2, y2);
		        		}
		        		
		        		//if the type of the current object is rectangle, draw a rectangle
		        		if (shp.type == "Rectangle") {
		        			Rect rec3 = (Rect) shp;
		        			
		        			rx1 = rec3.recX1(); 
		        			ry1 = rec3.recY1();
		        			rw = rec3.widthRec();
		        			rh = rec3.heightRec();
	 
		 	        		g.drawRect(rx1, ry1, rw, rh);
		        		}
		        		
		        	}
		        	
	        		//changing the color to draw objects to blue
		        	g.setColor(new Color(77, 158, 220));
		        	//draw objects of the selectedList
		        	for (DrawnShape shp : selectedList) {
		        		//if the type of the current object is point, draw an oval with the size of 5x5 and the coordinates as parameters
		        		if (shp.type == "Point") {
		        			Dot d3 = (Dot) shp;
		        			
		        			xp = d3.px();
			        		yp = d3.py();
			        		
			        		g.fillOval(xp, yp, 5, 5);
		        		}
		        		
		        		//if the type of the current object is line, draw a line
		        		if (shp.type == "Line") {
		        			Line lin3 = (Line) shp;
			       			
		        			x1 = lin3.x1();
			       			x2 = lin3.x2();
			       			y1 = lin3.y1();
			       			y2 = lin3.y2();
			       			 
			       			g.drawLine(x1, y1, x2, y2);
		        		}
		        		
		        		//if the type of the current object is rectangle, draw a rectangle
		        		if (shp.type == "Rectangle") {
		        			Rect rec3 = (Rect) shp;
		        			
		        			rx1 = rec3.recX1();
		        			ry1 = rec3.recY1();
		        			rw = rec3.widthRec();
		        			rh = rec3.heightRec();
	 
		 	        		g.drawRect(rx1, ry1, rw, rh);
		        		}
		        		
		        	}
	        		
	        	}
	        	
	        	

	    }

	    // MouseListener methods, event when mouse is pressed
	    public void mousePressed(MouseEvent e) {

	    	//function called when the point button is active
	    	if(clickedButton.equals("point")) {
	    		
	    		//add all objects of the selectedList to the shapeList
	    		for(DrawnShape s : selectedList) {
	    			shapeList.add(s);
	    		}
	    		//delete all objects in the selectedList
	    		selectedList.clear();
	    		// set the drawing function true to allow to draw on the panel
		        drawing = true;
		        
		        //measuring the coordinates where the mouse clicked and save them in variables
		        px = e.getX();
	    		py = e.getY();
	    	}
	    	
	    	//function called when the rectangle button is active
	    	if(clickedButton.equals("rectangle")) {
	    		
	    		//add all objects of the selectedList to the shapeList
	    		for(DrawnShape s : selectedList) {
	    			shapeList.add(s);
	    		}
	    		
	    		//empty selectedList
	    		selectedList.clear();
	    		
	    		//measuring the coordinates where the mouse clicked and save them in variables
	    		recX1 = e.getX();
	    		recY1 = e.getY();
	    	}
	        
	    	//function called when the line button is active
	    	if(clickedButton.equals("line")) {
	    		
	    		//add all objects of the selectedList to the shapeList
	    		for(DrawnShape s : selectedList) {
	    			shapeList.add(s);
	    		}
	    		
	    		//empty selectedList
	    		selectedList.clear();
	    		
	    		//measuring the coordinates where the mouse clicked and save them in variables
	    		lineX1 = e.getX();
	    		lineY1 = e.getY();
	    	}
	    	
	    	//function called when the select button is active
	    	if(clickedButton.equals("select")) {
	    		
	    		//add all objects of the selectedList to the shapeList
	    		for(DrawnShape s : selectedList) {
	    			shapeList.add(s);
	    		}
	    		
	    		//empty selectedList
	    		selectedList.clear();
	    		
	    		//measuring the coordinates where the mouse clicked and save them in variables
	    		selectClickX = e.getX();
	    		selectClickY = e.getY();
	    	}
	    	
	    	//function called when the select button is active
	    	if(clickedButton.equals("move")) {
	    		//measuring the coordinates where the mouse clicked and save them in variables
	    		moveX = e.getX();
	    		moveY = e.getY();
	    	}
	        
	    }

	    // MouseListener methods, event when mouse is released
	    public void mouseReleased(MouseEvent e) {
	    	//function called when the select button is active
	    	if(clickedButton.equals("select")) {
	    		//measuring the coordinates where the mouse released and save them in variables
	    		selectRelX = e.getX();
	    		selectRelY = e.getY();
	    	}
	        drawing = false;
	        repaint();
	    	//function called when the rectangle button is active
	    	if(clickedButton.equals("rectangle")) {
	    		//measuring the coordinates where the mouse released and save them in variables
	    		recX2 = e.getX();
	    		recY2 = e.getY();
	    	}
	        drawing = false;
	        repaint();
	    	//function called when the line button is active
	    	if(clickedButton.equals("line")) {
	    		//measuring the coordinates where the mouse released and save them in variables
	    		lineX2 = e.getX();
	    		lineY2 = e.getY();
	    	}
	        drawing = false;
	        repaint();
	        
	    	//function called when the move button is active
	        if(clickedButton.equals("move")) {
	    		//measuring the coordinates where the mouse released and calculate the distance between the start point and end point
	        	//save this in variables
	    		moveDistX = e.getX() - moveX;
	    		moveDistY = e.getY() - moveY;
	    		
	    		//iterate through the selectedList and change the coordinates of the objects accordingly to the move distance
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
	    public void mouseClicked(MouseEvent e) {}

	    public void mouseEntered(MouseEvent e) {}
	    
	    public void mouseExited(MouseEvent e) {}

	    public void mouseDragged(MouseEvent e) {}

	    public void mouseMoved(MouseEvent e) {}
	}
	
}