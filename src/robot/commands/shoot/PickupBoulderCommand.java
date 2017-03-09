package robot.commands.shoot;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.RobotMap;

/**
 * This command will start the boulder intake routine. This command also
 * contains many safeguards to ensure the robot cannot possibly be in control of
 * more than one boulder at once.
 */
public class PickupBoulderCommand extends Command {

	boolean timerSet = false;

	public PickupBoulderCommand() {
		requires(Robot.shooterSubsystem);
		requires(Robot.armSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timerSet = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Robot.shooterSubsystem.getShooterSpeed() == 0) {
			Robot.shooterSubsystem.startIntakeMotor();
			if (Robot.oi.getBallStuckButton()) {
				Robot.armSubsystem.startArmIntakeReverse();
			} else {
				Robot.armSubsystem.startArmIntake();
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (Robot.shooterSubsystem.isBoulderLoaded() && !timerSet) {
			// If the boulder is loaded, and the timer is false, initial
			this.setTimeout(this.timeSinceInitialized() + 0.25);
			timerSet = true;
		}

		return (timerSet && this.isTimedOut()) || Robot.oi.getCancel();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.shooterSubsystem.stopIntakeMotor();
		Robot.armSubsystem.stopArmIntake();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
