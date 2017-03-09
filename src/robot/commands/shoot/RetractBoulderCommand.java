package robot.commands.shoot;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.RobotMap;
import robot.subsystems.ShooterSubsystem.IntakeReverseSpeed;

/**
 * Retract boulder.
 * <p>
 * The retract boulder command has a complex set of movements to ensure the
 * boulder is sitting away from the shooter wheel before the wheel starts
 * spinning.
 * </p>
 * <p>
 * <p>
 * <strong>Step 1:</strong> spin the shooter motor in reverse so that the
 * boulder is touching the intake motors.<strong>
 * </p>
 * <p>
 * Step 2:</strong> retract the boulder using the intake motors.<strong>
 * </p>
 * <p>
 * Step 3:</strong> lock the intake rollers in place and stop the shooter motor
 * from spinning in reverse.
 * </p>
 * </p>
 *
 */
public class RetractBoulderCommand extends Command {

	private static boolean ARM_FULLY_UP = true;

	private boolean cancelButton = false;
	private boolean lockDelayStarted = false;
	private boolean retractStarted = false;
	private final boolean armPosition;

	public RetractBoulderCommand() {
		this(false);
	}

	public RetractBoulderCommand(boolean armPosition) {
		this.armPosition = armPosition;
		requires(Robot.shooterSubsystem);
		requires(Robot.armSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("initializing..");
		retractStarted = false;
		lockDelayStarted = false;
		Robot.shooterSubsystem.startShooterMotorReverse();
		Robot.shooterSubsystem.resetIntakeEncoder();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Start reversing the intake some time after the shooter starts
		// reversing
		if (!retractStarted) {
			if (timeSinceInitialized() > 0.5) {
				System.out.println("1");
				Robot.shooterSubsystem.resetIntakeEncoder();
				System.out.println("2");
				Robot.shooterSubsystem.setIntakeMotorReverse(IntakeReverseSpeed.LOW);
				if (armPosition == ARM_FULLY_UP) {
					System.out.println("Bank Shot Setup");
					Robot.armSubsystem.startArmIntake();
				} else {
				}
				System.out.println("3");
				Robot.shooterSubsystem.stopShooterMotor();
    			System.out.println("4");
				retractStarted = true;
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// If the cancel button is pressed, then end
		if (Robot.oi.getCancel()) {
			cancelButton = true;
			return true;
		}

		if (lockDelayStarted) {

			if (isTimedOut()) {
				return true;
			}

		} else {
			// Start the lock delay after shutting off the intake motor.
			// Give the motor a chance to stop before trying to lock it.
			
			if (Robot.shooterSubsystem.getIntakeDistance() < -230.0) {
				System.out.println("Stopping intake motor" + Robot.shooterSubsystem.getIntakeDistance());
				Robot.shooterSubsystem.stopIntakeMotor();
				setTimeout(timeSinceInitialized() + 0.3);
				lockDelayStarted = true;
			}

		}

		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.shooterSubsystem.stopShooterMotor();

		if (cancelButton) {
			Robot.shooterSubsystem.stopIntakeMotor();
			Robot.shooterSubsystem.setBoulderRetracted(false);
			Robot.shooterSubsystem.setRailPosition(false);
		} else {
			Robot.shooterSubsystem.lockIntakeMotor();
			Robot.shooterSubsystem.setBoulderRetracted(true);
		}

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}