package Theresa.power.buff;

import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class PastDustPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:PastDustPower";

    public PastDustPower(AbstractCreature owner, int amount) {
        super(POWER_ID,owner,amount);
        setAmountDescription();
    }

    @Override
    public void triggerOnBecomeDust(AbstractCard c) {
        this.flashWithoutSound();
        addToBot(new GainBlockAction(this.owner, this.amount));
    }
}
