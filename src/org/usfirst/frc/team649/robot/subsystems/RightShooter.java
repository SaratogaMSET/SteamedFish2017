package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.RobotMap;
import org.usfirst.frc.team649.robot.subsystems.LeftShooter.PIDConstantsShooter;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RightShooter extends PIDSubsystem {
	public CANTalon rightFlywheel;
	public Counter rightEin;
	public PIDController rightShooterPID;
	public double sampleTime = 0;
	public Timer time;
	
	public static class PIDConstantsShooter {
		public static final double ABS_TOLERANCE = 20; //1.2 degrees
		public static  double k_P = 1.85; //0.2, 1.0
		public static double k_I = 0.01;
		public static double k_D = 0.01;

	}
    // Initialize your subsystem here
    public RightShooter() {
    	super("Right Shooter PID", PIDConstantsShooter.k_P,PIDConstantsShooter.k_I,PIDConstantsShooter.k_D);
    	rightFlywheel = new CANTalon(RobotMap.Shooter.RIGHT_SHOOTER_FLYWHEEL_PORT);
    	rightFlywheel.enableBrakeMode(false);
    	rightEin = new Counter(RobotMap.Shooter.RIGHT_SHOOTER_EIN_PORT);
    	rightShooterPID = this.getPIDController();
    	rightShooterPID.setAbsoluteTolerance(PIDConstantsShooter.ABS_TOLERANCE);
    	rightShooterPID.setOutputRange(-1.0, 1.0);
    	time = new Timer();
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
        return getRightFlywheelEin();
    }

    protected void usePIDOutput(double output) {
    	rightFlywheel.set(output);
    }
    
    public double currentMonitoring() {
    	double current = 0;
		double returnCurrent = 0;
		if (rightFlywheel.getOutputCurrent() > current) {
			current = rightFlywheel.getOutputCurrent();
		}
		if (time.get() % sampleTime == 0) {
			returnCurrent = current;
			return returnCurrent;
		}
		return returnCurrent;
    }
}
