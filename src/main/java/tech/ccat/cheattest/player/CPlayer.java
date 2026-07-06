package tech.ccat.cheattest.player;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CPlayer {

    private final String name, uuid;
    private Long lastDupeMs, lastTPMs;

    public CPlayer(String name, String uuid){
        this.name = name;
        this.uuid = uuid;
        this.lastDupeMs = this.lastTPMs = System.currentTimeMillis();
    }

    public void updateDupeTimer(){
        this.lastDupeMs = System.currentTimeMillis();
    }

    public void updateTPTimer(){
        this.lastTPMs = System.currentTimeMillis();
    }

}
