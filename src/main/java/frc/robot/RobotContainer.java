// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PS4Controller.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.CoralPlacer;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Gimbal;
import frc.robot.subsystems.algae;
import frc.robot.commands.algae.algaeintaketime;
import frc.robot.commands.algae.algaeouttime;
import frc.robot.commands.coral.CoralIntakeCmd;
import frc.robot.commands.coral.CoralOutCmd;
import frc.robot.commands.elevator.elevatorautocmd;
import frc.robot.commands.elevator.elevatorpowdowncmd;
import frc.robot.commands.elevator.elevatorpowupcmd;
import frc.robot.commands.elevator.elevepowup_gim_alg;
import frc.robot.commands.elevator.levels;
import frc.robot.commands.elevator.levelsecondbase;
import frc.robot.commands.gimbal.Gimbalcmd;
import frc.robot.commands.gimbal.gimbalpowdowncmd;
import frc.robot.commands.gimbal.gimbalpowupcmd;
import frc.robot.commands.limelight.alignStation;
import frc.robot.commands.limelight.alignStation_a;
import frc.robot.commands.limelight.alignStation_b;
import frc.robot.commands.limelight.alignStation_b_algae;
import frc.robot.limelight.Limelight3DDistance;
import frc.robot.sensor.algaesense;
import frc.robot.sensor.metal;

public class RobotContainer {

  public final XboxController Driver_1 = new XboxController(0);
  public final XboxController Driver_2 = new XboxController(1);

  public Elevator elevator;
  private DriveSubsystem m_robotDrive ;
  public Gimbal gimbal;
  public algae algae;
  public Limelight3DDistance aprilDistance;
  public CoralPlacer coral;
  public metal elecorr;
  public algaesense algaeSensor;

  private final SendableChooser<Command> autoChooser;
  
  //Commands
  public static alignStation align;

  public RobotContainer(){
    
    algae = new algae(); 
    elecorr = new metal();
    elevator = new Elevator();
    m_robotDrive = new DriveSubsystem();
    algaeSensor = new algaesense();
    gimbal = new Gimbal();
    coral = new CoralPlacer();
    aprilDistance = new Limelight3DDistance();
    
    NamedCommands.registerCommand("Algae_sensorin", new algaeintaketime(algae,2,algaeSensor)); 
    NamedCommands.registerCommand("Algae_out", new algaeouttime(algae,1)); 
    NamedCommands.registerCommand("Gim_L3", new Gimbalcmd(gimbal, 195)); //190  185
    NamedCommands.registerCommand("Ele_L4", new elevatorautocmd(elevator, 65)); // 67
    NamedCommands.registerCommand("Coral_in", new CoralIntakeCmd(coral, 1));
    NamedCommands.registerCommand("Coral_out", new CoralOutCmd(coral, 0.5));
    NamedCommands.registerCommand("Ele_ground", new elevatorautocmd(elevator, 3)); //5
    NamedCommands.registerCommand("Gim_Ground", new Gimbalcmd(gimbal, 15));
    NamedCommands.registerCommand("Gim_Algae", new Gimbalcmd(gimbal, 120));
    NamedCommands.registerCommand("LL_Align_A", new alignStation_a(aprilDistance, m_robotDrive, 1.5));
    NamedCommands.registerCommand("LL_Align_B", new alignStation_b(aprilDistance, m_robotDrive, 1.5));
    NamedCommands.registerCommand("LL_Align_Algae", new alignStation_b_algae(aprilDistance, m_robotDrive, 1.5));
    NamedCommands.registerCommand("LL_Align", new alignStation(aprilDistance, m_robotDrive, 1.2));
    NamedCommands.registerCommand("Gim_power_up", new gimbalpowupcmd(gimbal, 120));
    NamedCommands.registerCommand("Gim_power_down", new gimbalpowdowncmd(gimbal, 120));
    NamedCommands.registerCommand("Ele_power_up", new elevatorpowupcmd(elevator, 20, 2));
    NamedCommands.registerCommand("Ele_power_down", new elevatorpowdowncmd(elevator, 25, 1.5));
    NamedCommands.registerCommand("Algae_checking", new elevepowup_gim_alg(elevator, gimbal, 120, algaeSensor, algae, 5, 23));

    autoChooser = AutoBuilder.buildAutoChooser("fwd");
    SmartDashboard.putData("Auto Chooser", autoChooser);  
  
    configureBindings();  
    m_robotDrive.setDefaultCommand(
      new RunCommand(
        () -> m_robotDrive.drive(
          -MathUtil.applyDeadband(Driver_1.getLeftY(),0.06),
          -MathUtil.applyDeadband(Driver_1.getLeftX(),0.06),
          -MathUtil.applyDeadband(Driver_1.getRightX(),0.06),
          true,false),
          m_robotDrive));
  }
   
