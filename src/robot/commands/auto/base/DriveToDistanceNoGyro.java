package robot.commands.auto.base;

import robot.Robot;

/**
 * This command drives to a specified distance using encoder counts.
 */
public class DriveToDistanceNoGyro extends AutoGoStraightCommandNoGyro {

	/**
	 * The distance to drive to.
	 */
	private double distanceSetpoint;

	private double speedSetpoint;

	/**
	 * The constructor for a new DriveToDistance command.
	 * 
	 * @param speed
	 *            The speed at which to drive.
	 * @param angle
	 *            The angle to drive at (in degrees).
	 * @param distance
	 *            The distance to drive to.
	 */
	public DriveToDistanceNoGyro(double speed, double distance) {
		super();
		this.speedSetpoint = speed;
		this.distanceSetpoint = distance;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.chassisSubsystem.resetEncoders();
		super.initialize();

		if (distanceSetpoint < 0) {
			setSpeed(speedSetpoint, Direction.BACKWARD);
		} else {
			setSpeed(speedSetpoint, Direction.FORWARD);
		}
	}

	/**
	 * Gets the distance set point.
	 * 
	 * @return the distance set point.
	 */
	public double getDistance() {
		return distanceSetpoint;
	}

	// Called once after isFinished returns true
	@Override
	protected boolean isFinished() {
		return (Math.abs(Robot.chassisSubsystem.getEncoderDistance()) >= Math.abs(distanceSetpoint));
	}
}
