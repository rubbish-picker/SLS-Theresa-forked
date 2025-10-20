package Theresa.power.buff;

import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class SilkPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:SilkPower";

    public SilkPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        setAmountDescription();
    }
}
