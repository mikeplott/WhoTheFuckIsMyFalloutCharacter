package com.company;

/**
 * Created by michaelplott on 10/20/16.
 */

//id IDENTITY, race VARCHAR, smithing INT, heavy_armor INT, block INT, two_handed INT, one_handed INT, archery INT, light_armor INT, " +
//"sneak INT, lockpicking INT, pickpocket INT, speech INT, alchemy INT, illusion INT, conjuration INT, destruction INT, restoration INT, alteration INT, enchanting INT
public class SkyrimCharacter {
    int id;
    int health;
    int magicka;
    int stamina;
    String race;
    int smithing;
    int heavyArmor;
    int block;
    int twoHanded;
    int oneHanded;
    int archery;
    int lightArmor;
    int sneak;
    int lockpicking;
    int pickpocket;
    int speech;
    int alchemy;
    int illusion;
    int conjuration;
    int destruction;
    int restoration;
    int alteration;
    int enchanting;

    public SkyrimCharacter(int id, String race, int smithing, int heavyArmor, int block, int twoHanded, int oneHanded, int archery, int lightArmor,
                           int sneak, int lockpicking, int pickpocket, int speech, int alchemy, int illusion, int conjuration, int destruction,
                           int restoration, int alteration, int enchanting) {
        this.id = id;
        this.race = race;
        this.smithing = smithing;
        this.heavyArmor = heavyArmor;
        this.block = block;
        this.twoHanded = twoHanded;
        this.oneHanded = oneHanded;
        this.archery = archery;
        this.lightArmor = lightArmor;
        this.sneak = sneak;
        this.lockpicking = lockpicking;
        this.pickpocket = pickpocket;
        this.speech = speech;
        this.alchemy = alchemy;
        this.illusion = illusion;
        this.conjuration = conjuration;
        this.destruction = destruction;
        this.restoration = restoration;
        this.alteration = alteration;
        this.enchanting = enchanting;
    }

    public SkyrimCharacter(String race, int smithing, int heavyArmor, int block, int twoHanded, int oneHanded, int archery, int lightArmor, int sneak,
                           int lockpicking, int pickpocket, int speech, int alchemy, int illusion, int conjuration, int destruction, int restoration,
                           int alteration, int enchanting) {
        this.race = race;
        this.smithing = smithing;
        this.heavyArmor = heavyArmor;
        this.block = block;
        this.twoHanded = twoHanded;
        this.oneHanded = oneHanded;
        this.archery = archery;
        this.lightArmor = lightArmor;
        this.sneak = sneak;
        this.lockpicking = lockpicking;
        this.pickpocket = pickpocket;
        this.speech = speech;
        this.alchemy = alchemy;
        this.illusion = illusion;
        this.conjuration = conjuration;
        this.destruction = destruction;
        this.restoration = restoration;
        this.alteration = alteration;
        this.enchanting = enchanting;
    }

    public int getId() {
        return id;
    }

    public int getHealth() {
        return health;
    }

    public int getMagicka() {
        return magicka;
    }

    public int getStamina() {
        return stamina;
    }

    public String getRace() {
        return race;
    }

    public int getSmithing() {
        return smithing;
    }

    public int getHeavyArmor() {
        return heavyArmor;
    }

    public int getBlock() {
        return block;
    }

    public int getTwoHanded() {
        return twoHanded;
    }

    public int getOneHanded() {
        return oneHanded;
    }

    public int getArchery() {
        return archery;
    }

    public int getLightArmor() {
        return lightArmor;
    }

    public int getSneak() {
        return sneak;
    }

    public int getLockpicking() {
        return lockpicking;
    }

    public int getPickpocket() {
        return pickpocket;
    }

    public int getSpeech() {
        return speech;
    }

    public int getAlchemy() {
        return alchemy;
    }

    public int getIllusion() {
        return illusion;
    }

    public int getConjuration() {
        return conjuration;
    }

    public int getDestruction() {
        return destruction;
    }

    public int getRestoration() {
        return restoration;
    }

    public int getAlteration() {
        return alteration;
    }

    public int getEnchanting() {
        return enchanting;
    }

    @Override
    public String toString() {
        return "SkyrimCharacter{" +
                "id=" + id +
                ", health=" + health +
                ", magicka=" + magicka +
                ", stamina=" + stamina +
                ", race='" + race + '\'' +
                ", smithing=" + smithing +
                ", heavyArmor=" + heavyArmor +
                ", block=" + block +
                ", twoHanded=" + twoHanded +
                ", oneHanded=" + oneHanded +
                ", archery=" + archery +
                ", lightArmor=" + lightArmor +
                ", sneak=" + sneak +
                ", lockpicking=" + lockpicking +
                ", pickpocket=" + pickpocket +
                ", speech=" + speech +
                ", alchemy=" + alchemy +
                ", illusion=" + illusion +
                ", conjuration=" + conjuration +
                ", destruction=" + destruction +
                ", restoration=" + restoration +
                ", alteration=" + alteration +
                ", enchanting=" + enchanting +
                '}';
    }
}
