package robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;
import robot.pids.PivotPID;

/**
 *
 */
public class PivotToAngleCommand extends Command {

	double angleSetpoint;

	public PivotToAngleCommand(double angle) {
		requires(Robot.chassisSubsystem);
		this.angleSetpoint = Robot.chassisSubsystem.getCurrentAngle() + angle;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		PivotPID.setEnabled(false);
		PivotPID.setSetpoint(angleSetpoint);
		PivotPID.setEnabled(true);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double leftSpeed;
		double rightSpeed;

		System.out.println("Setpoint:" + angleSetpoint);
		System.out.println("Difference:" + -Robot.chassisSubsystem.getAngleDifference(angleSetpoint));
		System.out.println("PIDOutput:" + PivotPID.getOutput());
		
		/*
		SmartDashboard.putNumber("Angle setpoint", angleSetpoint);
		SmartDashboard.putNumber("Angle difference", -Robot.chassisSubsystem.getAngleDifference(angleSetpoint));
		SmartDashboard.putNumber("AnglePIDOutput", PivotPID.getOutput());
		*/

		double turn = PivotPID.getOutput();
		
		if(turn > 0) {
			leftSpeed = turn;
			rightSpeed = 0;
		}
		else if (turn < 0) {
			leftSpeed = 0;
			rightSpeed = -turn;
		}
		else {
			leftSpeed = turn;
			rightSpeed = -turn;
		}

		System.out.println("Pivoting");
		Robot.chassisSubsystem.setSpeed(leftSpeed, rightSpeed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		double turn = Robot.oi.getTurn();
		if (Math.abs(turn) < 0.03) {
			return true;
		}
		return false;

	}

	// Called once after isFinished returns true
	protected void end() {
		PivotPID.setEnabled(false);
		/*
		 * Note: added motor stop incase it's not called by a separate command
		 * as in AutoGoStraightCommand calling GoStraightCommand
		 */
		Robot.chassisSubsystem.setSpeed(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
