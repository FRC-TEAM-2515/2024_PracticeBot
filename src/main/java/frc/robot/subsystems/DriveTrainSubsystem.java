// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//constants
import static frc.robot.Constants.DriveTrainConstants;

public class DriveTrainSubsystem extends SubsystemBase {

    private WPI_TalonSRX m_talonSRX1;
    private WPI_TalonSRX m_talonSRX3;
    private WPI_VictorSPX m_victorSPX2;
    private WPI_VictorSPX m_victorSPX4;
    private DifferentialDrive m_differentialDrive;

    private AHRS m_gyro;

    //constructor
    public DriveTrainSubsystem() {
        m_talonSRX1 = new WPI_TalonSRX(1); //left leader
        m_talonSRX3 = new WPI_TalonSRX(3); //left follower
        m_victorSPX2 = new WPI_VictorSPX(2); //right leader
        m_victorSPX4 = new WPI_VictorSPX(4); //right follower

        m_talonSRX1.configFactoryDefault();
        m_victorSPX2.configFactoryDefault();
        m_talonSRX3.configFactoryDefault();
        m_victorSPX4.configFactoryDefault();

        m_victorSPX2.follow(m_talonSRX1);
        m_victorSPX4.follow(m_talonSRX3);

        //invert right side
        m_talonSRX1.setInverted(false);
        m_victorSPX2.setInverted(false);
        m_talonSRX3.setInverted(true);
        m_victorSPX4.setInverted(true);

        m_talonSRX1.setNeutralMode(NeutralMode.Coast);
        m_victorSPX2.setNeutralMode(NeutralMode.Coast);
        m_talonSRX3.setNeutralMode(NeutralMode.Coast);
        m_victorSPX4.setNeutralMode(NeutralMode.Coast);

        //Set the peak and nominal outputs
        m_talonSRX1.configNominalOutputForward(0, 30);
        m_talonSRX1.configNominalOutputReverse(0, 30);
        m_talonSRX1.configPeakOutputForward(1, 30);
        m_talonSRX1.configPeakOutputReverse(-1, 30);

        m_talonSRX3.configNominalOutputForward(0, 30);
        m_talonSRX3.configNominalOutputReverse(0, 30);
        m_talonSRX3.configPeakOutputForward(1, 30);
        m_talonSRX3.configPeakOutputReverse(-1, 30);

        //Does this change how fast the motors speed up? 
        m_talonSRX1.configOpenloopRamp(DriveTrainConstants.kRampRate);
        m_talonSRX3.configOpenloopRamp(DriveTrainConstants.kRampRate);

        m_talonSRX1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
        m_talonSRX3.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);

        m_differentialDrive = new DifferentialDrive(m_talonSRX1, m_talonSRX3);
        addChild("Differential Drive", m_differentialDrive);
        m_differentialDrive.setSafetyEnabled(true);
        m_differentialDrive.setExpiration(0.1);
        m_differentialDrive.setMaxOutput(1.0);

        // Competition Chassis NavX
        m_gyro = new AHRS(Port.kMXP);
        // Practice Chassis NavX Micro
        //  m_gyro = new AHRS(Port.kUSB);

        resetEncoders();
        updateSmartDashboard();
    }

    //custom function to reset encoder values
    public void resetEncoders() {
        m_talonSRX1.setSelectedSensorPosition(0);
        m_talonSRX3.setSelectedSensorPosition(0);
        m_gyro.reset();
    }

    //custom function to return average encoder value in meters
    public double getEncoderMeters() {
        double leftEncoder = Math.abs(m_talonSRX1.getSelectedSensorPosition());
        double rightEncoder = Math.abs(m_talonSRX3.getSelectedSensorPosition());
        double averageEncoderPosition = (leftEncoder + rightEncoder) / 2;
        double converted = (Math.PI * Units.inchesToMeters(DriveTrainConstants.kWheelDiameterInches)) //circumference
                         * (averageEncoderPosition / DriveTrainConstants.kUnitsPerRev)                //revolutions
                         / DriveTrainConstants.kGearRatio;                                            //gear ratio
        return converted;
    }

    //custom function to actually drive robot
    public void drive(double speed, double rotation) {
        m_differentialDrive.arcadeDrive(speed, -rotation);
    }

    //custom function to update Smart Dashboard Values
    public void updateSmartDashboard() {
        SmartDashboard.putNumber("imu-yaw", m_gyro.getYaw());
        SmartDashboard.putNumber("imu-pitch", m_gyro.getPitch());
        SmartDashboard.putNumber("imu-roll", m_gyro.getRoll());
        SmartDashboard.putNumber("imu-angle", m_gyro.getAngle());

        SmartDashboard.putBoolean("imu-moving", m_gyro.isMoving());
        SmartDashboard.putBoolean("imu-connected", m_gyro.isConnected());
        SmartDashboard.putBoolean("imu-calibrating", m_gyro.isCalibrating());

        SmartDashboard.putNumber("Left Velocity", m_talonSRX1.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Right Velocity", m_talonSRX3.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Left Position", Math.abs(m_talonSRX1.getSelectedSensorPosition()));
        SmartDashboard.putNumber("Right Position", Math.abs(m_talonSRX3.getSelectedSensorPosition()));
        SmartDashboard.putNumber("Average Encoder Position (Meters)", getEncoderMeters());

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        updateSmartDashboard();
    }

    // /**
    //  * Example command factory method.
    //  *
    //  * @return a command
    //  */
    // public Command exampleMethodCommand() {
    //   // Inline construction of command goes here.
    //   // Subsystem::RunOnce implicitly requires `this` subsystem.
    //   return runOnce(
    //       () -> {
    //         /* one-time action goes here */
    //       });
    // }

    // /**
    //  * An example method querying a boolean state of the subsystem (for example, a digital sensor).
    //  *
    //  * @return value of some boolean subsystem state, such as a digital sensor.
    //  */
    // public boolean exampleCondition() {
    //   // Query some boolean state, such as a digital sensor.
    //   return false;
    // }

    // @Override
    // public void simulationPeriodic() {
    //   // This method will be called once per scheduler run during simulation
    // }
}
