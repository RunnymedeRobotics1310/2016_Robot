package robot;

public class Field {
	
	public enum Goal {
		LEFT ("Left", 60),
		CENTER ("Center", 145),
		RIGHT ("Right", 220);
		
		private final String stringValue;
		private final int requiredDistance;
		
		Goal (String stringValue, int requiredDistance) {
			this.stringValue = stringValue;
			this.requiredDistance = requiredDistance;
		}
		
		public static Goal toEnum(String stringValue) {
			for(Goal goal: Goal.values()) {
				if(goal.stringValue.equals(stringValue)) {
					return goal;
				}
			}
			System.out.println("Goal value (" + stringValue + ") is not a valid goal string");
			return null;
		}
		
		public int getRequiredDistance() {
			return this.requiredDistance;
		}
	}
	
	public enum Lane {
		CLOSE ("Close"),
		FAR ("Far");
		
		private final String stringValue;
		
		Lane(String stringValue) {
			this.stringValue = stringValue;
		}
		
		public static Lane toEnum(String stringValue) {
			for(Lane lane: Lane.values()) {
				if(lane.stringValue.equals(stringValue)) {
					return lane;
				}
			}
			System.out.println("Lane value (" + stringValue + ") is not a valid lane string");
			return null;
		}
	}
	
	public enum Slot { 
		
		//FIXME: APPROX VALUES USED, CHANGE BADLY.
		ONE   (1, 0), 
		TWO   (2, 48), 
		THREE (3, 96), 
		FOUR  (4, 144), 
		FIVE  (5, 192); 
	
		private final int intValue;
		
		private final double distanceToLeftWall;
		
		Slot (int intValue, double distanceToLeftWall) {
			this.intValue = intValue;
			this.distanceToLeftWall = distanceToLeftWall;
		}
		
		public static Slot toEnum(int intValue) {
			for (Slot slot: Slot.values()) {
				if (slot.intValue == intValue)  {
					return slot;
				}
			}
			System.out.println("Slot value (" + intValue + ") is not a valid slot");
			return null;
		}

		public double getDistanceToLeftWall() {
			return distanceToLeftWall;
		}
		
	}
	
	public enum Defense {

		LOW_BAR         ("Low Bar"),
		RAMPARTS        ("Ramparts"),
		MOAT            ("Moat"),
		ROCK_WALL       ("Rock Wall"),
		ROUGH_TERRAIN   ("Rough Terrain"),
		PORTCULLIS      ("Portcullis"),
		CHEVAL_DE_FRISE ("Cheval de Frise"); 
	
		private final String stringValue;
		
		Defense (String stringValue) {
			this.stringValue = stringValue;
		}
		
		public static Defense toEnum(String stringValue) {
			for (Defense defense: Defense.values()) {
				if (defense.stringValue.equals(stringValue))  {
					return defense;
				}
			}
			System.out.println("Defense value (" + stringValue + ") is not a valid defense string");
			return null;
		}

	}
}
