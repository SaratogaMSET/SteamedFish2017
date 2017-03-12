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
	public static final double zeroDegreePWMRight = 1500;
	public static final double maxDegreePWMRight = 1785;
	public static final double anglePerPWMRight = 285/(58.3-3.4);
	public static final double zeroDegreePWMLeft = 187;
	public static final double maxDegreePWMLeft = 482;
	public static final double anglePerPWMLeft = 285/(58.3-3.4);
	public static final double minAngle = 0;
	public static final double maxAngle = 100;
	
	public static Servo servoRight;
	public static Servo servoLeft;
	
	public HoodSubsystem() {
		servoRight = new Servo(RobotMap.Hood.HOOD_SERVO_RIGHT_PORT);
		servoLeft = new Servo(RobotMap.Hood.HOOD_SERVO_LEFT_PORT);
	}
	public void setServoRaw(double joystickVal) {
		servoRight.setRaw((int) ((joystickVal + 1.0) / 2.0 * 285) + 1500);
		servoLeft.setRaw((int) ((-joystickVal + 1.0) / 2.0 * 285) + 187);
	}
	public void setServoWithAngle(double angle) {
		if(angle <= maxAngle && angle >= minAngle){
			servoRight.setRaw((int)((angle-minAngle)*anglePerPWMRight+1500)); //1215
			servoLeft.setRaw((int) ((angle-minAngle)*anglePerPWMLeft + 187));
		}
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

