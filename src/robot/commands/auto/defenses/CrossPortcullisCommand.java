package robot.commands.auto.defenses;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.RobotMap.ArmLevel;
import robot.commands.arm.SetArmLevelCommand;
import robot.commands.auto.base.DriveToDistance;
import robot.commands.auto.base.DriveToProximity;

public class CrossPortcullisCommand extends CommandGroup {

	public CrossPortcullisCommand() {
		addSequential(new SetArmLevelCommand(ArmLevel.LOW_LEVEL));
		addSequential(new DriveToProximity(0.6, 0));
		addSequential(new DriveToDistance(1.0, 0, 100));
	}
}
