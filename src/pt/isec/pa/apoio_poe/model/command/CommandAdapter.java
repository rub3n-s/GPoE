package pt.isec.pa.apoio_poe.model.command;

import pt.isec.pa.apoio_poe.model.data.ManagementData;

abstract class CommandAdapter implements ICommand {
    protected ManagementData receiver;

    protected CommandAdapter(ManagementData receiver) {
        this.receiver = receiver;
    }
}
