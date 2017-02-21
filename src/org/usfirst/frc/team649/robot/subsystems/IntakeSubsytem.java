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
	public DoubleSolenoid leftIntakeSol;
	public DoubleSolenoid rightIntakeSol;
	public void IntakeSubystem(){
		rollerMotorLeft = new CANTalon(RobotMap.Intake.INTAKE_MOTOR_PORT_LEFT);
		rollerMotorRight = new CANTalon(RobotMap.Intake.INTAKE_MOTOR_PORT_RIGHT);
		//wheelRoller = new CANTalon(RobotMap.Intake.HOOPER_FEEDER_PORT);
		leftIntakeSol = new DoubleSolenoid(RobotMap.Intake.LEFT_INTAKE_SOL[0], RobotMap.Intake.LEFT_INTAKE_SOL[1],RobotMap.Intake.LEFT_INTAKE_SOL[2]);
		rightIntakeSol = new DoubleSolenoid(RobotMap.Intake.RIGHT_INTAKE_SOL[0], RobotMap.Intake.RIGHT_INTAKE_SOL[1],RobotMap.Intake.RIGHT_INTAKE_SOL[2]);
		
	}
	public void setIntakeSol(boolean down){
		if(down){
        	leftIntakeSol.set(DoubleSolenoid.Value.kForward);
        	rightIntakeSol.set(DoubleSolenoid.Value.kForward);
		}else{
			leftIntakeSol.set(DoubleSolenoid.Value.kReverse);
			rightIntakeSol.set(DoubleSolenoid.Value.kReverse);
		}
	}
	public boolean isIntakeDown(){
		if(leftIntakeSol.get() == DoubleSolenoid.Value.kForward && rightIntakeSol.get() == DoubleSolenoid.Value.kForward){
			return true;
		}else{
			return false;
		}
	}
	public void setIntakeRollerMotor(double power){
		rollerMotorRight.set(power);
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

