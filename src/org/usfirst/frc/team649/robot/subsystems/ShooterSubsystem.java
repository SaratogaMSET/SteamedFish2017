package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {
	
	public CANTalon leftFlywheel;
	public CANTalon rightFlywheel;
	public CANTalon feederMotor;
	//always rotates
	public CANTalon hooperMotorIn;
	//sine curve rotation
	public CANTalon hooperMotorOut;
	public Counter leftEin;
	public Counter rightEin;
	public static final double FEEDER_SPEED = 0.3;
	public static final double HOOPER_MOTOR_SPEED = 0.5;
	public static final int TARGET_RPM = 1600;
	public static final int MIN_RPM = 1500;
	public static final int MAX_RPM = 1700;
	public static final double MAX_SPEED_LEFT = 0.5;
	public static final double MIN_SPEED_RIGHT = 0.3;
	public static final double MIN_SPEED_LEFT = 0.4;
	public static final double MAX_SPEED_RIGHT = 0.5;
	
    public ShooterSubsystem(){
    	hooperMotorIn = new CANTalon(RobotMap.Shooter.HOOPER_IN_PORT);
    	hooperMotorOut = new CANTalon(RobotMap.Shooter.HOOPER_OUT_PORT);
    	leftFlywheel = new CANTalon(RobotMap.Shooter.LEFT_SHOOTER_FLYWHEEL_PORT);
    	rightFlywheel = new CANTalon(RobotMap.Shooter.RIGHT_SHOOTER_FLYWHEEL_PORT);
    	feederMotor = new CANTalon(RobotMap.Shooter.SHOOTER_FEEDER_PORT);
//    	leftEin = new Counter(RobotMap.Shooter.LEFT_SHOOTER_EIN_PORT);
//    	rightEin = new Counter(RobotMap.Shooter.RIGHT_SHOOTER_EIN_PORT);
    }
    public void setLeftFlywheel(double power){
    	leftFlywheel.set(power);
    }
    public void setRightFlywheel(double power){
    	rightFlywheel.set(power);
    }
    public double getRightFlywheel()
    {
    	return rightFlywheel.get();
    }
    public double getLeftFlywheel()
    {
    	return leftFlywheel.get();
    }
    public double getLeftFlywheelEin(){
    	return 60/leftEin.getPeriod();
    }
    public double getRightFlywheelEin(){
    	return 60/rightEin.getPeriod();
    }
    public void setFeedMotor(double power){
    	feederMotor.set(power);
    }
    public void setHooperIn(double speed){
    	hooperMotorIn.set(speed);
    }
    public void setHooperOut(double minSpeed, double maxSpeed, double period, double currentTime){
    	double a = (maxSpeed-minSpeed)/2;
    	double b = 2*Math.PI/period;
    	double d = a + minSpeed;
    	double speed = a*Math.sin(b*currentTime) + d;
    	hooperMotorOut.set(speed);
    }
    public void setHooperOutRaw(double speed){
    	hooperMotorOut.set(speed);
    }
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
    
}

