package org.usfirst.frc.team649.robot.subsystems;

import org.opencv.core.Mat;
import org.usfirst.frc.team649.robot.OI;
import org.usfirst.frc.team649.robot.RobotMap;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class CameraSwitcher {
	Thread cameraThread;
	OI oi;

	public CameraSwitcher() {
		oi = new OI();
		cameraThread = new Thread(() -> {

			AxisCamera axis = new AxisCamera("axis", RobotMap.Camera.axisPort);
			UsbCamera usb = new UsbCamera("cam1", 0);

			axis.setResolution(640, 480);
			usb.setResolution(640, 480);

			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink = CameraServer.getInstance().getVideo(axis);
			// Setup a CvSource. This will send images back to the Dashboard
			CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle", 640, 480);

			// Mats are very memory expensive. Lets reuse this Mat.
			Mat mat = new Mat();

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			while (!Thread.interrupted()) {
				// Tell the CvSink to grab a frame from a camera and put it in
				// the source mat.
				// If there is an error notify the output.
				if (oi.operator.davidSwitch())
					cvSink = CameraServer.getInstance().getVideo(axis);
				if (oi.driver.kyleSwitch())
					cvSink = CameraServer.getInstance().getVideo(usb);

				if (cvSink.grabFrame(mat) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSink.getError());
					// skip the rest of the current iteration
					continue;
				}

				// Give the output stream a new image to display
				outputStream.putFrame(mat);
			}
		});
	}
}
