package frc.robot;

import frc.subsystems.Climber;
import frc.subsystems.DriveSubsystem;
import frc.subsystems.Intake;
import frc.subsystems.Limelight;
import frc.subsystems.Shooter;

import org.wildstang.framework.core.Subsystems;

/*
 * All subsystems are enumerated here.
 * It is used in Robot.java to initialize all subsystems.
 */

public enum SBSubsystems implements Subsystems {

    // enumerate subsystems
    DRIVEBASE("DRIVEBASE", DriveSubsystem.class),
    INTAKE("INTAKE", Intake.class),
    CLIMBER("CLIMBER", Climber.class),
    LIMELIGHT("LIMELIGHT", Limelight.class),
    SHOOTER("SHOOTER", Shooter.class);
    
    private String name;
    private Class<?> subsystemClass;

    SBSubsystems(String name, Class<?> subsystemClass) {
        this.name = name;
        this.subsystemClass = subsystemClass;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<?> getSubsystemClass() {
        return subsystemClass;
    }
}
