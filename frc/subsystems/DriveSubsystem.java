// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import frc.robot.CANConstants;
import frc.robot.SBInputs;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder;


public class DriveSubsystem implements Subsystem {
  
  
  private final CANSparkMax m_frontLeft = new CANSparkMax(CANConstants.LEFT_LEAD_DEVICE_ID, MotorType.kBrushless);
  private final CANSparkMax m_rearLeft = new CANSparkMax(CANConstants.LEFT_FOLLOW_DEVICE_ID, MotorType.kBrushless);
  private final CANSparkMax m_frontRight = new CANSparkMax(CANConstants.RIGHT_LEAD_DEVICE_ID, MotorType.kBrushless);
  private final CANSparkMax m_rearRight = new CANSparkMax(CANConstants.RIGHT_FOLLOW_DEVICE_ID, MotorType.kBrushless);
  
  DifferentialDrive m_drive = new DifferentialDrive(m_frontLeft, m_frontRight);

  //Joystick
  //Joystick joystick;
  //XboxController xboxcontroller;
  private AnalogInput leftThrottleInput;
  private AnalogInput rightThrottleInput;
  private DigitalInput reverseThrottleInput;
  private double commandReverseLeftThrottle;
  private double commandReverseRightThrottle;
  private double commandReverseThrottle = 1;
  private boolean isReverse = false;
  private double commandLeftThrottle;
  private double commandRightThrottle;
  public  RelativeEncoder m_encoderLeftFront;

  public static final boolean kFrontLeftEncoderReversed = false;
  public static final boolean kRearLeftEncoderReversed = true;
  public static final boolean kFrontRightEncoderReversed = false;
  public static final boolean kRearRightEncoderReversed = true;

  @Override
  public void inputUpdate(Input source) {
    this.commandReverseLeftThrottle = leftThrottleInput.getValue()*commandReverseThrottle;
    this.commandReverseRightThrottle = -rightThrottleInput.getValue()*commandReverseThrottle;
    if (source == reverseThrottleInput) {
      reverseThrottle();
    }
    if (source == leftThrottleInput) {
      setLeftThrottle(this.commandReverseLeftThrottle);
      //setLeftThrottle(leftThrottleInput.getValue());
    }
    if (source == rightThrottleInput) {
      setRightThrottle(this.commandReverseRightThrottle);  
      //setRightThrottle(rightThrottleInput.getValue());
    }

  }

  @Override
  public void init() {
    //joystick = new Joystick(0);
    //xboxcontroller = new XboxController(1);

    leftThrottleInput = (AnalogInput) Core.getInputManager().getInput(SBInputs.DRIVER_LEFT_JOYSTICK_Y.getName());
    leftThrottleInput.addInputListener(this);
    rightThrottleInput = (AnalogInput) Core.getInputManager().getInput(SBInputs.DRIVER_RIGHT_JOYSTICK_Y.getName());
    rightThrottleInput.addInputListener(this);
    reverseThrottleInput = (DigitalInput) Core.getInputManager().getInput(SBInputs.MANIPULATOR_FACE_LEFT.getName());
    reverseThrottleInput.addInputListener(this);
    
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_frontLeft.setInverted(true);
    m_frontRight.setInverted(true);
    m_rearRight.follow(m_frontRight);
    m_rearLeft.follow(m_frontLeft);

    m_drive.setMaxOutput(.95);


    this.m_encoderLeftFront = m_frontLeft.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    m_encoderLeftFront.setPosition(0);
    
  }

  @Override
  public void selfTest() {
    // TODO Auto-generated method stub
    
  }

  // update the subsystem everytime the framework updates (every ~0.02 seconds)
  @Override
  public void update() {
    drive(this.commandReverseLeftThrottle*0.95, this.commandReverseRightThrottle*0.95);
    
    double position = m_encoderLeftFront.getPosition();
    SmartDashboard.putNumber("Encoder Position", position);
    
    
  }

  @Override
  public void resetState() {
    // TODO Auto-generated method stub
    
  }

  public void drive(double left, double right){
    if (isReverse) {
      m_drive.tankDrive(-right, -left);
    }
    else {
      m_drive.tankDrive(left,right );
    }
    //m_drive.tankDrive(leftThrottleInput.getValue()*0.95, rightThrottleInput.getValue()*0.95);
    //m_drive.tankDrive(xboxcontroller.getLeftY()*0.75, -xboxcontroller.getRightY()*0.75);
  }

  public void driveAuton(double left, double right){
    m_drive.tankDrive(left, right);
    //m_drive.tankDrive(xboxcontroller.getLeftY()*0.75, -xboxcontroller.getRightY()*0.75);
  }


  /**
   * Sets the max output of the drive.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    //TODO: make button to allow for more power during pushing
    m_drive.setMaxOutput(maxOutput);
  }


  @Override
  public String getName() {
    return "DRIVEBASE";
  }

  public void setLeftThrottle(double throttle) {
    this.commandLeftThrottle = throttle;
    if(throttle < .1 && throttle > -.1)
        this.commandLeftThrottle = 0;
  }

  public void setRightThrottle(double throttle) {
    this.commandRightThrottle = throttle;
    if(throttle < .1 && throttle > -.1)
      this.commandRightThrottle = 0;
  }

  public void reverseThrottle() {
    if (isReverse) {
      isReverse = false;
      this.commandReverseThrottle = 1;
    }
    else {
      isReverse = true;
      this.commandReverseThrottle = -1;
    }
    SmartDashboard.putBoolean("Reverse Command", isReverse);
  }

  public void driveInchesFoward(int inchesToMoveForward){
    

  }

  public void rotateDegrees(int degrees){
    //TODO: rotate robot x degrees
  }

  public void autoAlignToGoal(){
    //TODO:  implement
    //get limelight center
    //shift to center
      //call rotateDegrees
  }

}
