package org.usfirst.frc.team649.robot;

//**************************************************************************
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
// for logging to file and reading parameters from file ********************
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.usfirst.frc.team649.autonomousSequences.AutoFullSequence;
import org.usfirst.frc.team649.autonomousSequences.BlueSideBoilerGearShoot;
import org.usfirst.frc.team649.autonomousSequences.BlueSideGearFarSide;
import org.usfirst.frc.team649.autonomousSequences.BlueSideGearShootMiddle;
import org.usfirst.frc.team649.autonomousSequences.BlueSideGearShootMiddleWithTimeout;
import org.usfirst.frc.team649.autonomousSequences.BlueSideNoGearShootMiddle;
import org.usfirst.frc.team649.autonomousSequences.RedSideBoilerGearShoot;
import org.usfirst.frc.team649.autonomousSequences.RedSideGearFarSide;
import org.usfirst.frc.team649.autonomousSequences.RedSideGearShootMiddle;
import org.usfirst.frc.team649.autonomousSequences.RedSideNoGearShootMiddle;
import org.usfirst.frc.team649.commandgroups.ResetTurretSequence;
import org.usfirst.frc.team649.drivetrain.DrivetrainSubsystem;
import org.usfirst.frc.team649.drivetrain.DrivetrainSubsystem.AllianceSelector;
import org.usfirst.frc.team649.drivetrain.LeftDTPID;
import org.usfirst.frc.team649.drivetrain.RightDTPID;
import org.usfirst.frc.team649.gearcommands.SetFunnelCommand;
import org.usfirst.frc.team649.gearcommands.SetGearFlap;
import org.usfirst.frc.team649.robot.commands.DriveForTime;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommand;
import org.usfirst.frc.team649.robot.commands.GyroTurnPID;
import org.usfirst.frc.team649.robot.commands.RunCommpresorCommand;
import org.usfirst.frc.team649.robot.commands.ShiftDT;
import org.usfirst.frc.team649.robot.commands.SwitchDTMode;
import org.usfirst.frc.team649.robot.runnables.InitializeServerSocketThread;
import org.usfirst.frc.team649.robot.subsystems.CameraSwitcher;
import org.usfirst.frc.team649.robot.subsystems.GearSubsystem;
import org.usfirst.frc.team649.robot.subsystems.GyroSubsystem;
import org.usfirst.frc.team649.robot.subsystems.HoodSubsystem;
//import org.usfirst.frc.team649.robot.subsystems.HopperSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team649.robot.subsystems.LeftShooter;
import org.usfirst.frc.team649.robot.subsystems.RightShooter;
import org.usfirst.frc.team649.robot.subsystems.ShooterSubsystem;
import org.usfirst.frc.team649.robot.subsystems.TurretSubsystem;
import org.usfirst.frc.team649.shootercommands.TurretPIDABS;
import org.usfirst.frc.team649.util.Center;
import org.usfirst.frc.team649.util.GetShooterValues;
import org.usfirst.frc.team649.util.Lidar;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
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
	public static LeftShooter shootLeft;
	public static RightShooter shootRight;
	public static ShooterSubsystem shoot; 
	SendableChooser<Command> chooser = new SendableChooser<>();
	public boolean prevStateShooting;
	public static DrivetrainSubsystem drive;
	public static LeftDTPID leftDT;
	public static RightDTPID rightDT;
	public static GearSubsystem gear;
	public static GyroSubsystem gyro;
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
	public static boolean isIntakeFlapDown;
	public static boolean isGearFlickOut;
	public static boolean isShooterRunning;
	public static boolean isIntakeRunning;
	public static boolean prevStateFunnelFlap;
	public static String gearRollerState;
	public static boolean leftPOVPrevState;
	public static boolean isRed;
	public static boolean rightPOVPrevState;
	public static boolean robotEnabled = false;
	public static boolean isPIDTurn;
	public static boolean prevStateHang;
	public static boolean isAutoTimedOut;
	public static boolean isAutoTimeoutTurnTimedOut;
	public static boolean prevStateMiddleAuto;
	public static boolean prevStateBoilerAuto;
	public static boolean prevStateFarAuto;
	public Lidar lidar;
	public double matchTime;
	public static double turnAngle;
	public static double straightAngle1;
	public static double straightAngle2;
