package org.usfirst.frc.team649.robot.subsystems;

import org.opencv.core.Mat;
import org.usfirst.frc.team649.robot.OI;
import org.usfirst.frc.team649.robot.RobotMap;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class CameraSwitcher {
	Thread cameraThread;
	OI oi;
	AxisCamera axis; 
	UsbCamera usb;
	public boolean isCam1;
//	public CameraSwitcher() {
//		oi = new OI();
//		axis = CameraServer.getInstance().addAxisCamera(RobotMap.Camera.axisName,RobotMap.Camera.axisPort);
//		usb = CameraServer.getInstance().startAutomaticCapture();
//		isCam1 = true;
//	}
//	public void SteamyCameraSwitcher()
//	{
//		if(isCam1)
//		{
//			NetworkTable.getTable("").putString("CameraSelection", axis.getName());
//		}
//		else if(!isCam1)
//		{
//			NetworkTable.getTable("").putString("CameraSelection", usb.getName());
//		}
//	}
}
