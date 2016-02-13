package robot.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import robot.R_Subsystem;
import robot.R_Talon;
import robot.RobotMap;
import robot.commands.ShooterDefaultCommand;

public class ShooterSubsystem extends R_Subsystem {

	Talon intakeMotor  = new R_Talon(RobotMap.MotorMap.INTAKE_MOTOR);
	Talon shooterMotor = new R_Talon(RobotMap.MotorMap.SHOOTER_MOTOR);
	
	DigitalInput boulderProximitySensor = new DigitalInput(RobotMap.SensorMap.BOULDER_PROXIMITY_SENSOR.port);
	
	Counter shooterSpeedEncoder  = new Counter(RobotMap.SensorMap.SHOOTER_SPEED_ENCODER.port);
	
	DoubleSolenoid shooterRail = 
			new DoubleSolenoid(RobotMap.Pneumatics.SHOOTER_RAIL_UP.pcmPort, 
					           RobotMap.Pneumatics.SHOOTER_RAIL_DOWN.pcmPort);
	
	public void init() {
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ShooterDefaultCommand());
	}

	public void startShooterMotor() {
		shooterMotor.set(1.0);
	}

	public void startIntakeMotor() {
		intakeMotor.set(0.2); // Speed of intake when taking in a boulder
	}
	
	public void stopIntakeMotor() {
		intakeMotor.set(0.0);
	}
	
	public double getShooterSpeed() {
		return shooterSpeedEncoder.getRate();
	}
	
	public boolean isBoulderLoaded() {
		return !boulderProximitySensor.get();
	}
	
	@Override
	public void periodic() {
	}

	@Override
	public void updateDashboard() {
	}
}