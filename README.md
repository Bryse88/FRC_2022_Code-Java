https://github.com/wildstang/2021_robot_software/tree/Team-111A_Main


example of how to use a limelight subsystem
https://github.com/wildstang/2021_robot_software/blob/Team-111A_Main/src/main/java/org/wildstang/year2020/subsystems/launching/Shooter.java



# TODOS:
- Reverse button for defense
- (done)Limelight Distance Calculation (https://github.com/wildstang/2021_robot_software/blob/Team-111A_Main/src/main/java/org/wildstang/year2020/subsystems/launching/Limelight.java)
- Button to increase/decrease rpms used
- (done)auton selector on dashboard
- auton move forward and shoot ball into hopper
- auto align Subsystem
- lockin rpm for shooting.



# How to create an auton
- Create a class in /auto directory (see ExampleAutoProgram.java on what to extend)
- Create Steps for Auton and Reuse
- Add Auto Program to Robot.java at line AutoManager.getInstance().addProgram(new ExampleAutoProgram());


# How to create a subsystem
- create a class in /subsystems directory (implement Subsystem)
- add created Subsystem to SMSubsystems.java