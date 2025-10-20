package Theresa.power.buff;

import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class YoreLingerPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:YoreLingerPower";

    public YoreLingerPower(AbstractCreature owner, int amount) {
        super(POWER_ID,owner,amount);
        setAmountDescription();
    }

}
