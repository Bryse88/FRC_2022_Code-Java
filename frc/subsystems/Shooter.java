package frc.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.CANConstants;
import frc.robot.SBInputs;
import frc.robot.SBSubsystems;

/**
 * flywheel references
 * https://github.com/FRC-Sonic-Squirrels/Flywheel-Tuner/blob/2022-CTRE/src/main/java/frc/robot/Robot.java
 * 
 * TODO: have buttons uptick and downtick the target RPM
 */
public class Shooter implements Subsystem {

    // input
    AnalogInput manipulatorTriggerLeft;
    AnalogInput manipulatorTriggerRight;
    DigitalInput shooterUpButton;
    DigitalInput shooterDownButton;
    private TalonFXSensorCollection encoder;
    private Limelight limelightSubsystem;

    // output
    // rpmReadRumble ;
    TalonFX flywheelMotorFront;
    TalonFX flywheelMotorBack;

    // state
    boolean readToShoow = false;
    double ticks2RPm = 600.0 / 2048.0;
    boolean kime = false;

    // command
    int commandTargetRPMS;
    // int commandTargetRPMS = -13500;
    // private double distance = limelightSubsystem.getDistanceToTarget();
    boolean commandGetUpToSpeed = false;
    boolean commandShoot = false;
    private boolean distanceShooting = false;
    private boolean overide = false;
    double distance;
    double stuff;
    double yolo;

    //auton
    boolean startShooter;
    

    // private ShuffleboardTab flywheelTab;
    // private NetworkTableEntry flywheelTargetSpeed;
    public int getRPM() {
        int a = 0;
        double minD = distance - 6;
        double maxD = distance + 6;
        //System.out.print(minD);
        //System.out.println(distance);
        if (minD < 259.25 && 259.25 < maxD) {
            a = -14000;
        } else if (minD < 247.25 && 247.25 < maxD) {
            a = -13700;
        } else if (minD < 235.25 && 235.25 < maxD) {
            a = -13300;
        } else if (minD < 221.75 && 221.75 < maxD) {
            a = -13050;
        } else if (minD < 211.25 && 211.25 < maxD) {
            a = -12750;
        } else if (minD < 199.25 && 199.25 < maxD) {
            a = -12550;
        } else if (minD < 187.25 && 187.25 < maxD) {
            a = -12300;
        } else if (minD < 175.25 && 175.25 < maxD) {
            a = -12100;
        } else {
            a = 0;
            // std::cout << a << std::endl;
        }
        //System.out.println(minD);
        return a;
    }

    @Override
    public void inputUpdate(Input source) {
        SmartDashboard.putNumber("Shooter manipulatorTriggerLeft", manipulatorTriggerLeft.getValue());
        if(manipulatorTriggerLeft.getValue()>.2){
            /*commandTargetRPMS = getRPM();
            stuff = commandTargetRPMS;
            SmartDashboard.putNumber("Target Flywheel RPM", this.commandTargetRPMS);*/
            distanceShooting = true;
            overide = false; 
        }
        else {
            //if(startShooter){
                //commandGetUpToSpeed = true;
                //distanceShooting = false;
            //}else {
                distanceShooting = false;
                overide = true;
            //}
        }
        if(manipulatorTriggerRight.getValue()>.1){
            commandShoot = false;
        }else{
            commandShoot = false;
        }
        /*if (source == shooterUpButton) {
            shooterUpRPM();
        }
        if (source == shooterDownButton) {
            shooterDownRPM();
        }*/
        //commandTargetRPMS = getRPM();
        //stuff = commandTargetRPMS - 300;
        limelightSubsystem = (Limelight) Core.getSubsystemManager().getSubsystem(SBSubsystems.LIMELIGHT);
        distance = limelightSubsystem.getDistanceToTarget();
        System.out.println(distance);
    }

