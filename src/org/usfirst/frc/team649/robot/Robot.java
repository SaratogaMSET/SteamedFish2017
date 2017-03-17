package org.usfirst.frc.team649.robot;

import java.util.concurrent.ScheduledExecutorService;

import org.opencv.videoio.VideoCapture;
import org.usfirst.frc.team649.autonomousSequences.AutoFullSequence;
import org.usfirst.frc.team649.autonomousSequences.BlueSideGearShootMiddle;
import org.usfirst.frc.team649.drivetrain.DrivetrainSubsystem;
import org.usfirst.frc.team649.drivetrain.LeftDTPID;
import org.usfirst.frc.team649.drivetrain.RightDTPID;
import org.usfirst.frc.team649.gearcommands.SetFunnelCommand;
import org.usfirst.frc.team649.gearcommands.SetGearFlap;
import org.usfirst.frc.team649.intakecommands.SetIntakeWedgePistons;
import org.usfirst.frc.team649.robot.commands.RunCommpresorCommand;
import org.usfirst.frc.team649.robot.runnables.InitializeServerSocketThread;
import org.usfirst.frc.team649.robot.subsystems.CameraSwitcher;
import org.usfirst.frc.team649.robot.subsystems.GearSubsystem;
import org.usfirst.frc.team649.robot.subsystems.HoodSubsystem;
//import org.usfirst.frc.team649.robot.subsystems.HopperSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team649.robot.subsystems.LeftShooter;
import org.usfirst.frc.team649.robot.subsystems.LidarSubsystem;
import org.usfirst.frc.team649.robot.subsystems.RightShooter;
import org.usfirst.frc.team649.robot.subsystems.ShooterSubsystem;
import org.usfirst.frc.team649.robot.subsystems.TurretSubsystem;
import org.usfirst.frc.team649.shootercommands.ShooterPIDLeft;
import org.usfirst.frc.team649.shootercommands.TurretPID;
import org.usfirst.frc.team649.shootercommands.TurretPIDABS;
import org.usfirst.frc.team649.util.Center;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// for logging to file and reading parameters from file ********************
import java.util.*;
import java.io.*;
//**************************************************************************

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
	public static LeftShooter shootLeft;
	public static RightShooter shootRight;
	public static ShooterSubsystem shoot;
	SendableChooser<Command> chooser = new SendableChooser<>();
	public boolean prevStateShooting;
	public static DrivetrainSubsystem drive;
	public static LeftDTPID leftDT;
	public static RightDTPID rightDT;
	public static LidarSubsystem lidar;
	public static GearSubsystem gear;

	
	public static TurretSubsystem turret;
	public static HoodSubsystem hood;
	public static RunCommpresorCommand rcc;
	public static AutoFullSequence afs;
	public static CameraSwitcher camera;
	// public static HopperSubsystem hopper;

	public static boolean isPIDActiveLeft;
	public static boolean isPIDActiveRight;
	public static boolean isPIDActive;
	public static boolean isIntakeFlapDown;
	public static boolean isGearFlickOut;
	public static boolean isIntakeRunning;
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
	public static double visionDistance;
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

	public static boolean isTurretMax, isTurretMin;

	//more cam stuff still dont touch
	AxisCamera axis;
	UsbCamera usb;
	VideoSink server;
	boolean previousCameraTrigger = false;
	CvSink cvsink1;
	CvSink cvsink2;

	// for logging to file and reading parameters from file ********************
	public static final boolean debugMode=true;  // set this to true to enable
	public static final int maxTick=3000;   // at 50 samples/s need 3000 for 1 minute
	public static int tick;
	public static int value;
	public static double[] logTimer, logDtLftSpd, logDtRtSpd, logDtLftDist, logDtRtDist, logLftFly, logRtFly;
	public static double[] logHoodLft, logHoodRt, logHangI, logLidarDist;
	Map<String, Float> mapRobotParams = new HashMap<String, Float>();
	String inputLine;
	String[] inputWords = new String[100];
	public static String inFileRobotParam="/home/admin/param.txt", outFileLog="/home/admin/logfile.csv";
	//*********************************************************************************
	
	/**
	 * 
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		drive = new DrivetrainSubsystem();
		compressor = new Compressor();
		intake = new IntakeSubsystem();
		shoot = new ShooterSubsystem();
		shootLeft = new LeftShooter();
		shootRight = new RightShooter();
		camera = new CameraSwitcher();
		gear = new GearSubsystem();
		isPIDActive = false;
		isPIDActiveLeft = false;
		isPIDActiveRight = false;
		timer = new Timer();
		isPIDTurn = false;
		isTurretMax = false;
		isTurretMin = false;
		isIntakeRunning = false;
		isGearFlickOut = gear.getGearFlapSolPos();
		isIntakeFlapDown = intake.isIntakeDown();
		drive.resetEncoders();
		turret = new TurretSubsystem();
		turret.startingPos = turret.getTurretEncoderValue();
		hood = new HoodSubsystem();
		//begin camera init stuff plz dont touch it messes everything up
		axis = CameraServer.getInstance().addAxisCamera(RobotMap.Camera.axisName, RobotMap.Camera.axisPort);
		usb = CameraServer.getInstance().startAutomaticCapture();
		server = CameraServer.getInstance().getVideo();
		cvsink1 = new CvSink("axis");
		cvsink2 = new CvSink("usb");
		cvsink1.setSource(axis);
		cvsink1.setEnabled(true);
		cvsink2.setSource(usb);
		cvsink2.setEnabled(true);
		//end camera init here, theres still a third section!!!

		// for logging to file and reading parameters from file ********************
		if (debugMode) {	
			logTimer = new double[maxTick];
			logDtLftSpd=new double[maxTick];
			logDtRtSpd=new double[maxTick];
			//logDtLftDist=new double[maxTick];
			//logDtRtDist=new double[maxTick];
			//logLftFly=new double[maxTick];
			//logRtFly=new double[maxTick];
			//logHoodLft=new double[maxTick];
			//logHoodRt=new double[maxTick]; 
			logHangI=new double[maxTick];
			//logLidarDist=new double[maxTick];	
		} //end debugMode
		//*******************************************************************************

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		// for logging to file and reading parameters from file ********************
		if (debugMode){
		
			System.out.printf("In disabledInit, tick: %d\n",tick);

			//Output to log file
			if( tick > 10) {
				for( int i=0; i<tick; i++) {
					System.out.printf("timer %d %f\n", i, logTimer[i]);
				}
				try {
					PrintWriter writer = new PrintWriter(outFileLog, "UTF-8");
					
				    if (mapRobotParams.containsKey("varDrvTrnP")) writer.printf("varDrvTrnP, %f\r\n", mapRobotParams.get("varDrvTrnP"));
				    if (mapRobotParams.containsKey("varDrvTrnI")) writer.printf("varDrvTrnI, %f\r\n", mapRobotParams.get("varDrvTrnI"));
				    if (mapRobotParams.containsKey("varDrvTrnD")) writer.printf("varDrvTrnD, %f\r\n", mapRobotParams.get("varDrvTrnD"));
				    if (mapRobotParams.containsKey("varDrvTrnTol")) writer.printf("varDrvTrnTol, %f\r\n", mapRobotParams.get("varDrvTrnTol"));
				    if (mapRobotParams.containsKey("varDrvTrnDrift")) writer.printf("varDrvTrnDrift, %f\r\n", mapRobotParams.get("varDrvTrnDrift"));
				    if (mapRobotParams.containsKey("varDrvTrnMaxP")) writer.printf("varDrvTrnMaxP, %f\r\n", mapRobotParams.get("varDrvTrnMaxP"));
	
					for( int i=0; i<tick; i++) {
						writer.printf("%d, %f", i, logTimer[i]);
						writer.printf(", %f, %f", logDtLftSpd[i], logDtRtSpd[i]);
						//writer.printf(", %f, %f", logDtLftDist[i], logDtRtDist[i]);
						//writer.printf(", %f, %f", logLftFly[i],logRtFly[i]);
						writer.printf(", %f", logHangI[i]);
						writer.printf("\r\n");
					}
					writer.close();
									
				} catch (FileNotFoundException | UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
			//input from param file
			float value;
			System.out.printf("Reading in parameter values\n");
			try 
			{
				BufferedReader inputFile = new BufferedReader(new FileReader(inFileRobotParam)); //open file
				while ((inputLine = inputFile.readLine()) != null) { 
					if (!inputLine.isEmpty()) { // process non-empty lines
						inputWords=inputLine.split("\\s+"); //break one line into 2 words
		                if (!inputWords[1].isEmpty()) {
		                	value=Float.parseFloat(inputWords[1]); //convert 2nd word into float
		                	mapRobotParams.put(inputWords[0], value);//add parameter and value to map
		                	System.out.format("%s, %s\n",inputWords[0], inputWords[1]);
		                }  
					} 
		        } //end while
				inputFile.close(); //close file
		    } 
		    catch (Exception e) 
		    {   
		         e.printStackTrace();
		         System.exit(1);
		    } 
	
			// Read in parameters from file
		    System.out.format("Reading parameters from file\r\n");
			if (mapRobotParams.containsKey("varDrvTrnP")) System.out.printf("varDrvTrnP, %f\r\n", mapRobotParams.get("varDrvTrnP"));
			if (mapRobotParams.containsKey("varDrvTrnI")) System.out.printf("varDrvTrnI, %f\r\n", mapRobotParams.get("varDrvTrnI"));
			if (mapRobotParams.containsKey("varDrvTrnD")) System.out.printf("varDrvTrnD, %f\r\n", mapRobotParams.get("varDrvTrnD"));
			if (mapRobotParams.containsKey("varDrvTrnTol")) System.out.printf("varDrvTrnTol, %f\r\n", mapRobotParams.get("varDrvTrnTol"));
			if (mapRobotParams.containsKey("varDrvTrnDrift")) System.out.printf("varDrvTrnDrift, %f\r\n", mapRobotParams.get("varDrvTrnDrift"));
			if (mapRobotParams.containsKey("varDrvTrnMaxP")) System.out.printf("varDrvTrnMaxP, %f\r\n", mapRobotParams.get("varDrvTrnMaxP"));
	      
		} //end debug mode
		
		//********************************************************************************************

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
		// for logging to file and reading parameters from file ********************
		tick=0;
		//*********************************************************************************
		// new AutoFullSequence(drive.getPotPosition(), drive.getAutoGoal(),
		// drive.getAlliance());
		// drive.resetEncoders();
		// new DrivetrainPIDCommand(-90, true).start();
		new BlueSideGearShootMiddle().start();
		 //new TurretPIDABS(90.0).start();
		// new DriveForwardTurn().start();
		
		

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("P", drive.encoderDrivePID.getP());
		SmartDashboard.putNumber("I", drive.encoderDrivePID.getI());
		SmartDashboard.putNumber("D", drive.encoderDrivePID.getD());

		doTheDash();
	}

	@Override
	public void teleopInit() {
		//new RunCommpresorCommand(true).start();
		timer.reset();
		timer.start();
		drive.resetEncoders();
		// new SetGearFlap(false).start();
		// new SetIntakeWedgePistons(false).start();
		prevStateFunnelFlap = false;
		new SetFunnelCommand(false).start();
		// for logging to file and reading parameters from file ********************
		tick=0;
		//*********************************************************************************

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
//		SmartDashboard.putNumber("absolute encoders for 90", turret.translateAngleToABS(90));
		Scheduler.getInstance().run();
		drive.driveFwdRot(Robot.oi.driver.getForward(), -Robot.oi.driver.getRotation());
	
		if(oi.operator.intakeFlapUp()){
			if(isIntakeRunning){
				isIntakeFlapDown = false;
			}
			new SetIntakeWedgePistons(true).start();
		}else if(oi.operator.intakeFlapDown()){
			new SetIntakeWedgePistons(false).start();

		}
		if(oi.operator.runIntake()){
			if(!isIntakeRunning){
				isIntakeFlapDown = true;
			}
			intake.setIntakeRollerMotor(1.0);
			if(isIntakeFlapDown = false){
				new SetIntakeWedgePistons(false).start();

			}else{
				new SetIntakeWedgePistons(true).start();
			}
		}else{
			intake.setIntakeRollerMotor(0.0);
		}
		if(oi.operator.getGearFlap()){
			
				new SetGearFlap(false).start();
			
			
		}else{
			new SetGearFlap(true).start();

		}
//		if (oi.operator.getShoot()) {
//			
//			shootLeft.simpleBangBang(0.58, 0.62, 1600, 1800, 1500);
//			shootRight.simpleBangBang(0.58, 0.62, 1600, 1800, 1500);
////			new ShooterPIDLeft(1600).start();
//			
//		} else {
//			Robot.shootLeft.setLeftFlywheel(0.0);
//			Robot.shootRight.setRightFlywheel(0.0);
//		}
//		if (oi.operator.runFeederWheel()) {
//			shoot.setFeedMotor(1.0);
//		} else {
//			shoot.setFeedMotor(0.0);
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
//
//		}
//		if (oi.driver.shiftUp()) {
//			drive.shift(true);
//		} else {
//			drive.shift(false);
//		}
//		if (oi.operator.setFunnelPistonDown()) {
//			new SetFunnelCommand(true).start();
//		} else if  (oi.operator.setFunnelPistonUp()) {
//			new SetFunnelCommand(false).start();
//		}
//		if (oi.operator.runIntakeIn()) {
//			intake.setIntakeRollerMotor(1.0);
//		} else {
//			intake.setIntakeRollerMotor(0.0);
//		}
//		
//		if (oi.operator.runFunnelMotors()) {
//			gear.setFunnelMotor(-1.0);
//		} else {
//			gear.setFunnelMotor(0);
//		}
//		Robot.turret.manualSet(oi.operator.getTurret()/2);
//		turret.countCurrentPosition();
//		
//		//Camera Tele-Operated Periodic Stuff goes here, dont touch below this line!!
//		if(oi.driver.kyleSwitch() && !previousCameraTrigger)
//		{
//			DriverStation.getInstance().reportError("Running Camera 2!(Axis)",true);
//			NetworkTable.getTable("").putString("CameraSelection", axis.getName());
//			server.setSource(axis);
//		}
//		else if(oi.driver.kyleSwitch() && previousCameraTrigger)
//		{
//			DriverStation.getInstance().reportError("Running Camera 1!(USB)",true);
//			NetworkTable.getTable("").putString("CameraSelection", usb.getName());
//			server.setSource(usb);
//		}
//		previousCameraTrigger = oi.driver.kyleSwitch() && oi.driver.kyleSwitch();
		doTheDash();
		//ok you can touch from here on out, not recommended tho plz dont touch actually :)
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

	public static synchronized void updateVisionDistance(double d) {
		if (d > 10.0) {
			visionDistance = d;
		} else {
			System.out.println("Target Not in View");
			visionDistance = 0.0;
		}
	}

	public static void doTheDash() {
		// SmartDashboard.putString("Shifting?", drive.getShift());
		// SmartDashboard.putNumber("Goal Potentiometer", drive.getAutoGoal());
		// SmartDashboard.putNumber("Auto Position Potentiometer",
		// drive.getAutoGoal());
		// SmartDashboard.putString("Alliance Color Indicator", "1 is blue, 2 is
		// red!");
		// SmartDashboard.putNumber("Alliance Color", drive.getAlliance());
		// SmartDashboard.putBoolean("Is Compressor Running",
		// rcc.getCompressorState());
		SmartDashboard.putNumber("Agitator Curret", Robot.shoot.hooperMotor.getOutputCurrent());
		 SmartDashboard.putNumber("Right Motor Front Current",
		 drive.motors[0].getOutputCurrent());
		 SmartDashboard.putNumber("Right Motor Back Current",
		 drive.motors[1].getOutputCurrent());
		 SmartDashboard.putNumber("Left Motor Front Current",
		 drive.motors[2].getOutputCurrent());
		 SmartDashboard.putNumber("Left Motor Back Current",
		 drive.motors[3].getOutputCurrent());
		SmartDashboard.putNumber("Encoder Left Speed", drive.leftEncoderSpeed());
		SmartDashboard.putNumber("Encoder Right Speed", drive.rightEncoderSpeed());
		SmartDashboard.putNumber("Encoder Left Distance", drive.getDistanceDTLeft());
		SmartDashboard.putNumber("Encoder Right Distance", drive.getDistanceDTRight());
		SmartDashboard.putNumber("Left Ein", shootLeft.getLeftFlywheelEin());
		SmartDashboard.putNumber("Right Ein", shootRight.getRightFlywheelEin());
		SmartDashboard.putNumber("Hood Servo Right", hood.servoRight.getRaw());
		SmartDashboard.putNumber("Hood Servo Left", hood.servoLeft.getRaw());
		SmartDashboard.putNumber("Slider", oi.operator.getSlider());
		SmartDashboard.putBoolean("IR Break", gear.isGearLoaded());
		SmartDashboard.putNumber("Turret Encoder", turret.getTotalDist());
		SmartDashboard.putNumber("Raw Turret", turret.getTurretEncoderValue());
		SmartDashboard.putNumber("Hang Current", intake.currentMonitoring());
//		SmartDashboard.putNumber("Left DT Current", drive.getCurrentDTLeft()); 
//		SmartDashboard.putNumber("Right DT Current", drive.getCurrentDTRight());

		// SmartDashboard.putBoolean("is Close To Low", turret.is)
		// SmartDashboard.putNumber("Turret Encoder Value",
		// turret.getTurretEncoderValue());
		// SmartDashboard.putNumber("Current Turret Position",
		// turret.getCurrentPosition());
		// SmartDashboard.putBoolean("Is gear Loaded", gear.isGearLoaded());H
		// SmartDashboard.putBoolean("Intake flap Solenoid Open?",
		// gear.getIntakeFlapPos());
		// SmartDashboard.putBoolean("Gear Solenoid Position",
		// gear.getGearFlapSolPos());
		// SmartDashboard.putBoolean("At Hanging Limit?", hang.getHangLimit());
		// SmartDashboard.putBoolean("At Hook Sol", hang.getHookSol());
		// SmartDashboard.putNumber("Einstein Flywheel Count LEFT",
		// shoot.getLeftFlywheelEin());
		// SmartDashboard.putNumber("Einstein Flywheel COUNT RIGHT",
		// shoot.getRightFlywheelEin());
		// SmartDashboard.putNumber("Flywheel RPM LEFT",
		// shoot.getLeftFlywheel());
		// SmartDashboard.putNumber("Flywheel RPM RIGHT",
		// shoot.getRightFlywheel());
		// SmartDashboard.putBoolean("Is Right HAL tripped?",
		// turret.getRightHal());
		// SmartDashboard.putBoolean("Is Left HAL tripped?",
		// turret.getLeftHal());
		// SmartDashboard.putNumber("Distance Turret Moved",
		// turret.getEncoderDistance());
		// SmartDashboard.putString("Current Autonomous Position",
		// afs.getPos());
		// SmartDashboard.putString("Current Autonomous Goal", afs.getGoal());

		// for logging to file and reading parameters from file ********************
		if (debugMode) {	
			if (tick<maxTick) 
			{
				logTimer[tick]=timer.get();
				logDtLftSpd[tick]=drive.leftEncoderSpeed();
				logDtRtSpd[tick]=drive.rightEncoderSpeed();
				//logDtLftDist[tick]=drive.getDistanceDTLeft();
				//logDtRtDist[tick]=drive.getDistanceDTRight();
				//logLftFly[tick]=shoot.getLeftFlywheelEin();
				//logRtFly[tick]=shoot.getRightFlywheelEin();
				//logHoodLft[tick]=hood.servoRight.getRaw();
				//logHoodRt[tick]=hood.servoLeft.getRaw();
				logHangI[tick]=intake.currentMonitoring();
				//logLidarDist[tick]=	lidar.getDistance();	
			
				tick++;
			} // end maxTick check
		} //end debugMode
		//**************************************************************************************
	}

}
