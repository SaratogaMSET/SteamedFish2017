package org.usfirst.frc.team649.robot.subsystems;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */


public class HoodSubsystem extends Subsystem {
	
	public PWM hoodServo;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static final double zeroDegreePWM = 397;
	public static final double ninteyDegreePWM = 1560;
	
	public int angleToPWM(double angle){
		double PWMDifference = ninteyDegreePWM-zeroDegreePWM;
		return (int) (angle/90 * PWMDifference + zeroDegreePWM);
	}
	public void setServoWithPWM(int PWM){
		hoodServo.setRaw(PWM);
	}
	public void setServoWIthAngle(double angle){
		setServoWithPWM(angleToPWM(angle));
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

