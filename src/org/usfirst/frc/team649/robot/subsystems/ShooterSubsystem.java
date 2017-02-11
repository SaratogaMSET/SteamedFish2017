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
	public Counter leftEin;
	public Counter rightEin;
	public static final double feederSpeed = 0.3;
	public static final int TARGET_RPM = 1600;
	public static final int MIN_RPM = 1500;
	public static final int MAX_RPM = 1700;
	public static final double MAX_SPEED_LEFT = 0.5;
	public static final double MIN_SPEED_RIGHT = 0.3;
	public static final double MIN_SPEED_LEFT = 0.4;
	public static final double MAX_SPEED_RIGHT = 0.5;
	
    public ShooterSubsystem(){
    	leftFlywheel = new CANTalon(RobotMap.Shooter.LEFT_SHOOTER_FLYWHEEL_PORT);
    	rightFlywheel = new CANTalon(RobotMap.Shooter.RIGHT_SHOOTER_FLYWHEEL_PORT);
    	feederMotor = new CANTalon(RobotMap.Shooter.SHOOTER_FEEDER_PORT);
    	leftEin = new Counter(RobotMap.Shooter.LEFT_SHOOTER_EIN_PORT);
    	rightEin = new Counter(RobotMap.Shooter.RIGHT_SHOOTER_EIN_PORT);
    }
    public void setLeftFlywheel(double power){
    	leftFlywheel.set(power);
    }
    public void setRightFlywheel(double power){
    	rightFlywheel.set(power);
    }
    public double getLeftFlywheelEin(){
    	return 60/leftEin.getPeriod();
    }
    public double getRightFlyhweelEin(){
    	return 60/rightEin.getPeriod();
    }
    public void setFeedMotor(double power){
    	feederMotor.set(power);
    }
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
    
}

