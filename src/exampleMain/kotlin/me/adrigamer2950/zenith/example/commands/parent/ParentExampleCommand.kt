package me.adrigamer2950.zenith.example.commands.parent

import me.adrigamer2950.zenith.client.DefaultClient
import me.adrigamer2950.zenith.command.ParentCommand

class ParentExampleCommand : ParentCommand<DefaultClient>("parentexample", "An example of a parent command") {

    init {
        addSubCommands(SubExampleCommand())
    }
}