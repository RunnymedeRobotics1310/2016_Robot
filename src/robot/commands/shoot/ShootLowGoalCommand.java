package robot.commands.shoot;
import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.RobotMap;
import robot.subsystems.ShooterSubsystem.IntakeReverseSpeed;

public class ShootLowGoalCommand extends Command {

	public ShootLowGoalCommand() {
		requires(Robot.shooterSubsystem);
		requires(Robot.armSubsystem);
		this.setTimeout(0.5);
	}

	@Override
	protected void execute() {
		Robot.shooterSubsystem.setIntakeMotorReverse(IntakeReverseSpeed.HIGH);
		Robot.shooterSubsystem.startShooterMotorReverse();
		
		Robot.armSubsystem.startArmIntake();
		Robot.armSubsystem.setArmAngle(RobotMap.ArmLevel.INTAKE_LEVEL.getAngle());
	}

	@Override
	protected void initialize() {
		Robot.shooterSubsystem.resetIntakeEncoder();
	}
	
	@Override
	protected void end() {
		Robot.shooterSubsystem.stopIntakeMotor();
		Robot.shooterSubsystem.stopShooterMotor();
		Robot.armSubsystem.stopArmIntake();
		Robot.armSubsystem.setArmAngle(RobotMap.ArmLevel.INTAKE_LEVEL.getAngle());
	}
	
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	@Override
	protected void interrupted() {
		
	}

}