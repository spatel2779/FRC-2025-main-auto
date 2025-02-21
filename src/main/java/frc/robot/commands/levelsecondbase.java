package frc.robot.commands;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Gimbal;

public class levelsecondbase extends Command{
    public Elevator ele;
    public Gimbal gim;
    private double deg;
    double gimbaldeg;
    Timer timer;
    double time;

    public levelsecondbase(Elevator elev, Gimbal gimb, double eledeg,double gimdeg,double time){
        this.ele = elev;
        this.gim = gimb;
        this.deg = eledeg;
        this.gimbaldeg = gimdeg;
        this.time = time;
        addRequirements(elev,gimb);
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();


    }

    @Override
    public void execute() {
        gim.gimbaldeg(gimbaldeg);
        if(timer.get()>time){
            ele.ElevDegree(deg);
        }



    }

    @Override
    public void end(boolean interrupted) {
        timer.stop();
        timer.reset();
    }

    @Override
    public boolean isFinished() {
        if(timer.get()>4){
            System.out.println("outside the cmd");
            return true;
        }else{
            return false;
        }
  }

    
}
