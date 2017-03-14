package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LeftShooter extends Subsystem {
	public CANTalon leftFlywheel;
	public Counter leftEin;
    // Initialize your subsystem here
    public LeftShooter() {
    	leftFlywheel = new CANTalon(RobotMap.Shooter.LEFT_SHOOTER_FLYWHEEL_PORT);
    	leftFlywheel.enableBrakeMode(false);
    	leftEin = new Counter(RobotMap.Shooter.LEFT_SHOOTER_EIN_PORT);

        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    public void setLeftFlywheel(double power){
    	leftFlywheel.set(-power);
    }
    public double getLeftFlywheel()
    {
    	return leftFlywheel.get();
    }
    public double getLeftFlywheelEin(){
    	return 60/leftEin.getPeriod();
    }
    public void simpleBangBang(double minSpeed, double maxSpeed, double RPM, double maxRPMThresh, double minRPMThresh){
    	if(getLeftFlywheelEin() < minRPMThresh){
    		setLeftFlywheel(1.0);
    	}else if(getLeftFlywheelEin() > maxRPMThresh){
    		setLeftFlywheel(0.0);
    	}else if(getLeftFlywheelEin() <= RPM){
    		setLeftFlywheel(maxSpeed);
    	}else{
    		setLeftFlywheel(minSpeed);

    	}
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return 0.0;
    }

    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    }
}
