package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DrivetrainSubsystem extends PIDSubsystem {
	
	/*
	 * Values for Drivetrain Speeds
	 */
	
	public static class DrivePID {
		public static final double k_P = 0.2;
		public static final double k_I = 0.0;
		public static final double k_D = 0.01;
		
		public static final double ABSOLUTE_TOLERANCE = 0.05;
		
		public static final double DISTANCE_PER_PULSE = 12.56 / 256 * 60.0/14.0; // assuming cuz 4pi is circumference???
		//tried 0.4, 0.9, 0.8;
		// low  60/14
		//high 44/32
	}

	public PIDController drivePID;
	
	public static final double MAX_SPEED = 1500.0;
	public static final double MAX_LOW_SPEED = 700.0;

	public Encoder leftEncoder, rightEncoder;
	public CANTalon[] motors;
	public DoubleSolenoid driveSol;
	public boolean isAutoShiftTrue;
	Timer time;
	
	public DrivetrainSubsystem() {
		super ("Drivetrain PID", DrivePID.k_P, DrivePID.k_I, DrivePID.k_P);
		time = new Timer();
		isAutoShiftTrue = false;
		
		leftEncoder = new Encoder(RobotMap.Drivetrain.LEFT_SIDE_ENCODER[0],RobotMap.Drivetrain.LEFT_SIDE_ENCODER[1],false);
		rightEncoder = new Encoder(RobotMap.Drivetrain.RIGHT_SIDE_ENCODER[0],RobotMap.Drivetrain.RIGHT_SIDE_ENCODER[1],true);
	    leftEncoder.setDistancePerPulse(DrivePID.DISTANCE_PER_PULSE);
		rightEncoder.setDistancePerPulse(DrivePID.DISTANCE_PER_PULSE);
		motors = new CANTalon[4];
		for(int i = 0; i<motors.length;i++){
			motors[i] = new CANTalon(RobotMap.Drivetrain.MOTOR_PORTS[i]);
		}
		drivePID = this.getPIDController();
		
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
    
    public double getDistanceDTLeft() {
    	return leftEncoder.getDistance();
    }
    
    public double getDistanceDTRight() {
    	return rightEncoder.getDistance();
    }
    public double getDistanceDTBoth() {
    	return rightEncoder.getDistance()/2 + leftEncoder.getDistance()/2;
    }
    
    public boolean isOnTarget(double setpoint) {
    	return Math.abs(getDistanceDTBoth()-setpoint) < DrivePID.ABSOLUTE_TOLERANCE;
    }

    public double getTranslationalDistanceForTurn(double angle) {
		return (angle/ 360.0) * (4 * Math.PI) * 1.08;
	} 
	
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
//		SmartDashboard.putNumber("PIDInput", getDistanceDTBoth());
		return getDistanceDTBoth();
	}
	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
//		SmartDashboard.putNumber("PIDOutput", output);
		rawDrive(output, output);
		
	}

    
}

