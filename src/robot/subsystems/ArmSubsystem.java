package robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.arm.JoystickArmCommand;
import robot.utils.R_AbsoluteEncoder;
import robot.utils.R_PIDController;
import robot.utils.R_PIDInput;
import robot.utils.R_SafetyVictor;
import robot.utils.R_Subsystem;
import robot.utils.R_Victor;

/**
 * This subsystem has control over all arm related functions of the robot.
 */
public class ArmSubsystem extends R_Subsystem {

	DigitalInput armUpperLimitSwitch = new DigitalInput(RobotMap.SensorMap.ARM_UPPER_LIMIT.port);
	DigitalInput armLowerLimitSwitch = new DigitalInput(RobotMap.SensorMap.ARM_LOWER_LIMIT.port);
	R_SafetyVictor armDeployMotor = new R_SafetyVictor(RobotMap.MotorMap.ARM_DEPLOY_MOTOR, armUpperLimitSwitch, armLowerLimitSwitch);
	R_Victor armIntakeMotor = new R_Victor(RobotMap.MotorMap.ARM_INTAKE_MOTOR);

	R_AbsoluteEncoder armEncoder = new R_AbsoluteEncoder(2, RobotMap.ANALOG_OFFSET);

	R_PIDInput armPIDInput = new R_PIDInput() {
		@Override
		public double pidGet() {
			return (armEncoder.getAngle()-RobotMap.ArmLevel.LOWER_LIMIT.getAngle()) / 
					(RobotMap.ArmLevel.UPPER_LIMIT.getAngle()-RobotMap.ArmLevel.LOWER_LIMIT.getAngle());
		}
	};

	/*PIDOutput armPIDOutput = new PIDOutput() {

		@Override
		public void pidWrite(double speed) {
			if (speed >  0.4) { speed =  0.4; }
			if (speed < -0.4) { speed = -0.4; }
			armDeployMotor.set(speed);
		}
	};
	
	R_PIDController armPID = new R_PIDController(1.5, 0.0, 0.0, 0.0, armPIDInput, armPIDOutput);*/

	private boolean armDeployed = false;

	public void init() {

	}

	public void initDefaultCommand() {
		setDefaultCommand(new JoystickArmCommand());
	}

	@Override
	public void periodic() {
//		if (!getArmLowerLimit()) {
//			armEncoder.setEncoderAngle(RobotMap.ArmLevel.LOWER_LIMIT.angle);
//		}
		if (!getArmUpperLimit()) {
			armEncoder.setEncoderAngle(RobotMap.ArmLevel.UPPER_LIMIT.angle);
		}
		//armPID.calculate();
	}

	/**
	 * 
	 * @return Whether the arm is currently deployed
	 */
	public boolean getArmDeploy() {
		return armDeployed;
	}

	public double getArmAngle() {
		return armEncoder.getAngle();
	}
	
	public void deployArm() {
		if (armDeployed) {
			armDeployMotor.set(1.0);
		} else {
			armDeployMotor.set(-1.0);
		}
	}

	public boolean getArmLowerLimit() {
		return armLowerLimitSwitch.get();
	}

	public boolean getArmUpperLimit() {
		return armUpperLimitSwitch.get();
	}

	public void startArmIntake() {
		armIntakeMotor.set(1.0);
	}

	public void stopArmIntake() {
		armIntakeMotor.set(0.0);
	}
	
	public void startArmIntakeReverse() {
		armIntakeMotor.set(-1.0);
	}

	/**
	 * 
	 * @param armDeployed
	 *            The arm is considered up when this value is true.
	 */
	public void setArmDeploy(boolean armDeployed) {
		this.armDeployed = armDeployed;
	}

	public void resetArmEncoder() {
		armEncoder.reset();
	}

	/*public void setArmAngle(double armAngle) {
		// Set the arm to the appropriate angle and hold with a PID.
		armPID.setSetpoint((armAngle-RobotMap.ArmLevel.LOWER_LIMIT.getAngle()) /
				(RobotMap.ArmLevel.UPPER_LIMIT.getAngle()-RobotMap.ArmLevel.LOWER_LIMIT.getAngle()));
		if (!armPID.isEnabled()) {
			armPID.enable();
		}

	}

	public void disableArmPID() {
		armPID.disable();
	}*/

	/**
	 * 
	 * @param armDeployed
	 *            The arm is considered up when this value is true.
	 */
	public void setArmSpeed(double armSpeed) {
		armDeployMotor.set(armSpeed);
	}
	
	public void armUp() {
			setArmSpeed(0.3);
	}
	
	public void armDown() {
			setArmSpeed(-0.3);
	}
	
	public void armStop() {
		setArmSpeed(0);
	}

	@Override
	public void updateDashboard() {
		
	}
	
	@Override
	public void debugDashboard() {
		SmartDashboard.putNumber("Arm Encoder Angle", armEncoder.getAngle());
		SmartDashboard.putBoolean("Arm Max Limit Switch", armUpperLimitSwitch.get());
		SmartDashboard.putBoolean("Arm Min Limit Switch", armLowerLimitSwitch.get());
		//SmartDashboard.putData("Arm PID", armPID);
	}
}
