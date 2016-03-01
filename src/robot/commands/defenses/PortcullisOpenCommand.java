package robot.commands.defenses;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

/** 
 * The PortcullisOpen Command assumes that the robot is up against and touching the
 * portcullis before executing the command.
 * <p>
 * When opening the portcullis, the drive base needs to back up slightly and then go forward in order
 * to allow the portcullis to open
 */
public class PortcullisOpenCommand extends Command {

	private enum Step { ONE, TWO, THREE }
	
	private double startAngle = 0.0;
	private Step   step;
	
	private double BACKUP_SLOWLY_SPEED = -.04;
	
	public PortcullisOpenCommand() {
		requires(Robot.armSubsystem);
		requires(Robot.chassisSubsystem);
		step = Step.ONE;
	}
	
	@Override
	protected void initialize() {
		startAngle = Robot.armSubsystem.getArmAngle();
		Robot.chassisSubsystem.resetEncoders();
		step = Step.ONE;
	}

	@Override
	protected void execute() {
		
		// Part one of crossing the Portcullis is to put the arm up while driving
		// backwards slightly.
		if (step == Step.ONE) {
			Robot.chassisSubsystem.setSpeed(BACKUP_SLOWLY_SPEED, BACKUP_SLOWLY_SPEED);
			Robot.armSubsystem.setArmAngle(startAngle + 90);
			
			if (Robot.armSubsystem.getArmAngle() > startAngle+90) {
				step = Step.TWO;
				Robot.chassisSubsystem.setSpeed(0.0, 0.0);
			}
			return;
		}
		
	}

	@Override
	protected boolean isFinished() {
		if (step == Step.ONE) { return false; }
		return true;
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

}
