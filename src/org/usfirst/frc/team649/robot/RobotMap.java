package org.usfirst.frc.team649.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static final int OPERATOR_JOYSTICK = 0;
	public static final int DRIVE_JOYSTICK_HORIZONTAL = 2;
	public static final int DRIVE_JOYSTICK_VERTICAL = 1;
	public static final int AUTO_SELECTOR_POT_PORT = 0;

	public static class Drivetrain {

//		public static final int[] RIGHT_SIDE_ENCODER = { 6, 7 };
//		public static final int[] LEFT_SIDE_ENCODER = { 8, 9 };
		//S&A
		public static final int[] LEFT_SIDE_ENCODER = { 4, 5 };
		public static final int[] RIGHT_SIDE_ENCODER = { 6, 7 };
		// FR,BR,BL,FL
		public static final int[] MOTOR_PORTS = { 13, 14, 15, 16 };
		public static final int[] RIGHT_DRIVE_SOL = { 2, 0, 1 };
		public static final int[] LEFT_DRIVE_SOL = { 2, 2, 3 };
		public static final int GYRO_PORT = 0;
		
		public static final int SELECTOR_POT = 3;
		public static final int ALLIANCE_POT = 1;
	}

	public static class Intake {
		public static final int INTAKE_MOTOR_PORT_LEFT = 7;
		public static final int INTAKE_MOTOR_PORT_RIGHT = 6;
		// public static final int HOOPER_FEEDER_PORT = 6;
		public static final int[] LEFT_INTAKE_SOL = { 1, 4, 5 };
		public static final int[] RIGHT_INTAKE_SOL = { 1, 6, 7 };

	}

	public static class Shooter {
		public static final int SHOOTER_FEEDER_PORT = 3;
		public static final int LEFT_SHOOTER_FLYWHEEL_PORT = 9;
		public static final int RIGHT_SHOOTER_FLYWHEEL_PORT = 8;
		public static final int LEFT_SHOOTER_EIN_PORT = 9;
		public static final int RIGHT_SHOOTER_EIN_PORT = 8;
		public static final int HOOPER_PORT = 10;
	}

	public static class Hood {
		public static final int HOOD_SERVO_RIGHT_PORT = 0;
		public static final int HOOD_SERVO_LEFT_PORT = 1;
	}

	public static class Turret {
		public static
		final int TURRET_MOTOR_PORT = 11;
		//public static final int[] TURRET_ABS_ENCODER_PORT = { 8, 9 };
		public static final int TURRET_ABS_ENCODER_PORT = 2;
		public static final int TURRET_HALL = 1;
	}

	public static class Gear {
		public static final int GEAR_ROLLER_PORT = 4;
//		public static final int GEAR_IR_PORT = 2;
		public static final int[] GEAR_SOL_PORT = { 2, 6, 7 };
		public static final int[] GEAR_FUNNEL_PORT = { 2, 4, 5 };
	}

	


	public static class Camera {
		public static final String axisPort = "10.6.49.35";
		public static final String axisName = "axis0";
		public static final int axisResWidth = 320;
		public static final int axisResHeight = 480;
		public static final int axisFPS = 30;
		public static final int usbResWidth = 320;
		public static final int usbResHeight = 480;
		public static final int usbFPS = 30;
		public static final int usbPort = 1;
		public static final String backupAxisPort = "";
		public static final int backupAxisResWidth = 320;
		public static final int backupAxisResHeight = 480;
		public static final int backupAxisFPS = 30;
		public static boolean useSecondAxisCamera = false; 
	}
}

