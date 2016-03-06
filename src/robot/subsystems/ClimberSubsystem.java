package robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.climber.JoystickClimberCommand;
import robot.utils.R_Subsystem;
import robot.utils.R_Victor;

public class ClimberSubsystem extends R_Subsystem {

	R_Victor leftWinchMotor = new R_Victor(RobotMap.MotorMap.LEFT_WINCH);
	R_Victor rightWinchMotor = new R_Victor(RobotMap.MotorMap.RIGHT_WINCH);

	DoubleSolenoid leftScissorPiston = new DoubleSolenoid(RobotMap.Pneumatics.SCISSOR_LIFT_EXTEND1.pcmPort, RobotMap.Pneumatics.SCISSOR_LIFT_RETRACT1.pcmPort);
	DoubleSolenoid rightScissorPiston = new DoubleSolenoid(RobotMap.Pneumatics.SCISSOR_LIFT_EXTEND2.pcmPort, RobotMap.Pneumatics.SCISSOR_LIFT_RETRACT2.pcmPort);

	// Initialize the subsystem to Disable the intake PID.
	public ClimberSubsystem() {
	}


	public void init() {
		scissorDown();
	}

	public void initDefaultCommand() {
		setDefaultCommand(new JoystickClimberCommand());
	}

	public void scissorUp() {
		leftScissorPiston.set(Value.kReverse);
		rightScissorPiston.set(Value.kReverse);
	}
	
	public void scissorDown() {
		leftScissorPiston.set(Value.kForward);
		rightScissorPiston.set(Value.kForward);
	}
	
	public void winchOn() {
		leftWinchMotor.set(1.0);
		rightWinchMotor.set(1.0);
		leftScissorPiston.set(Value.kOff);
		rightScissorPiston.set(Value.kOff);
	}
	
	public void winchOff() {
		leftWinchMotor.set(0.0);
		rightWinchMotor.set(0.0);
	}
	
	@Override
	public void periodic() {
	}


	@Override
	public void updateDashboard() {
		/*SmartDashboard.putData("Left Scissor", leftScissorPiston);
		SmartDashboard.putData("Right Scissor", rightScissorPiston);
		SmartDashboard.putData("Left Winch", leftWinchMotor);
		SmartDashboard.putData("Right Winch", rightWinchMotor);*/
	}
}
