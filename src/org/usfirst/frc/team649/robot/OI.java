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
//	 public class Operator1 {
//		 public double getSlider(){
//			return ((-operatorJoystick.getRawAxis(3))+1)/2; 
//		}
//		 public boolean getShoot(){
//			 return operatorJoystick.getRawButton(1);
//		 }
//		 public boolean setGearFlapOut(){
//			 return operatorJoystick.getRawButton(5);
//		 }
//		 public boolean setGearFlapIn(){
//			 return operatorJoystick.getRawButton(3);
//		 }
//		 public boolean setFunnelPistonUp(){
//			 return operatorJoystick.getRawButton(6);
//		 }
//		 public boolean setFunnelPistonDown(){
//			 return operatorJoystick.getRawButton(4);
//		 }
//		 public boolean runFunnelMotors(){
//			 return operatorJoystick.getRawButton(7);
//		 }
//		 
//		 public boolean setDownIntakePistons(){
//			 return operatorJoystick.getRawButton(8);
//		 }
//		 public boolean setUpIntakePistons(){
//			 return operatorJoystick.getRawButton(9);
//		 }
//	
//		 public boolean runIntakeIn(){
//			 return operatorJoystick.getRawButton(11);
//		 }
//		 public boolean runFeederWheel(){
//			 return operatorJoystick.getRawButton(12);
//		 }
//		 public boolean runAgitator() {
//			 return operatorJoystick.getRawButton(2);
//		 }
//		 public double getTurret(){
//			 if(operatorJoystick.getX() >= 0.05||operatorJoystick.getX()<=-0.05){
//				 return operatorJoystick.getX()/2;
//			 } else {
//				 return 0;
//			 }
//		 }
//		
//		 
//	 }
	 public class Operator{
		 public boolean intakeFlapUp(){
			 return operatorJoystick.getRawButton(8);
		 }
		 public boolean intakeFlapDown(){
			 return operatorJoystick.getRawButton(7);
		 }
		 public boolean runIntake(){
			 return operatorJoystick.getRawButton(12);
		 }
		 public boolean slowShoot(){
			 return operatorJoystick.getRawButton(1) && !operatorJoystick.getRawButton(2);
		 }
		 public boolean fastShoot(){
			 return operatorJoystick.getRawButton(1) && operatorJoystick.getRawButton(2);
		 }
		 public boolean isManualTurret(){
			 if(operatorJoystick.getPOV() == 270){
				 return true;
			 }
			 return false;
		 }
		 public double getX(){
			 return operatorJoystick.getX();
		 }
		 public double getSlider(){
			 return operatorJoystick.getRawAxis(3);
		 }
		 public boolean getGearFlap(){
			 return operatorJoystick.getRawButton(3);
		 }
		 public boolean runFunnelMotorIn(){
			 return operatorJoystick.getRawButton(4);
		 }
		 public boolean runFunnelMotorOut(){
			 return operatorJoystick.getRawButton(6);
		 }
		 public boolean isDavidCamera(){
			 if(operatorJoystick.getPOV() == 90){
				 return true;
			 }
			 return false;
		 }
		 public boolean funnelFlapOut(){
			 return operatorJoystick.getRawButton(10);
		 }
		 public boolean funnelFlapIn(){
			 return operatorJoystick.getRawButton(9);
		 }
		 public boolean getHang(){
			 return operatorJoystick.getRawButton(11);
		 }
	 }
	 
	 public class Driver {
		 public double getForward() {
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

//			public Boolean shiftDown() {
//				return driveJoystickVertical.getRawButton(1);
//			}

			public Boolean shiftUp() {
				return driveJoystickHorizontal.getRawButton(1) || driveJoystickVertical.getRawButton(1);
			}
			public boolean kyleCamera()
			{
				return driveJoystickHorizontal.getRawButton(2);
			}
	 }
}