//	public UsbCamera lifecam = new UsbCamera("cam2", 1);
//	public VideoCapture video = new VideoCapture();
//	public AxisCamera axiscam = new AxisCamera("axis", "10.6.49.35");
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
	public static int currentManualShootRPM;
	public static boolean isVisionAimed;
	public static int count;
	
	public static boolean isTalonSRX;
	// more cam stuff still dont touch
//	AxisCamera axis;
//	UsbCamera usb;
//	VideoSink server;
//	boolean previousCameraTrigger = false;
//	CvSink cvsink1;
//	CvSink cvsink2;

	// for logging to file and reading parameters from file ********************
	public static boolean debugMode = false; // set this to true to enable
	public static final int maxTick = 3000; // at 50 samples/s need 3000 for 1
											// minute
	public static int tick;
	public static int value;
	public static double[] logTimer, logDtLftSpd, logDtRtSpd, logDtLftDist, logDtRtDist, logLftFly, logRtFly;
	public static double[] logHoodLft, logHoodRt, logHangI, logLidarDist;
	Map<String, Float> mapRobotParams = new HashMap<String, Float>();
	String inputLine;
	String[] inputWords = new String[100];
	public static String inFileRobotParam = "/home/admin/param.txt", outFileLog = "/home/admin/logfile.csv";
	// *********************************************************************************

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
		gyro = new GyroSubsystem();
		isPIDActive = false;
		isPIDActiveLeft = false;
		isPIDActiveRight = false;
		timer = new Timer();
		isPIDTurn = false;
		isTurretMax = false;
		isTurretMin = false;
		isIntakeRunning = false;
		isTurretPIDActive = false;
		isShooterRunning = false;
		prevStateHang = false;
		gearRollerState = "off";
		isGearFlickOut = gear.getGearFlapSolPos();
//		isIntakeFlapDown = intake.isIntakeDown();
		drive.resetEncoders();
		turret = new TurretSubsystem();
		turret.startingPos = 0.8684379317960067;
		hood = new HoodSubsystem();
		lidar = new Lidar(I2C.Port.kOnboard, 0xC4 >> 1);
		count = 0;
		turnAngle = 0;
		straightAngle1 = 0;
		straightAngle2 = 0;
		gyro.resetGyro();
		// for logging to file and reading parameters from file
		// ********************
		if (debugMode) {
			logTimer = new double[maxTick];
			logDtLftSpd = new double[maxTick];
			logDtRtSpd = new double[maxTick];
			// logDtLftDist=new double[maxTick];
			// logDtRtDist=new double[maxTick];
			// logLftFly=new double[maxTick];
			// logRtFly=new double[maxTick];
			// logHoodLft=new double[maxTick];
			// logHoodRt=new double[maxTick];
			logHangI = new double[maxTick];
			// logLidarDist=new double[maxTick];
		} // end debugMode
			// *******************************************************************************
		
		isTalonSRX = false;
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		// for logging to file and reading parameters from file
		// ********************
