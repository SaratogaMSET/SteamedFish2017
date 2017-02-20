package org.usfirst.frc.team649.robot.subsystems;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */


public class HoodSubsystem extends Subsystem {
	
	public PWM hoodServoLeft;
	

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static final double zeroDegreePWMLeft = 397;
	public static final double ninteyDegreePWMLeft = 1427/2;
	public static final double zeroDegreeRight = 1427;
	
	public int angleToPWMLeft(double angle){
		double PWMDifference = ninteyDegreePWMLeft-zeroDegreePWMLeft;
		return (int) (angle/90 * PWMDifference + zeroDegreePWMLeft);
	}
	public int angleToPWMRight(double angle){
		double PWMDifference = zeroDegreeRight-ninteyDegreePWMLeft;
		return (int) (angle*90 * PWMDifference + ninteyDegreePWMLeft);
	}
	public void setServoWithPWM(int PWMLeft, int PWMRight){
		hoodServoLeft.setRaw(PWMLeft);
		hoodServoLeft.setRaw(PWMRight);
	}
	public void setServoWIthAngle(double angle){
		setServoWithPWM(angleToPWMLeft(angle),angleToPWMRight(angle));
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

