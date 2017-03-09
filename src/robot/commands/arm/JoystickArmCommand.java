package robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;

/**
 * Default command for the {@link robot.subsystems.ArmSubsystem ArmSubsystem}. This command manages all of the
 * arms's actions.
 */
public class JoystickArmCommand extends Command {

    public JoystickArmCommand() {
       requires(Robot.armSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	if (Robot.oi.getCancel()){
    		Scheduler.getInstance().add(new ArmIntakeReverseCommand());
    	}
    	
    	
    	else {
	    	if (Robot.oi.getArmAngle() >= 0.0) {
	    	}
    	}
    	
    	/* Look for the Portcullis command
    	if (Robot.oi.getPortcullisOpenButton()) {
    		Scheduler.getInstance().add(new PortcullisOpenCommand());
    	}
    	*/
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
