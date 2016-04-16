package robot.oi;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NetworkTableOI {

	private static final String TRANSPARENT_WINDOW_TABLE     = "transparentWindow";
	private static final String TRANSPARENT_WINDOW_X         = "windowX";
	private static final String TRANSPARENT_WINDOW_Y         = "windowY";
	private static final String TRANSPARENT_WINDOW_NEW_EVENT = "newWindowEvent";
	
	NetworkTable transparentWindow;
	
	NetworkTableOI() {

		transparentWindow = NetworkTable.getTable(TRANSPARENT_WINDOW_TABLE);
		
		// Initialize the table variables
		transparentWindow.putBoolean(TRANSPARENT_WINDOW_NEW_EVENT, false);
		transparentWindow.putNumber(TRANSPARENT_WINDOW_X, 0.0);
		transparentWindow.putNumber(TRANSPARENT_WINDOW_Y, 0.0);
	}
	
	public boolean isNewEvent() {
		
		return transparentWindow.getBoolean(TRANSPARENT_WINDOW_NEW_EVENT, false);
	}
	
	public double getXAngle() {

		return transparentWindow.getNumber(TRANSPARENT_WINDOW_X, 0.0);
	}
	
	public void updateDashboard() {
		
		SmartDashboard.putString("Network OI", 
				"windowInput " + getXAngle() + " " + isNewEvent()); 

	}
}
