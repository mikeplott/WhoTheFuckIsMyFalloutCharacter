package com.company;

/**
 * Created by michaelplott on 10/19/16.
 */
public class FalloutCharacter {
    int id;
    int str;
    int per;
    int end;
    int cha;
    int intel;
    int agi;
    int luck;
    String desc;

    public FalloutCharacter() {
    }

    public FalloutCharacter(int str, int per, int end, int cha, int intel, int agi, int luck, String desc) {
        this.str = str;
        this.per = per;
        this.end = end;
        this.cha = cha;
        this.intel = intel;
        this.agi = agi;
        this.luck = luck;
        this.desc = desc;
    }

    public FalloutCharacter(int id, int str, int per, int end, int cha, int intel, int agi, int luck, String desc) {
        this.id = id;
        this.str = str;
        this.per = per;
        this.end = end;
        this.cha = cha;
        this.intel = intel;
        this.agi = agi;
        this.luck = luck;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", str=" + str +
                ", per=" + per +
                ", end=" + end +
                ", cha=" + cha +
                ", intel=" + intel +
                ", agi=" + agi +
                ", luck=" + luck +
                ", desc='" + desc + '\'' +
                '}';
    }
}
