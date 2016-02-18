package robot.commands.shoot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.subsystems.ShooterSubsystem.IntakeReverseSpeed;

public class RetractBoulderCommand extends Command {

	public RetractBoulderCommand() {
		requires(Robot.shooterSubsystem);
	}

	protected void initialize() {
		Robot.shooterSubsystem.resetIntakeEncoder();
	}

	protected void execute() {
		Robot.shooterSubsystem.setIntakeMotorReverse(IntakeReverseSpeed.LOW);
		if (Robot.shooterSubsystem.getRailPosition() != Value.kForward) {
			Robot.shooterSubsystem.setRailPosition(Value.kForward);
		}
	}

	protected boolean isFinished() {
		return (Math.abs(Robot.shooterSubsystem.getIntakeDistance()) > 90.0);
	}

	protected void end() {
		Robot.shooterSubsystem.stopIntakeMotor();
	}

	protected void interrupted() {
	}
}
