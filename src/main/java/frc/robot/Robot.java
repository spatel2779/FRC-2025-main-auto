// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.time.Year;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.algaeintakecmd;
import frc.robot.sensor.algaesense;
import frc.robot.subsystems.CoralPlacer;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.algae;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  public int flag;

  private final RobotContainer m_robotContainer;

  public Robot() {
    m_robotContainer = new RobotContainer();
    
    // m_robotContainer.elevator.encoder.setPosition(0);
    

  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    SmartDashboard.putNumber("Elevator-encoder", m_robotContainer.elevator.encoder.getPosition());
    SmartDashboard.putNumber("Absolute Encoder Gimbal", Math.toDegrees(m_robotContainer.gimbal.encoder.getPosition()));
    m_robotContainer.elecorr.dio();
    

    //elevator sensor reset
    if(!m_robotContainer.elecorr.input.get()){
      m_robotContainer.elevator.encoder.setPosition(0);
    }
  }


  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    flag = 0;
    // m_robotContainer.slide.setDefaultCommand(new RunCommand(() -> m_robotContainer.slide.LinearSlider(0),m_robotContainer.slide));
    m_robotContainer.elevator.setDefaultCommand(new RunCommand(()->m_robotContainer.elevator.setzero(), m_robotContainer.elevator));
    m_robotContainer.gimbal.setDefaultCommand(new RunCommand(() -> m_robotContainer.gimbal.GimbalControl(0),m_robotContainer.gimbal));
    m_robotContainer.coral.setDefaultCommand(new RunCommand(()-> m_robotContainer.coral.Take(0.0), m_robotContainer.coral));
    // m_robotContainer.funnel.setDefaultCommand(new RunCommand(() -> m_robotContainer.funnel.coralstart(0), m_robotContainer.funnel));
    // m_robotContainer.algae.setDefaultCommand(new RunCommand(()-> m_robotContainer.algae.Take(0.0), m_robotContainer.algae));

  }

  @Override
  public void teleopPeriodic() {
    if (m_robotContainer.Driver_1.getLeftTriggerAxis()>0.1){
        DriveConstants.kMaxSpeedMetersPerSecond = DriveConstants.kFastSpeedMetersPerSecond*(1-m_robotContainer.Driver_1.getLeftTriggerAxis()) ;
      }
      else{
        DriveConstants.kMaxSpeedMetersPerSecond = DriveConstants.kFastSpeedMetersPerSecond;
      }

    if(m_robotContainer.Driver_2.getLeftBumperButton()){
      DriveConstants.kMaxSpeedMetersPerSecond = DriveConstants.kSlowSpeedMetersPersecond ;
    }

    if (m_robotContainer.Driver_2.getAButton() && flag ==0){
      flag =1; 
      
    }
    else if(m_robotContainer.Driver_2.getYButton()){
      flag = 0;
    }

    if(flag == 1){
      if(algaesense.dio()){
        SmartDashboard.putBoolean("sensor start?",true );
        SmartDashboard.putBoolean("sensor start but halted?",false );

        m_robotContainer.algae.Take(-0.6);
      }else{
        SmartDashboard.putBoolean("sensor start but halted?",true );
        m_robotContainer.algae.Take(0);
      }
    }
    else if(flag ==0){
      SmartDashboard.putBoolean("sensor start?",false );
      SmartDashboard.putBoolean("sensor start but halted?",false );
      m_robotContainer.algae.Take(0);
    }

  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
