package robot.commands.auto.defenses;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.RobotMap.ArmLevel;
import robot.commands.arm.SetArmLevelCommand;
import robot.commands.auto.base.DriveToDistance;
import robot.commands.auto.base.DriveToProximity;
import robot.commands.auto.base.WaitCommand;

public class CrossChavelDeFriseCommand extends CommandGroup {

	public CrossChavelDeFriseCommand() {
		addSequential(new SetArmLevelCommand(ArmLevel.DRIVE_LEVEL));
		addSequential(new DriveToProximity(0.5, 0));
		addSequential(new DriveToDistance(0.5, 0, 6));
		addSequential(new SetArmLevelCommand(ArmLevel.INTAKE_LEVEL));
		addSequential(new WaitCommand(1.0));
		addSequential(new DriveToDistance(0.5, 0, 100));
		addSequential(new SetArmLevelCommand(ArmLevel.DRIVE_LEVEL));
	}
}
