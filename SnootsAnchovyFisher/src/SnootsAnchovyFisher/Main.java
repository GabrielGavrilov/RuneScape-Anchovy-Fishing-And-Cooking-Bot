package SnootsAnchovyFisher;

import org.osbot.Con;
import org.osbot.NP;
import org.osbot.rs07.api.NPCS;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

@ScriptManifest(version = 0.1, logo = "", info = "Simple Anchovy fisher & cooker", name = "Snoots' Anchovy Fisher", author = "Snoots")

public class Main extends Script {

    // @NAME: Areas
    // @DESCRIPTION: All the areas we will be using
    private Area fishingArea = new Area(3266, 3149, 3278, 3139);
    private Area bankArea = new Area(3271, 3161, 3269, 3173);
    private Area cookingArea = new Area(3271, 3179, 3274, 3182);

    //This boolean determines if the player has chosen the fishing option or not
    static boolean isFishing;

    ////////////////////////////////////////////////////////////////
    //                   Fishing for Anchovies                   //
    //////////////////////////////////////////////////////////////

    // @NAME: Fishing Function
    // @DESCRIPTION: This method stores the code for the fishing bot
    public void fishingFunction() throws InterruptedException {
        NPC anchovyFish = getNpcs().closest("Fishing spot");

        //If bot is at the fishing area & not interacting with the anchovies, then interact with them
        if(fishingArea.contains(myPlayer())) {
            if(getDialogues().inDialogue()) {
                getDialogues().clickContinue();
            } else if(!getDialogues().inDialogue()) {
                if(anchovyFish != null) {
                    if(!myPlayer().isInteracting(anchovyFish)) {
                        if(anchovyFish.interact("Small fishing Net")) {
                            sleep(random(5000, 1000));
                            new ConditionalSleep(25000) {
                                @Override
                                public boolean condition() {
                                    return !myPlayer().isInteracting(anchovyFish) || !anchovyFish.exists();
                                }
                            }.sleep();
                        }
                    }
                }
            }
        //If bot is not at the fishing area, then walk towards the fishing area
        } else if(!fishingArea.contains(myPlayer())) {
            walking.webWalk(fishingArea);
        }
    }

    // @NAME: Withdraw small fishing net
    // @DESCRIPTION: Withdraws the small fishing net from the bank
    public void bankWithdrawSmallFishingNet() {
        NPC banker = getNpcs().closest("Banker");

        //If bank area contains player, withdraw 1 small fishing net
        if(bankArea.contains(myPlayer())) {
            if(banker.interact("Bank")) {
                new ConditionalSleep(25000) {
                    @Override
                    public boolean condition() {
                        return bank.isOpen() == true;
                    }
                }.sleep();
            } if(bank.isOpen()) {
                if(bank.withdraw("Small fishing net", 1)) {
                    new ConditionalSleep(25000) {
                        @Override
                        public boolean condition() {
                            return bank.withdraw("Small fishing net", 1);
                        }
                    }.sleep();
                } if(bank.close()) {
                    new ConditionalSleep(25000) {
                        @Override
                        public boolean condition() {
                            return bank.close();
                        }
                    }.sleep();
                }
            }
        //if bank area does not contain player, walk to the area
        } else if(!bankArea.contains(myPlayer())) {
            walking.webWalk(bankArea);
        }
    }

    // @NAME: Deposit fish function
    // @DESCRIPTION: deposits all the fish that's in the inventory
    public void bankDepositFish() throws InterruptedException {
        NPC banker = getNpcs().closest("Banker");

        //if bot is in the bank area, deposit all expect small fishing net to the bank
        if(bankArea.contains(myPlayer())) {
            if(getDialogues().inDialogue()) {
                getDialogues().clickContinue();
            } else if(!getDialogues().inDialogue()) {
                if(!myPlayer().isInteracting(banker)) {
                    if(bank != null) {
                        if(banker.interact("Bank")) {
                            sleep(random(1000, 3000));
                            new ConditionalSleep(25000) {
                                @Override
                                public boolean condition() {
                                    return bank.isOpen() == true;
                                }
                            }.sleep();
                        } if(bank.isOpen() == true) {
                            if(bank.depositAllExcept("Small fishing net")) {
                                sleep(random(1000,3000));
                                new ConditionalSleep(25000) {
                                    @Override
                                    public boolean condition() {
                                        return bank.depositAllExcept("Small fishing net");
                                    }
                                }.sleep();
                            } if(bank.close()) {
                                sleep(random(1000, 3000));
                                new ConditionalSleep(25000) {
                                    @Override
                                    public boolean condition() {
                                        return bank.close();
                                    }
                                }.sleep();
                            }
                        }
                    }
                }
            }
        //If bot is not in the bank area, walk towards the bank area
        } else if(!bankArea.contains(myPlayer())) {
            walking.webWalk(bankArea);
        }
    }

    ////////////////////////////////////////////////////////////////
    //                      Cooking Anchovies                    //
    //////////////////////////////////////////////////////////////

