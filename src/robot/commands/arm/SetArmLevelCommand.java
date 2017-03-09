package robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.RobotMap.ArmLevel;

public class SetArmLevelCommand extends Command {

	private ArmLevel level;

	/**
	 * This command sets the arm to predetermined levels.
	 * 
	 * @param level
	 *            {@link ArmLevel} value.
	 */
	public SetArmLevelCommand(ArmLevel level) {
		requires(Robot.armSubsystem);
		this.level = level;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	// Called once after isFinished returns true
	protected void end() {
	}

}
