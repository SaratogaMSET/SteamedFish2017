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
		public static final int[] LEFT_SIDE_ENCODER = {6,7};
		public static final int[] RIGHT_SIDE_ENCODER = {8,9};
		// FR,BR,BL,FL
		public static final int[] MOTOR_PORTS = { 1, 2, 3, 4 };
		public static final int[] LEFT_ENCODER_PORTS = {0,1};
		public static final int[] RIGHT_ENCODER_PORTS = {2,3};
	}
	public static class Intake{
		public static final int INTAKE_MOTOR_PORT = 5;
		public static final int HOOPER_FEEDER_PORT = 6;
		public static final int LEFT_INTAKE_SOL = 4;//Last number made up need to add on port map
		public static final int RIGHT_INTAKE_SOL = 5;//Last number made up need to add on port map

	}
	public static class Hooper {
		//add never rests for agitator
	}
	public static class Shooter {
		public static final int SHOOTER_FEEDER_PORT = 7;
		public static final int LEFT_SHOOTER_FLYWHEEL_PORT = 8;
		public static final int RIGHT_SHOOTER_FLYWHEEL_PORT = 9;
		public static final int LEFT_SHOOTER_EIN_PORT = 6;
		public static final int RIGHT_SHOOTER_EIN_PORT = 7;
	}
	public static class Hood {
		public static final int HOOD_SERVO_PORT = 0;
	}
	public static class Turret {
		public static final int TURRET_MOTOR_PORT = 10;
		public static final int TURRET_ABS_ENCODER_PORT = 8;
		public static final int TURRET_HALL_EFFECT_PORT = 9;
	}
	public static class Gear {
		public static final int GEAR_ROLLER_PORT = 11;
		public static final int GEAR_FUNNEL_SERVO_PORT = 1;
		public static final int GEAR_IR_PORT = 11;
		public static final int GEAR_SOL_PORT = 10;
	}
	public static class Hanger {
		public static final int HANGER_PISTON = 12;
		public static final int HANGER_MOTOR_PORT = 12;
		public static final int HANGER_LIMIT_SWITCH_PORT = 13;
	}

}
