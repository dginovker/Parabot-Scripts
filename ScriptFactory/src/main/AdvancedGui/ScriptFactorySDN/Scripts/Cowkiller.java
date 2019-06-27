package main.AdvancedGui.ScriptFactorySDN.Scripts;

import main.AdvancedGui.ScriptFactorySDN.ScriptFactoryScript;
import org.parabot.environment.scripts.Category;

public class Cowkiller extends ScriptFactoryScript {
    public Cowkiller() {
        super(
                "Cow killer (with banking)",
                "Before",
                Category.COMBAT,
                1.0,
                "Kills cows in lumbridge and banks in the castle",
                "If Inventory-slots-used(28)\n" +
                        "Run-subscript(Openlummybank)\n" +
                        "Bank-all-except-IDs()\n" +
                        "Run-subscript(Walktocows)\n" +
                        "Endif\n" +
                        "IfNot In-Combat()\n" +
                        "If Entity-is-around(81,397,1767,1768)\n" +
                        "Take-Ground-item(2132)\n" +
                        "Take-Ground-item(526)\n" +
                        "Take-Ground-item(1739)\n" +
                        "Interact-with-entity-by-ID(81,397,1767,1768,1)\n" +
                        "Endif\n" +
                        "IfNot Entity-is-around(81,397,1767,1768)\n" +
                        "Run-subscript(Walktocows)\n" +
                        "Endif\n" +
                        "Endif\n",
                new String[]{"Walktocows", "Openlummybank"}
        );
    }

    public static class Walktocows extends ScriptFactoryScript
    {

        public Walktocows() {
            super(
                    "Walktocows",
                    "Before",
                    "Dependency",
                    1.0,
                    "Walks to the cows in lumbridge from anywhere",
                    "IfNot Entity-is-around(81,397,1767,1768)\n" +
                            "Type(::stuck,1)\n" +
                            "Sleep(1500)\n" +
                            "Walk-to(3241,3226,14000)\n" +
                            "Walk-to(3259,3233,14000)\n" +
                            "Walk-to(3256,3250,14000)\n" +
                            "Walk-to(3252,3266,14000)\n" +
                            "Interact-with-entity-by-location(3253,3266,1)\n" +
                            "Walk-to(3258,3268,5000)\n" +
                            "Endif\n",
                    new String[]{}
            );
        }
    }
    public static class Openlummybank extends ScriptFactoryScript
    {

        public Openlummybank() {
            super(
                    "Openlummybank",
                    "Before",
                    "Dependency",
                    1.0,
                    "Opens the bank in lumbridge castle from anywhere",
                    "Type(::stuck,1)\n" +
                            "Sleep(1500)\n" +
                            "Interact-with-entity-by-location(3217,3218,1)\n" +
                            "Interact-with-entity-by-location(3215,3211,1)\n" +
                            "Interact-with-entity-by-location(3204,3207,1)\n" +
                            "Sleep(15000)\n" +
                            "Interact-with-entity-by-location(3204,3207,1)\n" +
                            "Interact-with-entity-by-location(3204,3207,2)\n" +
                            "Interact-with-entity-by-ID(494,3)\n" +
                            "Sleep(6000)\n",
                    new String[]{}
            );
        }
    }
}
