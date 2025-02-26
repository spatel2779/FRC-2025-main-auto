// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Constants.DriveConstants;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  public int flag;
  PowerDistribution m_pdp;
  private final RobotContainer m_robotContainer;
  public double gyroval = 0;


  public Robot() {
    m_robotContainer = new RobotContainer();
    m_robotContainer.elevator.encoder.setPosition(0);
    m_pdp = new PowerDistribution(1, ModuleType.kRev);

    

  }
  @Override
  public void robotInit(){

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
  public void autonomousPeriodic() {
    SmartDashboard.putNumber("Gyroval",  m_robotContainer.m_robotDrive.yaw());
  }

  @Override
  public void autonomousExit() {
    gyroval = m_robotContainer.m_robotDrive.yaw();
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    flag = 0;
    m_robotContainer.m_robotDrive.zeroHeading();
    m_robotContainer.m_robotDrive.OFFSET = gyroval+180;
    m_robotContainer.elevator.setDefaultCommand(new RunCommand(()->m_robotContainer.elevator.setzeropower(), m_robotContainer.elevator));
    m_robotContainer.gimbal.setDefaultCommand(new RunCommand(() -> m_robotContainer.gimbal.GimbalControl(0),m_robotContainer.gimbal));
    m_robotContainer.coral.setDefaultCommand(new RunCommand(()-> m_robotContainer.coral.Take(0.0), m_robotContainer.coral));
    // m_robotContainer.algae.setDefaultCommand(new RunCommand(()-> m_robotContainer.algae.Take(0.0), m_robotContainer.algae));

  }

  @Override
  public void teleopPeriodic() {
    SmartDashboard.putNumber("Gyrovalteleop",  m_robotContainer.m_robotDrive.yaw());

    m_robotContainer.led.set(-0.87);
    // double voltage = m_pdp.getVoltage();
    // SmartDashboard.putNumber("Voltage", voltage);
    // double temperatureCelsius = m_pdp.getTemperature();
    // SmartDashboard.putNumber("Temperature", temperatureCelsius);
    //   // Get the total current of all channels.
    //   double totalCurrent = m_pdp.getTotalCurrent();
    //   SmartDashboard.putNumber("Total Current", totalCurrent);
  
    //   // Get the total power of all channels.
    //   // Power is the bus voltage multiplied by the current with the units Watts.
    //   double totalPower = m_pdp.getTotalPower();
    //   SmartDashboard.putNumber("Total Power", totalPower);
  
    //   // Get the total energy of all channels.
    //   // Energy is the power summed over time with units Joules.
    //   double totalEnergy = m_pdp.getTotalEnergy();
    //   SmartDashboard.putNumber("Total Energy", totalEnergy);
    //   double current8 = m_pdp.getCurrent(8);
    //   SmartDashboard.putNumber("Current Channel 8", current8);


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

    if(flag==1 && m_robotContainer.algaeSensor.dio()){
      m_robotContainer.algae.Take(-0.6);
    } else{
      m_robotContainer.algae.Take(0);

    }

    if(m_robotContainer.aprilDistance.hasTargetA() && m_robotContainer.aprilDistance.hasTargetB()){
      m_robotContainer.led.set(0.73);
    }else if(m_robotContainer.aprilDistance.hasTargetA()){
      m_robotContainer.led.set(-0.15);
    }else if(m_robotContainer.aprilDistance.hasTargetB()){
      m_robotContainer.led.set(-0.11);
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
