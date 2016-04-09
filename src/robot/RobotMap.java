package robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    private final static boolean inverted = true;

    public final static double ANALOG_OFFSET = 310;
    
    public static double GROUND_LEVEL_ANGLE = 70.0;
    public static double LOWER_LIMIT_ANGLE = 95.0;
    public static double LOW_LEVEL_ANGLE = 100.0;
    public static double INTAKE_LEVEL_ANGLE = 135.0;
    public static double PORTCULLIS_LEVEL_ANGLE = 165.0;
    public static double SHOOT_LEVEL_ANGLE = 225.0;
    public static double DRIVE_LEVEL_ANGLE = 250.0;
    public static double BANK_SHOT_LEVEL_ANGLE = 325.0;
    public static double UPPER_LIMIT_ANGLE = 350.0;
    
    public enum ArmLevel {
    	GROUND_LEVEL(GROUND_LEVEL_ANGLE),
    	LOWER_LIMIT(LOWER_LIMIT_ANGLE),
    	LOW_LEVEL(LOW_LEVEL_ANGLE),
    	INTAKE_LEVEL(INTAKE_LEVEL_ANGLE),
    	PORTCULLIS_LEVEL(PORTCULLIS_LEVEL_ANGLE),
    	SHOOT_LEVEL(SHOOT_LEVEL_ANGLE),
    	DRIVE_LEVEL(DRIVE_LEVEL_ANGLE),
    	BANK_SHOT_LEVEL(BANK_SHOT_LEVEL_ANGLE),
    	UPPER_LIMIT(UPPER_LIMIT_ANGLE);
    	
    	public final double angle;
    	
    	ArmLevel(double angle) {
    		this.angle = angle;
    	}
    	
    	public double getAngle() {
    		return this.angle;
    	}
    }
    
    public enum MotorMap {
        LEFT_MOTOR      (0,  RobotMap.inverted),
        RIGHT_MOTOR     (1, !RobotMap.inverted),
        INTAKE_MOTOR    (2,  RobotMap.inverted),
        SHOOTER_MOTOR   (3,  RobotMap.inverted),
        ARM_DEPLOY_MOTOR(4, !RobotMap.inverted),
        ARM_INTAKE_MOTOR(5, !RobotMap.inverted),
        LEFT_WINCH      (7,  RobotMap.inverted),
        RIGHT_WINCH     (6, !RobotMap.inverted);

        public final int port;
        public final boolean inverted;

        MotorMap(int port, boolean invertedState) {
            this.port = port;
            this.inverted = invertedState;
        }
    }
    
    public enum SensorMap {
    	// Analog Ports
    	GYRO                      (0),
    	REAR_ULTRASONIC           (1),
    	ANGLE_ENCODER             (2),
    	FRONT_ULTRASONIC		  (3),
    	
    	// Digital Ports
    	SHOOTER_SPEED_ENCODER     (6),
    	LEFT_PROXIMITY_SENSOR     (7),
    	RIGHT_PROXIMITY_SENSOR    (8),
    	UPPER_PROXIMITY_SENSOR    (9),
    	BOULDER_PROXIMITY_SENSOR  (10),
    	ARM_LOWER_LIMIT           (12),
    	ARM_UPPER_LIMIT           (11);
    	
        public final int port;

        SensorMap(int port) {
            this.port = port;
        }
    }
    
    public enum EncoderMap {
    	LEFT (0, 1, 9000.0, 22000.0, 96.84, !RobotMap.inverted),
    	RIGHT(2, 3, 9000.0, 22000.0, 96.84,  RobotMap.inverted),
    	//FIXME: FIX THE LOW AND HIGH GEAR SPEED.
    	INTAKE_ENCODER(4, 5, 1800.0, 1800.0, 29.75, RobotMap.inverted);
    	//Counts per inch not accurate
    	
    	public final int ch1;
    	public final int ch2;
    	public final double maxRate;
    	public final double maxRateHigh;
    	public final double countsPerInch;
    	public final boolean inverted;
    	
    	EncoderMap(int ch1, int ch2, double maxRate, double maxRateHigh, double countsPerInch,
    			boolean inverted) {
    		this.ch1 = ch1;
    		this.ch2 = ch2;
    		this.maxRate = maxRate;
    		this.maxRateHigh = maxRateHigh;
    		this.countsPerInch = countsPerInch;
    		this.inverted = inverted;
    	}
    }
    
    public enum Pneumatics{
    	BALLSHIFTER   			(0),
    	SHOOTER_RAIL 			(2),
    	SCISSOR_LIFT_RELEASE    (3),
    	SCISSOR_LIFT_EXTEND1    (4),
    	SCISSOR_LIFT_RETRACT1   (5),
    	SCISSOR_LIFT_EXTEND2    (6),
    	SCISSOR_LIFT_RETRACT2   (7);
    	
       	//Pneumatics control module port
    	public final int pcmPort;
 
    	Pneumatics (int pcmPort){
    		this.pcmPort = pcmPort;
    	
    	} 
    }
    
    public enum UltrasonicDirection {
    	FRONT, REAR;
    }
}
