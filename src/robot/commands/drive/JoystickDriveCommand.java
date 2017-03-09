package robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;
import robot.RobotMap;
import robot.commands.shoot.AlignAndShootHighShotCommand;
import robot.subsystems.ChassisSubsystem.Gear;

/**
 * Default command for the {@link robot.subsystems.ChassisSubsystem
 * ChassisSubsystem}. This command manages all of drive train's actions.
 */
public class JoystickDriveCommand extends Command {

	// Actual FOV Of the Axis Camera M1011: 47.0
	// Degrees per pixel: 0.196875

	public JoystickDriveCommand() {
		requires(Robot.chassisSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double speed = Robot.oi.getSpeed();
		double turn = Robot.oi.getTurn();
		double leftSpeed;
		double rightSpeed;
		
		if(Robot.oi.getArmUp()) {
			Robot.armSubsystem.armUp();
		}
		else if(Robot.oi.getArmDown()) {
			Robot.armSubsystem.armDown();
		}
		
		else {
			Robot.armSubsystem.armStop();
		}

		if (Robot.oi.getGyroReset()) {
			Robot.chassisSubsystem.resetGyroHeading();
		}

		if (Robot.oi.getGyroCalibrate()) {
			Robot.chassisSubsystem.calibrateGyro();
		}

		/**
		 * If the chassisSubsystem says that we should be in high gear, switch
		 * to high gear, otherwise, switch to low gear.
		 */
		if (Robot.oi.getGearShiftButton() >= 0.5) {
			Robot.chassisSubsystem.setGear(Gear.HIGH);
		} else {
			Robot.chassisSubsystem.setGear(Gear.LOW);
		}

		

		/**
		 * If the user is not turning, then follow the gyro using the GoStraight
		 * command.
		 */
		/*
		 * if (Math.abs(turn) < 0.03) { Scheduler.getInstance().add(new
		 * GoStraightCommand(Robot.chassisSubsystem.getCurrentAngle())); return;
		 * }
		 */

		/*
		 * double defaultValue = 0; Robot.chassisSubsystem.resetGyroHeading();
		 * double angle = Robot.oi.table.getNumber("angle", defaultValue);
		 * System.out.println(angle);
		 * 
		 * if(Robot.oi.getAlignShotButton()) { Scheduler.getInstance().add(new
		 * PivotToAngleCommand(angle)); return; }
		 */

		double targetCenterX = Robot.oi.getVisionTargetCenter();
		
		if (Robot.oi.getManualAlignShotButton()) {
			Scheduler.getInstance().add(new AlignAndShootHighShotCommand(MatchPeriod.TELEOP));
			return;
		}

		// Use the driver input to set the speed and direction.
		if (Math.abs(speed) < 0.03) {
			leftSpeed = turn / 2.0;
			rightSpeed = -turn / 2.0;
		} else {
			leftSpeed = (turn < 0.0) ? speed * (1.0 + turn) : speed;
			rightSpeed = (turn < 0.0) ? speed : speed * (1.0 - turn);
		}

		Robot.chassisSubsystem.setSpeed(leftSpeed, rightSpeed);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
