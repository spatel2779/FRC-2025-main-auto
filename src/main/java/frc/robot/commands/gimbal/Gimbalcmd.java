package frc.robot.commands.gimbal;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Gimbal;

public class Gimbalcmd extends Command{
    public Gimbal gimbal;
    public Integer angle;
    public Timer timer;
    public double time;

    public Gimbalcmd(Gimbal gim, int deg, double timetaken){
        this.gimbal = gim;
        this.angle = deg;
        this.time = timetaken;
        
    }

    
    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();

    }

    @Override
    public void execute() {
        gimbal.gimbaldeg(angle);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
    if ((Math.toDegrees(gimbal.encoder.getPosition()) < angle +3 && Math.toDegrees(gimbal.encoder.getPosition()) > angle -3)|| timer.get()>time){
        System.out.println("Gimbal L3 reached");
        return true;
    }else{
    return false;
    }
  } 
}
