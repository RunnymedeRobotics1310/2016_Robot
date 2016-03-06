package robot.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.shoot.JoystickShootCommand;
import robot.utils.R_PIDController;
import robot.utils.R_PIDInput;
import robot.utils.R_Subsystem;
import robot.utils.R_Victor;

public class ShooterSubsystem extends R_Subsystem {

	public enum IntakeReverseSpeed {
		LOW, HIGH
	}

	R_Victor intakeMotor = new R_Victor(RobotMap.MotorMap.INTAKE_MOTOR);
	R_Victor shooterMotor = new R_Victor(RobotMap.MotorMap.SHOOTER_MOTOR);

	DigitalInput boulderProximitySensor = new DigitalInput(RobotMap.SensorMap.BOULDER_PROXIMITY_SENSOR.port);

	Counter shooterSpeedEncoder = new Counter(RobotMap.SensorMap.SHOOTER_SPEED_ENCODER.port);

	Encoder intakeEncoder = new Encoder(RobotMap.EncoderMap.INTAKE_ENCODER.ch1, RobotMap.EncoderMap.INTAKE_ENCODER.ch2);

	Solenoid shooterRail = new Solenoid(RobotMap.Pneumatics.SHOOTER_RAIL_DOWN.pcmPort);

	R_PIDInput intakeLockPIDInput = new R_PIDInput() {
		@Override
		public double pidGet() {
			return -(RobotMap.EncoderMap.INTAKE_ENCODER.inverted ? -1.0 : 1.0) * intakeEncoder.getDistance();
		}
	};

	R_PIDInput shooterSpeedPIDInput = new R_PIDInput() {
		@Override
		public double pidGet() {
			return getShooterSpeed() / 100.0;
		}
	};

	R_PIDController intakeLockPID = new R_PIDController(0.01, 0.0, 0.0, 0.0, intakeLockPIDInput, intakeMotor);

	R_PIDController shooterSpeedPID = new R_PIDController(1.0, 0.0, 0.0, 1.0, shooterSpeedPIDInput, shooterMotor);

	// Initialize the subsystem to Disable the intake PID.
	public ShooterSubsystem() {
		intakeLockPID.setSetpoint(0.0);
		intakeLockPID.disable();
	}

	private boolean boulderRetracted = false;

	public double getIntakeDistance() {
		return intakeEncoder.getDistance();
	}

	public double getIntakeSpeed() {
		return intakeEncoder.getRate();
	}

	public boolean getRailPosition() {
		return shooterRail.get();
	}

	public double getShooterSpeed() {
		return shooterSpeedEncoder.getRate();
	}

	public void init() {
		// Initialize the shooter speed controller to count
		// rpms. One count = 1 rpm.
		shooterSpeedEncoder.setDistancePerPulse(1.0);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new JoystickShootCommand());
	}

	public boolean isBoulderLoaded() {
		return !boulderProximitySensor.get();
	}

	public boolean isBoulderRetracted() {
		return boulderRetracted;
	}

	public void lockIntakeMotor() {
		intakeEncoder.reset();
		intakeLockPID.reset();
		intakeMotor.set(0.0);
		intakeLockPID.enable();
	}

	@Override
	public void periodic() {
		intakeLockPID.calculate();
		shooterSpeedPID.calculate();
	}

	public void resetIntakeEncoder() {
		intakeEncoder.reset();
	}

	public void setBoulderRetracted(boolean ballRetracted) {
		this.boulderRetracted = ballRetracted;
	}

	public void setIntakeMotorReverse(IntakeReverseSpeed intakeReverseSpeed) {

		if (intakeLockPID.isEnabled()) {
			intakeLockPID.disable();
		}

		if (intakeReverseSpeed == IntakeReverseSpeed.LOW) {
			intakeMotor.set(-0.3);
		} else {
			intakeMotor.set(-1.0);
		}
	}

	public void setRailPosition(boolean b) {
		shooterRail.set(b);
	}

	public void startIntakeMotor() {

		if (intakeLockPID.isEnabled()) {
			intakeLockPID.disable();
		}

		intakeMotor.set(1.0); // Speed of intake when taking in a boulder
	}

	public void startShooterMotor() {
		shooterMotor.set(1.0);
	}

	public void startShooterMotorReverse() {
		shooterMotor.set(-0.5);
	}

	public void setShooterSpeed(double speedSetPoint) {
		if (!shooterSpeedPID.isEnabled()) {
			shooterSpeedPID.enable();
		}
		shooterSpeedPID.setSetpoint(speedSetPoint);
	}

	public void stopIntakeMotor() {

		if (intakeLockPID.isEnabled()) {
			intakeLockPID.disable();
		}

		intakeMotor.set(0.0);
	}

	public void stopShooterMotor() {
		if (shooterSpeedPID.isEnabled()) {
			shooterSpeedPID.disable();
		}
		shooterMotor.set(0.0);
	}
	
	public double getShootSpeedSetPoint() {
		return shooterSpeedPID.getSetpoint();
	}

	@Override
	public void updateDashboard() {
		//SmartDashboard.putData("Intake Lock PID", intakeLockPID);
		//SmartDashboard.putNumber("Intake Lock PID Output", intakeMotor.get());
		SmartDashboard.putData("Intake motor", intakeMotor);
		SmartDashboard.putNumber("Shooter Speed", getShooterSpeed());
		SmartDashboard.putNumber("Intake Speed", getIntakeSpeed());
		SmartDashboard.putNumber("Intake Distance", getIntakeDistance());
		SmartDashboard.putBoolean("Boulder Loaded", isBoulderLoaded());
		SmartDashboard.putData("Shooter Speed PID", shooterSpeedPID);
	}
}
