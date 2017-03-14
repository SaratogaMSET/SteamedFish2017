package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {
	
	public CANTalon feederMotor;
	//always rotates
	public CANTalon hooperMotor;
	

    public ShooterSubsystem(){
    	
    	hooperMotor = new CANTalon(RobotMap.Shooter.HOOPER_PORT);
    	feederMotor = new CANTalon(RobotMap.Shooter.SHOOTER_FEEDER_PORT);
    }
  
   
    
   
    
   
    public void setFeedMotor(double power){
    	feederMotor.set(power);
    	hooperMotor.set(-power);
    }
//    public void setHooperIn(double speed){
//    	hooperMotorIn.set(speed);
//    }
//    public void setHooperOut(double minSpeed, double maxSpeed, double period, double currentTime){
//    	double a = (maxSpeed-minSpeed)/2;
//    	double b = 2*Math.PI/period;
//    	double d = a + minSpeed;
//    	double speed = a*Math.sin(b*currentTime) + d;
//    	hooperMotorOut.set(speed);
//    }
//    public void setHooperOutRaw(double speed){
//    	hooperMotorOut.set(speed);
//    }
   
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
    
}

