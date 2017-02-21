package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeSubsytem extends Subsystem {
	
	public CANTalon rollerMotorLeft, rollerMotorRight;
	public CANTalon wheelRoller;
	public Solenoid leftIntakeSol;
	public Solenoid rightIntakeSol;
	public void IntakeSubystem(){
		rollerMotorLeft = new CANTalon(RobotMap.Intake.INTAKE_MOTOR_PORT_LEFT);
		rollerMotorRight = new CANTalon(RobotMap.Intake.INTAKE_MOTOR_PORT_RIGHT);
		wheelRoller = new CANTalon(RobotMap.Intake.HOOPER_FEEDER_PORT);
		leftIntakeSol = new Solenoid(RobotMap.Intake.LEFT_INTAKE_SOL);
		rightIntakeSol = new Solenoid(RobotMap.Intake.RIGHT_INTAKE_SOL);
		
	}
	public void setIntakeSol(boolean down){
		if(down){
        	leftIntakeSol.set(true);
        	rightIntakeSol.set(true);
		}else{
			leftIntakeSol.set(false);
			rightIntakeSol.set(false);
		}
	}
	public boolean isIntakeDown(){
		if(leftIntakeSol.get() == true && rightIntakeSol.get() == true){
			return true;
		}else{
			return false;
		}
	}
	public void setIntakeRollerMotor(double power){
		rollerMotorLeft.set(power);
		rollerMotorLeft.set(-power);
	}
	public void setWheelRollers(double power){
		wheelRoller.set(power);
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

