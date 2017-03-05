package org.usfirst.frc.team649.robot;

import java.util.concurrent.ScheduledExecutorService;

import org.opencv.videoio.VideoCapture;
import org.usfirst.frc.team649.autonomousSequences.AutoFullSequence;
import org.usfirst.frc.team649.drivetrain.DrivetrainSubsystem;
import org.usfirst.frc.team649.drivetrain.LeftDTPID;
import org.usfirst.frc.team649.drivetrain.RightDTPID;
import org.usfirst.frc.team649.gearcommands.SetFunnelCommand;
import org.usfirst.frc.team649.gearcommands.SetGearFlap;
import org.usfirst.frc.team649.intakecommands.SetIntakeWedgePistons;
import org.usfirst.frc.team649.robot.RobotMap.Camera;
import org.usfirst.frc.team649.robot.commands.RunCommpresorCommand;
import org.usfirst.frc.team649.robot.runnables.InitializeServerSocketThread;
import org.usfirst.frc.team649.robot.subsystems.CameraSwitcher;
import org.usfirst.frc.team649.robot.subsystems.GearSubsystem;
import org.usfirst.frc.team649.robot.subsystems.HangSubsystem;
import org.usfirst.frc.team649.robot.subsystems.HoodSubsystem;
//import org.usfirst.frc.team649.robot.subsystems.HopperSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team649.robot.subsystems.LidarSubsystem;
import org.usfirst.frc.team649.robot.subsystems.ShooterSubsystem;
import org.usfirst.frc.team649.robot.subsystems.TurretSubsystem;
import org.usfirst.frc.team649.util.Center;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	public static Compressor compressor;
	public static IntakeSubsystem intake;
	public static ShooterSubsystem shoot;
	SendableChooser<Command> chooser = new SendableChooser<>();
	public boolean prevStateShooting;
	public static DrivetrainSubsystem drive;
	public static LeftDTPID leftDT;
	public static RightDTPID rightDT;
	public static LidarSubsystem lidar;
	public static GearSubsystem gear;
	public static HangSubsystem hang;
	public static TurretSubsystem turret;
	public static HoodSubsystem hood;
	public static RunCommpresorCommand rcc;
	public static AutoFullSequence afs;
	public static CameraSwitcher camera;
	// public static HopperSubsystem hopper;

	public static boolean isPIDActiveLeft;
	public static boolean isPIDActiveRight;
	public static boolean isPIDActive;
	public static boolean isTurretPIDActive;
	public static boolean isShooterRunning;
	public static boolean prevStateIntakePistons;
	public static boolean prevStateGearFlap;
	public static boolean prevStateFunnelFlap;
	public static boolean robotEnabled = false;
	public static boolean isPIDTurn;
	public UsbCamera lifecam = new UsbCamera("cam2", 1);
	public VideoCapture video = new VideoCapture();
	public AxisCamera axiscam = new AxisCamera("axis", "10.6.49.35");
	// Vision Paths
	public static String initPath = "/home/admin/initializeAdb.sh";
	public static String pullPath = "/home/admin/pullTextFile.sh";
	public static String endPath = "/home/admin/endApp.sh";
	public static String visionFile = "/home/admin/vision.txt";
	/*
	 * Vision Need to change the values for this years vision
	 */
	public static String ip = "N/A";
	public static Center currCenter;
	public static double prevTimeCenterUpdated = 0;
	public static double rateCenterUpdated = 0;
	public static int PORT = 5805;
	public static boolean isRIOServerStarted; // makes sure we are connected
	public static Timer timer;
	public static boolean isReceivingData; // makes sure data is being sent
											// regularly
	public static double VISION_INIT_TIME_OUT = 6; // seconds
	public static double MAX_PERIOD_BETWEEN_RECIEVING_DATA = 1.5; // seconds
	public static ScheduledExecutorService adbTimer;
	public static InitializeServerSocketThread initThread;
	public static double SCREEN_X = 288;
	public static double SCREEN_Y = 352;
	public static double GOOD_X = SCREEN_X / 2.0;
	public static double GOOD_Y = SCREEN_Y / 2.0;
	public static double FIELD_OF_VIEW = 0.8339;
	public static double CENTER_TOLERANCE = 8;

	/**
	 * 
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		drive = new DrivetrainSubsystem();
//		compressor = new Compressor();
//		intake = new IntakeSubsytem();
//		shoot = new ShooterSubsystem();
//		camera = new CameraSwitcher();
		// hopper = new HopperSubsystem();
		// prevStateShooting = false;
		// leftDT = new LeftDTPID();
		// rightDT = new RightDTPID();
		// lidar = new LidarSubsystem();
		//gear = new GearSubsystem();
		// hang = new HangSubsystem();
		isPIDActive = false;
		isPIDActiveLeft = false;
		isPIDActiveRight = false;
		isTurretPIDActive = false;
		timer = new Timer();
		isShooterRunning = false;
		isPIDTurn = false;
		//doTheDash();
		drive.resetEncoders();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		// new AutoFullSequence(drive.getPotPosition(), drive.getAutoGoal(),
		// drive.getAlliance());
		
	}
	

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
//		doTheDash();
	}

	@Override
	public void teleopInit() {
		//new RunCommpresorCommand(true).start();
		timer.reset();
		timer.start();
		// prevStateGearFlap = false;
		// new SetGearFlap(false).start();
		// prevStateIntakePistons = false;
		// new SetIntakePistons(false).start();
		prevStateFunnelFlap = false;
		//new SetFunnelCommand(false).start();

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {

		Scheduler.getInstance().run();

		drive.driveFwdRot(Robot.oi.driver.getForward(), Robot.oi.driver.getRotation());
//		if (oi.operator.runFunnelMotors()) {
//			intake.setIntakeRollerMotor(0.5);
//		} else {
//			intake.setIntakeRollerMotor(0.0);
//		}
//		if (oi.operator.getShoot()) {
//			Robot.shoot.setLeftFlywheel(oi.operator.getSlider());
//			Robot.shoot.setRightFlywheel(oi.operator.getSlider());
//		} else {
//			Robot.shoot.setLeftFlywheel(0.0);
//			Robot.shoot.setRightFlywheel(0.0);
//		}
//		if (oi.operator.setDownIntakePistons()) {
//			new SetIntakeWedgePistons(true).start();
//		} else if (oi.operator.setUpIntakePistons()) {
//			new SetIntakeWedgePistons(false).start();
//		}
//		if (oi.operator.setGearFlapIn()) {
//			new SetGearFlap(true).start();
//		} else if (oi.operator.setGearFlapOut()) {
//			new SetGearFlap(false).start();
//		}
//		if (oi.operator.setFunnelPistonDown()) {
//			new SetFunnelCommand(true).start();
//		} else if (oi.operator.setFunnelPistonUp()) {
//			new SetFunnelCommand(false).start();
//		}
//		if (oi.operator.runFeedIn()) {
//			shoot.setFeedMotor(0.7);
//		} else if (oi.operator.runFeedOut()) {
//			shoot.setFeedMotor(-0.7);
//		} else {
//			shoot.setFeedMotor(0);
//		}
//		if (oi.operator.runAgitator()) {
//			shoot.setHooperIn(1.0);
//			shoot.setHooperOutRaw(1.0);
//		} else {
//			shoot.setHooperIn(0.0);
//			shoot.setHooperOutRaw(0.0);
//		}
//		if (oi.operator.runFunnelMotors()) {
//			gear.setFunnelMotor(0.5);
//		} else {
//			gear.setFunnelMotor(0);
//		}
//		Robot.turret.manualSet(oi.operator.getTurret());
		// SmartDashboard.putNumber("lidar", lidar.getDistance());
		// SmartDashboard.putBoolean("lidar", lidar.getAderess());
		/*
		 * if(oi.operator.getShoot() && !prevStateShooting){ new
		 * BangBangThenShootCommand(shoot.TARGET_RPM, shoot.MIN_SPEED_RIGHT,
		 * shoot.MAX_SPEED_RIGHT, shoot.MAX_SPEED_LEFT, shoot.MIN_SPEED_LEFT,
		 * shoot.MIN_RPM, shoot.MAX_RPM).start(); } prevStateShooting =
		 * oi.operator.getShoot();
		 * 
		 */
		doTheDash();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {

	}

	public static boolean isCenterWithinTolerance(boolean x, boolean y) {
		boolean x_inrange = Math.abs(GOOD_X - currCenter.x) < CENTER_TOLERANCE;
		boolean y_inrange = Math.abs(GOOD_Y - currCenter.y) < CENTER_TOLERANCE;

		if (x && y) {
			return x_inrange && y_inrange;
		} else if (x) {
			return x_inrange;
		} else if (y) {
			return y_inrange;
		} else {
			return false;
		}
	}

	public static synchronized void updateCenter(Center c) {
		if (c != null) {
			currCenter = new Center(c.x, c.y);
			rateCenterUpdated = 1 / (timer.get() - prevTimeCenterUpdated); // seconds
			prevTimeCenterUpdated = timer.get();

		} else {
			System.out.println("ERROR: passed in null center");
		}
	}

	public static void doTheDash() {
//		SmartDashboard.putString("Shifting?", drive.getShift());
//		SmartDashboard.putNumber("Goal Potentiometer", drive.getAutoGoal());
//		SmartDashboard.putNumber("Auto Position Potentiometer", drive.getAutoGoal());
//		SmartDashboard.putString("Alliance Color Indicator", "1 is blue, 2 is red!");
//		SmartDashboard.putNumber("Alliance Color", drive.getAlliance());
//		SmartDashboard.putBoolean("Is Compressor Running", rcc.getCompressorState());
//		SmartDashboard.putNumber("Right Motor Front Current", drive.motors[0].getOutputCurrent());
//		SmartDashboard.putNumber("Right Motor Back Current", drive.motors[1].getOutputCurrent());
//		SmartDashboard.putNumber("Left Motor Front Current", drive.motors[2].getOutputCurrent());
//		SmartDashboard.putNumber("Left Motor Back Current", drive.motors[3].getOutputCurrent());
		SmartDashboard.putNumber("Encoder Left Speed", drive.leftEncoderSpeed());
		SmartDashboard.putNumber("Encoder Right Speed", drive.rightEncoderSpeed());
		SmartDashboard.putNumber("Encoder Left Distance", drive.getDistanceDTLeft());
		SmartDashboard.putNumber("Encoder Right Distance", drive.getDistanceDTRight());

//		SmartDashboard.putBoolean("Is gear Loaded", gear.isGearLoaded());
//		SmartDashboard.putBoolean("Intake flap Solenoid Open?", gear.getIntakeFlapPos());
//		SmartDashboard.putBoolean("Gear Solenoid Position", gear.getGearFlapSolPos());
//		SmartDashboard.putBoolean("At Hanging Limit?", hang.getHangLimit());
//		SmartDashboard.putBoolean("At Hook Sol", hang.getHookSol());
//		SmartDashboard.putNumber("Einstein Flywheel Count LEFT", shoot.getLeftFlywheelEin());
//		SmartDashboard.putNumber("Einstein Flywheel COUNT RIGHT", shoot.getRightFlywheelEin());
//		SmartDashboard.putNumber("Flywheel RPM LEFT", shoot.getLeftFlywheel());
//		SmartDashboard.putNumber("Flywheel RPM RIGHT", shoot.getRightFlywheel());
//		SmartDashboard.putBoolean("Is Right HAL tripped?", turret.getRightHal());
//		SmartDashboard.putBoolean("Is Left HAL tri pped?", turret.getLeftHal());
//		SmartDashboard.putNumber("Distance Turret Moved", turret.getEncoderDistance());
//		SmartDashboard.putString("Current Autonomous Position", afs.getPos());
//		SmartDashboard.putString("Current Autonomous Goal", afs.getGoal());
	}

}

