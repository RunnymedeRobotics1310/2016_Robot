package robot.subsystems;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.vision.USBCamera;
import robot.Robot;
import robot.utils.R_Subsystem;

public class CameraSubsystem extends R_Subsystem {

	USBCamera cam;
	CameraServer server;

	public void init() {
		cam = new USBCamera("cam0");
		cam.openCamera();
		cam.setExposureManual(100);
		cam.setBrightness(100);
		cam.setWhiteBalanceManual(10000); // white balance, unsure what max is
											// (10000?)
		server = CameraServer.getInstance();
		server.setQuality(20);
		server.startAutomaticCapture(cam);
	}

	public void initDefaultCommand() {
	}

	@Override
	public void periodic() {

	}

	@Override
	public void updateDashboard() {

	}

	private final NetworkTable grip = NetworkTable.getTable("grip/targets");
	double[] widths = grip.getNumberArray("width", new double[0]);
	double[] xPositions = grip.getNumberArray("centerX", new double[0]);
	double distanceFromCenter;

	// TODO Does this method actually work?
	public void followCamera(){
		distanceFromCenter = widths[0]/2.0 - xPositions[0]; //positive means the target is too far to the right, negative means it is too far left
		System.out.println("xPosition: " + xPositions[0] + "distanceFromCenter: " + distanceFromCenter);

		if(xPositions[0] != -1) //If this is set to the default value, the network table was not found.
		{
			if(distanceFromCenter > 15) //Vision target is too far to the right, rotate right
				Robot.chassisSubsystem.setSpeed(0.0,0.40);
			else if(distanceFromCenter < -15) //Vision target is too far to the left, rotate left
				Robot.chassisSubsystem.setSpeed(0.0,-0.40);
			else
			{
				Robot.chassisSubsystem.setSpeed(0.0,0.0); //stop, centered within 15 pixels
				System.out.println("Centered!  "); //The robot is lined up with the xPosition printed above
			}
		}
		else
		{
			System.out.println("Network table error!!!");
		}
	}
}
