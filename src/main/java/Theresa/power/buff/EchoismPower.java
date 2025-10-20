package Theresa.power.buff;

import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class EchoismPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:EchoismPower";

    public EchoismPower(AbstractCreature owner, int amount) {
        super(POWER_ID,owner,amount);
        setAmountDescription();
    }

    @Override
    public void onSpecificTrigger() {
        this.flash();
    }
}
