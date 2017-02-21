package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.RobotMap;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.PIDConstants;

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
	

	public static class PIDConstants {
		public static final double PID_ABSOLUTE_TOLERANCE =3.0;
		public static final double ABS_TOLERANCE = 3.0;
		public static  double k_P = .05; //0.2
		public static double k_I = 0;
		public static double k_D = 0.03;
		public static final double DISTANCE_PER_PULSE = 12.56 / 256 * 60.0/14.0; // assuming cuz 4pi is circumference???

	}

	
	public static final double MAX_SPEED = 1500.0;
	public static final double MAX_LOW_SPEED = 700.0;

	public Encoder leftEncoder, rightEncoder;
	public CANTalon[] motors;
	public DoubleSolenoid driveSol;
	public PIDController encoderDrivePID;
	public boolean isAutoShiftTrue;
	Timer time;
	
	public DrivetrainSubsystem() {
		super ("Drivetrain PID", PIDConstants.k_P, PIDConstants.k_I, PIDConstants.k_P);
		time = new Timer();
		isAutoShiftTrue = false;
		
		leftEncoder = new Encoder(RobotMap.Drivetrain.LEFT_SIDE_ENCODER[0],RobotMap.Drivetrain.LEFT_SIDE_ENCODER[1],false);
		rightEncoder = new Encoder(RobotMap.Drivetrain.RIGHT_SIDE_ENCODER[0],RobotMap.Drivetrain.RIGHT_SIDE_ENCODER[1],true);
	    leftEncoder.setDistancePerPulse(PIDConstants.DISTANCE_PER_PULSE);
		rightEncoder.setDistancePerPulse(PIDConstants.DISTANCE_PER_PULSE);
		motors = new CANTalon[4];
		for(int i = 0; i<motors.length;i++){
			motors[i] = new CANTalon(RobotMap.Drivetrain.MOTOR_PORTS[i]);
		}
		encoderDrivePID = this.getPIDController();
		encoderDrivePID.setAbsoluteTolerance(PIDConstants.PID_ABSOLUTE_TOLERANCE);
		encoderDrivePID.setOutputRange(-.65, .65);		
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
    
    @Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return getDistanceDTBoth();
	}

	public double getEncDistToSetpoint(double setpoint){
		double diffL = Math.abs(leftEncoder.getDistance() - setpoint);
		double diffR = Math.abs(rightEncoder.getDistance() - setpoint);
		double diffEncoders = leftEncoder.getDistance()- rightEncoder.getDistance();
		
		if (Math.abs(diffEncoders) < 10){
			if (diffL <= diffR){
				return leftEncoder.getDistance();
			}
			else {
				return rightEncoder.getDistance();
			}
		}
		else{
			if (diffEncoders > 0){
				//left is greater than right
				return leftEncoder.getDistance();
			}
			else{
				return rightEncoder.getDistance();
			}
		}
	}
	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		rawDrive(-output,-output);
		
	}

	public double getTranslationalDistanceForTurn(double angle) {
		 System.out.println((angle/ 360.0) * (25.125 * Math.PI));
		 return (angle/ 360.0) * (25.125 * Math.PI) * 1.08;
	}
	public boolean isOnTarget(double distance) {
		// TODO Auto-generated method stub
		return Math.abs(getDistanceDTBoth() - distance) < DrivetrainSubsystem.PIDConstants.ABS_TOLERANCE;
	}
    
}

