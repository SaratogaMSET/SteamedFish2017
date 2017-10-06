package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.RobotMap;
import org.usfirst.frc.team649.robot.subsystems.GyroSubsystem.GyroPIDConstants;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GyroSubsystem extends PIDSubsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public static class GyroPIDConstants {
		
		// 0.2, 0, 0.01
		public static final double k_p = 0.05;
		public static final double k_i = 0;
		public static final double k_d = 0.1;
		
		public static final double PID_ABS_TOLERANCE = 1.0;
	}
	
	public PIDController gyroPIDController;
	public AnalogGyro Gyro;
	public ADXRS450_Gyro Gyroscope;
	public boolean drivingStraight;
	public static double lastMotorPower;
	public static double currentMotorPower;
	public int oscillations;
	public double oscillationTime;
	public double pidOutput;
	public Timer timer;
	public boolean oscillationStarted;
	public double lastInput;
	public double lastLastInput;
	public boolean isADXRS450;
	
	public GyroSubsystem() {
		super("GyroSubsystem", GyroPIDConstants.k_p, GyroPIDConstants.k_i, GyroPIDConstants.k_d);
		Gyro = new AnalogGyro(RobotMap.Drivetrain.GYRO_PORT);
//		Gyroscope = new ADXRS450_Gyro();
		drivingStraight = false;
		oscillations = 0;
		oscillationTime = 0;
		pidOutput = 0;
		timer = new Timer();
		oscillationStarted = false;
		lastInput = 0;
		lastLastInput = 0;
//		isADXRS450 = false;
		
		gyroPIDController = this.getPIDController();
		gyroPIDController.setAbsoluteTolerance(GyroPIDConstants.PID_ABS_TOLERANCE);
		gyroPIDController.setOutputRange(-0.5, 0.5);
	}
	
	public double getAngle() {
//		if(isADXRS450) {
//			return radiansToDegrees(Gyroscope.getAngle());
//		}
		return Gyro.getAngle();
	}
	
	public double getRate() {
		if(isADXRS450) {
			return Gyroscope.getRate();
		}
		return -99999999;
	}
	public void resetGyro() {
//		if(isADXRS450) {
//			Gyroscope.reset();
//			return ;
//		}
		Gyro.reset();
	}

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
//		if (Math.abs(getAngle()) <= Math.abs(lastInput)){
//			double input = extrapolatePoint();
//			lastLastInput = lastInput;
//			lastInput = input;
//			return input;
//		}
		return getAngle();
	}



	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		currentMotorPower = output;
		if(drivingStraight) {
			double left;
			double right;
			if (output > 0) {
				left = 1.0;
				right = 1.0 - output;
			} else if (output < 0) {
				right = 1.0 + output;
				left = 1.0;
			} else {
				right = 1.0;
				left = 1.0;
			}
			Robot.drive.rawDrive(left, right);
		} else {
			Robot.drive.rawDrive(output, -output);
		}

	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public double extrapolatePoint() {
		double delta = lastInput-lastLastInput;
		return lastInput + delta;
	}
	
	public double radiansToDegrees(double radians) {
		return radians * (180/Math.PI);
	}
}