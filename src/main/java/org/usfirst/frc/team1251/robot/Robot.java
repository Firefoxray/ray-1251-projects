package org.usfirst.frc.team1251.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1251.robot.commands.*;
import org.usfirst.frc.team1251.robot.subsystems.*;
import org.usfirst.frc.team1251.robot.teleopInput.driverInput.HumanInput;
import org.usfirst.frc.team1251.robot.teleopInput.gamepad.GamePad;
import org.usfirst.frc.team1251.robot.teleopInput.gamepad.ModernGamePad;
import org.usfirst.frc.team1251.robot.teleopInput.triggers.Always;
import org.usfirst.frc.team1251.robot.triggers.ArmDownJustNowTrigger;
import org.usfirst.frc.team1251.robot.virtualSensors.ArmPosition;
import org.usfirst.frc.team1251.robot.virtualSensors.CrateDetector;
import org.usfirst.frc.team1251.robot.virtualSensors.DriveFeedback;
import org.usfirst.frc.team1251.robot.virtualSensors.ElevatorPosition;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    //private PowerDistributionPanel pdp = new PowerDistributionPanel();
    //private BigMotor bigMotor;
    private Collector collector;


    //public static final DriveTrain driveTrain = new DriveTrain();


    private Command autonomousCommand;
    private SendableChooser chooser;
    private DriveFeedback driveFeedback;
    private Arm arm;
    private ArmPosition armPosition;
    private BigMotor bigMotor;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

        // Set up driver input
        GamePad driverGamePad = new ModernGamePad(new Joystick(0));
        GamePad crateGamePad = new ModernGamePad(new Joystick(1));

        HumanInput humanInput = new HumanInput(driverGamePad, crateGamePad);

        SmartDashboard.putBoolean("Reset Encoders", false);

        // Create virtual sensors (used by mechanisms, subsystems and commands)
        ArmPosition armPosition = new ArmPosition();
        ElevatorPosition elevatorPosition = new ElevatorPosition();
        CrateDetector crateDetector = new CrateDetector();
        DriveFeedback driveFeedback = new DriveFeedback();

        // Create subsystems (used by commands)
        // Use `DeferredCmdSupplier` to handle the chicken/egg problem with default commands
        DeferredCmdSupplier<Command> armDefaultCmdSupplier = new DeferredCmdSupplier<>();
        Arm arm = new Arm(armPosition, armDefaultCmdSupplier);

        DeferredCmdSupplier<Command> elevatorDefaultCmdSupplier = new DeferredCmdSupplier<>();
        Collector collector = new Collector(crateDetector);

        DeferredCmdSupplier<Command> clawDefaultCmdSupplier = new DeferredCmdSupplier<>();
        //BigMotor motor = new BigMotor(bigMotor);
        //BigMotor motor = new BigMotor(bigMotor);

        // We will never provide a default command to be used during initialization for the DriveTrain or the
        // DriveTrainShifter -- we will set it manually when tele-op initializes. Feed in an empty command supplier.
        //DriveTrain driveTrain = new DriveTrain(new DeferredCmdSupplier<>(), driveFeedback);

        // We will never provide a default command to be used during initialization for the DriveTrainShifter -- we will
        // set it manually when tele-op initializes. Feed in an empty command supplier.

        // We will never provide a default command to be used during initialization for the ElevatorShifter -- we will
        // set it manually when tele-op initializes. Feed in an empty command supplier.
        ElevatorShifter elevatorShifter = new ElevatorShifter(new DeferredCmdSupplier<>());

        // Create commands
        BigMotorHigh bigMotorHigh = new BigMotorHigh(bigMotor);
        BigMotorLow bigMotorLow = new BigMotorLow(bigMotor);
        TeleopMoveArm moveArm = new TeleopMoveArm(humanInput, arm);
        MoveBigMotor moveBigMotor = new MoveBigMotor(humanInput, bigMotor);
        ShiftElevator shiftElevatorUp = new ShiftElevator(elevatorShifter, ElevatorShifter.Gear.HIGH);
        ShiftElevator shiftElevatorDown = new ShiftElevator(elevatorShifter, ElevatorShifter.Gear.LOW);
        //Eject cubeEject = new Eject(collector, humanInput);


        // Create a command to slow arm decent and attach it to a trigger which indicates that the arm is down as
        // far as it is supposed to go.

        // Assign default commands
        armDefaultCmdSupplier.set(moveArm);
        clawDefaultCmdSupplier.set(moveBigMotor);



        // assign driver-initiated command triggers.
        humanInput.attachCommandTriggers(shiftElevatorUp, shiftElevatorDown, bigMotorLow, bigMotorHigh);

        // Attach sensor-based command triggers
        ArmDownJustNowTrigger armDownJustNowTrigger = new ArmDownJustNowTrigger(armPosition);

        // Uncomment to test a controller on port 5
        //initGamepadTest();

        // TODO: Do we need a chooser?
        // chooser = new SendableChooser();
        // chooser.addDefault("Default Auto", new MoveElevator());
//        chooser.addObject("My Auto", new MyAutoCommand());
        // SmartDashboard.putData("Auto mode", chooser);

        this.driveFeedback = driveFeedback;


        //this.teleopDriveCmd = teleopDrive;

        this.bigMotor = bigMotor;
        this.collector = collector;

        this.arm = arm;
        this.armPosition = armPosition;

    }

    private void initGamepadTest() {
        Trigger trigger = new Always();
        trigger.whileActive(new TestGamepad());
    }


    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    public void disabledInit() {

    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
     * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
     * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
     * below the Gyro
     * <p>1
     * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
     * or additional comparisons to the switch structure below with additional strings & commands.
     */
    public void autonomousInit() {
        System.out.println("Auto Init");
        //this.driveTrain.setDefaultCommand(null);



        MotorFactory.setBrakeMode(true);
        //autoChooser.initialize();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        //this.driveTrain.setDefaultCommand(this.teleopDriveCmd);
        //this.driveTrainShifter.setDefaultCommand(this.driveTrainAutoShift);
        MotorFactory.setBrakeMode(false);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }

}