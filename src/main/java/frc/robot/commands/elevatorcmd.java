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
        elevator.L2Control(height);
    }

    @Override
    public void end(boolean interrupted) {
        
    }

    @Override
    public boolean isFinished() {
    return false;
  }
    
}