    @Override
    public void init() {
        flywheelMotorFront = new TalonFX(CANConstants.FLYWHEEL_DEVICE1);
        flywheelMotorBack = new TalonFX(CANConstants.FLYWHEEL_DEVICE2);

        flywheelMotorBack.follow(flywheelMotorFront);

        manipulatorTriggerLeft = (AnalogInput) Core.getInputManager()
                .getInput(SBInputs.MANIPULATOR_TRIGGER_LEFT.getName());
        manipulatorTriggerLeft.addInputListener(this);
        manipulatorTriggerRight = (AnalogInput) Core.getInputManager()
                .getInput(SBInputs.MANIPULATOR_TRIGGER_RIGHT.getName());
        manipulatorTriggerRight.addInputListener(this);
        shooterUpButton = (DigitalInput) Core.getInputManager().getInput(SBInputs.MANIPULATOR_FACE_UP.getName());
        shooterUpButton.addInputListener(this);
        // shooterDownButton = (DigitalInput)
        // Core.getInputManager().getInput(SBInputs.MANIPULATOR_FACE_UP.getName());
        // shooterDownButton.addInputListener(this);
        // shooterDownButton = (DigitalInput)
        // Core.getInputManager().getInput(SBInputs.MANIPULATOR_FACE_DOWN.getName());
        // shooterDownButton.addInputListener(this);
        shooterDownButton = (DigitalInput) Core.getInputManager().getInput(SBInputs.MANIPULATOR_DPAD_DOWN.getName());
        shooterDownButton.addInputListener(this);

        encoder = flywheelMotorFront.getSensorCollection();

        SmartDashboard.putNumber("Velocity (RPM)", encoder.getIntegratedSensorVelocity() * ticks2RPm);

        // flywheelTab = Shuffleboard.getTab("Flywheel Tab");
        // flywheelTargetSpeed = flywheelTab.add("Flywheel speed",
        // 10000).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min",
        // 6000, "max", 20000)).getEntry();
        /*limelightSubsystem = (Limelight) Core.getSubsystemManager().getSubsystem(SBSubsystems.LIMELIGHT);
        distance = limelightSubsystem.getDistanceToTarget();
        System.out.println(distance);*/
        // SmartDashboard.putNumber("Target Flywheel RPM", this.commandTargetRPMS );
    }

    @Override
    public void selfTest() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() {
        SmartDashboard.putBoolean("Shooter commandGetUpToSpeed", commandGetUpToSpeed);
        SmartDashboard.putNumber("Velocity (RPM)", encoder.getIntegratedSensorVelocity());
        //SmartDashboard.putNumber("Target Flywheel RPM", this.commandTargetRPMS);
        // System.out.println(commandTargetRPMS);

        if (overide) {
            flywheelMotorFront.set(ControlMode.PercentOutput, manipulatorTriggerRight.getValue());// / ticks2RPm);
            commandTargetRPMS = getRPM();
            //System.out.println("help");
            stuff = commandTargetRPMS + 300;
            SmartDashboard.putNumber("Target Flywheel RPM", this.commandTargetRPMS);
        } else if (distanceShooting) {
            commandTargetRPMS = getRPM();
            //System.out.println("help");
            stuff = commandTargetRPMS;
            SmartDashboard.putNumber("Target Flywheel RPM", this.commandTargetRPMS);
            flywheelMotorFront.set(ControlMode.Velocity, stuff);
        }
        else if(commandGetUpToSpeed){
            flywheelMotorFront.set(ControlMode.Velocity, yolo);
        }
        else {
            flywheelMotorFront.set(ControlMode.PercentOutput, 0);
            // flywheelMotorBack.set(ControlMode.PercentOutput, 0);
        }
    }

        @Override
    public void resetState() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getName() {
        return "SHOOTER";
    }

    public void shooterUpRPM() {
        commandTargetRPMS -= 100;
    }

    public void shooterDownRPM() {
        commandTargetRPMS += 100;
    }

    public void shooterOn(double rpmTarget) {
        commandGetUpToSpeed = true;
        distanceShooting = false;
        yolo = -13500;
        System.out.println("hhgjgjgjgk");
    }

    public void shooterOff() {
        commandGetUpToSpeed = false;
        distanceShooting = false;
        yolo = 0;
    }
}
