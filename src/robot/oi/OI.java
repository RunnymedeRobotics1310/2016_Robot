
package robot.oi;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Field.Defense;
import robot.Field.Goal;
import robot.Field.Lane;
import robot.Field.Slot;
import robot.Field.Target;
import robot.RobotMap;
import robot.commands.auto.AutoDriveAndShootCommand;
import robot.commands.auto.base.DriveToProximity;
import robot.utils.R_Extreme3DPro_GameController;
import robot.utils.R_GameController;
import robot.utils.R_GameController.Axis;
import robot.utils.R_GameController.Button;
import robot.utils.R_GameController.Stick;
import robot.utils.R_GameController.Trigger;
import robot.utils.R_GameControllerFactory;

public class OI {
	
	private NetworkTable table;
	private NetworkTableOI networkTableOI = new NetworkTableOI();
	
	public OI() {
		table = NetworkTable.getTable("GRIP/TargetInfo");
	}
	
	public enum ButtonMap  {
		//Driver Controls
		OUTER_INTAKE_BOULDER(Button.LEFT_BUMPER),
		EXTAKE_BOULDER(Button.B),
		RESET_GYRO(Button.BACK),
		CALIBRATE_GYRO(Button.START),
		CANCEL_COMMAND(Button.X),


		
		//Operator Controls
		SHOOT_BOULDER(Button.BUTTON1),
		WIND_UP_SHOOTER(Button.BUTTON2),
		WIND_UP_BANK_SHOT(Button.BUTTON3),
		SHOT_ALIGN(Button.BUTTON4),
		SHOT_NO_LONGER_ALIGN(Button.BUTTON5),
		ARM_PID_OVERRIDE(Button.BUTTON6),
		CLIMB(Button.BUTTON7),
		SCISSOR_RELEASE(Button.BUTTON8),
		//ROTATE_ARM_MIN_POS(Button.BUTTON9),
		ROTATE_ARM_PICKUP_POS(Button.BUTTON12),
		ROTATE_ARM_DRIVE_POS(Button.BUTTON11),
		/*ROTATE_ARM_PORTCULLIS_POS(Button.BUTTON12)*/;
		
		private Button button;

		ButtonMap(Button button) {
			this.button = button;
		}

		public Button getButton() {
			return this.button;
		}
	}
	
	public enum TriggerMap {
		
		//Driver Controls
		INNER_INTAKE_BOULDER(Trigger.LEFT), SHIFT_GEARS(Trigger.RIGHT);
		
		private Trigger trigger;

		TriggerMap(Trigger trigger) {
			this.trigger = trigger;
		}

		public Trigger getTrigger() {
			return this.trigger;
		}
	}
	
	public enum POVMap {
		UP(0), RIGHT(90), DOWN(180), LEFT(270);
		
		private int angle;
		
		POVMap(int angle) {
			this.angle = angle;
		}
		
		public int getAngle() {
			return this.angle;
		}
	}
	
	public enum Nudge { LEFT, RIGHT, NONE };
	
	private R_GameController driverStick = R_GameControllerFactory.getGameController(0);
	private R_Extreme3DPro_GameController operatorStick = new R_Extreme3DPro_GameController(1);
	
	private boolean operatorStickPreviouslyCentered = false;
	
	private AutoChooser autoChooser = new AutoChooser();
	
	public boolean getNoLongerAlignShotButton() {
		return operatorStick.getButton(ButtonMap.SHOT_NO_LONGER_ALIGN.getButton());
	}
	
	public boolean getAlignShotButton() {
		return operatorStick.getButton(ButtonMap.SHOT_ALIGN.getButton());
	}
	

	public boolean getOuterIntakeBoulderButton() {
		return driverStick.getButton(ButtonMap.OUTER_INTAKE_BOULDER.getButton());
	}
	
	public double getInnerIntakeBoulderButton() {
		return driverStick.getTrigger(TriggerMap.INNER_INTAKE_BOULDER.getTrigger());
	}
	
	public double getGearShiftButton() {
		return driverStick.getTrigger(TriggerMap.SHIFT_GEARS.getTrigger());
	}
	
	public boolean getExtakeBoulderButton() {
		return driverStick.getButton(ButtonMap.EXTAKE_BOULDER.getButton());
	}
	
	public boolean getResetGyroButton() {
		return driverStick.getButton(ButtonMap.RESET_GYRO.getButton());
	}
	
	public boolean getWindUpBankShotButton() {
		return operatorStick.getButton(ButtonMap.WIND_UP_BANK_SHOT.getButton());
	}
	
	public boolean getWindUpShooterButton() {
		return operatorStick.getButton(ButtonMap.WIND_UP_SHOOTER.getButton());
	}
	
	public boolean getShootButton() {
		return operatorStick.getButton(ButtonMap.SHOOT_BOULDER.getButton());
	}
	
	public boolean getBallStuckButton() {
		return driverStick.getButton(Button.A);
	}
	
	//public boolean getRotateArmMinPosButton() {
	//	return operatorStick.getButton(ButtonMap.ROTATE_ARM_MIN_POS.getButton());
	//}
	
	public boolean getRotateArmLowPosButton() {
		return operatorStick.getButton(ButtonMap.ROTATE_ARM_PICKUP_POS.getButton());
	}
	
	public boolean getRotateArmDrivePosButton() {
		return operatorStick.getButton(ButtonMap.ROTATE_ARM_DRIVE_POS.getButton());
	}
	