//		if (debugMode) {
//
//			System.out.printf("In disabledInit, tick: %d\n", tick);
//
//			// Output to log file
//			if (tick > 10) {
//				try {
//					outFileLog = new SimpleDateFormat("'/home/lvuser/logfile'.MMddhhmm'.csv'").format(new Date());
//					System.out.printf("outputfile is: %s\n",  outFileLog);
//					
//					File file = new File(outFileLog);
//					PrintWriter writer = new PrintWriter(outFileLog, "UTF-8");
//
//					for( String name: mapRobotParams.keySet() ) {
//			            double value = mapRobotParams.get(name);    
//						writer.printf("%s, %f\n", name, value );
//					}
//
//					for (int i = 0; i < tick; i++) {
//						writer.printf("%d, %f", i, logTimer[i]);
//						writer.printf(", %f, %f", logDtLftSpd[i], logDtRtSpd[i]);
//						writer.printf(", %f, %f", logDtLftDist[i], logDtRtDist[i]);
//						// writer.printf(", %f, %f", logLftFly[i],logRtFly[i]);
//						writer.printf(", %f", logHangI[i]);
//						writer.printf("\r\n");
//					}
//					writer.close();
//
//				} catch (FileNotFoundException | UnsupportedEncodingException e) {
//					debugMode = false;
//				}
//			}
//
//			// input from param file
//			float value;
//			System.out.printf("Reading in parameter values\n");
//			try {
//				BufferedReader inputFile = new BufferedReader(new FileReader(inFileRobotParam)); // open
//																									// file
//				while ((inputLine = inputFile.readLine()) != null) {
//					if (!inputLine.isEmpty()) { // process non-empty lines
//						inputWords = inputLine.split("\\s+"); // break one line
//																// into 2 words
//						if (!inputWords[1].isEmpty()) {
//							value = Float.parseFloat(inputWords[1]); // convert
//																		// 2nd
//																		// word
//																		// into
//																		// float
//							mapRobotParams.put(inputWords[0], value);// add
//																		// parameter
//																		// and
//																		// value
//																		// to
//																		// map
//							System.out.format("%s, %s\n", inputWords[0], inputWords[1]);
//						}
//					}
//				} // end while
//				inputFile.close(); // close file
//			} catch (Exception e) {
//				debugMode = false;
//			}
//
//		} // end debug mode
//
//		// ********************************************************************************************

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
		isAutoTimedOut = false;
		isAutoTimeoutTurnTimedOut = false;
		// for logging to file and reading parameters from file
		// ********************
		tick = 0;
		 drive.resetEncoders();
		 gyro.resetGyro();
		 turret.resetEncoder();

		// *********************************************************************************
		// drive.getAlliance());
//		new SwitchDTMode(true);
//    	new ShiftDT(false);
//		new DrivetrainPIDCommand(70, false).start();
		isShooterRunning = true;
		

		turnAngle = 0;
//		new RedSideGearShootMiddle().start();
//		new BlueSideGearShootMiddle().start();
//		new TurretPIDABS(0.21*60).start();
//		new GyroTurnPID(56).start();
//		new BlueSideGearShootMiddleWithTimeout().start();
//		new BlueSideGearShootMiddle().start();
//		if(drive.getAlliance() == AllianceSelector.RED || drive.getAlliance() == AllianceSelector.RED_NO_SHOOT){
//			isRed = true;
//		}else{
//			isRed = false;
//		}
//		new TurretPIDABS(0.21*60).start();
		drive.motors[0].changeControlMode(TalonControlMode.PercentVbus);
		drive.motors[1].changeControlMode(TalonControlMode.Follower);
		drive.motors[1].set(RobotMap.Drivetrain.MOTOR_PORTS[0]);
		drive.motors[2].changeControlMode(TalonControlMode.PercentVbus);
		drive.motors[3].changeControlMode(TalonControlMode.Follower);
		drive.motors[3].set(RobotMap.Drivetrain.MOTOR_PORTS[2]);
//		new RedSideGearShootMiddle().start();
//		new AutoFullSequence(drive.getAutoGoal(), drive.getAlliance()).start();
//		SmartDashboard.putNumber("Get Alliance", drive.getAlliance());
//		SmartDashboard.putNumber("Get Program", drive.getAutoGoal());
//		new RedSideBoilerGearShoot().start();
		new BlueSideBoilerGearShoot().start();
//		new RedSideGearShootMiddle().start();
//		new BlueSideGearShootMiddle().start();
//		new RedSideGearFarSide().start();
		//new BlueSideGearFarSide().start();
//		new .start();tr3tr
//		new BlueSideBoilerGearNoShoot().start();

//		new BlueSideGearShootMiddle().start();
//		new BlueSideBoilerGearShoot().start();
//		new RedSideGearNoShootMiddle().start();
//		new RedSideGearFarSide().start();
//		new RedSideBoilerGearShoot().start();
//		new BlueSideGearFarSide().start();
//		new AutoFullSequence(drive.getAutoGoal(), drive.getAlliance()).start();
		//new DriveForTime(0.9).start();
