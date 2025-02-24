package frc.robot.commands.limelight;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.limelight.Limelight3DDistance;
import frc.robot.subsystems.DriveSubsystem;

public class llaligntoreef extends Command {
    
    Limelight3DDistance limelight3dDistance;
    DriveSubsystem driveSubsystem;
    Timer timer;
    public llaligntoreef(Limelight3DDistance llm1, DriveSubsystem roboDrive){
        this.limelight3dDistance = llm1;
        this.driveSubsystem =  roboDrive;
    }
    @Override
    public void initialize(){
        timer = new Timer();
        timer.start();

    }
    @Override
    public void execute() {
        limelight3dDistance.reeflimelightA(driveSubsystem);
        SmartDashboard.putBoolean("llrefcommand", true);
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.drive(0, 0, 0, false, false);
    }

    @Override
    public boolean isFinished() {
        if (timer.get()>3)
        return true;
        else
        return false;
        
  }
}
