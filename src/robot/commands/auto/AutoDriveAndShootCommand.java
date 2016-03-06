package robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.Field.Defense;
import robot.Field.Goal;
import robot.Field.Lane;
import robot.Field.Slot;
import robot.commands.auto.base.DriveToCenterProximity;
import robot.commands.auto.base.DriveToDistance;
import robot.commands.auto.base.DriveToProximity;
import robot.commands.auto.base.DriveToUltraDistance;
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

public class AutoDriveAndShootCommand extends CommandGroup {	
	
	public AutoDriveAndShootCommand(Slot slot, Defense defense, Lane lane, Goal goal) {
		double waitTime = 4.0;
		double autoSpeed = 0.8;

		switch (defense) {
		case LOW_BAR:
			addSequential(new CrossLowBarCommand());
			break;
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

		// Rotate to 90 degrees, because that's what we always do.
		addSequential(new RotateToAngleCommand(90, waitTime));

		// If the ultasonic distance is not within threshold then
		// wait until the path is clear and then continue.
		addSequential(new WaitUntilPathClear(waitTime, slot));

		addSequential(new DriveToUltraDistance(autoSpeed, 90, goal.getRequiredDistance()));

		// Rotate to 0 degrees, because that's what we always do.
		addSequential(new RotateToAngleCommand(0, waitTime));

		if (goal != Goal.CENTER) {
			addSequential(new DriveToCenterProximity(autoSpeed, 0));
			addSequential(new DriveToDistance(autoSpeed, 0, -20));
		} else {
			addSequential(new DriveToProximity(autoSpeed-0.3, 0));
		}

		final int rampAngle = 60;
		
		switch (goal) {
		case LEFT:
			addSequential(new RotateToAngleCommand(rampAngle, waitTime));
			addSequential(new DriveToProximity(autoSpeed, rampAngle));
			break;
		case CENTER:
			// do nothing.
			break;
		case RIGHT:
			addSequential(new RotateToAngleCommand(360 - rampAngle, waitTime));
			addSequential(new DriveToProximity(autoSpeed, 360 - rampAngle));
			break;
		}

		// shoot
		addSequential(new SetupHighShotCommand());
		addSequential(new ShootHighGoalCommand());
	}
}
