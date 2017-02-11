package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DrivetrainSubsystem extends Subsystem {
	
	/*
	 * Values for Drivetrain Speeds
	 */
	public static final double MAX_SPEED = 1500.0;
	public static final double MAX_LOW_SPEED = 700.0;

	public Encoder leftEncoder, rightEncoder;
	public CANTalon[] motors;
	public DoubleSolenoid driveSol;
	public boolean isAutoShiftTrue;
	Timer time;
	
	public DrivetrainSubsystem() {
		motors = new CANTalon[4];
		for(int i = 0; i<motors.length;i++){
			motors[i] = new CANTalon(RobotMap.Drivetrain.MOTOR_PORTS[i]);
		}
	}
	public void shift(boolean highGear){
		driveSol.set(highGear ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
	}
	public void autoShift(){
		if((Math.abs(leftEncoder.getRate()) + Math.abs(rightEncoder.getRate())) > MAX_LOW_SPEED*1.7){
			shift(true);
			isAutoShiftTrue = true;
			time.start();
		}else if(time.get() > 1.0){
			shift(false);
			isAutoShiftTrue = false;
			time.stop();
			time.reset();
		}
	}
	public double leftEncoderSpeed(){
		return leftEncoder.getRate();
	}
	public double rightEncoderSpeed(){
		return rightEncoder.getRate();
	}
	public void driveFwdRot(double fwd, double rot) {
		double left = fwd + rot, right = fwd - rot;
        double max = Math.max(1, Math.max(Math.abs(left), Math.abs(right)));
        left /= max;
        right /= max;
        rawDrive(left, right);
    }
    public void rawDrive(double left, double right) {
        motors[0].set(right);
        motors[1].set(right);
        motors[2].set(-left);
        motors[3].set(-left);     
    }
    public void resetEncoders(){
    	leftEncoder.reset();
    	rightEncoder.reset();
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

