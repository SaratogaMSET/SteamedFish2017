
package org.usfirst.frc.team649.robot;

import org.usfirst.frc.team649.robot.commands.RunCommpresorCommand;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeSubsytem;
import org.usfirst.frc.team649.robot.subsystems.LeftDTPID;
import org.usfirst.frc.team649.robot.subsystems.LidarSubsystem;
import org.usfirst.frc.team649.robot.subsystems.RightDTPID;
import org.usfirst.frc.team649.robot.subsystems.ShooterSubsystem;
import org.usfirst.frc.team649.shootercommands.BangBangThenShootCommand;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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
	public static IntakeSubsytem intake;
	public static ShooterSubsystem shoot;
	SendableChooser<Command> chooser = new SendableChooser<>();
	public boolean prevStateShooting;
	public static DrivetrainSubsystem drive;
	public static LeftDTPID leftDT;
	public static RightDTPID rightDT;
	public static LidarSubsystem lidar;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
//		drive = new DrivetrainSubsystem();
//		compressor = new Compressor();
//		intake = new IntakeSubsytem();
//		shoot = new ShooterSubsystem();
//		prevStateShooting = false;
//		leftDT = new LeftDTPID();
//		rightDT = new RightDTPID();
		lidar = new LidarSubsystem(Port.kMXP);
		
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
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
//		new RunCommpresorCommand(true).start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
//		if(oi.operator.getShoot() && !prevStateShooting){
//			new BangBangThenShootCommand(shoot.TARGET_RPM, shoot.MIN_SPEED_RIGHT, shoot.MAX_SPEED_RIGHT, shoot.MAX_SPEED_LEFT, shoot.MIN_SPEED_LEFT, shoot.MIN_RPM, shoot.MAX_RPM).start();
//		}
//		prevStateShooting = oi.operator.getShoot();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {

	}
}
