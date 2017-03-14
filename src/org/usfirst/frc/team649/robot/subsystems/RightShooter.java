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
public class RightShooter extends Subsystem {
	public CANTalon rightFlywheel;
	public Counter rightEin;

    // Initialize your subsystem here
    public RightShooter() {
    	rightFlywheel = new CANTalon(RobotMap.Shooter.RIGHT_SHOOTER_FLYWHEEL_PORT);
    	rightFlywheel.enableBrakeMode(false);
    	rightEin = new Counter(RobotMap.Shooter.RIGHT_SHOOTER_EIN_PORT);

        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    public void setRightFlywheel(double power){
    	rightFlywheel.set(power);
    }
    public double getRightFlywheel()
    {
    	return rightFlywheel.get();
    }
    public double getRightFlywheelEin(){
    	return 60/rightEin.getPeriod();
    }
    public void simpleBangBang(double minSpeed, double maxSpeed, double RPM, double maxRPMThresh, double minRPMThresh){
    	if(getRightFlywheelEin() < minRPMThresh){
    		setRightFlywheel(1.0);
    	}else if(getRightFlywheelEin() > maxRPMThresh){
    		setRightFlywheel(0.0);
    	}else if(getRightFlywheelEin() <= RPM){
    		setRightFlywheel(maxSpeed);
    	}else{
    		setRightFlywheel(minSpeed);
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
