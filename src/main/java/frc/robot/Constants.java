package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

public class Constants {

    public static final class DriveConstants{
        public static double kSlowSpeedMetersPersecond=1;
        public static double kFastSpeedMetersPerSecond=2.2;
        public static double kMaxSpeedMetersPerSecond = kFastSpeedMetersPerSecond;
        public static final double kMaxAngularSpeed = 2;
        public static final double kMaxLimelightSpeedMetersPerSecond = 2;



        public static final double kTrackWidth = Units.inchesToMeters(26.5);
        public static final double kWheelBase = Units.inchesToMeters(26.5);
        public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
            new Translation2d(kWheelBase/2, kTrackWidth/2),
            new Translation2d(kWheelBase/2, -kTrackWidth/2),
            new Translation2d(-kWheelBase/2, kTrackWidth/2),
            new Translation2d(-kWheelBase/2, -kTrackWidth/2)
        );
        public static final double kFrontLeftChassisAngularOffset = -Math.PI/2;
        public static final double kFrontRightChassisAngularOffset = 0;
        public static final double kBackLeftChassisAngularOffset = Math.PI;
        public static final double kBackRightChassisAngularOffset = Math.PI/2;

        public static final int kFrontLeftDrivingCanId = 1;
        public static final int kFrontRightDrivingCanId = 3;
        public static final int kBackRightDrivingCanId = 5;
        public static final int kBackLeftDrivingCanId = 7;

        public static final int kFrontLeftTurningCanId = 2;
        public static final int kFrontRightTurningCanId = 4;
        public static final int kBackRightTurningCanId = 6;
        public static final int kBackLeftTurningCanId = 8;

        public static final boolean kGyroReversed = false;
       
    }
    public static final class ModuleConstants{
        public static final int kDrivingMotorPinionTeeth = 14;
        public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRps / 60;
        public static final double kWheelDiameterMeters = 0.0762;
        public static final double kWheelCircumfrenceMeters = kWheelDiameterMeters * Math.PI;
        public static final double kDrivingMotorReduction = (45.0*22)/(kDrivingMotorPinionTeeth*15);
        public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps*kWheelCircumfrenceMeters)/kDrivingMotorReduction;
    }

    public static final class OIConstants{
        public static final int kDriverControllerPort = 0;
        public static final double kDriveDeadband = 0.05;
    }

    public static final class AutoConstants{
        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;
        public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
        public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;
    
        public static final double kPXController = 1;
        public static final double kPYController = 1;
        public static final double kPThetaController = 1;

        public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
            kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
    }
    
    public static final class NeoMotorConstants{
        public static final double kFreeSpeedRps = 5676;
    }
}