  private void configureBindings() {
    //Driver Controls for Driver 1
    new JoystickButton(Driver_1, Button.kR1.value)
    .whileTrue(new RunCommand(
      () -> m_robotDrive.setX(),
      m_robotDrive));

    new JoystickButton(Driver_1, Button.kTriangle.value)
    .whileTrue(new RunCommand(
      () -> m_robotDrive.driveresetHeading(),
      m_robotDrive));

    new JoystickButton(Driver_1, Button.kCircle.value)
    .whileTrue(new RunCommand(
      ()-> aprilDistance.reeflimelightA(m_robotDrive)));
    
      new JoystickButton(Driver_1, Button.kCross.value)
      .whileTrue(new RunCommand(
        ()-> aprilDistance.reeflimelightB(m_robotDrive)));// - negative for right side
        // ()-> aprilDistance.reeflimelightB(m_robotDrive)));
    
    new JoystickButton(Driver_1, Button.kSquare.value)
    .whileTrue(new RunCommand( ()-> aprilDistance.stationlimelight(m_robotDrive), m_robotDrive));
  

    new JoystickButton(Driver_1, Button.kSquare.value)
    .whileFalse(new RunCommand( ()-> aprilDistance.limelightTable.getEntry("ledMode").setNumber(1), aprilDistance));

    // /*Mechanism Control for Driver 2*/

    // POV for Elevator position 
    //ground
    new POVButton(Driver_2, 180).onTrue(
      new levels(elevator, gimbal, 3, 100,0.5));
    //L2
    new POVButton(Driver_2, 270).onTrue(
      // new RunCommand(()->elevator.degele(10)));
      new levelsecondbase(elevator, gimbal, 9, 210, 0.5));
    //L3
    new POVButton(Driver_2, 0).onTrue(
      new levels(elevator, gimbal, 24, 210,0.5));
    // //L4
    new POVButton(Driver_2, 90).onTrue(
      new levels(elevator, gimbal, 61, 200,0.5));

    // // Coral Presets
    // new JoystickButton(Driver_2, Button.kOptions.value)
    // .onTrue(new RunCommand(()-> gimbal.gimbaldeg(12)));

    //manual gimbal control 
    new Trigger(() -> Driver_2.getRightY()<-0.1).whileTrue(
      new RunCommand(() -> gimbal.gimbalpowup(Math.abs(Driver_2.getRightY()),elevator), gimbal));

    new Trigger(() -> Driver_2.getRightY()>0.1).whileTrue(
      new RunCommand(() -> gimbal.gimbalpowdown(Driver_2.getRightY()), gimbal));


    //L2 algae intake
    new JoystickButton(Driver_2, Button.kL1.value).onTrue(
      new RunCommand(()->gimbal.gimbaldeg(110), gimbal));//tested
    //L3 algae intake
    new JoystickButton(Driver_2, Button.kR1.value).onTrue(
      new levels(elevator, gimbal, 25, 110,0.5));//tested


    // new JoystickButton(Driver_1, Button.kSquare.value)
    // .whileTrue(new RunCommand(() -> aprilDistance.reeflimelight(m_robotDrive), aprilDistance));


    //buttonfor coral intake
    new JoystickButton(Driver_2, Button.kCircle.value).whileTrue(
      new RunCommand(() -> coral.Take(0.6), coral));//Red Right in
    //button for coral outtake
    new JoystickButton(Driver_2, Button.kCross.value).whileTrue(
      new RunCommand(() -> coral.Take(-0.6),coral)); //blue left out
    

    //button for algae intake
    new JoystickButton(Driver_2, Button.kSquare.value).whileTrue(
      new RunCommand(() -> algae.Take(-0.6),algae)); // Green  
    //button for algae outtake
    new JoystickButton(Driver_2, Button.kTriangle.value).whileTrue(
      new RunCommand(() -> algae.Take(1),algae));//yellow
 

    //manual elevator control 
    new Trigger(() -> Math.abs(Driver_2.getLeftTriggerAxis())>0.1)
    .whileTrue(new RunCommand(() -> elevator.ElevatorDown(gimbal, Driver_2.getLeftTriggerAxis()), elevator));
    
    new Trigger(() -> Math.abs(Driver_2.getRightTriggerAxis())>0.1)
    .whileTrue(new RunCommand(() -> elevator.ElevatorUP(gimbal, Driver_2.getRightTriggerAxis()), elevator));
    }

  public Command getAutonomousCommand() {

    return new SequentialCommandGroup( autoChooser.getSelected());
  }
  
}
