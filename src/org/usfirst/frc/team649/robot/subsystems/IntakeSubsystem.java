package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class IntakeSubsystem extends Subsystem {
	
	public CANTalon blackRollerMotor, redWheelRoller;
	public DoubleSolenoid leftIntakeSol;
	public DoubleSolenoid rightIntakeSol;
	
	public IntakeSubsystem() {
		blackRollerMotor = new CANTalon(RobotMap.Intake.INTAKE_MOTOR_PORT_LEFT);
		redWheelRoller = new CANTalon(RobotMap.Intake.INTAKE_MOTOR_PORT_RIGHT);
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
		blackRollerMotor.set(-power); //this is red
		//rollerMotorLeft.set(-power);
		SmartDashboard.putNumber("Intake current", blackRollerMotor.getOutputCurrent());
	}
	public void setWheelRollers(double power){
		redWheelRoller.set(power);
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

