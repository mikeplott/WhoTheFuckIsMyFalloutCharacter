package com.company;

import java.util.ArrayList;

/**
 * Created by michaelplott on 10/20/16.
 */
public class FalloutWrapper {
    ArrayList<FalloutCharacter> falloutCharacters = new ArrayList<>();

    public FalloutWrapper() {
    }

    public FalloutWrapper(ArrayList<FalloutCharacter> falloutCharacters) {
        this.falloutCharacters = falloutCharacters;
    }

    public ArrayList<FalloutCharacter> getFalloutCharacters() {
        return falloutCharacters;
    }
}
