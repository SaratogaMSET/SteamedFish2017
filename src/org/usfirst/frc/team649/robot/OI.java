package org.usfirst.frc.team649.robot;

import org.usfirst.frc.team649.robot.OI.Driver;
import org.usfirst.frc.team649.robot.OI.Operator;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public Joystick operatorJoystick;
	public Joystick driveJoystickHorizontal;
	public Joystick driveJoystickVertical;

	public Driver driver;
	public Operator operator;
	
	public OI() {
		operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
		driveJoystickHorizontal = new Joystick(RobotMap.DRIVE_JOYSTICK_HORIZONTAL);
		driveJoystickVertical = new Joystick(RobotMap.DRIVE_JOYSTICK_VERTICAL);
		driver = new Driver();
		operator = new Operator();
	}
	 public class Operator {
		 public boolean getShoot(){
			 return operatorJoystick.getRawButton(1);
		 }
	 }
	 public class Driver {
		 public double getForward() {
				// TODO Auto-generated method stub
				if (driveJoystickVertical.getY() >= 0.05 || driveJoystickVertical.getY() <= -0.05) {
					return -driveJoystickVertical.getY();
				} else {
					return 0.0;
				}

			}

			public double getRotation() {
				if (driveJoystickHorizontal.getX() >= 0.05 || driveJoystickHorizontal.getX() <= -0.05) {
					return -driveJoystickHorizontal.getX();
				} else {
					return 0.0;
				}
			}

			public Boolean shiftDown() {
				return driveJoystickVertical.getRawButton(1);
			}

			public Boolean shiftUp() {
				return driveJoystickHorizontal.getRawButton(1);
			}
	 }
}
