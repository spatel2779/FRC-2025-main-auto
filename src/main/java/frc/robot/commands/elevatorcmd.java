package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

public class elevatorcmd extends Command{
    public Elevator elevator;
    public double height;

    public elevatorcmd(Elevator elevatormove, double move){
        this.elevator = elevatormove;
        this.height = move;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        elevator.ElevDegree(height);
    }

    @Override
    public void end(boolean interrupted) {
        elevator.setzero();
    }

    @Override
    public boolean isFinished() {
    if (Math.toDegrees(elevator.encoder.getPosition()) < height +2 && Math.toDegrees(elevator.encoder.getPosition()) > height -2){
    return true;
    }else{
    return false;
    }
  }
}
