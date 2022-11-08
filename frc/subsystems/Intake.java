package frc.subsystems;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.CANConstants;
import frc.robot.SBInputs;

public class Intake implements Subsystem {

    //inputs
    private AnalogInput intakeTrigger;
    private DigitalInput intakeReverseTrigger;
    private DigitalInput intakeReverseMiddleTrigger;
    private AnalogInput intakeShooterTrigger;

    //outputs
    private TalonSRX intake;
    private TalonSRX innerIntake;

    //states
    private double runSpeed = .5;
    private double runInnerSpeed = .5;
    private boolean moveForward = true;

    //commands
    boolean commandIntakeFrontOn = false;
    boolean commandIntakeMiddleOn = false;

    //auton requests
    boolean autonIntakeOn = false;


    @Override
    public void inputUpdate(Input source) {
        //This is only called if the inputs are triggered
        SmartDashboard.putNumber("Intake Trigger", intakeTrigger.getValue());
        if ((intakeTrigger.getValue() < -.5)) {
            moveForward = false;
            commandIntakeFrontOn = true;
            commandIntakeMiddleOn = true;
        }
        else if (intakeReverseTrigger.getValue()) {
            moveForward = true;
            commandIntakeFrontOn = true;
            commandIntakeMiddleOn = true;
        }
        else if (intakeReverseMiddleTrigger.getValue()) {
            moveForward = true;
            commandIntakeMiddleOn = true;
        }
        else {
            commandIntakeFrontOn = false;
            commandIntakeMiddleOn = false;
        }
    }

    @Override
    public void init() {
        intake = new TalonSRX(CANConstants.INTAKE_DEVICE);
        innerIntake = new TalonSRX(CANConstants.INNER_INTAKE_DEVICE);
        
        intakeTrigger = (AnalogInput) Core.getInputManager().getInput(SBInputs.DRIVER_TRIGGER_RIGHT.getName());
        intakeTrigger.addInputListener(this);

        intakeReverseTrigger = (DigitalInput) Core.getInputManager().getInput(SBInputs.DRIVER_FACE_LEFT.getName());
        intakeReverseTrigger.addInputListener(this);

        intakeReverseMiddleTrigger = (DigitalInput) Core.getInputManager().getInput(SBInputs.DRIVER_FACE_UP.getName());
        intakeReverseMiddleTrigger.addInputListener(this);
    }

    @Override
    public void selfTest() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update() {
        SmartDashboard.putNumber("Intake Speed", runSpeed);
        SmartDashboard.putNumber("Hopper Speed", runInnerSpeed);
        SmartDashboard.putBoolean("Intake On", commandIntakeFrontOn);
        SmartDashboard.putBoolean("Intake iddle On", commandIntakeMiddleOn);
 
        if(commandIntakeFrontOn){
            if(!this.moveForward){
                //go in reverse
                intake.set(ControlMode.PercentOutput,  runSpeed * -1);
            }
            else{
                intake.set(ControlMode.PercentOutput,  runSpeed);
            }
            
        }
        else{
            intake.set(ControlMode.PercentOutput,  0);
        }
        if(commandIntakeMiddleOn){
            if(!this.moveForward) {
                //go in reverse
                innerIntake.set(ControlMode.PercentOutput, runInnerSpeed * -1);
            }
            else {
                innerIntake.set(ControlMode.PercentOutput,  runInnerSpeed);
            }
        }
        else{
            innerIntake.set(ControlMode.PercentOutput,  0);
        }
    }

    @Override
    public void resetState() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getName() {
        
        return "INTAKE";
    }

    public void turnIntakeOn(boolean reverse){
        moveForward = reverse;
        commandIntakeFrontOn = true;
        commandIntakeMiddleOn = true;
    }
    public void turnIntakeOff(boolean reverse){
        moveForward = reverse;
        commandIntakeFrontOn = false;
        commandIntakeMiddleOn = false;
    }
    
}
