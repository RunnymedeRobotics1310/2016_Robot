package robot.commands.shoot;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

public class WindupCommand extends Command {

	double speedSetPointPercent;

	public WindupCommand() {
		requires(Robot.shooterSubsystem);
	}

	protected void initialize() {
		
		// Shooter speeds.
		// Low speed = 50rps    - OI input = +1.0
		// High speed = 95rps   - OI input = -1.0
		
		speedSetPointPercent = 0.25 * (-Robot.oi.getShootSpeed() + 1) + 0.5;

		if (Robot.shooterSubsystem.isBoulderRetracted()) {
			Robot.shooterSubsystem.setShooterSpeed(speedSetPointPercent);
		}

		Robot.shooterSubsystem.setRailPosition(true);
	}

	protected void execute() {
		speedSetPointPercent = 0.1 * (-Robot.oi.getShootSpeed() + 1) + 0.8;
		Robot.shooterSubsystem.setShooterSpeed(speedSetPointPercent);
	}

	protected boolean isFinished() {
		
		// Finish the windup when the windup done button is pressed.  This allows for
		// dyamic update of the shooter speed.
		if (Robot.oi.getWindUpLockButton()) { return true; }
		
		// Finish the wind up when at 94% of the target.
//		if (Robot.shooterSubsystem.getShooterSpeed() > speedSetPointPercent * 0.94 * 100) {
//			return true;
//		}
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
