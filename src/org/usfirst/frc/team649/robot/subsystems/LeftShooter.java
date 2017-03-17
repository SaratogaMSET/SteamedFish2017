package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.RobotMap;
import org.usfirst.frc.team649.robot.subsystems.RightShooter.PIDConstantsShooter;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LeftShooter extends PIDSubsystem {
	public CANTalon leftFlywheel;
	public Counter leftEin;
	public PIDController shooterLeftPID;
	public double sampleTime = 0.5;
	public Timer time;

    // Initialize your subsystem here
	public static class PIDConstantsShooter {
		public static final double ABS_TOLERANCE = 20; //1.2 degrees
		public static  double k_P = 1.85; //0.2, 1.0
		public static double k_I = 0.01;
		public static double k_D = 0.01;

	}
	
    public LeftShooter() {
    	super("Left Shooter PID", PIDConstantsShooter.k_P,PIDConstantsShooter.k_I,PIDConstantsShooter.k_D);
    	leftFlywheel = new CANTalon(RobotMap.Shooter.LEFT_SHOOTER_FLYWHEEL_PORT);
    	leftFlywheel.enableBrakeMode(false);
    	leftEin = new Counter(RobotMap.Shooter.LEFT_SHOOTER_EIN_PORT);
    	shooterLeftPID = this.getPIDController();
    	shooterLeftPID.setAbsoluteTolerance(PIDConstantsShooter.ABS_TOLERANCE);
    	shooterLeftPID.setOutputRange(-1.0, 1.0);
    	time = new Timer();
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
        return getLeftFlywheelEin();
    }

    protected void usePIDOutput(double output) {
    	SmartDashboard.putNumber("shoot left value", output);
        leftFlywheel.set(-output);
    }
    
    public double currentMonitoring() {
    	double current = 0;
		double returnCurrent = 0;
		if (leftFlywheel.getOutputCurrent() > current) {
			current = leftFlywheel.getOutputCurrent();
		}
		if (time.get() % sampleTime == 0) {
			returnCurrent = current;
			return returnCurrent;
		}
		return returnCurrent;
    }
}
