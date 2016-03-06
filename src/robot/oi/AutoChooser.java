package robot.oi;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser {

	public enum AutoMode {
		DO_NOTHING, DRIVE_AND_SHOOT;
	}
	SendableChooser autoModeChooser = new SendableChooser();
	SendableChooser laneChooser = new SendableChooser();
	SendableChooser defenceChooser = new SendableChooser();
	SendableChooser distanceChooser = new SendableChooser();
	SendableChooser goalChooser = new SendableChooser();

	public AutoChooser() {
		autoModeChooser.addDefault("Drive and Shoot", "Drive and Shoot");
		autoModeChooser.addObject("Do nothing", "Do Nothing");
		autoModeChooser.addObject("Drive to 70",  "Drive to 70");

		laneChooser.addObject("1", 1);
		laneChooser.addObject("2", 2);
		laneChooser.addObject("3", 3);
		laneChooser.addObject("4", 4);
		laneChooser.addObject("5", 5);

		defenceChooser.addObject("Low Bar",         "Low Bar");
		defenceChooser.addObject("Ramparts",        "Ramparts");
		defenceChooser.addObject("Moat",            "Moat");
		defenceChooser.addObject("Rock Wall",       "Rock Wall");
		defenceChooser.addObject("Rough Terrain",   "Rough Terrain");
		defenceChooser.addObject("Portcullis",      "Portcullis");
		defenceChooser.addObject("Cheval de Frise", "Cheval de Frise");

		distanceChooser.addObject("Close", "Close");
		distanceChooser.addObject("Far",   "Far");

		goalChooser.addObject("Left",   "Left");
		goalChooser.addObject("Center", "Center");
		goalChooser.addObject("Right",  "Right");

		SmartDashboard.putData("Auto mode",     autoModeChooser);
		SmartDashboard.putData("Slot position", laneChooser);
		SmartDashboard.putData("Defences",      defenceChooser);
		SmartDashboard.putData("Distance",      distanceChooser);
		SmartDashboard.putData("Goal",          goalChooser);
	}
	
	public String getAutoMode() {
		return (String) autoModeChooser.getSelected();
	}

	/**
	 * 
	 * @return The selected lane, as an integer
	 */
	public int getSelectedSlot() {
		return (int) laneChooser.getSelected();
	}

	/**
	 * 
	 * @return The selected defense, as a string
	 */
	public String getSelectedDefence() {
		return (String) defenceChooser.getSelected();
	}

	/**
	 * 
	 * @return The selected distance, as a string
	 */
	public String getSelectedDistance() {
		return (String) distanceChooser.getSelected();
	}

	/**
	 * 
	 * @return The selected goal, as a String
	 */
	public String getSelectedGoal() {
		return (String) goalChooser.getSelected();
	}
}