package frc.robot.limelight;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DriveSubsystem;

public class Limelight3DDistance extends SubsystemBase{

    private final NetworkTable limelightTable;
    private final NetworkTable limelightTablefrontA;
    private final NetworkTable limelightTablefrontB;



    public Limelight3DDistance() {
        this.limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        this.limelightTablefrontA = NetworkTableInstance.getDefault().getTable("limelight-a");
        this.limelightTablefrontB = NetworkTableInstance.getDefault().getTable("limelight-b");

    }


    private double[] getTargetCamerSpace() {

        return limelightTable.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
    }

    private boolean hasTarget() {
        return LimelightHelpers.getTV("");
    }

    public void updateDistance(DriveSubsystem m_robotDrive, Integer offset) {
        SmartDashboard.putBoolean("Cross Button", true);

        if (hasTarget()) {
            double[] distance = getTargetCamerSpace();
            double tx = limelightTable.getEntry("tx").getDouble(0.0);   
            double ty = limelightTable.getEntry("ty").getDouble(0.0); 
            double tz = limelightTable.getEntry("tz").getDouble(0.0);   
  

            SmartDashboard.putNumber("tx", distance[0]);
            SmartDashboard.putNumber("ty", distance[1]);
            SmartDashboard.putNumber("tz", distance[2]);
            SmartDashboard.putNumber("pitch", distance[3]);
            SmartDashboard.putNumber("yaw", distance[4]);
            SmartDashboard.putNumber("roll", distance[5]);
            m_robotDrive.drive(distance[2]>0.7?(-(distance[2]-0.3)*0.8):0, (distance[0]+ offset), -(distance[4]*0.01), false,true);
            
            
        }else{
            m_robotDrive.drive(0,0,0, true,false);

        } 

    }

    public void stoprobot(DriveSubsystem m_robotDrive){
        m_robotDrive.drive(0, 0, 0, false, true);
    }

    public void align(DriveSubsystem m_robotDrive, Integer aprilID){
        double[] distance1 = getTargetCamerSpace();
        if(LimelightHelpers.getFiducialID("limelight")==aprilID){
            m_robotDrive.drive(0, 0, -distance1[4]*0.01, false, true);
        }
        else{
            stoprobot(m_robotDrive);
        }
    }
    public void reeflimelightA(DriveSubsystem m_robotDrive, double slowval){
    double[] botval= limelightTablefrontA.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
    double llfront_detect = limelightTablefrontA.getEntry("tv").getInteger(0);
    if (llfront_detect>0){

        double tx = limelightTablefrontA.getEntry("tx").getDouble(0.0);   
        double ty = limelightTablefrontA.getEntry("ty").getDouble(0.0); 
        double tz = limelightTablefrontA.getEntry("tz").getDouble(0.0);   


        SmartDashboard.putNumber("tx", botval[0]);
        SmartDashboard.putNumber("ty", botval[1]);
        SmartDashboard.putNumber("tz", botval[2]);
        SmartDashboard.putNumber("pitch", botval[3]);
        SmartDashboard.putNumber("yaw", botval[4]);
        SmartDashboard.putNumber("roll", botval[5]);
        m_robotDrive.drive((botval[2]-0.7)*0.3*0.5*(Constants.DriveConstants.kMaxLimelightSpeedMetersPerSecond*slowval), (-botval[0])*1.2*0.5*(Constants.DriveConstants.kMaxLimelightSpeedMetersPerSecond*slowval), -(botval[4]*0.03), false,true);
        
        
    }else{
        m_robotDrive.drive(0,0,0, true,false);
    } 

    }
    public void reeflimelightB(DriveSubsystem m_robotDrive, double slowval){
        double[] botval= limelightTablefrontB.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
        double llfront_detect = limelightTablefrontB.getEntry("tv").getInteger(0);
        if (llfront_detect>0){
    
            double tx = limelightTablefrontB.getEntry("tx").getDouble(0.0);   
            double ty = limelightTablefrontB.getEntry("ty").getDouble(0.0); 
            double tz = limelightTablefrontB.getEntry("tz").getDouble(0.0);   
    
    
            SmartDashboard.putNumber("tx", botval[0]);
            SmartDashboard.putNumber("ty", botval[1]);
            SmartDashboard.putNumber("tz", botval[2]);
            SmartDashboard.putNumber("pitch", botval[3]);
            SmartDashboard.putNumber("yaw", botval[4]);
            SmartDashboard.putNumber("roll", botval[5]);
            m_robotDrive.drive((botval[2]-0.7)*0.3*0.5*(Constants.DriveConstants.kMaxLimelightSpeedMetersPerSecond*slowval), (-botval[0])*1.2*0.5*(Constants.DriveConstants.kMaxLimelightSpeedMetersPerSecond*slowval), -(botval[4]*0.03), false,true);
            
            
        }else{
            m_robotDrive.drive(0,0,0, true,false);
        } 

}

public void stationlimelight(DriveSubsystem m_robotDrive, double slowval){
    double[] botval= limelightTable.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
    double llfront_detect = limelightTable.getEntry("tv").getInteger(0);
    if (llfront_detect>0){

        double tx = limelightTable.getEntry("tx").getDouble(0.0);   
        double ty = limelightTable.getEntry("ty").getDouble(0.0); 
        double tz = limelightTable.getEntry("tz").getDouble(0.0);   


        SmartDashboard.putNumber("tx", botval[0]);
        SmartDashboard.putNumber("ty", botval[1]);
        SmartDashboard.putNumber("tz", botval[2]);
        SmartDashboard.putNumber("pitch", botval[3]);
        SmartDashboard.putNumber("yaw", botval[4]);
        SmartDashboard.putNumber("roll", botval[5]);
        m_robotDrive.drive((botval[2]-0.7)*0.3*0.5*(Constants.DriveConstants.kMaxLimelightSpeedMetersPerSecond*slowval), (-botval[0])*1.2*0.5*(Constants.DriveConstants.kMaxLimelightSpeedMetersPerSecond*slowval), -(botval[4]*0.03), false,true);
        
        
    }else{
        m_robotDrive.drive(0,0,0, true,false);
    } 

}
}
