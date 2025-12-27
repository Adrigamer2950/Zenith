package me.devadri.zenith.example.commands.parent

import me.devadri.zenith.client.DefaultClient
import me.devadri.zenith.command.ParentCommand

class ParentExampleCommand : ParentCommand<DefaultClient>("parentexample", "An example of a parent command") {

    init {
        addSubCommands(SubExampleCommand())
    }
}