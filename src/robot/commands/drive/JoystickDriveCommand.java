
package robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.subsystems.ChassisSubsystem.Gear;

/**
 *
 */
public class JoystickDriveCommand extends Command {

	public JoystickDriveCommand() {
		requires(Robot.chassisSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double speed = Robot.oi.getSpeed();
		double turn = Robot.oi.getTurn();
		double leftSpeed;
		double rightSpeed;

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
		if (Robot.oi.getTurbo()) {
			Robot.chassisSubsystem.setGear(Gear.HIGH);
		} else {
			Robot.chassisSubsystem.setGear(Gear.LOW);
		}
		
		/**
		 * If the user is not turning, then follow the gyro using the GoStraight
		 * command.
		 */ 
		/*if (Math.abs(turn) < 0.03) { 
			Scheduler.getInstance().add(new GoStraightCommand(Robot.chassisSubsystem.getCurrentAngle())); 
			return;
		}*/
		 
		if (Math.abs(speed) < 0.03) {
			leftSpeed  =   turn / 2.0;
			rightSpeed = - turn / 2.0;
		} else {
			leftSpeed  = (turn < 0) ? speed * (1 + turn) : speed;
			rightSpeed = (turn < 0) ? speed              : speed * (1 - turn) ;
		}

		Robot.chassisSubsystem.setSpeed(leftSpeed, rightSpeed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
