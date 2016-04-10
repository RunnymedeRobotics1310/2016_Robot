package robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.Field.Defense;
import robot.Field.Goal;
import robot.Field.Lane;
import robot.Field.Slot;
import robot.Field.Target;
import robot.RobotMap;
import robot.RobotMap.ArmLevel;
import robot.RobotMap.UltrasonicDirection;
import robot.commands.arm.SetArmLevelCommand;
import robot.commands.auto.base.DriveToDistance;
import robot.commands.auto.base.DriveToProximity;
import robot.commands.auto.base.DriveToUltraDistance;
import robot.commands.auto.base.WaitCommand;
import robot.commands.auto.base.WaitUntilPathClear;
import robot.commands.auto.defenses.CrossChavelDeFriseCommand;
import robot.commands.auto.defenses.CrossLowBarCommand;
import robot.commands.auto.defenses.CrossMoatCommand;
import robot.commands.auto.defenses.CrossPortcullisCommand;
import robot.commands.auto.defenses.CrossRampartsCommand;
import robot.commands.auto.defenses.CrossRockWallCommand;
import robot.commands.auto.defenses.CrossRoughTerrainCommand;
import robot.commands.drive.RotateToAngleCommand;
import robot.commands.shoot.SetupHighShotCommand;
import robot.commands.shoot.ShootHighGoalCommand;
import robot.commands.shoot.ShootLowGoalCommand;

public class AutoDriveAndShootCommand extends CommandGroup {

	public AutoDriveAndShootCommand(Slot slot, Defense defense, Lane lane, Target target, Goal goal) {
		double waitTime = 4.0;
		double autoSpeed = 0.7;
		double rampAngle = 60;

		switch (defense) {
		case LOW_BAR:

			double angle;

			angle = (target == Target.LEFT) ? rampAngle : 0;

			addSequential(new CrossLowBarCommand());

			addParallel(new SetupHighShotCommand());

			if (target != Target.CENTER) {
				addSequential(new DriveToUltraDistance(autoSpeed, 0, 79, UltrasonicDirection.FRONT));
				addSequential(new RotateToAngleCommand(angle, 3));
				addSequential(new DriveToProximity(autoSpeed, angle));
			} else {
				addSequential(new RotateToAngleCommand(90, 3));
				addSequential(new DriveToUltraDistance(autoSpeed, 90, 200, UltrasonicDirection.REAR));
				addSequential(new RotateToAngleCommand(0, 3));
				addSequential(new DriveToProximity(autoSpeed, 0));
			}

			addSequential(new WaitCommand(0.5));
			addSequential(new ShootHighGoalCommand());

			if (target != Target.CENTER) {
				addSequential(new WaitCommand(0.5));
				addSequential(new RotateToAngleCommand(90, 3));
				addSequential(new DriveToUltraDistance(autoSpeed, 90, 10, UltrasonicDirection.REAR));
				addSequential(new RotateToAngleCommand(0, 3));
				addSequential(new SetArmLevelCommand(ArmLevel.LOW_LEVEL));
				//addSequential(new DriveToProximity(0.7, 0));
				addSequential(new DriveToDistance(autoSpeed, 0, -240));

			}

			return;

		case MOAT:
			addSequential(new CrossMoatCommand());
			break;
		case RAMPARTS:
			addSequential(new CrossRampartsCommand());
			break;
		case ROCK_WALL:
			addSequential(new CrossRockWallCommand());
			break;
		case ROUGH_TERRAIN:
			addSequential(new CrossRoughTerrainCommand());
			break;
		case PORTCULLIS:
			addSequential(new CrossPortcullisCommand());
			break;
		case CHEVAL_DE_FRISE:
			addSequential(new CrossChavelDeFriseCommand());
			break;
		}

		// If the far lane is selected, go for another 50 inches.
		if (lane == Lane.FAR) {
			addSequential(new DriveToDistance(autoSpeed, 0, 50));
		}

		// If the slot is 4 and the Goal is center, then the robot is
		// already lined up.
		if (!(slot == Slot.FOUR && target == Target.CENTER)) {

			// Rotate to 90 degrees, because that's what we always do.
			addSequential(new RotateToAngleCommand(90, waitTime));

			// If the ultrasonic distance is not within threshold then
			// wait until the path is clear and then continue.
			addSequential(new WaitUntilPathClear(waitTime, slot));

			double distance = target.getRequiredDistance();

			// If the slot is FIVE, then we will be going backwards and so we
			// need
			// to stop sooner, so tweak the distance.
			if (slot == Slot.FIVE) {
				distance += 15;
			}

			// addSequential(new DriveToDistance(autoSpeed, 90, 45));
			addSequential(new DriveToUltraDistance(autoSpeed, 90, distance, RobotMap.UltrasonicDirection.REAR));

			// Rotate to 0 degrees, because that's what we always do.
			addSequential(new RotateToAngleCommand(0, waitTime));
		}

		if (target != Target.CENTER) {

			addSequential(new DriveToUltraDistance(autoSpeed, 0, 33, RobotMap.UltrasonicDirection.FRONT));

			// addSequential(new DriveToCenterProximity(autoSpeed, 0));
			// addSequential(new DriveToDistance(autoSpeed, 0, -16));
		} else {
			addSequential(new DriveToProximity(autoSpeed - 0.3, 0));
		}

		switch (target) {
		case LEFT:
			addSequential(new RotateToAngleCommand(rampAngle, waitTime));
			addSequential(new DriveToProximity(autoSpeed - 0.4, rampAngle));
			break;
		case CENTER:
			// Do nothing.
			break;
		case RIGHT:
			addSequential(new RotateToAngleCommand(360 - rampAngle, waitTime));
			addSequential(new DriveToProximity(autoSpeed - 0.4, 360 - rampAngle));
			break;
		}

		// Shoot
		switch (goal) {
		case HIGH:
			addSequential(new SetupHighShotCommand());
			addSequential(new ShootHighGoalCommand());
			break;
		case LOW:
			addSequential(new DriveToDistance(0.4, rampAngle, 30));
			addSequential(new ShootLowGoalCommand());
			break;
		}
	}
}
