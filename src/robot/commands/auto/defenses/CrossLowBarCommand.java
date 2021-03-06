package robot.commands.auto.defenses;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.RobotMap.ArmLevel;
import robot.commands.arm.SetArmLevelCommand;
import robot.commands.auto.base.DriveToDistance;
import robot.commands.auto.base.DriveToProximity;

/**
 *	This CommandGroup contains the routine required to cross the Low Bar.
 */
public class CrossLowBarCommand extends CommandGroup {

	public CrossLowBarCommand() {
		addSequential(new SetArmLevelCommand(ArmLevel.LOW_LEVEL));
		addSequential(new DriveToProximity(0.7, 0));
		addSequential(new DriveToDistance(0.7, 0, 30));
		addParallel(new SetArmLevelCommand(ArmLevel.DRIVE_LEVEL));
		addSequential(new DriveToDistance(0.7, 0, 90));
	}
}
