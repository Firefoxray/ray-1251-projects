package org.usfirst.frc.team1251.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;

/**
 * Used to supply a command which is not available at the time of this object's construction.
 *
 * This class is primarily used to resolve the chicken/egg scenario which discourages the use of constructor injection
 * in a Subsystem and its default Command. More specifically, it is impossible to inject a Subsystem into a Command's
 * constructor AND inject that Command into the Subsystem's constructor as a default command:
 *
 *     // Attempt 1: Create Subsystem first.
 *     Subsystem exampleSubsystem = new ExampleSubsystem(exampleCommand); // exampleCommand does not yet exist!
 *     Command exampleCommand = new ExampleCommand(exampleSubsystem);
 *
 *
 *     // Attempt 2: Create Command first.
 *     Command exampleCommand = new ExampleCommand(exampleSubsystem); // exampleSubsystem does not yet exist!
 *     Subsystem exampleSubsystem = new ExampleSubsystem(exampleCommand);
 *
 * This is most likely why `public static` properties are so prevalent in the library's examples. The idea is that
 * the `static` property will be populated by the time it is used. However, use of static access of dependencies has
 * been shown to create hard to find NPEs because it is difficult to see what order things need to be created.
 *
 * This class solves that scenario by creating a supplier for the default command that can be injected into the
 * Subsystem constructor and then populated later, once the Command has been created:
 *
 *     class ExampleSubsystem extends Subsystem {
 *         ...
 *         public ExampleSubsystem(DeferredCmdSupplier<ExampleCommand> defaultCmdSupplier) {
 *           this.defaultCmdSupplier = defaultCmdSupplier;
 *         }
 *
 *         protected void initDefaultCommand() {
 *           this.setDefaultCommand(this.defaultCmdSupplier.get());
 *         }
 *     }
 *
 *
 *     // 1. Create deferred default container
 *     // 2. Create the subsystem
 *     // 3. Create the command which uses the subsystem
 *     // 4. Set the command into the deferred default container
 *     DeferredCmdSupplier<ExampleCommand> exampleCommandSupplier = new DeferredCmdSupplier<>();
 *     Subsystem exampleSubsystem = new ExampleSubsystem(exampleCommandSupplier);
 *     Command exampleCommand = new ExampleCommand(exampleSubsystem);
 *     deferredExampleCommand.set(exampleCommand)
 *
 * **WARNING:** You should always populate the `DeferredCmdSupplier` before the `Scheduler` runs for the first time.
 * Declaring the default command after the scheduler has run for the first time is not supported and may cause
 * unexpected behavior!
 */
public class DeferredCmdSupplier<T extends Command> implements Supplier<T> {

    /**
     * The command to supply.
     */
    private T command;

    @Override
    public T get() {
        return command;
    }

    /**
     * Set the contained command.
     *
     * @param command The command to place within this container or `null` to unset the current command.
     */
    public void set(T command) {
        this.command = command;
    }
}