// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import javax.swing.text.StyleContext.SmallAttributeSet;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrainSubsystem extends SubsystemBase {

    private Encoder m_leftEncoder;
    private Encoder m_rightEncoder;
    private WPI_TalonSRX m_talonSRX1;
    private WPI_TalonSRX m_talonSRX3;
    private WPI_VictorSPX m_victorSPX2;
    private WPI_VictorSPX m_victorSPX4;
    private DifferentialDrive m_differentialDrive;

    /** Creates a new ExampleSubsystem. */
    public DriveTrainSubsystem() {
        m_leftEncoder = new Encoder(0, 1, false, EncodingType.k4X);
        addChild("Left Encoder",m_leftEncoder);
        m_leftEncoder.setDistancePerPulse(0.0088);

        m_rightEncoder = new Encoder(0, 1, false, EncodingType.k4X);
        addChild("Right Encoder",m_rightEncoder);
        m_rightEncoder.setDistancePerPulse(0.0088);

        m_talonSRX1 = new WPI_TalonSRX(1);
        m_talonSRX3 = new WPI_TalonSRX(3);
        m_victorSPX2 = new WPI_VictorSPX(2);
        m_victorSPX2.follow(m_talonSRX1);
        m_victorSPX4 = new WPI_VictorSPX(4);
        m_victorSPX4.follow(m_talonSRX3);

        m_differentialDrive = new DifferentialDrive(m_talonSRX1, m_talonSRX3);
        addChild("Differential Drive", m_differentialDrive);
        m_differentialDrive.setSafetyEnabled(true);
        m_differentialDrive.setExpiration(0.1);
        m_differentialDrive.setMaxOutput(1.0);
    }

    //custom function to return average encoder value
    public double getEncoderMeters() {
        return (m_leftEncoder.get() + m_rightEncoder.get()) / 2;
    }

    //custom function to actually drive robot
    public void arcadeDrive(double speed, double rotation) {
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
        SmartDashboard.putNumber("Left Encoder: ", m_leftEncoder.get());
        SmartDashboard.putNumber("Right Encoder: ", m_rightEncoder.get());
        SmartDashboard.putNumber("Average Encoder: ", getEncoderMeters());
    }

    

    // @Override
    // public void simulationPeriodic() {
    //   // This method will be called once per scheduler run during simulation
    // }
}
