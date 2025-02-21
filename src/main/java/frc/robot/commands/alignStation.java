package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.limelight.Limelight3DDistance;
import frc.robot.subsystems.CoralPlacer;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Elevator;

public class alignStation extends Command{
    public Limelight3DDistance llm;
    public Elevator ele;
    public CoralPlacer coral;
    public DriveSubsystem m_robotDrive;
    public Timer timer;
    public double temptimer;

    public alignStation(Limelight3DDistance llm1, DriveSubsystem roboDrive){
        this.llm = llm1;
        this.m_robotDrive = roboDrive;
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();

    }

    @Override
    public void execute() {
        llm.stationlimelight(m_robotDrive, 1);
        if (timer.get()>2){
            llm.stoprobot(m_robotDrive);
        }
        
    }

    @Override
    public void end(boolean interrupted) {
        ele.ElevDegree(0);
        
    }

    @Override
    public boolean isFinished() {
    return true;
  }

    
}