    // @NAME: Withdraw anchovies function
    // @DESCRIPTION: Script withdraws the anchovies from the bank
    public void bankWithdrawAnchovies() {
        NPC banker = getNpcs().closest("Banker");

        //If bot is in the bank area, withdraw anchovies
        if(bankArea.contains(myPlayer())) {
            if(banker.interact("Bank")) {
                new ConditionalSleep(25000) {
                    @Override
                    public boolean condition() {
                        return bank.isOpen() == true;
                    }
                }.sleep();
            } if(bank.isOpen()) {
                if(bank.withdrawAll("Raw anchovies")) {
                    new ConditionalSleep(25000) {
                        @Override
                        public boolean condition() {
                            return bank.withdrawAll("Raw anchovies");
                        }
                    }.sleep();
                } if(bank.close()) {
                    new ConditionalSleep(25000) {
                        @Override
                        public boolean condition() {
                            return bank.close();
                        }
                    }.sleep();
                }
            }
        //If bot is not in the bank area, walk towards the bank area
        } else if(!bankArea.contains(myPlayer())) {
            walking.webWalk(bankArea);
        }
    }

    // @NAME: Cook anchovies function
    // @DESCRIPTION: Cooks all the anchovies that are in the inventory
    public void cookAnchovies() throws InterruptedException {
        RS2Object stove = objects.closest("Range");

        //If player is in the cooking area, cook the anchovies
        if(cookingArea.contains(myPlayer())) {
            if(stove.interact("Cook")) {
                sleep(random(3000, 5000));
                new ConditionalSleep(25000) {
                    @Override
                    public boolean condition() {
                        return getDialogues().inDialogue();
                    }
                }.sleep();
            } if(getDialogues().inDialogue()) {
                if(getDialogues().selectOption(1)) {
                    new ConditionalSleep(30000) {
                        @Override
                        public boolean condition() {
                            return !getInventory().contains("Raw anchovies");
                        }
                    }.sleep();
                }
            }
        //If player is not in the cooking area, walk to the area
        } else if(!cookingArea.contains(myPlayer())) {
            walking.webWalk(cookingArea);
        }
    }

    // @NAME: Bank deposit cooked anchovies function
    // @DESCRIPTION: Deposits the cooked anchovies into the bank
    public void bankDepositAnchovies() {
        NPC banker = getNpcs().closest("Banker");

        //If player is in the bank area, deposit all whats in the inventory
        if(bankArea.contains(myPlayer())) {
            if(banker.interact("Bank")) {
                new ConditionalSleep(25000) {
                    @Override
                    public boolean condition() {
                        return bank.isOpen() == true;
                    }
                }.sleep();
            } if(bank.isOpen()) {
                if(bank.depositAll()) {
                    new ConditionalSleep(25000) {
                        @Override
                        public boolean condition() {
                            return bank.depositAll();
                        }
                    }.sleep();
                } if(bank.close()) {
                    new ConditionalSleep(25000) {
                        @Override
                        public boolean condition() {
                            return bank.close();
                        }
                    }.sleep();
                }
            }
        //If player is not in the bank area, walk to the area
        } else if(!bankArea.contains(myPlayer())) {
            walking.webWalk(bankArea);
        }
    }

    ////////////////////////////////////////////////////////////////
    //                        OSBot Overides                     //
    //////////////////////////////////////////////////////////////

    // @NAME: onStart function
    // @DESCRIPTION: Script gets executed once everytime the bot starts
    @Override
    public void onStart() {
        isFishing = true;
        log("'Snoots' Anchovy fishing & cooking' has been started.");
    }

    // @NAME: onLoop function
    // @DESCRIPTION: Script loops whatever is inside this method
    @Override
    public int onLoop() throws InterruptedException {
        if(!getInventory().isFull() && getInventory().contains("Small fishing net") && isFishing == true) {
            fishingFunction();
        } else if(getInventory().isFull() && isFishing == true) {
            bankDepositFish();
        } else if(!getInventory().isFull() && !getInventory().contains("Small fishing net") && isFishing == true) {
            bankWithdrawSmallFishingNet();
        } else if(!getInventory().contains("Raw anchovies") && !getInventory().contains("Anchovies") && isFishing == false) {
            bankWithdrawAnchovies();
        } else if(getInventory().contains("Raw anchovies") && isFishing == false) {
            cookAnchovies();
        } else if(getInventory().contains("Anchovies") && !getInventory().contains("Raw anchovies") && isFishing == false) {
            bankDepositAnchovies();
        } else if(!bank.contains("Raw anchovies") && isFishing == false) {
            log("No raw anchovies to withdraw!");
        }

        return 1000;
    }

    // @NAME: onExit function
    // @DESCRIPTION: Script gets executed once everytime the bot stops
    @Override
    public void onExit() {
        log("'Snoots' Anchovy fishing & cooking' has stopped.");
    }
}
