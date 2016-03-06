package robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;
import robot.commands.defenses.PortcullisOpenCommand;

public class JoystickArmCommand extends Command {

    public JoystickArmCommand() {
       requires(Robot.armSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if (Robot.oi.getCancel()){
    		Scheduler.getInstance().add(new ArmIntakeReverseCommand());
    	}
    	
    	if (Robot.oi.getArmPIDOverride()) {
    		Robot.armSubsystem.disableArmPID();
        	Robot.armSubsystem.setArmSpeed(Robot.oi.getArmSpeed());
    	} else {

	    	if (Robot.oi.getArmAngle() >= 0.0) {
	    		Robot.armSubsystem.setArmAngle(Robot.oi.getArmAngle());
	    	}
    	}
    	
    	// Look for the Portcullis command
    	if (Robot.oi.getPortcullisOpenButton()) {
    		Scheduler.getInstance().add(new PortcullisOpenCommand());
    	}
    	
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
