// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrainSubsystem extends SubsystemBase {

    private WPI_TalonSRX m_talonSRX1;
    private WPI_TalonSRX m_talonSRX3;
    private WPI_VictorSPX m_victorSPX2;
    private WPI_VictorSPX m_victorSPX4;
    private DifferentialDrive m_differentialDrive;

    /** Creates a new ExampleSubsystem. */
    public DriveTrainSubsystem() {
        
        m_talonSRX1 = new WPI_TalonSRX(1);
        m_talonSRX3 = new WPI_TalonSRX(3);
        m_victorSPX2 = new WPI_VictorSPX(2);
        m_victorSPX2.follow(m_talonSRX1);
        m_victorSPX4 = new WPI_VictorSPX(4);
        m_victorSPX4.follow(m_talonSRX3);

        m_talonSRX1.setInverted(false);
        m_talonSRX3.setInverted(true);

        m_talonSRX1.setNeutralMode(NeutralMode.Coast);
        m_talonSRX3.setNeutralMode(NeutralMode.Coast);

        m_talonSRX1.configFactoryDefault();
        m_victorSPX2.configFactoryDefault();
        m_talonSRX3.configFactoryDefault();
        m_victorSPX4.configFactoryDefault();

        /* Set the peak and nominal outputs */
        m_talonSRX1.configNominalOutputForward(0, 30);
        m_talonSRX1.configNominalOutputReverse(0, 30);
        m_talonSRX1.configPeakOutputForward(1, 30);
        m_talonSRX1.configPeakOutputReverse(-1, 30);

        m_talonSRX3.configNominalOutputForward(0, 30);
        m_talonSRX3.configNominalOutputReverse(0, 30);
        m_talonSRX3.configPeakOutputForward(1, 30);
        m_talonSRX3.configPeakOutputReverse(-1, 30);

        m_differentialDrive = new DifferentialDrive(m_talonSRX1, m_talonSRX3);
        addChild("Differential Drive", m_differentialDrive);
        m_differentialDrive.setSafetyEnabled(true);
        m_differentialDrive.setExpiration(0.1);
        m_differentialDrive.setMaxOutput(1.0);

        resetEncoders();
    }

    //custom function to reset encoder values
    private void resetEncoders() {
        m_talonSRX1.setSelectedSensorPosition(0);
        m_talonSRX3.setSelectedSensorPosition(0);
    }

    //custom function to return average encoder value in meters
    //currently does not convert to meters!
    public double getEncoderMeters() {
        double leftEncoder = m_talonSRX1.getSelectedSensorPosition();
        //double leftEncoder = m_talonSRX1.getSelectedSensorVelocity();
        double rightEncoder = m_talonSRX3.getSelectedSensorPosition();
        //double rightEncoder = m_talonSRX3.getSelectedSensorVelocity();
        return (leftEncoder + rightEncoder) / 2;
    }

    //custom function to actually drive robot
    public void drive(double speed, double rotation) {
        m_differentialDrive.arcadeDrive(-speed, rotation);
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

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        SmartDashboard.putNumber("Left Encoder: ", m_talonSRX1.getSelectedSensorPosition());
        SmartDashboard.putNumber("Right Encoder: ", m_talonSRX3.getSelectedSensorPosition());
        SmartDashboard.putNumber("Average Encoder: ", getEncoderMeters());
    }

    

    // @Override
    // public void simulationPeriodic() {
    //   // This method will be called once per scheduler run during simulation
    // }
}
