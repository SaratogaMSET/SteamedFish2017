package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.RobotMap;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */


public class HoodSubsystem extends Subsystem {
	


    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static final double zeroDegreePWM = 397;
	public static final double maxDegreeValue = 198.8696;
	public static final double maxDegreePWM = 1427;
	public static final double PWMPerDegree = (maxDegreePWM-zeroDegreePWM)/maxDegreeValue;
	public static final double minActualPWM = 3.3; //temporary
	public static final double maxActualPWM = 67.9; //temporary 
	public static Servo servoRight;
	public static Servo servoLeft;
	
	public HoodSubsystem() {
		servoRight = new Servo(RobotMap.Hood.HOOD_SERVO_RIGHT_PORT);
		servoLeft = new Servo(RobotMap.Hood.HOOD_SERVO_LEFT_PORT);
	}
	public void setServoRaw(double joystickVal) {
		servoRight.setRaw((int) ((joystickVal + 1.0) / 2.0 * 285) + 1500);
		servoLeft.setRaw((int) ((-joystickVal + 1.0) / 2.0 * 295) + 187);
	}
	public void setServoWithAngle(double angle) {
		servoLeft.setRaw(getPWMValueLeft(angle));
		servoRight.setRaw(getPWMValueRight(angle));
	}
	public int getPWMValueRight(double degree) {
		if (degree > 35) {
			degree = 35;
		} else if (degree < 0) {
			degree = 0;
		}
		int pwmVal = (int)(((degree/35) * 285) + 1500);
		return pwmVal;
	}
	public int getPWMValueLeft(double degree) {
		if (degree > 35) {
			degree = 35;
		} else if (degree < 0) {
			degree = 0;
		}
		degree = -(35-degree);
		int pwmVal = (int)(((degree/35) * 295) + 187);
		return pwmVal;
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

