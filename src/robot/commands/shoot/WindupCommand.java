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
		// Low speed = 80rps    - OI input = +1.0
		// High speed = 95rps   - OI input = -1.0
		
		speedSetPointPercent = 0.35 * (-Robot.oi.getShootSpeed() + 1) + 0.3;

		if (Robot.shooterSubsystem.isBoulderRetracted()) {
			Robot.shooterSubsystem.setShooterSpeed(speedSetPointPercent);
		}

		Robot.shooterSubsystem.setRailPosition(true);
	}

	protected void execute() {
		speedSetPointPercent = 0.35 * (-Robot.oi.getShootSpeed() + 1) + 0.3;
		Robot.shooterSubsystem.setShooterSpeed(speedSetPointPercent);
	}

	protected boolean isFinished() {
		// Finish the wind up when at 94% of the target.
		if (Robot.shooterSubsystem.getShooterSpeed() > speedSetPointPercent * 0.94 * 100) {
			return true;
		}
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
