package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

public class elevatorcmd extends Command{
    public Elevator elevator;
    public double height;
    public Timer timer;

    public elevatorcmd(Elevator elevatormove, double move){
        this.elevator = elevatormove;
        this.height = move;
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();

    }

    @Override
    public void execute() {
        elevator.ElevDegree(height);
    }

    @Override
    public void end(boolean interrupted) {
        elevator.setzeropower();
        timer.stop();
        timer.reset();
    }

    @Override
    public boolean isFinished() {
    if ((Math.toDegrees(elevator.encoder.getPosition()) < height +5 && Math.toDegrees(elevator.encoder.getPosition()) > height -5)|| timer.get()>3){
    return true;
    }else{
    return false;
    }
  }
}
