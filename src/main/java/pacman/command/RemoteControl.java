package pacman.command;

import javafx.scene.input.KeyCode;
import pacman.model.entity.dynamic.player.Controllable;

import java.util.HashMap;
import java.util.Map;

public class RemoteControl {
    Map<KeyCode, Command> commands = new HashMap<>();
    Controllable pacman;


    public RemoteControl(Controllable pacman){
        for(KeyCode keyCode : KeyCode.values()){
            commands.put(keyCode, new ConcreteNoCommand());
        }
        this.pacman = pacman;


    }
    public void setCommand(KeyCode code, Command command){
        commands.put(code, command);
    }
    public void onKeyPressed(KeyCode code){
        commands.get(code).execute();
    }
}