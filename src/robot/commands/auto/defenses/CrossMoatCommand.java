package robot.commands.auto.defenses;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.RobotMap.ArmLevel;
import robot.commands.arm.SetArmLevelCommand;
import robot.commands.auto.base.DriveToDistance;
import robot.commands.drive.ShiftGearCommand;
import robot.subsystems.ChassisSubsystem.Gear;

public class CrossMoatCommand extends CommandGroup {

	public CrossMoatCommand() {
		addSequential(new SetArmLevelCommand(ArmLevel.DRIVE_LEVEL));
		addSequential(new ShiftGearCommand(Gear.HIGH));
		addSequential(new DriveToDistance(1.0, 0, 150));
	}
}
