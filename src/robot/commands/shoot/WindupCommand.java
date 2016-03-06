package robot.commands.shoot;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

public class WindupCommand extends Command {

	double speedSetPoint;

	public WindupCommand() {
		requires(Robot.shooterSubsystem);
	}

	protected void initialize() {
		speedSetPoint = 0.1 * (-Robot.oi.getShootSpeed() + 1) + 0.8;

		if (Robot.shooterSubsystem.isBoulderRetracted()) {
			Robot.shooterSubsystem.setShooterSpeed(speedSetPoint);
		}

		Robot.shooterSubsystem.setRailPosition(true);
	}

	protected void execute() {
		speedSetPoint = 0.1 * (-Robot.oi.getShootSpeed() + 1) + 0.8;
		Robot.shooterSubsystem.setShooterSpeed(speedSetPoint);
	}

	protected boolean isFinished() {
		if (Robot.shooterSubsystem.getShooterSpeed() > speedSetPoint * 0.8 * 100) {
			return true;
		}
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
