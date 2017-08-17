/* LatLonScreenshot.java
 * @author Jared McDonald
 * Purpose: This program prompts the user for latitude and longitude coordinates and sends an Google Static Maps API request to get a screenshot of the location. 
 * Date: August 2017
 * Used with Google Static Maps API (https://developers.google.com/maps/documentation/static-maps/)
 */
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

public class LatLonScreenshot {
	double lat = 0; //Latitude variable
	double lon = 0; //Longitude variable
	DecimalFormat df = new DecimalFormat("#.######"); //DecimalFormat to limit the doubles to 6 digit places max for API requests.
	String httpURL = "https://maps.googleapis.com/maps/api/staticmap?"; //beginning of the http url used to make the API request. Will be modified later on.
	String apiKey = ""; //Insert your API key here. Get one at https://developers.google.com/maps/documentation/static-maps/get-api-key
	Random r = new Random(); //Random object to generate random doubles on invalid inputs
	int zoom = 1; //Zoom level changed by the JSlider object in the window. The higher the zoom, the more magnified the map. Min is 1, Max is 20.
	//Zoom levels: 1(World)(Min), 5 (Continent), 10 (City), 15 (Streets), 20 (Buildings)(Max)
	private JFrame frmLatlongScreenshot; //JFrame for the program
	private JTextField latField; //Latitude text field
	private JTextField lonField; //Longitude text field
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { //Try to create a new window of the program
					LatLonScreenshot window = new LatLonScreenshot();
					window.frmLatlongScreenshot.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public LatLonScreenshot() {
		initialize(); //fill the window up with elements
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLatlongScreenshot = new JFrame(); //Our window holding all the elements
		frmLatlongScreenshot.setTitle("LatLong Screenshot"); //Title
		frmLatlongScreenshot.setBounds(100, 100, 412, 295); //Size and bounds of the window
		frmLatlongScreenshot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLatlongScreenshot.getContentPane().setLayout(null);
		
		JLabel latLabel = new JLabel("Latitude:"); //Latitude text label
		latLabel.setBounds(10, 11, 57, 14); //Size and bounds of the label
		frmLatlongScreenshot.getContentPane().add(latLabel); //Add it to the window
		
		latField = new JTextField(); //Latitude text field
		latField.setColumns(10);
		latField.setBounds(90, 8, 86, 20); //Size and bounds of the text field
		frmLatlongScreenshot.getContentPane().add(latField); //Add it to the window
		
		lonField = new JTextField(); //Longitude text field
		lonField.setColumns(10);
		lonField.setBounds(90, 39, 86, 20); //Size and bounds of the text field
		frmLatlongScreenshot.getContentPane().add(lonField);  //Add it to the window
		
		JLabel lonLabel = new JLabel("Longitude:"); //Longitude label
		lonLabel.setBounds(10, 45, 70, 14);
		frmLatlongScreenshot.getContentPane().add(lonLabel);  //Add it to the window
		
		JLabel mapLabel = new JLabel(); //label the map will be displayed on
		mapLabel.setIcon(null); //Make it empty at first
		mapLabel.setBounds(186, 11, 200, 200);
		frmLatlongScreenshot.getContentPane().add(mapLabel);  //Add it to the window
		

		
		JButton btnClear = new JButton("Clear"); //button to clear all the input fields and mapLabel
		btnClear.addActionListener(new ActionListener() { //when the button is clicked...
			public void actionPerformed(ActionEvent arg0) { //here's what you do
				latField.setText(""); //Clear the latitude text field
				lonField.setText(""); //Clear the longitude text field
				mapLabel.setIcon(null);	//Set the map label to null/blank	
			}
		});
		btnClear.setBounds(100, 70, 76, 23);
		frmLatlongScreenshot.getContentPane().add(btnClear);  //Add it to the window
		
		JTextArea txtrLatitudeMustBe = new JTextArea(); //TextArea to hold the instructions
		txtrLatitudeMustBe.setEditable(false); //don't edit the instructions
		txtrLatitudeMustBe.setWrapStyleWord(true); //Wrap the words so they dont break up
		txtrLatitudeMustBe.setLineWrap(true); //Wrap the lines so all the words fit
		txtrLatitudeMustBe.setFont(new Font("Tahoma", Font.PLAIN, 10)); //Set the font to Tahoma so it fits and is the same font as everything else
		txtrLatitudeMustBe.setText("Latitude must be between -180 and 180.\r\nLongitude must be between -90 and 90.\r\nIncorrect input will be randomized.\r\nUse the slider to set zoom level.\r\nPress Clear to clear all fields."); //The instructions
		txtrLatitudeMustBe.setBounds(10, 134, 166, 111);
		frmLatlongScreenshot.getContentPane().add(txtrLatitudeMustBe);  //Add it to the window
		
		JSlider ZoomSlider = new JSlider(); //JSlider to control the zoom level for the queries
		ZoomSlider.setValue(10); //Default value is 10 (See the city)
		ZoomSlider.setMinimum(1); //Minimum zoom is 1 (See the world)
		ZoomSlider.setMaximum(20); //Maximum is 20 (See the buildings)
		ZoomSlider.setBounds(35, 97, 112, 26);
		frmLatlongScreenshot.getContentPane().add(ZoomSlider);  //Add it to the window
		
		
		JLabel lblNewLabel = new JLabel("-"); //Label to show the direction of where to drag the slider
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER); //Center the text so it looks nice
		lblNewLabel.setBounds(10, 104, 19, 14);
		frmLatlongScreenshot.getContentPane().add(lblNewLabel);  //Add it to the window
		
		JLabel label_2 = new JLabel("+"); //Label to show the direction of where to drag the slider
		label_2.setHorizontalAlignment(SwingConstants.CENTER); //Center the text so it looks nice
		label_2.setBounds(157, 104, 19, 14);
		frmLatlongScreenshot.getContentPane().add(label_2);
		
		JButton button = new JButton("Go!"); //Go/Search button to run the query through the API
		button.setBounds(10, 70, 63, 23);
		frmLatlongScreenshot.getContentPane().add(button);  //Add it to the window
		button.addActionListener(new ActionListener() { //When the button is clicked...
		public void actionPerformed(ActionEvent arg0) { //Do the following
			httpURL = "https://maps.googleapis.com/maps/api/staticmap?"; //Set the httpURL back to its default so the queries match the inputs given
			try { //Try to do the following
				lat = Double.parseDouble(latField.getText()); //parse the latitude textField into a Double
				if(lat > 180){ //If the latitude is greater than 180, the max
					//System.out.println("Input was larger than 180. 180 shall be used.");
					lat = 180.00; //Set it to 180 degrees
				}
				else if(lat < -180){ //If the latitude is less than -180, the min
					//System.out.println("Input was less than -180. -180 shall be used.");
					lat = -180.00; //Set it to -180 degrees
				}
				lon = Double.parseDouble(lonField.getText()); //parse the longitude textField into a Double
				if(lon > 90){ //If the longitude is greater than 90, the max
					//System.out.println("Input was larger than 90. 90 shall be used.");
					lon = 90.00; //set it to 90 degrees
				}
				else if(lon < -90){ //If the longitude is less than -90, the min
					//System.out.println("Input was less than -90. -90 shall be used.");
					lon = -90.00; //Set it to -90 degrees
				}
			}
			catch(Exception e){ //If any exception occurs parsing the Doubles
				lat = -180.00 + (180.00 - -180.00) * r.nextDouble(); //randomize lat within its max and min bounds
				lon = -90.00 + (90.00 - -90.00) * r.nextDouble();    //randomize lon within its max and min bounds			
			}
			zoom = ZoomSlider.getValue(); //Get the value of the JSlider and set it to zoom variable
			lat = Double.parseDouble(df.format(lat)); //Format the lat double to limit it to 6 decimal places (the most the API will take)
			latField.setText(Double.toString(lat)); //Set the text in the textField to the input in case it was randomized so you knoow where you are looking
			lon = Double.parseDouble(df.format(lon)); //Format the lon double to limit it to 6 decimal places (the most the API will take)
			lonField.setText(Double.toString(lon)); //Set the text in the textField to the input in case it was randomized so you knoow where you are looking
			httpURL = httpURL + "center="+lat+","+lon+"&zoom="+zoom+"&size=200x200&key="+apiKey; //Modify the httpURL to include the latitude, longitude, zoom level, map size, and the API key
			try { //Try to do the following
				URL url = new URL(httpURL); //Create a URL object with the httpURL for our reuest to the API
				url.openConnection(); //Send the request
				mapLabel.setIcon(new ImageIcon(ImageIO.read(url))); //Create an image using the result from the request and show it on the mapLabel
			}
			catch(Exception e) { //Catch the exception
				//No exception occurs since the URL is formatted correctly
			}
		}
		});
	}
}
