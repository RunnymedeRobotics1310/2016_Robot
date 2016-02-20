package robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.Field.Defense;
import robot.Field.Goal;
import robot.Field.Lane;
import robot.Field.Slot;
import robot.commands.auto.base.DriveToDistance;
import robot.commands.auto.base.DriveToProximity;
import robot.commands.auto.base.DriveToUltraDistance;
import robot.commands.auto.base.WaitUntilPathClear;
import robot.commands.drive.RotateToAngle;
import robot.commands.shoot.SetupHighShotCommand;
import robot.commands.shoot.ShootHighGoalCommand;

public class AutoDriveAndShootCommand extends CommandGroup {

	public AutoDriveAndShootCommand(Slot slot, Defense defense, Lane lane, Goal goal) {
		double speed = 0.5;
		double waitTime = 4.0;

		switch (defense) {
		case LOW_BAR:
			addSequential(new DriveToDistance(speed, 0, 192));
			break;
		case MOAT:
			addSequential(new DriveToDistance(speed, 0, 192));
			break;
		case RAMPARTS:
			addSequential(new DriveToDistance(speed, 0, 192));
			break;
		case ROCK_WALL:
			addSequential(new DriveToDistance(speed, 0, 192));
			break;
		case ROUGH_TERRAIN:
			addSequential(new DriveToDistance(speed, 0, 192));
			break;
		case PORTCULLIS:
			break;
		case CHEVAL_DE_FRISE:
			break;

		}

		// If the far lane is selected, go for another 50 inches.
		if (lane == Lane.FAR) {
			addSequential(new DriveToDistance(speed, 0, 50));
		}

		// Rotate to 90 degrees, because that's what we always do.
		addSequential(new RotateToAngle(90, waitTime));

		// If the ultasonic distance is not within threshold then
		// wait until the path is clear and then continue.
		addSequential(new WaitUntilPathClear(waitTime, slot));

		switch (goal) {
		case LEFT:
			addSequential(new DriveToUltraDistance(speed, 90, Goal.LEFT.getRequiredDistance()));
			break;
		case CENTER:
			addSequential(new DriveToUltraDistance(speed, 90, Goal.CENTER.getRequiredDistance()));
			break;
		case RIGHT:
			addSequential(new DriveToUltraDistance(speed, 90, Goal.RIGHT.getRequiredDistance()));
			break;
		}

		// Rotate to 0 degrees, because that's what we always do.
		addSequential(new RotateToAngle(0, waitTime));

		addSequential(new DriveToProximity(speed, 0));

		if (goal != Goal.CENTER) {
			addSequential(new DriveToDistance(speed, 0, -20));
		}

		int rampAngle = 50;

		switch (goal) {
		case LEFT:
			addSequential(new RotateToAngle(rampAngle, waitTime));
			addSequential(new DriveToProximity(speed, rampAngle));
			break;
		case CENTER:
			// do nothing.
			break;
		case RIGHT:
			addSequential(new RotateToAngle(360 - rampAngle, waitTime));
			addSequential(new DriveToProximity(speed, 360 - rampAngle));
			break;
		}

		// shoot
		addSequential(new SetupHighShotCommand());
		addSequential(new ShootHighGoalCommand());

	}
}