package Theresa.power.buff;

import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class EndPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:EndPower";

    public EndPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        setAmountDescription();
    }
}