	//public boolean getRotateArmPortcullisPosButton() {
		//return operatorStick.getButton(ButtonMap.ROTATE_ARM_PORTCULLIS_POS.getButton());
	//}
	
	public double getArmAngle() {
		/*if (getRotateArmMinPosButton()) {
			return RobotMap.ArmLevel.GROUND_LEVEL.angle;
		}*/
		if (getRotateArmLowPosButton()) {
			return RobotMap.ArmLevel.LOW_LEVEL.angle;
		}
		if (getRotateArmDrivePosButton()) {
			return RobotMap.ArmLevel.DRIVE_LEVEL.angle;
		}
		/*if (getRotateArmPortcullisPosButton()) {
			return RobotMap.ArmLevel.PORTCULLIS_LEVEL.angle;
			}
		*/

		return -1.0;
	}
	
	public double getArmSpeed() {
		return operatorStick.getAxis(Axis.Y) * operatorStick.getAxis(Axis.Y) * operatorStick.getAxis(Axis.Y);
	}

	
	public boolean getArmPIDOverride() {
		return operatorStick.getButton(ButtonMap.ARM_PID_OVERRIDE.getButton());
	}

	
	/*public boolean getPortcullisOpenButton() {
		return operatorStick.getButton(ButtonMap.PORTCULLIS_OPEN.getButton());
	}*/
	
	public boolean getClimbButton() {
		return operatorStick.getButton(ButtonMap.CLIMB.getButton()); 
	}
	
	public boolean getScissorReleaseButton() {
		return operatorStick.getButton(ButtonMap.SCISSOR_RELEASE.getButton()); 
	}
	
	public double getSpeed() {
		double joystickValue = driverStick.getAxis(Stick.LEFT, Axis.Y);
		return -Math.round(joystickValue * Math.abs(joystickValue) * 100) / 100.0;
	}

	public double getTurn() {
		double joystickValue = driverStick.getAxis(Stick.RIGHT, Axis.X);
		return Math.round(joystickValue * Math.abs(joystickValue) * 100) / 100.0;
	}
	
	public double getPOV() {
		return driverStick.getPOVAngle();
	}
	
	public Nudge getNudge() {
		if (operatorStickPreviouslyCentered) {
			if (operatorStick.getAxis(Axis.X) < -.5) {
				operatorStickPreviouslyCentered = false;
				return Nudge.LEFT;
			}
			if (operatorStick.getAxis(Axis.X) > .5) {
				operatorStickPreviouslyCentered = false;
				return Nudge.RIGHT;
			}
		}
		return Nudge.NONE;
	}
	
	public boolean getRotateLeft() {
		return (getPOV() == POVMap.LEFT.getAngle());
	}
	
	public boolean getRotateRight() {
		return (getPOV() == POVMap.RIGHT.getAngle());
	}

	public double getShootSpeed() {
		double stickValue = operatorStick.getAxis(Axis.SLIDER);
		return Math.round(stickValue * Math.abs(stickValue) * 100) / 100.0;
	}
	
	public boolean getCancel() {
		return driverStick.getButton(ButtonMap.CANCEL_COMMAND.getButton());
	}
	
	public boolean getGyroReset() {
		return driverStick.getButton(ButtonMap.RESET_GYRO.getButton());
	}

	public boolean getGyroCalibrate() {
		return driverStick.getButton(ButtonMap.CALIBRATE_GYRO.getButton());
	}
	
	public Command getAutoCommand() {

		// Main Auto Mode
		switch (autoChooser.getAutoMode()) {
		case "Do Nothing":
			return null;
		case "Drive and Shoot":
			return new AutoDriveAndShootCommand(getSlot(), getDefense(), getLane(), getTarget(), getGoal());
		case "Drive to Proximity":
			return new DriveToProximity(0.5, 0);
		default:
			return null;
		}
	}
	
	public Defense getDefense() {
		return Defense.toEnum(autoChooser.getSelectedDefence());
	}

	public Slot getSlot() {
		return Slot.toEnum(autoChooser.getSelectedSlot());
	}
	
	public Lane getLane() {
		return Lane.toEnum(autoChooser.getSelectedDistance());
	}

	public Goal getGoal() {
		return Goal.toEnum(autoChooser.getSelectedGoal());
	}
	
	public Target getTarget() {
		return Target.toEnum(autoChooser.getSelectedTarget());
	}

	
    public double getVisionTargetCenter() {
    	double [] xValues = table.getNumberArray("centerX", new double [0]);
    	if (xValues.length != 1) {
    		return RobotMap.NO_VISION_TARGET;
    	}
    	return xValues[0];
    }


	/**
	 * Update the periodic running elements of the dashboard
	 * <p>
	 * i.e. all toggle buttons
	 */
	public void periodic() {
		if (Math.abs(operatorStick.getAxis(Axis.X)) < 0.5) {
			operatorStickPreviouslyCentered = true;
		}
	}

	/**
	 * Put any items on the dashboard
	 */
	public void updateDashboard() {
		SmartDashboard.putString("Driver Controllers", driverStick.toString());
		SmartDashboard.putString("Operator Controllers", operatorStick.toString());
		SmartDashboard.putNumber("Vision Target Center", getVisionTargetCenter());
		SmartDashboard.putBoolean("Target Lock", (getVisionTargetCenter() != RobotMap.NO_VISION_TARGET));
	}
	
}