//		new ResetTurretSequence().start();
//		new TurretPIDABS(60.0).start();

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
//		SmartDashboard.putNumber("P", drive.encoderDrivePID.getP());
//		SmartDashboard.putNumber("I", drive.encoderDrivePID.getI());
//		SmartDashboard.putNumber("D", drive.encoderDrivePID.getD());
		SmartDashboard.putBoolean("IS THE AUTO ACTUALY", Robot.isAutoTimedOut);
		doTheDash();
	}

	@Override
	public void teleopInit() {
		// new RunCommpresorCommand(true).start();
		isShooterRunning = false;
		

		gyro.resetGyro();
		timer.reset();
		timer.start();
		drive.resetEncoders();
		// new SetGearFlap(false).start();
		// new SetIntakeWedgePistons(false).start();
		prevStateFunnelFlap = false;
		leftPOVPrevState = oi.operator.isLeftPOV();
		rightPOVPrevState = oi.operator.isRightPOV();
		new SwitchDTMode(false).start();
		// for logging to file and reading parameters from file
		// ********************
		tick = 0;
		currentManualShootRPM = 1100;
		prevStateMiddleAuto = false;
		prevStateBoilerAuto = false;
		prevStateFarAuto = false;
		startVisionThreads();	
		isVisionAimed = false;
		// *********************************************************************************

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		// FUN CAMERA STUFF IM GOING CRAZY FROM CAMERAS HAHAHAHAHAHHAHA
		// Begin Camera Init Code! Cannot go in Method or Class! MUST BE IN
		// ROBOTINIT BY ITSELF!
//		Thread t = new Thread(() -> {
//
//			boolean allowCam1 = false;
//
//			AxisCamera camera1 = CameraServer.getInstance().addAxisCamera(RobotMap.Camera.axisPort);
//			camera1.setResolution(RobotMap.Camera.axisResWidth, RobotMap.Camera.axisResWidth);
//			camera1.setFPS(RobotMap.Camera.axisFPS);
//			UsbCamera camera2 = CameraServer.getInstance().startAutomaticCapture();
//			camera2.setResolution(RobotMap.Camera.usbResWidth, RobotMap.Camera.usbResHeight);
//			camera2.setFPS(RobotMap.Camera.usbFPS);
//			// camera3 is the backup axis camera, to use it, enable the
//			// useBackupCamera variable in RobotMap.
//			/*
//			AxisCamera camera3 = CameraServer.getInstance().addAxisCamera(RobotMap.Camera.backupAxisPort);
//			camera3.setResolution(RobotMap.Camera.backupAxisResWidth, RobotMap.Camera.backupAxisResHeight);
//			camera3.setFPS(RobotMap.Camera.backupAxisFPS);
//			*/
//			CvSink cvSink1 = CameraServer.getInstance().getVideo(camera1);
//			CvSink cvSink2 = CameraServer.getInstance().getVideo(camera2);
//			CvSink cvSink3 = CameraServer.getInstance().getVideo(camera3);
//			CvSource outputStream = CameraServer.getInstance().putVideo("Switcher", 320, 240);
//
//			Mat image = new Mat();
//
//			while (!Thread.interrupted()) {
//
//				if (oi.operator.isDavidCamera() || oi.driver.kyleCamera()) {
//					allowCam1 = !allowCam1;
//				}
//				if (allowCam1) {
//					cvSink2.setEnabled(false);
//					cvSink1.setEnabled(true);
//					cvSink1.grabFrame(image);
//				} else {
//					cvSink1.setEnabled(false);
//					cvSink2.setEnabled(true);
//					cvSink2.grabFrame(image);
//					if (RobotMap.Camera.useSecondAxisCamera = true) {
//						if (allowCam1) {
//							cvSink1.setEnabled(false);
//							cvSink3.setEnabled(true);
//							cvSink3.grabFrame(image);
//						} else {
//							cvSink3.setEnabled(false);
//							cvSink1.setEnabled(true);
//							cvSink1.grabFrame(image);
//						}
//
//					} else {
//
//					}
//				}
//
//				outputStream.putFrame(image);
//			}
//
//		});
//		t.start();
		// SmartDashboard.putNumber("absolute encoders for 90",
		// turret.translateAngleToABS(90));
		Scheduler.getInstance().run();
		turret.countCurrentPosition();
		double joyXVal = Robot.oi.driver.getRotation();
		double joyYVal = Robot.oi.driver.getForward();
		if(((Math.abs(joyXVal) < 0.2) && joyYVal == 0 )|| Robot.oi.driver.isVBusPush()){
			//Vbus
			drive.driveFwdRotTeleop(joyYVal, joyXVal,true);

		}else if(Math.abs(joyXVal) < 0.2 && Math.abs(joyYVal)<0.2){
			drive.driveFwdRotTeleop(joyYVal, -joyXVal,true);
		}else{
			if(joyYVal > 0){
				joyYVal = Math.pow(Math.abs(joyYVal), 1.32);
			}else{
				joyYVal = -Math.pow(Math.abs(joyYVal), 1.32);
			}
			if(joyXVal > 0){
			    if(joyXVal > 0.3){
					joyXVal = Math.pow(Math.abs(joyXVal), 1.75);
				}else{
					joyXVal = Math.pow(Math.abs(joyXVal), 3);
				}
				
			}else{
				if(joyXVal < -0.3){
					joyXVal = -Math.pow(Math.abs(joyXVal), 1.75);
				}else{
					joyXVal = -Math.pow(Math.abs(joyXVal), 3);
				}
			}
			drive.driveFwdRotTeleop(-joyYVal, joyXVal,false);
		}
		SmartDashboard.putNumber("Left Speed", Robot.drive.getLeftSpeed());
		SmartDashboard.putNumber("Right Speed", Robot.drive.getRightSpeed());
		SmartDashboard.putNumber("Left Distance", Robot.drive.getDistanceDTLeft());
		SmartDashboard.putNumber("Right Distance", Robot.drive.getDistanceDTRight());
	//ANKURS VERSION TEST AFTER CORRECT DISTANCE VALUES
//		double raw;
//		double outRaw;
//		double inRaw;
//		double outDist;
//		double inDist;
//		double outSpeed;
//		double inSpeed;
//		if(joyXVal != 0){
//			raw = Math.abs(27.0/2.0 *joyYVal/joyXVal);
//			outRaw = raw + 27/2;
//			inRaw = raw - 27/2;
//			outDist = 2 * Math.PI * outRaw;
//			inDist = 2 * Math.PI * inRaw;
//			double outspeed = Math.abs(joyYVal)+Math.abs(joyXVal);
//			double inspeed = outspeed*inDist/outDist;
//			if(outspeed>1){
//				outspeed/=outspeed;
//				inspeed/=outspeed;
//			}
//			if(joyXVal > 0){
//				if(joyYVal> 0){
//					Robot.drive.rawDriveVelPidAnkur(-outspeed, -inspeed, false, true);
//				}else{
//					Robot.drive.rawDriveVelPidAnkur(-outspeed, -inspeed, false, false);
//				}
//				
//			}else{
//				if(joyYVal> 0){
//					Robot.drive.rawDriveVelPidAnkur(-outspeed, -inspeed, true, true);
//				}else{
//					Robot.drive.rawDriveVelPidAnkur(-outspeed, -inspeed, true, false);
//				}
//			}
//		}else{
//			Robot.drive.driveFwdRotTeleop(-joyYVal, 0, false);
//		}
		

		if(oi.operator.getTeleopShot()){
			currentManualShootRPM = 1475;
		}else if(oi.operator.isLeftPOV() && !leftPOVPrevState){
			if(currentManualShootRPM > 1100){
				currentManualShootRPM -= 25;
			}
		}else if(oi.operator.isRightPOV() && !rightPOVPrevState){
			if(currentManualShootRPM < 2000){
				currentManualShootRPM += 25;
			}
		}
		if(oi.operator.isLeftPOV()){
			leftPOVPrevState = true;
		}else{
			leftPOVPrevState = false;
		}
		if(oi.operator.isRightPOV()){
			rightPOVPrevState = true;
		}else{
			rightPOVPrevState = false;
		}
		if(oi.operator.isManualTurret()){
			turret.turn(oi.operator.getX()/2);
		}else if(oi.operator.getHang()){
			if(!prevStateHang){
//				new TurretPIDABS(180).start();
			}
		}else{
			turret.turn(0.0);
		}
		if(oi.operator.getTeleopShot()){
//			hood.setServoRaw(.35 ); //.31
		}else if(oi.operator.isManualHood()){
//			hood.setServoRaw(-oi.operator.getSlider());
			SmartDashboard.putNumber("Actual Hodd", -oi.operator.getSlider());
		}
//		if (oi.operator.intakeFlapUp()) {
//			new SetIntakeWedgePistons(false).start();'
//		} else if (oi.operator.intakeFlapDown()) {
//			new SetIntakeWedgePistons(true).start();
//
//		}
		
		if (oi.operator.getGearFlap()) {

			new SetGearFlap(false).start();

		} else {
			new SetGearFlap(true).start();
		}
		if(oi.operator.runFunnelMotorIn()){
			if(gearRollerState != "gear"){
				new SetFunnelCommand(true).start();
			}
			gearRollerState = "gear";
			gear.setFunnelMotor(-0.57);
		}else if(oi.operator.runFunnelMotorOut()){
			if(gearRollerState != "ball"){
				new SetFunnelCommand(true).start();
			}
			gearRollerState = "ball";
			gear.setFunnelMotor(0.5);
		}else{
			gearRollerState = "off";
			gear.setFunnelMotor(0);
		}
		if(oi.operator.funnelFlapOut()){
			new SetFunnelCommand(true).start();
		}else if(oi.operator.funnelFlapIn()){
			new SetFunnelCommand(false).start();
		}
		if(oi.operator.getHang()){
			prevStateHang = true;
			
			intake.setHangMotor(1.0);
		}else if (oi.operator.runIntake()) {
			
			intake.setIntakeRollerMotor(1.0);
			intake.setWheelRollers(1.0);
//			 
			isIntakeRunning = true;
		}else if(oi.operator.purgeGear()){
			intake.setWheelRollers(-1.0);
		}else{
//			prevStateHang = false;
			intake.setHangMotor(0.0);
			intake.setWheelRollers(0.0);

		}
		
		if(oi.operator.slowShoot()){
		
			shootLeft.simpleBangBang(GetShooterValues.returnShooterMinPower(currentManualShootRPM), GetShooterValues.returnShooterMaxPower(currentManualShootRPM),currentManualShootRPM, currentManualShootRPM+200, currentManualShootRPM-200);
			shootRight.simpleBangBang(GetShooterValues.returnShooterMinPower(currentManualShootRPM), GetShooterValues.returnShooterMaxPower(currentManualShootRPM),currentManualShootRPM, currentManualShootRPM+200, currentManualShootRPM-200);
//			shootLeft.setLeftFlywheel(oi.operator.getSliderShoot());
//			shootRight.setRightFlywheel(oi.operator.getSliderShoot());

			if(shootLeft.getLeftFlywheelEin() >currentManualShootRPM-100  && shootRight.getRightFlywheelEin() > currentManualShootRPM-100){
				shoot.setFeedMotor(1.0);
				shoot.setHooperMotor(0.5);
				intake.setWheelRollers(0.5);
			}
//			
		}else if(oi.operator.fastShoot()){
//			shootLeft.setLeftFlywheel(oi.operator.getSliderShoot());
//			shootRight.setRightFlywheel(oi.operator.getSliderShoot());
			shootLeft.simpleBangBang(GetShooterValues.returnShooterMinPower(currentManualShootRPM), GetShooterValues.returnShooterMaxPower(currentManualShootRPM),currentManualShootRPM, currentManualShootRPM+200, currentManualShootRPM-200);
			shootRight.simpleBangBang(GetShooterValues.returnShooterMinPower(currentManualShootRPM), GetShooterValues.returnShooterMaxPower(currentManualShootRPM),currentManualShootRPM, currentManualShootRPM+200, currentManualShootRPM-200);
			if(shootLeft.getLeftFlywheelEin() >currentManualShootRPM-100  && shootRight.getRightFlywheelEin() > currentManualShootRPM-100){
				shoot.setFeedMotor(1.0);
				shoot.setHooperMotor(1.0);
				intake.setWheelRollers(0.5);

			}

		}else{
			shootLeft.setLeftFlywheel(0.0);
			shootRight.setRightFlywheel(0.0);
			shoot.setFeedMotor(0);
			shoot.setHooperMotor(0);
		}
//		if(oi.game.isMiddleAuto() && !prevStateMiddleAuto){
//			if(drive.getAlliance() == 1 && drive.getAlliance() == 3){
//				new RedSideGearShootMiddle().start();
//			}else{
//				new BlueSideGearShootMiddle().start();
//			}
//		}else if(oi.game.isBoilerAuto() && !prevStateBoilerAuto){
//			if(drive.getAlliance() == 1 && drive.getAlliance() == 3){
//				new RedSideBoilerGearShoot().start();
//			}else{
//				new BlueSideBoilerGearShoot().start();
//			}
//		}else if(oi.game.isFarAuto() && !prevStateFarAuto){
//			if(drive.getAlliance() == 1 && drive.getAlliance() == 3){
//				new RedSideGearFarSide().start();
//			}else{
//				new BlueSideGearFarSide().start();
//			}
//		}
		 if (oi.driver.shiftUp()) {
			 drive.shift(true);
		 } else {
			 drive.shift(false);
		 }
//		 prevStateMiddleAuto = oi.game.isMiddleAuto();
//		 prevStateBoilerAuto = oi.game.isBoilerAuto();
//		 prevStateFarAuto = oi.game.isFarAuto();
//		 SmartDashboard.putNumber("FWD Drive", oi.driver.getForward());
//		 SmartDashboard.putNumber("ROT Drive", oi.driver.getRotation());
	
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
	public void startVisionThreads(){
		System.out.println("STARTING VISION");

		
		initThread = new InitializeServerSocketThread();
		initThread.start();
		
		//wait for thread to finish the socket initialization
		try {
			Timer t = new Timer();
			t.start();
			while (!isRIOServerStarted){
				if (t.get() > VISION_INIT_TIME_OUT){
					throw new InterruptedException("DUN GOOFED -----> VISION TIMED OUT IN INIT");
				}
			}
			System.out.println(">--VISION initialized & running --ROBOT");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("X--VISION initialize FAILED --ROBOT");
		}
		
//		this.adbTimer = Executors.newSingleThreadScheduledExecutor();
//		this.adbTimer.scheduleAtFixedRate(new Thread(new PullVisionTxtThread()), 0, PULL_PERIOD, TimeUnit.MILLISECONDS);
	}
//	public static synchronized void updateCenter(Center c) {
//		if (c != null) {
//			currCenter = new Center(c.x, c.y);
//			rateCenterUpdated = 1 / (timer.get() - prevTimeCenterUpdated); // seconds
//			prevTimeCenterUpdated = timer.get();
//
//		} else {
//			System.out.println("ERROR: passed in null center");
//		}
//	}
//
//	public static synchronized void updateVisionDistance(double d) {
//		if (d > 10.0) {
//			visionDistance = d;
//		} else {
//			System.out.println("Target Not in View");
//			visionDistance = 0.0;
//		}
//	}
	public static synchronized void updateVisionOnTarget(boolean isAimed){
		isVisionAimed = isAimed;
	}

	public void doTheDash() {
		
		SmartDashboard.putNumber("Turret Encoder", turret.getTotalDist());
		SmartDashboard.putNumber("Turret Speed Set", currentManualShootRPM);
		
//		SmartDashboard.putNumber("Agitator Curret", Robot.shoot.hooperMotor.getOutputCurrent());
//		SmartDashboard.putNumber("Right Motor Front Current", drive.motors[0].getOutputCurrent());
//		SmartDashboard.putNumber("Right Motor Back Current", drive.motors[1].getOutputCurrent());
//		SmartDashboard.putNumber("Left Motor Front Current", drive.motors[2].getOutputCurrent());
//		SmartDashboard.putNumber("Left Motor Back Current", drive.motors[3].getOutputCurrent());
//		SmartDashboard.putNumber("Encoder Left Speed", drive.leftEncoderSpeed());
//		SmartDashboard.putNumber("Encoder Right Speed", drive.rightEncoderSpeed());
		SmartDashboard.putNumber("Encoder Left Distance", drive.getDistanceDTLeft());
		SmartDashboard.putNumber("Encoder Right Distance", drive.getDistanceDTRight());
//		SmartDashboard.putNumber("Left Ein", shootLeft.getLeftFlywheelEin());
//		SmartDashboard.putNumber("Right Ein", shootRight.getRightFlywheelEin());
//		SmartDashboard.putNumber("Hood Servo Right", hood.servoRight.getRaw());
//		SmartDashboard.putNumber("Hood Servo Left", hood.servoLeft.getRaw());
//		SmartDashboard.putNumber("Slider", oi.operator.getSliderShoot());
//		SmartDashboard.putBoolean("IR Break", gear.isGearLoaded());
//		SmartDashboard.putNumber("Raw Turret", turret.getTurretEncoderValue());
//		SmartDashboard.putNumber("Hang Current", intake.blackRollerMotor.getOutputCurrent());
//		SmartDashboard.putBoolean("Turret Hal", Robot.turret.getTurretHal());
		SmartDashboard.putNumber("Program Pot value", drive.programSelectorPot.get());
		SmartDashboard.putNumber("Pot", drive.alliancePot.get());
		SmartDashboard.putNumber("Alliance Selection", drive.getAlliance());
		SmartDashboard.putNumber("Get Goal", drive.getAutoGoal());
//		SmartDashboard.putNumber("slider for hood", oi.operator.getSlider());
//		SmartDashboard.putNumber("Encoder Value Turret", value)
		drive.displayAutoProgram();
//		SmartDashboard.putNumber("Straight Deviation 1", straightAngle1);
//		SmartDashboard.putNumber("Straight Deviation 2", straightAngle2);
		SmartDashboard.putNumber("PID Turn Gyro Angle", turnAngle);
		SmartDashboard.putNumber("Gyro Val", gyro.getAngle());
		SmartDashboard.putNumber("Match Time", drive.getMatchTime());

		if(count == 12){
			boolean isLidarAimed;
			double lidarValue = lidar.getSample();
			 if(lidarValue > Lidar.minDistance && lidarValue < Lidar.minDistance){
					isLidarAimed = true;
			 }else{
				 	isLidarAimed = false;
			 }
			 SmartDashboard.putBoolean("is lidar aimed", isLidarAimed);
				SmartDashboard.putNumber("lidar value", lidarValue);
				count = 0;
		}
		count ++;
		

		 SmartDashboard.putBoolean("Is Vision Lined Up", isVisionAimed);

		// for logging to file and reading parameters from file
		// ********************
//		if (debugMode) {
//			if (tick < maxTick) {
//				logTimer[tick] = timer.get();
//				logDtLftSpd[tick] = drive.leftEncoderSpeed();
//				logDtRtSpd[tick] = drive.rightEncoderSpeed();
//				// logDtLftDist[tick]=drive.getDistanceDTLeft();
//				// logDtRtDist[tick]=drive.getDistanceDTRight();
//				// logLftFly[tick]=shoot.getLeftFlywheelEin();
//				// logRtFly[tick]=shoot.getRightFlywheelEin();
//				// logHoodLft[tick]=hood.servoRight.getRaw();
//				// logHoodRt[tick]=hood.servoLeft.getRaw();
//				logHangI[tick] = intake.currentMonitoring();
//				// logLidarDist[tick]= lidar.getDistance();
//
//				tick++;
//			} // end maxTick check
//		} // end debugMode
			// **************************************************************************************
	}

}
